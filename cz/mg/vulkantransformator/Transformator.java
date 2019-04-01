package cz.mg.vulkantransformator;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.converters.Converter;
import cz.mg.vulkantransformator.entities.*;
import cz.mg.vulkantransformator.entities.c.*;
import cz.mg.vulkantransformator.entities.vk.*;
import cz.mg.vulkantransformator.entities.vulkan.*;
import cz.mg.vulkantransformator.translators.*;
import cz.mg.vulkantransformator.parsers.*;
import cz.mg.vulkantransformator.utilities.FileUtilities;


public class Transformator {
    private final ChainList<EntityTriplet> entities = new CachedChainList<>();
    private final String vulkanCoreFilePath;
    private final String outputDitectoryPath;
    private final boolean[] enabled = new boolean[EntityType.values().length];

    private final Parser[] parsers = new Parser[]{
            new TypeParser(),
            new EnumParser(),
            new FlagParser(),
            new FlagBitsParser(),
            new HandleParser(),
            new StructureParser(),
            new UnionParser(),
            new InfoParser(),
            new FunctionParser(),
            new CallbackParser(),
            new DefineParser()
    };

    public Transformator(String vulkanCoreFilePath, String outputDitectoryPath) {
        this.vulkanCoreFilePath = vulkanCoreFilePath;
        this.outputDitectoryPath = outputDitectoryPath;
        for(int i = 0; i < enabled.length; i++) enabled[i] = true;
    }

    public ChainList<EntityTriplet> getEntities() {
        return entities;
    }

    public void setEnabled(EntityType entityType, boolean enabled){
        this.enabled[entityType.ordinal()] = enabled;
    }

    public void run() {
        entities.clear();
        FileUtilities.checkIfExists(outputDitectoryPath);
        clearDirectories();
        createDirectories();
        addSystemTypeEntities();
        addTypeEntities();
        addMiscEntities();
        parseEntities();
        saveEntities();
        saveVulkan();
    }

    private void addSystemTypeEntities(){
        for(String systemType : Configuration.SYSTEM_TYPES){
            entities.addLast(convertEntity(new CSystemType(systemType)));
        }
    }

    private void addTypeEntities(){
        entities.addLast(convertEntity(new CType("int32_t", "VkEnum")));
        entities.addLast(convertEntity(new CType("int32_t", "VkFlagBits")));
    }

    private void addMiscEntities(){
        for(String[] miscNames : Configuration.MISC_NAMES){
            entities.addLast(new MiscTriplet(
                    miscNames[0] != null ? new CMisc(miscNames[0]) : null,
                    miscNames[1] != null ? new VkMisc(miscNames[1]) : null,
                    miscNames[2] != null ? new VulkanMisc(miscNames[2]) : null
            ));
        }
    }

    private void parseEntities(){
        ChainList<String> lines = getLines();
        for(int i = 0; i < lines.count(); i++){
            if(lines.get(i).startsWith("    ")) continue;
            for(Parser parser : parsers){
                try {
                    CEntity centity = parser.parse(lines, i);
                    if(centity != null){
                        entities.addLast(convertEntity(centity));
                        break;
                    }
                } catch(RuntimeException e){
                    throw new RuntimeException("At line " + (i+1) + ": " + lines.get(i), e);
                }
            }
        }
    }

    private EntityTriplet convertEntity(CEntity centity){
        Converter converter = Converter.create(centity.getEntityType());
        CEntity c = centity;
        VkEntity vk = converter.convert(c);
        VulkanEntity vulkan = converter.convert(vk);
        return EntityTriplet.create(c, vk, vulkan);
    }

    private void saveEntities(){
        for(EntityTriplet entity : entities){
            if(enabled[entity.getEntityType().ordinal()]){
                try {
                    saveEntity(entity);
                } catch(RuntimeException e){
                    throw new RuntimeException("Could not save entity " + entity.getEntityName(), e);
                }
            }
        }
    }

    private void saveEntity(EntityTriplet entity){
        String base = outputDitectoryPath;
        for(EntityGroup group : EntityGroup.values()){
            if(entity.get(group) != null){
                String relativePath = Configuration.getPath(group);
                String filename = entity.get(group).getName() + getFileExtension(group);
                String code = Translator.translate(group, entity);
                FileUtilities.saveFile(base + "/" + relativePath + "/" + filename, code);
            }
        }
    }

    private void saveVulkan(){
        try {
            for(EntityGroup group : EntityGroup.values()){
                String base = outputDitectoryPath;
                String relativePath = Configuration.getPath(group);
                String filename = "Vk" + getFileExtension(group);
                String code = VulkanTranslator.translate(group, entities);
                if(code != null) FileUtilities.saveFile(base + "/" + relativePath + "/" + filename, code);
            }
        } catch(RuntimeException e){
            throw new RuntimeException("Could not save vulkan.", e);
        }
    }

    private String getFileExtension(EntityGroup group){
        switch(group){
            case C: return ".c";
            case VK: return ".java";
            case VULKAN: return ".java";
            default: throw new UnsupportedOperationException("" + group);
        }
    }

    private void clearDirectories(){
        String base = outputDitectoryPath;
        for(String rootDirectory : Configuration.ROOT_DIRECTORIES) FileUtilities.deleteDirectory(base + "/" + rootDirectory);
    }

    private void createDirectories(){
        String base = outputDitectoryPath;
        for(EntityGroup group : EntityGroup.values()) FileUtilities.createDirectory(base + "/" + Configuration.getPath(group));
    }

    private ChainList<String> getLines(){
        if(vulkanCoreFilePath == null || vulkanCoreFilePath.length() == 0) return FileUtilities.loadFileLines(Transformator.class, "vulkan_core.h");
        return FileUtilities.loadFileLines(vulkanCoreFilePath);
    }

    public String[] test(String name) {
        ChainList<String> lines = getLines();
        for(int i = 0; i < lines.count(); i++){
            if(lines.get(i).contains(name)){
                for(Parser parser : parsers){
                    CEntity centity = parser.parse(lines, i);
                    if(centity != null){
                        if(centity.getName().equals(name)){
                            EntityTriplet entity = convertEntity(centity);
                            return new String[]{
                                    Translator.translate(EntityGroup.C, entity),
                                    Translator.translate(EntityGroup.VK, entity),
                                    Translator.translate(EntityGroup.VULKAN, entity)
                            };
                        }
                    }
                }
            }
        }
        return new String[]{ null, null, null };
    }
}

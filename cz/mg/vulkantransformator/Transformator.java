package cz.mg.vulkantransformator;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.converters.Converter;
import cz.mg.vulkantransformator.entities.Entity;
import cz.mg.vulkantransformator.entities.c.*;
import cz.mg.vulkantransformator.entities.vk.*;
import cz.mg.vulkantransformator.translators.*;
import cz.mg.vulkantransformator.parsers.*;
import cz.mg.vulkantransformator.utilities.FileUtilities;
import cz.mg.collections.text.Text;


public class Transformator {
    private final ChainList<VkEntity> entities = new CachedChainList<>();
    private final Text vulkanCoreFilePath;
    private final Text outputDitectoryPath;
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

    public Transformator(Text vulkanCoreFilePath, Text outputDitectoryPath) {
        this.vulkanCoreFilePath = vulkanCoreFilePath;
        this.outputDitectoryPath = outputDitectoryPath;
        for(int i = 0; i < enabled.length; i++) enabled[i] = true;
    }

    public ChainList<VkEntity> getEntities() {
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
        addAditionalTypeEntities();
        addMiscEntities();
        parseEntities();
        saveEntities();
        saveVulkan();
    }

    private void addSystemTypeEntities(){
        for(Text systemType : Configuration.SYSTEM_TYPES){
            entities.addLast(convertEntity(new CSystemType(systemType)));
        }
    }

    private void addAditionalTypeEntities(){
        for(Text[] aditionalType : Configuration.ADITIONAL_TYPES){
            entities.addLast(convertEntity(new CType(aditionalType[0], aditionalType[1])));
        }
    }

    private void addMiscEntities(){
        for(Text[] miscNames : Configuration.MISC_NAMES){
            entities.addLast(new VkMisc(new CMisc(miscNames[0]), miscNames[1]));
        }
    }

    private void parseEntities(){
        ChainList<Text> lines = getLines();
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

    private VkEntity convertEntity(CEntity c){
        return Converter.convertEntity(c);
    }

    private void saveEntities(){
        for(VkEntity entity : entities){
            if(enabled[entity.getEntityType().ordinal()]){
                try {
                    saveEntity(entity);
                } catch(RuntimeException e){
                    throw new RuntimeException("Could not save entity " + entity.getName(), e);
                }
            }
        }
    }

    private void saveEntity(VkEntity entity){
        Text base = outputDitectoryPath;
        for(EntityGroup group : EntityGroup.values()){
            if(get(group, entity).getName() != null){
                Text relativePath = Configuration.getPath(group);
                Text filename = get(group, entity).getName().append(getFileExtension(group));
                Text code = Translator.translate(group, entities, entity);
                FileUtilities.saveFile(base + "/" + relativePath + "/" + filename, code);
            }
        }
    }

    private static Entity get(EntityGroup group, VkEntity e){
        switch (group){
            case C: return e.getC();
            case VK: return e;
            default: throw new UnsupportedOperationException("" + group);
        }
    }

    private void saveVulkan(){
        try {
            for(EntityGroup group : EntityGroup.values()){
                Text base = outputDitectoryPath;
                Text relativePath = Configuration.getPath(group);
                Text filename = group.getName().lowerCase().upperFirst().append(getFileExtension(group));
                Text code = CoreTranslator.translate(group, entities);
                if(code != null) FileUtilities.saveFile(base + "/" + relativePath + "/" + filename, code);
            }
        } catch(RuntimeException e){
            throw new RuntimeException("Could not save vulkan.", e);
        }
    }

    private Text getFileExtension(EntityGroup group){
        switch(group){
            case C: return new Text(".c");
            case VK: return new Text(".java");
            default: throw new UnsupportedOperationException("" + group);
        }
    }

    private void clearDirectories(){
        Text base = outputDitectoryPath;
        for(Text rootDirectory : Configuration.ROOT_DIRECTORIES) FileUtilities.deleteDirectory(base + "/" + rootDirectory);
    }

    private void createDirectories(){
        Text base = outputDitectoryPath;
        for(EntityGroup group : EntityGroup.values()) FileUtilities.createDirectory(base + "/" + Configuration.getPath(group));
    }

    private ChainList<Text> getLines(){
        if(vulkanCoreFilePath == null || vulkanCoreFilePath.count() == 0) return FileUtilities.loadFileLines(Transformator.class, "vulkan_core.h");
        return FileUtilities.loadFileLines(vulkanCoreFilePath);
    }

    public Text[] test(Text name) {
        ChainList<Text> lines = getLines();
        for(int i = 0; i < lines.count(); i++){
            if(lines.get(i).contains(name)){
                for(Parser parser : parsers){
                    CEntity centity = parser.parse(lines, i);
                    if(centity != null){
                        if(centity.getName().equals(name)){
                            VkEntity entity = convertEntity(centity);
                            return new Text[]{
                                    Translator.translate(EntityGroup.C, entities, entity),
                                    Translator.translate(EntityGroup.VK, entities, entity)
                            };
                        }
                    }
                }
            }
        }
        return new Text[]{ null, null, null };
    }
}

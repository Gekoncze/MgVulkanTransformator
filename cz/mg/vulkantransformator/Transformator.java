package cz.mg.vulkantransformator;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.converters.Converter;
import cz.mg.vulkantransformator.entities.Entity;
import cz.mg.vulkantransformator.entities.c.*;
import cz.mg.vulkantransformator.entities.vk.*;
import cz.mg.vulkantransformator.fixes.SkipConditionalEntitiesFix;
import cz.mg.vulkantransformator.fixes.UnknownTypeFix;
import cz.mg.vulkantransformator.translators.*;
import cz.mg.vulkantransformator.parsers.*;
import cz.mg.vulkantransformator.utilities.FileUtilities;
import cz.mg.collections.text.Text;


public class Transformator {
    private final ChainList<CEntity> centities = new CachedChainList<>();
    private final ChainList<VkEntity> entities = new CachedChainList<>();
    private final Version version;
    private final Text inputPath;
    private final Text outputPath;

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

    public Transformator(Version version, Text inputPath, Text outputPath) {
        this.version = version;
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        Configuration.version = version;
    }

    public ChainList<VkEntity> getEntities() {
        return entities;
    }

    public void run() {
        entities.clear();
        FileUtilities.checkIfExists(outputPath);
        clearDirectories();
        createDirectories();
        addSystemTypeEntities();
        addAditionalTypeEntities();
        addMiscEntities();
        parseEntities();
        convertEntities();
        fixDefines();
        fixUnknownTypes();
        saveEntities();
        saveCore();
    }

    private void addSystemTypeEntities(){
        for(Text systemType : Configuration.SYSTEM_TYPES){
            centities.addLast(new CSystemType(systemType));
        }
    }

    private void addAditionalTypeEntities(){
        for(Text[] aditionalType : Configuration.ADITIONAL_TYPES){
            centities.addLast(new CType(aditionalType[0], aditionalType[1]));
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
                    if(centity != null) centities.addLast(centity);
                } catch(RuntimeException e){
                    throw new RuntimeException("At line " + (i+1) + ": " + lines.get(i), e);
                }
            }
        }
    }

    private void convertEntities(){
        for(CEntity centity : centities){
            try {
                entities.addLast(convertEntity(centity));
            } catch(RuntimeException e){
                throw new RuntimeException("Could not convert entity " + centity.getName() + ".", e);
            }
        }
    }

    private void fixDefines(){
        for(VkEntity entity : entities){
            if(entity instanceof VkDefine){
                VkDefine define = (VkDefine) entity;
                for(Text[] d : Configuration.DEFINE_VALUES){
                    if(define.getName().equals(d[0])){
                        define.setValue(d[1]);
                    }
                }
            }
        }
    }

    private void fixUnknownTypes(){
        UnknownTypeFix fix = new UnknownTypeFix(entities);
        fix.fix();
    }

    private VkEntity convertEntity(CEntity c){
        return Converter.convertEntity(centities, c);
    }

    private void saveEntities(){
        for(VkEntity entity : entities){
            try {
                saveEntity(entity);
            } catch(RuntimeException e){
                throw new RuntimeException("Could not save entity " + entity.getName(), e);
            }
        }
    }

    private void saveEntity(VkEntity entity){
        Text base = outputPath;
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

    private void saveCore(){
        try {
            for(EntityGroup group : EntityGroup.values()){
                Text base = outputPath;
                Text relativePath = Configuration.getPath(group);
                Text filename = group.getName().lowerCase().upperFirst().append(getFileExtension(group));
                Text code = CoreTranslator.translate(group, entities);
                if(code != null) FileUtilities.saveFile(base + "/" + relativePath + "/" + filename, code);
            }
        } catch(RuntimeException e){
            throw new RuntimeException("Could not save vulkan core.", e);
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
        Text base = outputPath;
        for(Text rootDirectory : Configuration.ROOT_DIRECTORIES) FileUtilities.deleteDirectory(base + "/" + rootDirectory);
    }

    private void createDirectories(){
        Text base = outputPath;
        for(EntityGroup group : EntityGroup.values()) FileUtilities.createDirectory(base + "/" + Configuration.getPath(group));
    }

    private ChainList<Text> getLines(){
        return new SkipConditionalEntitiesFix().fix(loadLines());
    }

    private ChainList<Text> loadLines(){
        switch(version){
            case VERSION_1_0: return FileUtilities.loadFileLines(Transformator.class, "vulkan_1_0.h");
            case VERSION_1_1: return FileUtilities.loadFileLines(Transformator.class, "vulkan_1_1.h");
            case VERSION_OTHER: return FileUtilities.loadFileLines(inputPath);
            default: throw new UnsupportedOperationException("Unsupported vulkan version.");
        }
    }

    public enum Version {
        VERSION_1_0,
        VERSION_1_1,
        VERSION_OTHER
    }
}

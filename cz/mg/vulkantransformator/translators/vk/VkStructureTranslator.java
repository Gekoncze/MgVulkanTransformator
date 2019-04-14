package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.converters.utilities.TypenameConverter;
import cz.mg.vulkantransformator.entities.c.CType;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.entities.vk.VkStructure;
import cz.mg.vulkantransformator.entities.vk.VkVariable;
import cz.mg.vulkantransformator.translators.vk.templates.TemplatesVk;


public class VkStructureTranslator extends VkTranslator {
    private static final Text propertyTemplate = TemplatesVk.load("parts/Property");
    private static final Text ppTemplate = new Text("private VkObject %VKPROPERTYNAME% = null;");
    private static final Text setPPTemplate = new Text("this.%VKPROPERTYNAME% = %VKPROPERTYNAME%;");

    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity e, Text template) {
        VkStructure vk = (VkStructure) e;
        return super.genCode(entities, e, template
                .replace("%PROPERTIES%", genPropertiesVk(vk.getFields()))
        );
    }

    public static Text genPropertiesVk(ChainList<VkVariable> fields){
        ChainList<Text> properties = new CachedChainList<>();
        for(VkVariable field : fields) properties.addLast(genPropertyVk(field));
        return properties.toText("\n");
    }

    public static Text genPropertyVk(VkVariable field){
        return propertyTemplate
                .replace("%PP%", genPP(field))
                .replace("%SETPP%", genSetPP(field))
                .replace("%VKPROPERTYTYPE%", field.getTypename())
                .replace("%VKPROPERTYNAME%", field.getName())
                .replace("%VKPROPERTYNAMEC%", field.getName().upperFirst())
                .replace("%ARGUMENT%", VkFunctionTranslator.genArgument(field));
    }

    public static Text genPP(VkVariable field){
        return field.getPointerCount() > 0 ? ppTemplate : new Text("");
    }

    public static Text genSetPP(VkVariable field){
        return field.getPointerCount() > 0 ? setPPTemplate : new Text("");
    }




    public static Text getJavaDataType(ChainList<VkEntity> entities, VkVariable field){
        VkEntity fieldTypeEntity = findEntity(entities, field.getTypename());
        if(fieldTypeEntity == null) return null;
        if(field.getC().getDatatype().equals("char*")) return new Text("String");
        if(field.getTypename().endsWith(".Array")) return null;
        if(field.getTypename().endsWith(".Pointer")) return null;
        if(field.getC().getArrayCount() != null) return null;
        if(field.getC().getPointerCount() > 0) return null;
        switch (fieldTypeEntity.getEntityType()){
            case ENUM: return new Text("int");
            case FLAGS: return new Text("int");
            case FLAG_BITS: return new Text("int");
            case TYPE: return TypenameConverter.cTypenameToJava((((CType)fieldTypeEntity.getC()).getType()));
            case SYSTEM_TYPE: return TypenameConverter.cTypenameToJava(fieldTypeEntity.getC().getName());
            default: return null;
        }
    }

    public static VkEntity findEntity(ChainList<VkEntity> entities, Text typename){
        for(VkEntity entity : entities) if(entity.getName() != null) if(entity.getName().equals(typename)) return entity;
        return null;
    }
}

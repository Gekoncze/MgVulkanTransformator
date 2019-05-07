package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.entities.vk.VkStructure;
import cz.mg.vulkantransformator.entities.vk.VkVariable;
import cz.mg.vulkantransformator.translators.vk.templates.TemplatesVk;


public class VkStructureTranslator extends VkTranslator {
    private static final Text propertyTemplate = TemplatesVk.load("parts/Property");
    private static final Text ppTemplate = new Text("private VkObject %PROPERTYNAME% = null;");
    private static final Text setPPTemplate = new Text("this.%PROPERTYNAME% = %PROPERTYNAME%;");
    private static final Text simplifiedProperty = TemplatesVk.load("parts/PropertySimplified");
    private static final Text simplifiedPropertyArray = TemplatesVk.load("parts/PropertySimplifiedArray");
    private static final Text simplifiedPropertyString = TemplatesVk.load("parts/PropertySimplifiedString");

    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity e, Text template) {
        VkStructure vk = (VkStructure) e;
        return super.genCode(entities, e, template
                .replace("%PROPERTIES%", genPropertiesVk(entities, vk.getFields()))
        );
    }

    public static Text genPropertiesVk(ChainList<VkEntity> entities, ChainList<VkVariable> fields){
        ChainList<Text> properties = new CachedChainList<>();
        VkVariable lastField = null;
        for(VkVariable field : fields){
            properties.addLast(genPropertyVk(entities, field, lastField));
            lastField = field;
        }
        return properties.toText("\n");
    }

    public static Text genPropertyVk(ChainList<VkEntity> entities, VkVariable field, VkVariable lastField){
        return propertyTemplate
                .replace("%SIMPLIFIED%", genPropertySimplified(entities, field, lastField))
                .replace("%PP%", genPP(field))
                .replace("%SETPP%", genSetPP(field))
                .replace("%PROPERTYTYPE%", field.getTypename())
                .replace("%PROPERTYNAME%", field.getName())
                .replace("%PROPERTYNAMEC%", field.getName().upperFirst())
                .replace("%ARGUMENT%", VkFunctionTranslator.genArgument(field));
    }

    public static Text genPropertySimplified(ChainList<VkEntity> entities, VkVariable field, VkVariable lastField){
        Text java = field.getSimplifiedJavaType();
        if(field.getC().isString()){
            return simplifiedPropertyString;
        } else if(java != null) {
            return simplifiedProperty.replace("%JAVATYPE%", java);
        } else if(lastField != null && lastField.getTypename().equals("VkUInt32") && lastField.getName().endsWith("Count") && (field.getPointerCount() == 1 || field.getArrayCount() != null)){
            return simplifiedPropertyArray
                    .replace("%PROPERTYNAMECC%", lastField.getName().upperFirst());
        }
        return new Text("");
    }

    public static Text genPP(VkVariable field){
        return field.getPointerCount() > 0 ? ppTemplate : new Text("");
    }

    public static Text genSetPP(VkVariable field){
        return field.getPointerCount() > 0 ? setPPTemplate : new Text("");
    }
}

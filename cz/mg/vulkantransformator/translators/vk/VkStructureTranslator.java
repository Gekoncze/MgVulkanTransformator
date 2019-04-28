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
    private static final Text ppTemplate = new Text("private VkObject %VKPROPERTYNAME% = null;");
    private static final Text setPPTemplate = new Text("this.%VKPROPERTYNAME% = %VKPROPERTYNAME%;");
    private static final Text simplifiedProperty = TemplatesVk.load("parts/PropertySimplified");
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
        for(VkVariable field : fields) properties.addLast(genPropertyVk(entities, field));
        return properties.toText("\n");
    }

    public static Text genPropertyVk(ChainList<VkEntity> entities, VkVariable field){
        return propertyTemplate
                .replace("%SIMPLIFIED%", genPropertySimplified(entities, field))
                .replace("%PP%", genPP(field))
                .replace("%SETPP%", genSetPP(field))
                .replace("%VKPROPERTYTYPE%", field.getTypename())
                .replace("%VKPROPERTYNAME%", field.getName())
                .replace("%VKPROPERTYNAMEC%", field.getName().upperFirst())
                .replace("%ARGUMENT%", VkFunctionTranslator.genArgument(field));
    }

    public static Text genPropertySimplified(ChainList<VkEntity> entities, VkVariable field){
        if(field.getC().isString()){
            return simplifiedPropertyString;
        } else {
            Text java = field.getSimplifiedJavaType();
            if(java == null) return new Text("");
            return simplifiedProperty.replace("%JAVATYPE%", java);
        }
    }

    public static Text genPP(VkVariable field){
        return field.getPointerCount() > 0 ? ppTemplate : new Text("");
    }

    public static Text genSetPP(VkVariable field){
        return field.getPointerCount() > 0 ? setPPTemplate : new Text("");
    }
}

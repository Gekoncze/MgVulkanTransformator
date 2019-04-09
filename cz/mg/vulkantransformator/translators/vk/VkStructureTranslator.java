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
    private static final Text setTemplateVk =   new Text("        set%VKPROPERTYNAMEC%(%VKPROPERTYNAME%);");
    private static final Text ppTemplate = new Text("private VkObject %VKPROPERTYNAME% = null;");
    private static final Text setPPTemplate = new Text("this.%VKPROPERTYNAME% = %VKPROPERTYNAME%;");

    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity e, Text template) {
        VkStructure vk = (VkStructure) e;
        return super.genCode(entities, e, template
                .replace("%PARAMETERS%", genParameters(vk.getFields()))
                .replace("%SETS%", genSets(vk.getFields()))
                .replace("%PROPERTIES%", genPropertiesVk(vk.getFields()))
        );
    }

    public static Text genParameters(ChainList<VkVariable> fields){
        ChainList<Text> parameters = new CachedChainList<>();
        for(VkVariable field : fields) parameters.addLast(genParameter(field));
        return parameters.toText(", ");
    }

    public static Text genParameter(VkVariable field){
        return field.getTypename().append(" ").append(field.getName());
    }

    public static Text genSets(ChainList<VkVariable> fields){
        ChainList<Text> sets = new CachedChainList<>();
        for(VkVariable field : fields) sets.addLast(genSet(field));
        return sets.toText("\n");
    }

    public static Text genSet(VkVariable field){
        return setTemplateVk
                .replace("%VKPROPERTYNAME%", field.getName())
                .replace("%VKPROPERTYNAMEC%", field.getName().upperFirst());
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
}

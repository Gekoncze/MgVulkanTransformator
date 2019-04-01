package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.StructureTriplet;
import cz.mg.vulkantransformator.entities.vk.VkVariable;
import cz.mg.vulkantransformator.translators.vk.templates.TemplatesVk;
import cz.mg.vulkantransformator.utilities.StringUtilities;


public class VkStructureTranslator extends VkTranslator {
    private static final String propertyTemplate = TemplatesVk.load("Property");
    private static final String setTemplateVk =   "        set%VKPROPERTYNAMEC%(%VKPROPERTYNAME%);";
    private static final String ppTemplate = "private VkObject %VKPROPERTYNAME% = null;";
    private static final String setPPTemplate = "this.%VKPROPERTYNAME% = %VKPROPERTYNAME%;";

    @Override
    public String genCode(EntityTriplet e, String template) {
        StructureTriplet entity = (StructureTriplet) e;
        return super.genCode(e, template
                .replace("%PARAMETERS%", genParameters(entity.getVk().getFields()))
                .replace("%SETS%", genSets(entity.getVk().getFields()))
                .replace("%PROPERTIES%", genPropertiesVk(entity.getVk().getFields()))
        );
    }

    public static String genParameters(ChainList<VkVariable> fields){
        ChainList<String> parameters = new CachedChainList<>();
        for(VkVariable field : fields) parameters.addLast(genParameter(field));
        return parameters.toString(", ");
    }

    public static String genParameter(VkVariable field){
        return field.getTypename() + " " + field.getName();
    }

    public static String genSets(ChainList<VkVariable> fields){
        ChainList<String> sets = new CachedChainList<>();
        for(VkVariable field : fields) sets.addLast(genSet(field));
        return sets.toString("\n");
    }

    public static String genSet(VkVariable field){
        return setTemplateVk
                .replace("%VKPROPERTYNAME%", field.getName())
                .replace("%VKPROPERTYNAMEC%", StringUtilities.capitalFirst(field.getName()));
    }

    public static String genPropertiesVk(ChainList<VkVariable> fields){
        ChainList<String> properties = new CachedChainList<>();
        for(VkVariable field : fields) properties.addLast(genPropertyVk(field));
        return properties.toString("\n");
    }

    public static String genPropertyVk(VkVariable field){
        return propertyTemplate
                .replace("%PP%", genPP(field))
                .replace("%SETPP%", genSetPP(field))
                .replace("%VKPROPERTYTYPE%", field.getTypename())
                .replace("%VKPROPERTYNAME%", field.getName())
                .replace("%VKPROPERTYNAMEC%", StringUtilities.capitalFirst(field.getName()))
                .replace("%ARGUMENT%", VkFunctionTranslator.genArgument(field));
    }

    public static String genPP(VkVariable field){
        return field.getPointerCount() > 0 ? ppTemplate : "";
    }

    public static String genSetPP(VkVariable field){
        return field.getPointerCount() > 0 ? setPPTemplate : "";
    }
}

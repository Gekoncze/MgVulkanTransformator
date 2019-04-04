package cz.mg.vulkantransformator.translators.vulkan;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.StructureTriplet;
import cz.mg.vulkantransformator.entities.vulkan.VulkanVariable;
import cz.mg.vulkantransformator.translators.vulkan.templates.TemplatesVulkan;
import cz.mg.vulkantransformator.utilities.StringUtilities;


public class VulkanStructureTranslator extends VulkanTranslator {
    private static final String propertyTemplate = TemplatesVulkan.load("parts/Property");

    @Override
    public String genCode(EntityTriplet e, String template) {
        StructureTriplet entity = (StructureTriplet) e;
        return super.genCode(e, template
                .replace("%PARAMETERS%", genParameters(entity.getVulkan().getFields()))
                .replace("%ARGUMENTS%", genArguments(entity.getVulkan().getFields()))
                .replace("%PROPERTIES%", genProperties(entity.getVulkan().getFields()))
        );
    }

    public static String genParameters(ChainList<VulkanVariable> fields){
        ChainList<String> parameters = new CachedChainList<>();
        for(VulkanVariable field : fields) parameters.addLast(genParameter(field));
        return parameters.toString(", ");
    }

    public static String genParameter(VulkanVariable field){
        return field.getTypename() + " " + field.getName();
    }

    public static String genArguments(ChainList<VulkanVariable> fields){
        ChainList<String> arguments = new CachedChainList<>();
        for(VulkanVariable field : fields) arguments.addLast(genArgument(field));
        return arguments.toString(", ");
    }

    public static String genArgument(VulkanVariable field){
        return field.getName() + ".getVk()";
    }

    public static String genProperties(ChainList<VulkanVariable> fields) {
        ChainList<String> properties = new CachedChainList<>();
        for(VulkanVariable field : fields) properties.addLast(genProperty(field));
        return properties.toString("\n");
    }

    public static String genProperty(VulkanVariable field) {
        return propertyTemplate
                .replace("%PROPERTYTYPE%", field.getTypename())
                .replace("%PROPERTYNAME%", field.getName())
                .replace("%PROPERTYNAMEC%", StringUtilities.capitalFirst(field.getName()));
    }
}

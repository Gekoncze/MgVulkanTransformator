package cz.mg.vulkantransformator.translators.vulkan;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.UnionTriplet;
import cz.mg.vulkantransformator.entities.vulkan.VulkanVariable;
import cz.mg.vulkantransformator.translators.vulkan.templates.TemplatesVulkan;


public class VulkanUnionTranslator extends VulkanTranslator {
    private static final String constructorTemplate = TemplatesVulkan.load("parts/UnionConstructor");

    @Override
    public String genCode(ChainList<EntityTriplet> entities, EntityTriplet e, String template) {
        UnionTriplet entity = (UnionTriplet) e;
        return super.genCode(entities, e, template
                .replace("%PROPERTIES%", VulkanStructureTranslator.genProperties(entity.getVulkan().getFields()))
                .replace("%CONSTRUCTORS%", genConstructors(entity.getVulkan().getFields())));
    }

    public static String genConstructors(ChainList<VulkanVariable> fields) {
        ChainList<String> constructors = new CachedChainList<>();
        for(VulkanVariable field : fields) constructors.addLast(genConstructor(field));
        return constructors.toString("\n");
    }

    public static String genConstructor(VulkanVariable field) {
        return constructorTemplate
                .replace("%PARAMETER%", VulkanStructureTranslator.genParameter(field))
                .replace("%ARGUMENT%", VulkanStructureTranslator.genArgument(field));
    }
}

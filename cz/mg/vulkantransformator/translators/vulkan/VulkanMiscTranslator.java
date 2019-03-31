package cz.mg.vulkantransformator.translators.vulkan;

import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.translators.vulkan.templates.TemplatesVulkan;


public class VulkanMiscTranslator extends VulkanTranslator {
    @Override
    public String genCode(EntityTriplet e, String template) {
        if(e.getVulkan() == null) return null;
        String header = template;
        String filename = "misc/" + e.getVulkan().getName().replaceFirst("Vulkan", "");
        template = header + TemplatesVulkan.load(filename);
        return super.genCode(e, template);
    }
}

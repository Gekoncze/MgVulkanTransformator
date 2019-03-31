package cz.mg.vulkantransformator.translators.vk;

import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.translators.vk.templates.TemplatesVk;


public class VkMiscTranslator extends VkTranslator {
    @Override
    public String genCode(EntityTriplet e, String template) {
        if(e.getVk() == null) return null;
        String header = template;
        String filename = "misc/" + e.getVk().getName().replaceFirst("Vk", "");
        template = header + TemplatesVk.load(filename);
        return super.genCode(e, template);
    }
}

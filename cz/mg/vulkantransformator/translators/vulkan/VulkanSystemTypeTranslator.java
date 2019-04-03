package cz.mg.vulkantransformator.translators.vulkan;

import cz.mg.vulkantransformator.converters.TypenameConverter;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.SystemTypeTriplet;


public class VulkanSystemTypeTranslator extends VulkanTranslator {
    @Override
    public String genCode(EntityTriplet e, String template) {
        SystemTypeTriplet entity = (SystemTypeTriplet) e;
        return super.genCode(e, template
                .replace("%JAVATYPE%", TypenameConverter.cTypenameToJava(entity.getC().getName()))
        );
    }
}

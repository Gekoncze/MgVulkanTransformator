package cz.mg.vulkantransformator.translators.vk;

import cz.mg.vulkantransformator.converters.TypenameConverter;
import cz.mg.vulkantransformator.entities.EntityTriplet;


public class VkSystemTypeTranslator extends VkTranslator {
    @Override
    public String genCode(EntityTriplet e, String template) {
        return super.genCode(e, template
                .replace("%JAVATYPE%", TypenameConverter.cTypenameToJava(e.getC().getName()))
        );
    }
}

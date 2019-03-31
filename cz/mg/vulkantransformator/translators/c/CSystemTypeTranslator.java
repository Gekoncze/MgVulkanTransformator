package cz.mg.vulkantransformator.translators.c;

import cz.mg.vulkantransformator.converters.TypenameConverter;
import cz.mg.vulkantransformator.entities.EntityTriplet;


public class CSystemTypeTranslator extends CTranslator {
    @Override
    public String genCode(EntityTriplet e, String template) {
        return super.genCode(e, template
                .replace("%JNITYPE%", TypenameConverter.cTypenameToJni(e.getC().getName()))
        );
    }
}

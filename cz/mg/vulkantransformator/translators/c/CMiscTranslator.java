package cz.mg.vulkantransformator.translators.c;

import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.translators.c.templates.TemplatesC;


public class CMiscTranslator extends CTranslator {
    @Override
    public String genCode(EntityTriplet e, String template) {
        if(e.getC() == null) return null;
        String header = template;
        String filename = "misc/" + e.getC().getName().replaceFirst("Vk", "");
        template = header + TemplatesC.load(filename);
        return super.genCode(e, template);
    }
}

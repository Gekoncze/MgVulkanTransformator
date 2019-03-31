package cz.mg.vulkantransformator.translators.c;

import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.InfoTriplet;


public class CInfoTranslator extends CTranslator {
    @Override
    public String genCode(EntityTriplet e, String template) {
        InfoTriplet entity = (InfoTriplet) e;
        return super.genCode(e, template
                .replace("%PROPERTIES%", CStructureTranslator.genPropertiesC(entity.getC().getFields()))
        );
    }
}

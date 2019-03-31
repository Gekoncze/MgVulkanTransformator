package cz.mg.vulkantransformator.translators.c;

import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.UnionTriplet;


public class CUnionTranslator extends CTranslator {
    @Override
    public String genCode(EntityTriplet e, String template) {
        UnionTriplet entity = (UnionTriplet) e;
        return super.genCode(e, template
                .replace("%PROPERTIES%", CStructureTranslator.genPropertiesC(entity.getC().getFields()))
        );
    }
}

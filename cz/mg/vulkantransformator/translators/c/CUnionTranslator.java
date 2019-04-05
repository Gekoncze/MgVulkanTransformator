package cz.mg.vulkantransformator.translators.c;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.UnionTriplet;


public class CUnionTranslator extends CTranslator {
    @Override
    public String genCode(ChainList<EntityTriplet> entities, EntityTriplet e, String template) {
        UnionTriplet entity = (UnionTriplet) e;
        return super.genCode(entities, e, template
                .replace("%PROPERTIES%", CStructureTranslator.genPropertiesC(entity.getC().getFields()))
        );
    }
}

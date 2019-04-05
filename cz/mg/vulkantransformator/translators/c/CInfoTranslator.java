package cz.mg.vulkantransformator.translators.c;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.InfoTriplet;


public class CInfoTranslator extends CTranslator {
    @Override
    public String genCode(ChainList<EntityTriplet> entities, EntityTriplet e, String template) {
        InfoTriplet entity = (InfoTriplet) e;
        return super.genCode(entities, e, template
                .replace("%PROPERTIES%", CStructureTranslator.genPropertiesC(entity.getC().getFields()))
        );
    }
}

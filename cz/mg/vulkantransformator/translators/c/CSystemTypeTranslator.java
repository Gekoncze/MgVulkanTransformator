package cz.mg.vulkantransformator.translators.c;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.converters.TypenameConverter;
import cz.mg.vulkantransformator.entities.EntityTriplet;


public class CSystemTypeTranslator extends CTranslator {
    @Override
    public String genCode(ChainList<EntityTriplet> entities, EntityTriplet e, String template) {
        return super.genCode(entities, e, template
                .replace("%JNITYPE%", TypenameConverter.cTypenameToJni(e.getC().getName()))
        );
    }
}

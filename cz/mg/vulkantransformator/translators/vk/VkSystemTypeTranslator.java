package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.converters.TypenameConverter;
import cz.mg.vulkantransformator.entities.EntityTriplet;


public class VkSystemTypeTranslator extends VkTranslator {
    @Override
    public String genCode(ChainList<EntityTriplet> entities, EntityTriplet e, String template) {
        return super.genCode(entities, e, template
                .replace("%JAVATYPE%", TypenameConverter.cTypenameToJava(e.getC().getName()))
        );
    }
}

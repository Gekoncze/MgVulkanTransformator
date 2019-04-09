package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.converters.utilities.TypenameConverter;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.collections.text.Text;


public class VkSystemTypeTranslator extends VkTranslator {
    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity e, Text template) {
        return super.genCode(entities, e, template
                .replace("%JAVATYPE%", TypenameConverter.cTypenameToJava(e.getC().getName()))
        );
    }
}

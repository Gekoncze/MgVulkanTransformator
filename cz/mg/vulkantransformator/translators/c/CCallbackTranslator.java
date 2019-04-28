package cz.mg.vulkantransformator.translators.c;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.entities.vk.VkEntity;


public class CCallbackTranslator extends CFunctionTranslator {
    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity e, Text template) {
        return super.genCode(entities, e, template);
    }
}

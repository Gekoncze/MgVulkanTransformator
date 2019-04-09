package cz.mg.vulkantransformator.translators.c;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CCallback;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.entities.vk.VkCallback;
import cz.mg.vulkantransformator.entities.vk.VkEntity;


public class CCallbackTranslator extends CTranslator {
    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity e, Text template) {
        VkCallback vk = (VkCallback) e;
        CCallback c = (CCallback) e.getC();
        return super.genCode(entities, e, template
                .replace("%RETURN%", CFunctionTranslator.genReturn(c.getReturnType()))
                .replace("%JNIPARAMETERS%", CFunctionTranslator.genParameters(c.getParameters(), c.getReturnType()))
                .replace("%ARGUMENTS%", CFunctionTranslator.genArguments(c.getParameters()))
        );
    }
}

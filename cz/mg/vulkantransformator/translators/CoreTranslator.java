package cz.mg.vulkantransformator.translators;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.EntityGroup;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.translators.c.CCoreTranslator;
import cz.mg.vulkantransformator.translators.vk.VkCoreTranslator;


public class CoreTranslator {
    public static Text translate(EntityGroup group, ChainList<VkEntity> entities){
        switch (group){
            case C: return CCoreTranslator.translateC(entities);
            case VK: return VkCoreTranslator.translateVk(entities);
            default: throw new UnsupportedOperationException("" + group);
        }
    }

    public static Text translateSimplified(EntityGroup group, ChainList<VkEntity> entities){
        switch (group){
            case C: return CCoreTranslator.translateSimplifiedC(entities);
            case VK: return VkCoreTranslator.translateSimplifiedVk(entities);
            default: throw new UnsupportedOperationException("" + group);
        }
    }
}

package cz.mg.vulkantransformator.translators;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.EntityGroup;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.translators.c.CCoreTranslator;
import cz.mg.vulkantransformator.translators.vk.VkCoreTranslator;
import cz.mg.vulkantransformator.translators.vulkan.VulkanCoreTranslator;


public class CoreTranslator {
    public static String translate(EntityGroup group, ChainList<EntityTriplet> entities){
        switch (group){
            case C: return CCoreTranslator.translateC(entities);
            case VK: return VkCoreTranslator.translateVk(entities);
            case VULKAN: return VulkanCoreTranslator.translate(entities);
            default: throw new UnsupportedOperationException("" + group);
        }
    }
}

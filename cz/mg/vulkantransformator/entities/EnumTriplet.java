package cz.mg.vulkantransformator.entities;

import cz.mg.vulkantransformator.entities.c.CEnum;
import cz.mg.vulkantransformator.entities.vk.VkEnum;
import cz.mg.vulkantransformator.entities.vulkan.VulkanEnum;


public class EnumTriplet extends EntityTriplet<CEnum, VkEnum, VulkanEnum> {
    public EnumTriplet(CEnum c, VkEnum vk, VulkanEnum vulkan) {
        super(c, vk, vulkan);
    }
}

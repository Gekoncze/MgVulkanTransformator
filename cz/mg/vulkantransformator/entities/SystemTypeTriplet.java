package cz.mg.vulkantransformator.entities;

import cz.mg.vulkantransformator.entities.c.CSystemType;
import cz.mg.vulkantransformator.entities.vk.VkSystemType;
import cz.mg.vulkantransformator.entities.vulkan.VulkanSystemType;


public class SystemTypeTriplet extends EntityTriplet<CSystemType, VkSystemType, VulkanSystemType> {
    public SystemTypeTriplet(CSystemType c, VkSystemType vk, VulkanSystemType vulkan) {
        super(c, vk, vulkan);
    }
}

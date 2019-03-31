package cz.mg.vulkantransformator.entities;

import cz.mg.vulkantransformator.entities.c.CFlagBits;
import cz.mg.vulkantransformator.entities.vk.VkFlagBits;
import cz.mg.vulkantransformator.entities.vulkan.VulkanFlagBits;


public class FlagBitsTriplet extends EntityTriplet<CFlagBits, VkFlagBits, VulkanFlagBits> {
    public FlagBitsTriplet(CFlagBits c, VkFlagBits vk, VulkanFlagBits vulkan) {
        super(c, vk, vulkan);
    }
}

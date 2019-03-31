package cz.mg.vulkantransformator.entities;

import cz.mg.vulkantransformator.entities.c.CFlags;
import cz.mg.vulkantransformator.entities.vk.VkFlags;
import cz.mg.vulkantransformator.entities.vulkan.VulkanFlags;


public class FlagsTriplet extends EntityTriplet<CFlags, VkFlags, VulkanFlags> {
    public FlagsTriplet(CFlags c, VkFlags vk, VulkanFlags vulkan) {
        super(c, vk, vulkan);
    }
}

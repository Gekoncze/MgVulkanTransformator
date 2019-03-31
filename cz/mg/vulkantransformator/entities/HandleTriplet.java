package cz.mg.vulkantransformator.entities;

import cz.mg.vulkantransformator.entities.c.CHandle;
import cz.mg.vulkantransformator.entities.vk.VkHandle;
import cz.mg.vulkantransformator.entities.vulkan.VulkanHandle;


public class HandleTriplet extends EntityTriplet<CHandle, VkHandle, VulkanHandle> {
    public HandleTriplet(CHandle c, VkHandle vk, VulkanHandle vulkan) {
        super(c, vk, vulkan);
    }
}

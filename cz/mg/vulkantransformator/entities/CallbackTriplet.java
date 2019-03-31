package cz.mg.vulkantransformator.entities;

import cz.mg.vulkantransformator.entities.c.CCallback;
import cz.mg.vulkantransformator.entities.vk.VkCallback;
import cz.mg.vulkantransformator.entities.vulkan.VulkanCallback;


public class CallbackTriplet extends FunctionTriplet<CCallback, VkCallback, VulkanCallback> {
    public CallbackTriplet(CCallback c, VkCallback vk, VulkanCallback vulkan) {
        super(c, vk, vulkan);
    }
}

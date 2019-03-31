package cz.mg.vulkantransformator.entities;

import cz.mg.vulkantransformator.entities.c.CFunction;
import cz.mg.vulkantransformator.entities.vk.VkFunction;
import cz.mg.vulkantransformator.entities.vulkan.VulkanFunction;


public class FunctionTriplet<C extends CFunction, VK extends VkFunction, VULKAN extends VulkanFunction> extends EntityTriplet<C, VK, VULKAN> {
    public FunctionTriplet(C c, VK vk, VULKAN vulkan) {
        super(c, vk, vulkan);
    }
}

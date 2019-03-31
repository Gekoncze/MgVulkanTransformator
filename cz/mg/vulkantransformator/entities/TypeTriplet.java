package cz.mg.vulkantransformator.entities;

import cz.mg.vulkantransformator.entities.c.CType;
import cz.mg.vulkantransformator.entities.vk.VkType;
import cz.mg.vulkantransformator.entities.vulkan.VulkanType;


public class TypeTriplet extends EntityTriplet<CType, VkType, VulkanType> {
    public TypeTriplet(CType c, VkType vk, VulkanType vulkan) {
        super(c, vk, vulkan);
    }
}

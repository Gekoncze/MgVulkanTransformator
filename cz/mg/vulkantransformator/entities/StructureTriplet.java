package cz.mg.vulkantransformator.entities;

import cz.mg.vulkantransformator.entities.c.CStructure;
import cz.mg.vulkantransformator.entities.vk.VkStructure;
import cz.mg.vulkantransformator.entities.vulkan.VulkanStructure;


public class StructureTriplet extends EntityTriplet<CStructure, VkStructure, VulkanStructure> {
    public StructureTriplet(CStructure c, VkStructure vk, VulkanStructure vulkan) {
        super(c, vk, vulkan);
    }
}

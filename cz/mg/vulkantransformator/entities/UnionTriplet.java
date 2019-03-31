package cz.mg.vulkantransformator.entities;

import cz.mg.vulkantransformator.entities.c.CUnion;
import cz.mg.vulkantransformator.entities.vk.VkUnion;
import cz.mg.vulkantransformator.entities.vulkan.VulkanUnion;


public class UnionTriplet extends EntityTriplet<CUnion, VkUnion, VulkanUnion> {
    public UnionTriplet(CUnion c, VkUnion vk, VulkanUnion vulkan) {
        super(c, vk, vulkan);
    }
}

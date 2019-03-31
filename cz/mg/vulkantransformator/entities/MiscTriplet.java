package cz.mg.vulkantransformator.entities;

import cz.mg.vulkantransformator.entities.c.CMisc;
import cz.mg.vulkantransformator.entities.vk.VkMisc;
import cz.mg.vulkantransformator.entities.vulkan.VulkanMisc;


public class MiscTriplet extends EntityTriplet<CMisc, VkMisc, VulkanMisc> {
    public MiscTriplet(CMisc c, VkMisc vk, VulkanMisc vulkan) {
        super(c, vk, vulkan);
    }
}

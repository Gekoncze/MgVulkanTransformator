package cz.mg.vulkantransformator.entities;

import cz.mg.vulkantransformator.entities.c.CInfo;
import cz.mg.vulkantransformator.entities.vk.VkInfo;
import cz.mg.vulkantransformator.entities.vulkan.VulkanInfo;


public class InfoTriplet extends EntityTriplet<CInfo, VkInfo, VulkanInfo> {
    public InfoTriplet(CInfo c, VkInfo vk, VulkanInfo vulkan) {
        super(c, vk, vulkan);
    }
}

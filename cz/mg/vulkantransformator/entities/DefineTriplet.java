package cz.mg.vulkantransformator.entities;

import cz.mg.vulkantransformator.entities.c.CDefine;
import cz.mg.vulkantransformator.entities.vk.VkDefine;
import cz.mg.vulkantransformator.entities.vulkan.VulkanDefine;


public class DefineTriplet extends EntityTriplet<CDefine, VkDefine, VulkanDefine> {
    public DefineTriplet(CDefine c, VkDefine vk, VulkanDefine vulkan) {
        super(c, vk, vulkan);
    }

    public boolean isString(){
        return getC().getValue().startsWith("\"");
    }
}

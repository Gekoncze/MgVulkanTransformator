package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.entities.c.CDefine;
import cz.mg.vulkantransformator.entities.vk.VkDefine;
import cz.mg.vulkantransformator.entities.vulkan.VulkanDefine;
import cz.mg.vulkantransformator.utilities.StringUtilities;


public class DefineConverter implements Converter<CDefine, VkDefine, VulkanDefine> {
    @Override
    public VkDefine convert(CDefine c) {
        return new VkDefine(
                c.getName(),
                c.getValue()
        );
    }

    @Override
    public VulkanDefine convert(VkDefine vk) {
        return new VulkanDefine(
                StringUtilities.replaceFirst(vk.getName(), "VK_", ""),
                vk.getValue()
        );
    }
}

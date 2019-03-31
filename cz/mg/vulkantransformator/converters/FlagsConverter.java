package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.entities.c.CFlags;
import cz.mg.vulkantransformator.entities.vk.VkFlags;
import cz.mg.vulkantransformator.entities.vulkan.VulkanFlags;


public class FlagsConverter implements Converter<CFlags, VkFlags, VulkanFlags> {
    @Override
    public VkFlags convert(CFlags c) {
        return new VkFlags(TypenameConverter.cTypenameToVk(c.getName()));
    }

    @Override
    public VulkanFlags convert(VkFlags vk) {
        return new VulkanFlags(TypenameConverter.vkTypenameToV(vk.getName()));
    }
}

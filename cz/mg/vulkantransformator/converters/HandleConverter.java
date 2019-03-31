package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.entities.c.CHandle;
import cz.mg.vulkantransformator.entities.vk.VkHandle;
import cz.mg.vulkantransformator.entities.vulkan.VulkanHandle;


public class HandleConverter implements Converter<CHandle, VkHandle, VulkanHandle> {
    @Override
    public VkHandle convert(CHandle c) {
        return new VkHandle(TypenameConverter.cTypenameToVk(c.getName()));
    }

    @Override
    public VulkanHandle convert(VkHandle vk) {
        return new VulkanHandle(TypenameConverter.vkTypenameToV(vk.getName()));
    }
}

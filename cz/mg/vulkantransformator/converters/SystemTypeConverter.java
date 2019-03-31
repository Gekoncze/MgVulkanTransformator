package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.entities.c.CSystemType;
import cz.mg.vulkantransformator.entities.vk.VkSystemType;
import cz.mg.vulkantransformator.entities.vulkan.VulkanSystemType;


public class SystemTypeConverter implements Converter<CSystemType, VkSystemType, VulkanSystemType> {
    @Override
    public VkSystemType convert(CSystemType c) {
        return new VkSystemType(TypenameConverter.cTypenameToVk(c.getName()));
    }

    @Override
    public VulkanSystemType convert(VkSystemType vk) {
        return new VulkanSystemType(TypenameConverter.vkTypenameToV(vk.getName()));
    }
}

package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.entities.c.CType;
import cz.mg.vulkantransformator.entities.vk.VkType;
import cz.mg.vulkantransformator.entities.vulkan.VulkanType;


public class TypeConverter implements Converter<CType, VkType, VulkanType> {
    @Override
    public VkType convert(CType c) {
        return new VkType(
                TypenameConverter.cTypenameToVk(c.getType()),
                TypenameConverter.cTypenameToVk(c.getName())
        );
    }

    @Override
    public VulkanType convert(VkType vk) {
        return new VulkanType(
                TypenameConverter.vkTypenameToV(vk.getBase()),
                TypenameConverter.vkTypenameToV(vk.getName())
        );
    }
}

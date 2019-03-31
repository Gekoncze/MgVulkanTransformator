package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.entities.c.CEnum;
import cz.mg.vulkantransformator.entities.c.CValue;
import cz.mg.vulkantransformator.entities.vk.VkEnum;
import cz.mg.vulkantransformator.entities.vk.VkValue;
import cz.mg.vulkantransformator.entities.vulkan.VulkanEnum;
import cz.mg.vulkantransformator.entities.vulkan.VulkanValue;


public class EnumConverter implements Converter<CEnum, VkEnum, VulkanEnum> {
    @Override
    public VkEnum convert(CEnum c) {
        VkEnum vk = new VkEnum(TypenameConverter.cTypenameToVk(c.getName()));
        for(CValue value : c.getValues()) vk.getValues().addLast(new VkValue(value.getName(), value.getValue()));
        return vk;
    }

    @Override
    public VulkanEnum convert(VkEnum vk) {
        VulkanEnum vulkan = new VulkanEnum(TypenameConverter.vkTypenameToV(vk.getName()));
        for(VkValue value : vk.getValues()){
            String newName = TypenameConverter.vkEnumnameToVulkan(value.getName(), vk.getName());
            String newValue = vk.getName() + "." + value.getName();
            vulkan.getValues().addLast(new VulkanValue(newName, newValue));
        }
        return vulkan;
    }
}

package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.entities.c.CFlagBits;
import cz.mg.vulkantransformator.entities.c.CValue;
import cz.mg.vulkantransformator.entities.vk.VkFlagBits;
import cz.mg.vulkantransformator.entities.vk.VkValue;
import cz.mg.vulkantransformator.entities.vulkan.VulkanFlagBits;
import cz.mg.vulkantransformator.entities.vulkan.VulkanValue;


public class FlagBitsConverter implements Converter<CFlagBits, VkFlagBits, VulkanFlagBits> {
    @Override
    public VkFlagBits convert(CFlagBits c) {
        VkFlagBits vk = new VkFlagBits(TypenameConverter.cTypenameToVk(c.getName()));
        for(CValue value : c.getValues()) vk.getValues().addLast(new VkValue(value.getName(), value.getValue()));
        return vk;
    }

    @Override
    public VulkanFlagBits convert(VkFlagBits vk) {
        VulkanFlagBits vulkan = new VulkanFlagBits(TypenameConverter.vkTypenameToV(vk.getName()));
        for(VkValue value : vk.getValues()){
            String newName = TypenameConverter.vkEnumnameToVulkan(value.getName(), vk.getName());
            String newValue = vk.getName() + "." + value.getName();
            vulkan.getValues().addLast(new VulkanValue(newName, newValue));
        }
        return vulkan;
    }
}

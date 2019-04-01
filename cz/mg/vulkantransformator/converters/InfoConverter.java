package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.entities.c.CVariable;
import cz.mg.vulkantransformator.entities.c.CInfo;
import cz.mg.vulkantransformator.entities.vk.VkVariable;
import cz.mg.vulkantransformator.entities.vk.VkInfo;
import cz.mg.vulkantransformator.entities.vulkan.VulkanVariable;
import cz.mg.vulkantransformator.entities.vulkan.VulkanInfo;


public class InfoConverter implements Converter<CInfo, VkInfo, VulkanInfo> {
    @Override
    public VkInfo convert(CInfo c) {
        VkInfo vk = new VkInfo(TypenameConverter.cTypenameToVk(c.getName()));
        for(CVariable field : c.getFields()){
            vk.getFields().addLast(new VkVariable(
                    DatatypeConverter.cDatatypeToVk(field.getTypename(), field.getPointerCount(), field.getArrayCount()),
                    field.getName(),
                    field.getPointerCount(),
                    field.getArrayCount()
            ));
        }
        return vk;
    }

    @Override
    public VulkanInfo convert(VkInfo vk) {
        VulkanInfo vulkan = new VulkanInfo(TypenameConverter.vkTypenameToV(vk.getName()));
        for(VkVariable field : vk.getFields()){
            vulkan.getFields().addLast(new VulkanVariable(
                    DatatypeConverter.vkDatatypeToV(field.getTypename()),
                    field.getName()
            ));
        }
        return vulkan;
    }
}

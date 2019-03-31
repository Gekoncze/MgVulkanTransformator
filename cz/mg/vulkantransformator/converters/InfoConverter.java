package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.entities.c.CField;
import cz.mg.vulkantransformator.entities.c.CInfo;
import cz.mg.vulkantransformator.entities.vk.VkField;
import cz.mg.vulkantransformator.entities.vk.VkInfo;
import cz.mg.vulkantransformator.entities.vulkan.VulkanField;
import cz.mg.vulkantransformator.entities.vulkan.VulkanInfo;


public class InfoConverter implements Converter<CInfo, VkInfo, VulkanInfo> {
    @Override
    public VkInfo convert(CInfo c) {
        VkInfo vk = new VkInfo(TypenameConverter.cTypenameToVk(c.getName()));
        for(CField field : c.getFields()){
            vk.getFields().addLast(new VkField(
                    DatatypeConverter.cDatatypeToVk(field.getType(), field.getPointerCount(), field.getArrayCount()),
                    field.getName(),
                    field.getArrayCount()
            ));
        }
        return vk;
    }

    @Override
    public VulkanInfo convert(VkInfo vk) {
        VulkanInfo vulkan = new VulkanInfo(TypenameConverter.vkTypenameToV(vk.getName()));
        for(VkField field : vk.getFields()){
            vulkan.getFields().addLast(new VulkanField(
                    DatatypeConverter.vkDatatypeToV(field.getType()),
                    field.getName()
            ));
        }
        return vulkan;
    }
}

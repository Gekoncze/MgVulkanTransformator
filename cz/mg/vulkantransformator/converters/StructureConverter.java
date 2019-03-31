package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.entities.c.CField;
import cz.mg.vulkantransformator.entities.c.CStructure;
import cz.mg.vulkantransformator.entities.vk.VkField;
import cz.mg.vulkantransformator.entities.vk.VkStructure;
import cz.mg.vulkantransformator.entities.vulkan.VulkanField;
import cz.mg.vulkantransformator.entities.vulkan.VulkanStructure;


public class StructureConverter implements Converter<CStructure, VkStructure, VulkanStructure> {
    @Override
    public VkStructure convert(CStructure c) {
        VkStructure vk = new VkStructure(TypenameConverter.cTypenameToVk(c.getName()));
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
    public VulkanStructure convert(VkStructure vk) {
        VulkanStructure vulkan = new VulkanStructure(TypenameConverter.vkTypenameToV(vk.getName()));
        for(VkField field : vk.getFields()){
            vulkan.getFields().addLast(new VulkanField(
                    DatatypeConverter.vkDatatypeToV(field.getType()),
                    field.getName()
            ));
        }
        return vulkan;
    }
}

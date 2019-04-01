package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.entities.c.CVariable;
import cz.mg.vulkantransformator.entities.c.CStructure;
import cz.mg.vulkantransformator.entities.vk.VkVariable;
import cz.mg.vulkantransformator.entities.vk.VkStructure;
import cz.mg.vulkantransformator.entities.vulkan.VulkanVariable;
import cz.mg.vulkantransformator.entities.vulkan.VulkanStructure;


public class StructureConverter implements Converter<CStructure, VkStructure, VulkanStructure> {
    @Override
    public VkStructure convert(CStructure c) {
        VkStructure vk = new VkStructure(TypenameConverter.cTypenameToVk(c.getName()));
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
    public VulkanStructure convert(VkStructure vk) {
        VulkanStructure vulkan = new VulkanStructure(TypenameConverter.vkTypenameToV(vk.getName()));
        for(VkVariable field : vk.getFields()){
            vulkan.getFields().addLast(new VulkanVariable(
                    DatatypeConverter.vkDatatypeToV(field.getTypename()),
                    field.getName()
            ));
        }
        return vulkan;
    }
}

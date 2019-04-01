package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.entities.c.CVariable;
import cz.mg.vulkantransformator.entities.c.CUnion;
import cz.mg.vulkantransformator.entities.vk.VkVariable;
import cz.mg.vulkantransformator.entities.vk.VkUnion;
import cz.mg.vulkantransformator.entities.vulkan.VulkanVariable;
import cz.mg.vulkantransformator.entities.vulkan.VulkanUnion;


public class UnionConverter implements Converter<CUnion, VkUnion, VulkanUnion> {
    @Override
    public VkUnion convert(CUnion c) {
        VkUnion vk = new VkUnion(TypenameConverter.cTypenameToVk(c.getName()));
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
    public VulkanUnion convert(VkUnion vk) {
        VulkanUnion vulkan = new VulkanUnion(TypenameConverter.vkTypenameToV(vk.getName()));
        for(VkVariable field : vk.getFields()){
            vulkan.getFields().addLast(new VulkanVariable(
                    DatatypeConverter.vkDatatypeToV(field.getTypename()),
                    field.getName()
            ));
        }
        return vulkan;
    }
}

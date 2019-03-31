package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.entities.c.CField;
import cz.mg.vulkantransformator.entities.c.CUnion;
import cz.mg.vulkantransformator.entities.vk.VkField;
import cz.mg.vulkantransformator.entities.vk.VkUnion;
import cz.mg.vulkantransformator.entities.vulkan.VulkanField;
import cz.mg.vulkantransformator.entities.vulkan.VulkanUnion;


public class UnionConverter implements Converter<CUnion, VkUnion, VulkanUnion> {
    @Override
    public VkUnion convert(CUnion c) {
        VkUnion vk = new VkUnion(TypenameConverter.cTypenameToVk(c.getName()));
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
    public VulkanUnion convert(VkUnion vk) {
        VulkanUnion vulkan = new VulkanUnion(TypenameConverter.vkTypenameToV(vk.getName()));
        for(VkField field : vk.getFields()){
            vulkan.getFields().addLast(new VulkanField(
                    DatatypeConverter.vkDatatypeToV(field.getType()),
                    field.getName()
            ));
        }
        return vulkan;
    }
}

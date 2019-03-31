package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.entities.c.CCallback;
import cz.mg.vulkantransformator.entities.c.CParameter;
import cz.mg.vulkantransformator.entities.vk.VkCallback;
import cz.mg.vulkantransformator.entities.vk.VkParameter;
import cz.mg.vulkantransformator.entities.vulkan.VulkanCallback;


public class CallbackConverter implements Converter<CCallback, VkCallback, VulkanCallback> {
    @Override
    public VkCallback convert(CCallback c) {
        VkCallback vk = new VkCallback(
                FunctionConverter.convert(c.getReturnType()),
                TypenameConverter.cTypenameToVk(c.getName())
        );
        for(CParameter parameter : c.getParameters()){
            vk.getParameters().addLast(FunctionConverter.convert(parameter));
        }
        return vk;
    }

    @Override
    public VulkanCallback convert(VkCallback vk) {
        VulkanCallback vulkan = new VulkanCallback(
                FunctionConverter.convert(vk.getReturnType()),
                TypenameConverter.vkTypenameToV(vk.getName())
        );
        for(VkParameter parameter : vk.getParameters()){
            vulkan.getParameters().addLast(FunctionConverter.convert(parameter));
        }
        return vulkan;
    }
}

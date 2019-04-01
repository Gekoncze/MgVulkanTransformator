package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.entities.c.CCallback;
import cz.mg.vulkantransformator.entities.c.CVariable;
import cz.mg.vulkantransformator.entities.vk.VkCallback;
import cz.mg.vulkantransformator.entities.vk.VkVariable;
import cz.mg.vulkantransformator.entities.vulkan.VulkanCallback;


public class CallbackConverter implements Converter<CCallback, VkCallback, VulkanCallback> {
    @Override
    public VkCallback convert(CCallback c) {
        VkCallback vk = new VkCallback(
                FunctionConverter.convert(c.getReturnType()),
                TypenameConverter.cTypenameToVk(c.getName())
        );
        for(CVariable Variable : c.getParameters()){
            vk.getParameters().addLast(FunctionConverter.convert(Variable));
        }
        return vk;
    }

    @Override
    public VulkanCallback convert(VkCallback vk) {
        VulkanCallback vulkan = new VulkanCallback(
                FunctionConverter.convert(vk.getReturnType()),
                TypenameConverter.vkTypenameToV(vk.getName())
        );
        for(VkVariable Variable : vk.getParameters()){
            vulkan.getVariables().addLast(FunctionConverter.convert(Variable));
        }
        return vulkan;
    }
}

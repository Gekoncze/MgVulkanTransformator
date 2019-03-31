package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.entities.c.CFunction;
import cz.mg.vulkantransformator.entities.c.CParameter;
import cz.mg.vulkantransformator.entities.vk.VkFunction;
import cz.mg.vulkantransformator.entities.vk.VkParameter;
import cz.mg.vulkantransformator.entities.vulkan.VulkanFunction;
import cz.mg.vulkantransformator.entities.vulkan.VulkanParameter;


public class FunctionConverter implements Converter<CFunction, VkFunction, VulkanFunction> {
    @Override
    public VkFunction convert(CFunction c) {
        VkFunction vk = new VkFunction(
                convert(c.getReturnType()),
                TypenameConverter.cTypenameToVk(c.getName())
        );
        for(CParameter parameter : c.getParameters()){
            vk.getParameters().addLast(convert(parameter));
        }
        return vk;
    }

    public static VkParameter convert(CParameter param){
        return new VkParameter(
                DatatypeConverter.cDatatypeToVk(param.getTypename(), param.getPointerCount(), null),
                param.getName(),
                param.isVoid()
        );
    }

    @Override
    public VulkanFunction convert(VkFunction vk) {
        VulkanFunction vulkan = new VulkanFunction(
                convert(vk.getReturnType()),
                TypenameConverter.vkTypenameToV(vk.getName())
        );
        for(VkParameter parameter : vk.getParameters()){
            vulkan.getParameters().addLast(convert(parameter));
        }
        return vulkan;
    }

    public static VulkanParameter convert(VkParameter parameter){
        return new VulkanParameter(
                TypenameConverter.vkTypenameToV(parameter.getTypename()),
                parameter.getName(),
                parameter.isEmpty()
        );
    }
}

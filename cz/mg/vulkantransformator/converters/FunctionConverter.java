package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.entities.c.CFunction;
import cz.mg.vulkantransformator.entities.c.CVariable;
import cz.mg.vulkantransformator.entities.vk.VkFunction;
import cz.mg.vulkantransformator.entities.vk.VkVariable;
import cz.mg.vulkantransformator.entities.vulkan.VulkanFunction;
import cz.mg.vulkantransformator.entities.vulkan.VulkanVariable;


public class FunctionConverter implements Converter<CFunction, VkFunction, VulkanFunction> {
    @Override
    public VkFunction convert(CFunction c) {
        VkFunction vk = new VkFunction(
                convert(c.getReturnType()),
                TypenameConverter.cTypenameToVk(c.getName())
        );
        for(CVariable Variable : c.getParameters()){
            vk.getParameters().addLast(convert(Variable));
        }
        return vk;
    }

    public static VkVariable convert(CVariable param){
        return new VkVariable(
                DatatypeConverter.cDatatypeToVk(param.getTypename(), param.getPointerCount(), null),
                param.getName(),
                param.getPointerCount(),
                param.getArrayCount()
        );
    }

    @Override
    public VulkanFunction convert(VkFunction vk) {
        VulkanFunction vulkan = new VulkanFunction(
                convert(vk.getReturnType()),
                TypenameConverter.vkTypenameToV(vk.getName())
        );
        for(VkVariable Variable : vk.getParameters()){
            vulkan.getVariables().addLast(convert(Variable));
        }
        return vulkan;
    }

    public static VulkanVariable convert(VkVariable Variable){
        return new VulkanVariable(
                TypenameConverter.vkTypenameToV(Variable.getTypename()),
                Variable.getName()
        );
    }
}

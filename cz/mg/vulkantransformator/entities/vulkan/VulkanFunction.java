package cz.mg.vulkantransformator.entities.vulkan;

import cz.mg.collections.list.chainlist.ChainList;


public class VulkanFunction implements VulkanEntity {
    private final VulkanVariable returnType;
    private final String name;
    private final ChainList<VulkanVariable> variables = new ChainList<>();

    public VulkanFunction(VulkanVariable returnType, String name) {
        this.returnType = returnType;
        this.name = name;
    }

    public VulkanVariable getReturnType() {
        return returnType;
    }

    @Override
    public String getName() {
        return name;
    }

    public ChainList<VulkanVariable> getVariables() {
        return variables;
    }
}

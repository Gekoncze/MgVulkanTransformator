package cz.mg.vulkantransformator.entities.vulkan;

import cz.mg.collections.list.chainlist.ChainList;


public class VulkanFunction implements VulkanEntity {
    private final VulkanParameter returnType;
    private final String name;
    private final ChainList<VulkanParameter> parameters = new ChainList<>();

    public VulkanFunction(VulkanParameter returnType, String name) {
        this.returnType = returnType;
        this.name = name;
    }

    public VulkanParameter getReturnType() {
        return returnType;
    }

    @Override
    public String getName() {
        return name;
    }

    public ChainList<VulkanParameter> getParameters() {
        return parameters;
    }
}

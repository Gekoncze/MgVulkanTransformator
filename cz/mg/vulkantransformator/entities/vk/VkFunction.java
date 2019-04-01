package cz.mg.vulkantransformator.entities.vk;

import cz.mg.collections.list.chainlist.ChainList;


public class VkFunction implements VkEntity {
    private final VkVariable returnType;
    private final String name;
    private final ChainList<VkVariable> parameters = new ChainList<>();

    public VkFunction(VkVariable returnType, String name) {
        this.returnType = returnType;
        this.name = name;
    }

    public VkVariable getReturnType() {
        return returnType;
    }

    @Override
    public String getName() {
        return name;
    }

    public ChainList<VkVariable> getParameters() {
        return parameters;
    }
}

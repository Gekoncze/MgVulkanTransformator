package cz.mg.vulkantransformator.entities.vk;

import cz.mg.collections.list.chainlist.ChainList;


public class VkFunction implements VkEntity {
    private final VkParameter returnType;
    private final String name;
    private final ChainList<VkParameter> parameters = new ChainList<>();

    public VkFunction(VkParameter returnType, String name) {
        this.returnType = returnType;
        this.name = name;
    }

    public VkParameter getReturnType() {
        return returnType;
    }

    @Override
    public String getName() {
        return name;
    }

    public ChainList<VkParameter> getParameters() {
        return parameters;
    }
}

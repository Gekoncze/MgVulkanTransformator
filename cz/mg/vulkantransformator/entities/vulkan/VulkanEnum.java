package cz.mg.vulkantransformator.entities.vulkan;

import cz.mg.collections.list.chainlist.ChainList;


public class VulkanEnum implements VulkanEntity {
    private final String name;
    private final ChainList<VulkanValue> values = new ChainList<>();

    public VulkanEnum(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public ChainList<VulkanValue> getValues() {
        return values;
    }
}

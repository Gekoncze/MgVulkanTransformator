package cz.mg.vulkantransformator.entities.vulkan;

import cz.mg.collections.list.chainlist.ChainList;


public class VulkanStructure implements VulkanEntity {
    private final String name;
    private final ChainList<VulkanField> fields = new ChainList<>();

    public VulkanStructure(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public ChainList<VulkanField> getFields() {
        return fields;
    }
}

package cz.mg.vulkantransformator.entities.vk;

import cz.mg.collections.list.chainlist.ChainList;


public class VkStructure implements VkEntity {
    private final String name;
    private final ChainList<VkField> fields = new ChainList<>();

    public VkStructure(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public ChainList<VkField> getFields() {
        return fields;
    }
}

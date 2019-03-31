package cz.mg.vulkantransformator.entities.vk;

import cz.mg.collections.list.chainlist.ChainList;


public class VkEnum implements VkEntity {
    private final String name;
    private final ChainList<VkValue> values = new ChainList<>();

    public VkEnum(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public ChainList<VkValue> getValues() {
        return values;
    }
}

package cz.mg.vulkantransformator.entities.vk;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CEnum;
import cz.mg.collections.text.Text;


public class VkEnum<C extends CEnum> extends VkEntity<C> {
    private final ChainList<VkValue> values;

    public VkEnum(C c, Text name, ChainList<VkValue> values) {
        super(c, name);
        this.values = values;
    }

    public ChainList<VkValue> getValues() {
        return values;
    }
}

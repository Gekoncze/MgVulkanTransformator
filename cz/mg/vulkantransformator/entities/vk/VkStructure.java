package cz.mg.vulkantransformator.entities.vk;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CStructure;
import cz.mg.collections.text.Text;


public class VkStructure<C extends CStructure> extends VkEntity<C> {
    private final ChainList<VkVariable> fields;

    public VkStructure(C c, Text name, ChainList<VkVariable> fields) {
        super(c, name);
        this.fields = fields;
    }

    public ChainList<VkVariable> getFields() {
        return fields;
    }
}

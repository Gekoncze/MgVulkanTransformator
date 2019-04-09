package cz.mg.vulkantransformator.entities.vk;

import cz.mg.vulkantransformator.entities.c.CType;
import cz.mg.collections.text.Text;


public class VkType extends VkEntity<CType> {
    private final Text base;

    public VkType(CType c, Text name, Text base) {
        super(c, name);
        this.base = base;
    }

    public Text getBase() {
        return base;
    }
}

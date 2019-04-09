package cz.mg.vulkantransformator.entities.vk;

import cz.mg.vulkantransformator.entities.c.CValue;
import cz.mg.collections.text.Text;


public class VkValue extends VkEntity<CValue> {
    private final Text value;

    public VkValue(CValue c, Text name, Text value) {
        super(c, name);
        this.value = value;
    }

    public Text getValue() {
        return value;
    }
}

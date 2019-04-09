package cz.mg.vulkantransformator.entities.vk;

import cz.mg.vulkantransformator.entities.c.CDefine;
import cz.mg.collections.text.Text;


public class VkDefine extends VkEntity<CDefine> {
    private final Text value;

    public VkDefine(CDefine c, Text name, Text value) {
        super(c, name);
        this.value = value;
    }

    public Text getValue() {
        return value;
    }

    public boolean isString(){
        return getC().getValue().startsWith("\"");
    }
}

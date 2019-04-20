package cz.mg.vulkantransformator.entities.vk;

import cz.mg.vulkantransformator.entities.c.CDefine;
import cz.mg.collections.text.Text;


public class VkDefine extends VkEntity<CDefine> {
    private Text value;

    public VkDefine(CDefine c, Text name, Text value) {
        super(c, name);
        this.value = value;
    }

    public Text getValue() {
        return value;
    }

    public void setValue(Text value) {
        this.value = value;
    }

    public boolean isString(){
        return getC().getValue().startsWith("\"");
    }

    public boolean isFloat(){
        return getC().getValue().endsWith("f");
    }

    public boolean isInteger(){
        return getC().getName().equals("VK_TRUE") || getC().getName().equals("VK_FALSE");
    }
}

package cz.mg.vulkantransformator.entities.vk;

import cz.mg.vulkantransformator.entities.c.CVariable;
import cz.mg.collections.text.Text;


public class VkVariable extends VkEntity<CVariable> {
    private Text typename;
    private final int pointerCount;
    private final Text arrayCount;

    public VkVariable(CVariable c, Text name, Text typename, int pointerCount, Text arrayCount) {
        super(c, name);
        this.typename = typename;
        this.pointerCount = pointerCount;
        this.arrayCount = arrayCount;
    }

    public Text getTypename() {
        return typename;
    }

    public void setTypename(Text typename) {
        this.typename = typename;
    }

    public int getPointerCount() {
        return pointerCount;
    }

    public Text getArrayCount() {
        return arrayCount;
    }

    public boolean isEmpty(){
        return typename.equals("VkObject") && pointerCount == 0;
    }

    public boolean isValue(){
        return pointerCount == 0 && arrayCount == null;
    }
}

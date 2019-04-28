package cz.mg.vulkantransformator.entities.vk;

import cz.mg.vulkantransformator.entities.c.CVariable;
import cz.mg.collections.text.Text;


public class VkVariable extends VkEntity<CVariable> {
    private Text typename;
    private final int pointerCount;
    private final Text arrayCount;
    private final Text simplifiedJavaType;
    private final Text simplifiedJniType;

    public VkVariable(CVariable c, Text name, Text typename, int pointerCount, Text arrayCount, Text simplifiedJavaType, Text simplifiedJniType) {
        super(c, name);
        this.typename = typename;
        this.pointerCount = pointerCount;
        this.arrayCount = arrayCount;
        this.simplifiedJavaType = simplifiedJavaType;
        this.simplifiedJniType = simplifiedJniType;
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

    public Text getSimplifiedJavaType() {
        return simplifiedJavaType;
    }

    public Text getSimplifiedJniType() {
        return simplifiedJniType;
    }

    public boolean isVoid(){
        return typename.equals("VkObject") && pointerCount == 0;
    }

    public boolean isValue(){
        return pointerCount == 0 && arrayCount == null;
    }
}

package cz.mg.vulkantransformator.entities.c;

import cz.mg.vulkantransformator.EntityType;
import cz.mg.collections.text.Text;


public class CVariable extends CEntity {
    private Text typename;
    private final int pointerCount;
    private final Text arrayCount;
    private final Usage usage;

    public CVariable(Text name, Text typename, int pointerCount, Text arrayCount, Usage usage) {
        super(name);
        if(pointerCount > 2) throw new UnsupportedOperationException();
        this.typename = typename;
        this.pointerCount = pointerCount;
        this.arrayCount = arrayCount;
        this.usage = usage;
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

    public Usage getUsage() {
        return usage;
    }

    public boolean isVoid(){
        return typename.equals("void") && pointerCount == 0;
    }

    public boolean isVoidPointer(){
        return typename.equals("void") && pointerCount == 1;
    }

    public boolean isFunctionPointer(){
        return typename.equals("PFN_vkVoidFunction") && pointerCount == 0;
    }

    public boolean isString(){
        return getDatatype().equals("char*");
    }

    public Text getDatatype(){
        return typename.append(Text.repeat("*", pointerCount + (arrayCount != null ? 1 : 0)));
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.VARIABLE;
    }

    public enum Usage {
        FIELD,
        PARAMETER,
        RETURN
    }
}

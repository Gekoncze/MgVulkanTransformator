package cz.mg.vulkantransformator.entities.c;

import cz.mg.vulkantransformator.EntityType;
import cz.mg.collections.text.Text;


public class CVariable extends CEntity {
    private Text typename;
    private final int pointerCount;
    private final Text arrayCount;

    public CVariable(Text name, Text typename, int pointerCount, Text arrayCount) {
        super(name);
        if(pointerCount > 2) throw new UnsupportedOperationException();
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

    public boolean isVoid(){
        return typename.equals("void") && pointerCount == 0;
    }

    public Text getDatatype(){
        return typename.append(Text.repeat("*", pointerCount + (arrayCount != null ? 1 : 0)));
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.VARIABLE;
    }
}

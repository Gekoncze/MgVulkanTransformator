package cz.mg.vulkantransformator.entities.c;

import cz.mg.vulkantransformator.utilities.StringUtilities;


public class CVariable implements CEntity {
    private final String typename;
    private final String name;
    private final int pointerCount;
    private final String arrayCount;

    public CVariable(String typename, String name, int pointerCount, String arrayCount) {
        if(typename.equals("const") || name.equals("const")) throw new RuntimeException("Illegal CParameter: '" + typename + "' '" + name + "'");
        if(pointerCount > 2) throw new UnsupportedOperationException();
        this.typename = typename;
        this.name = name;
        this.pointerCount = pointerCount;
        this.arrayCount = arrayCount;
    }

    public String getTypename() {
        return typename;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getPointerCount() {
        return pointerCount;
    }

    public String getArrayCount() {
        return arrayCount;
    }

    public boolean isVoid(){
        return typename.equals("void") && pointerCount == 0;
    }

    public String getDatatype(){
        return typename + StringUtilities.repeat("*", pointerCount);
    }
}

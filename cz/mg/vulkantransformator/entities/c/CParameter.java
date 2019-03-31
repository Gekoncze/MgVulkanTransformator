package cz.mg.vulkantransformator.entities.c;

import cz.mg.vulkantransformator.utilities.StringUtilities;


public class CParameter {
    private final String typename;
    private final String name;
    private final int pointerCount;

    public CParameter(String typename, String name, int pointerCount) {
        if(typename.equals("const") || name.equals("const")) throw new RuntimeException("Illegal CParameter: '" + typename + "' '" + name + "'");
        this.typename = typename;
        this.name = name;
        this.pointerCount = pointerCount;
        if(pointerCount > 2) throw new UnsupportedOperationException();
    }
    public String getTypename() {
        return typename;
    }

    public String getName() {
        return name;
    }

    public int getPointerCount() {
        return pointerCount;
    }

    public String getDatatype(){
        return typename + StringUtilities.repeat("*", pointerCount);
    }

    public boolean isVoid(){
        return typename.equals("void") && pointerCount == 0;
    }
}

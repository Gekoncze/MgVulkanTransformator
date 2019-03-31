package cz.mg.vulkantransformator.entities.c;

public class CField {
    private final String type;
    private final String name;
    private final int pointerCount;
    private final String arrayCount;

    public CField(String type, String name, int pointerCount, String arrayCount) {
        this.type = type;
        this.name = name;
        this.pointerCount = pointerCount;
        this.arrayCount = arrayCount;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getPointerCount() {
        return pointerCount;
    }

    public String getArrayCount() {
        return arrayCount;
    }
}

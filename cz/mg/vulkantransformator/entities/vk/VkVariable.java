package cz.mg.vulkantransformator.entities.vk;

public class VkVariable implements VkEntity {
    private final String typename;
    private final String name;
    private final int pointerCount;
    private final String arrayCount;

    public VkVariable(String typename, String name, int pointerCount, String arrayCount) {
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

    public boolean isEmpty(){
        return typename.equals("VkObject") && pointerCount == 0;
    }

    public boolean isValue(){
        return pointerCount == 0 && arrayCount == null;
    }
}

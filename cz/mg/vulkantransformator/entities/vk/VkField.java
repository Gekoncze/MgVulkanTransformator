package cz.mg.vulkantransformator.entities.vk;

public class VkField {
    private final String type;
    private final String name;
    private final String arrayCount;

    public VkField(String type, String name, String arrayCount) {
        this.type = type;
        this.name = name;
        this.arrayCount = arrayCount;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getArrayCount() {
        return arrayCount;
    }
}

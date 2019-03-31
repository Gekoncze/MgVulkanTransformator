package cz.mg.vulkantransformator.entities.vk;

public class VkValue {
    private final String name;
    private final String value;

    public VkValue(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}

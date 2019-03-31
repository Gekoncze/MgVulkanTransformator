package cz.mg.vulkantransformator.entities.vulkan;

public class VulkanValue {
    private final String name;
    private final String value;

    public VulkanValue(String name, String value) {
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

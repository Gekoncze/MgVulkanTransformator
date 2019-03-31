package cz.mg.vulkantransformator.entities.vulkan;

public class VulkanField {
    private final String type;
    private final String name;

    public VulkanField(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}

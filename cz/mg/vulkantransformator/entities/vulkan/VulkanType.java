package cz.mg.vulkantransformator.entities.vulkan;


public class VulkanType implements VulkanEntity {
    private final String type;
    private final String name;

    public VulkanType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }
}

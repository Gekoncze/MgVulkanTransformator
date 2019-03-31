package cz.mg.vulkantransformator.entities.vulkan;


public class VulkanSystemType implements VulkanEntity {
    private final String name;

    public VulkanSystemType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}

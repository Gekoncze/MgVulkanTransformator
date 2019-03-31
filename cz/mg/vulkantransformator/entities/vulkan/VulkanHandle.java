package cz.mg.vulkantransformator.entities.vulkan;


public class VulkanHandle implements VulkanEntity {
    private final String name;

    public VulkanHandle(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}

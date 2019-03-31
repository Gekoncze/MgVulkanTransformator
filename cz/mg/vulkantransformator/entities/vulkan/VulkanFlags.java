package cz.mg.vulkantransformator.entities.vulkan;


public class VulkanFlags implements VulkanEntity {
    private final String name;

    public VulkanFlags(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}

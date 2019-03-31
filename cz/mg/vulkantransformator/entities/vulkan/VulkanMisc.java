package cz.mg.vulkantransformator.entities.vulkan;

public class VulkanMisc implements VulkanEntity {
    private final String name;

    public VulkanMisc(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}

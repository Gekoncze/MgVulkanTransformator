package cz.mg.vulkantransformator.entities.vulkan;


public class VulkanType implements VulkanEntity {
    private final String base;
    private final String name;

    public VulkanType(String base, String name) {
        this.base = base;
        this.name = name;
    }

    public String getBase() {
        return base;
    }

    @Override
    public String getName() {
        return name;
    }
}

package cz.mg.vulkantransformator.entities.vulkan;


public class VulkanDefine implements VulkanEntity {
    private final String name;
    private final String value;

    public VulkanDefine(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}

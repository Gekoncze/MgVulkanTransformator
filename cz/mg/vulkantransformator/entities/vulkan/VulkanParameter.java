package cz.mg.vulkantransformator.entities.vulkan;

public class VulkanParameter {
    private final String typename;
    private final String name;
    private final boolean empty;

    public VulkanParameter(String typename, String name, boolean empty) {
        this.typename = typename;
        this.name = name;
        this.empty = empty;
    }

    public String getTypename() {
        return typename;
    }

    public String getName() {
        return name;
    }

    public boolean isEmpty() {
        return empty;
    }
}

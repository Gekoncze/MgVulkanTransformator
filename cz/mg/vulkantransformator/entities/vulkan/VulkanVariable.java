package cz.mg.vulkantransformator.entities.vulkan;

public class VulkanVariable implements VulkanEntity {
    private final String typename;
    private final String name;

    public VulkanVariable(String typename, String name) {
        this.typename = typename;
        this.name = name;
    }

    public String getTypename() {
        return typename;
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean isEmpty(){
        return typename.equals("VulkanObject");
    }
}

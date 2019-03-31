package cz.mg.vulkantransformator.entities.vk;


public class VkHandle implements VkEntity {
    private final String name;

    public VkHandle(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}

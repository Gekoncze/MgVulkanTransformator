package cz.mg.vulkantransformator.entities.vk;


public class VkSystemType implements VkEntity {
    private final String name;

    public VkSystemType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}

package cz.mg.vulkantransformator.entities.vk;


public class VkFlags implements VkEntity {
    private final String name;

    public VkFlags(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}

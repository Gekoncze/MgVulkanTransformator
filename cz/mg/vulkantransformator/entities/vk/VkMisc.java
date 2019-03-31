package cz.mg.vulkantransformator.entities.vk;

public class VkMisc implements VkEntity {
    private final String name;

    public VkMisc(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}

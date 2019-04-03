package cz.mg.vulkantransformator.entities.vk;


public class VkType implements VkEntity {
    private final String base;
    private final String name;

    public VkType(String base, String name) {
        this.base = base;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getBase() {
        return base;
    }
}

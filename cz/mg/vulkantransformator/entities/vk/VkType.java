package cz.mg.vulkantransformator.entities.vk;


public class VkType implements VkEntity {
    private final String type;
    private final String name;

    public VkType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}

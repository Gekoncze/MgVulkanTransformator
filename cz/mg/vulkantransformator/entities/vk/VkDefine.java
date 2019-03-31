package cz.mg.vulkantransformator.entities.vk;

public class VkDefine implements VkEntity {
    private final String name;
    private final String value;

    public VkDefine(String name, String value) {
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

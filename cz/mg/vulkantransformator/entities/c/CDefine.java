package cz.mg.vulkantransformator.entities.c;

public class CDefine implements CEntity {
    private final String name;
    private final String value;

    public CDefine(String name, String value) {
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

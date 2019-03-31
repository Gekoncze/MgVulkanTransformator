package cz.mg.vulkantransformator.entities.c;

public class CValue {
    private final String name;
    private final String value;

    public CValue(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}

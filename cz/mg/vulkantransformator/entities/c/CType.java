package cz.mg.vulkantransformator.entities.c;

public class CType implements CEntity {
    private final String type;
    private final String name;

    public CType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }
}

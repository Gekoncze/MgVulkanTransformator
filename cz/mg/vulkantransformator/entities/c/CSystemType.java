package cz.mg.vulkantransformator.entities.c;

public class CSystemType implements CEntity {
    private final String name;

    public CSystemType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}

package cz.mg.vulkantransformator.entities.c;


public class CFlags implements CEntity {
    private final String name;

    public CFlags(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}

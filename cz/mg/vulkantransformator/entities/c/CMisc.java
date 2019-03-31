package cz.mg.vulkantransformator.entities.c;

public class CMisc implements CEntity {
    private final String name;

    public CMisc(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}

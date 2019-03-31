package cz.mg.vulkantransformator.entities.c;

public class CHandle implements CEntity {
    private final String name;
    private final boolean dispatchable;

    public CHandle(String name, boolean dispatchable) {
        this.name = name;
        this.dispatchable = dispatchable;
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean isDispatchable() {
        return dispatchable;
    }
}

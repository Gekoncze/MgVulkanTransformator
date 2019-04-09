package cz.mg.vulkantransformator.entities.c;

import cz.mg.vulkantransformator.EntityType;
import cz.mg.collections.text.Text;


public class CHandle extends CEntity {
    private final boolean dispatchable;

    public CHandle(Text name, boolean dispatchable) {
        super(name);
        this.dispatchable = dispatchable;
    }

    public boolean isDispatchable() {
        return dispatchable;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.HANDLE;
    }
}

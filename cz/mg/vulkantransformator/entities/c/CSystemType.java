package cz.mg.vulkantransformator.entities.c;

import cz.mg.vulkantransformator.EntityType;
import cz.mg.collections.text.Text;


public class CSystemType extends CEntity {
    public CSystemType(Text name) {
        super(name);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.SYSTEM_TYPE;
    }
}

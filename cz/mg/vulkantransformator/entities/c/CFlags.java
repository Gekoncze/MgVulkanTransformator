package cz.mg.vulkantransformator.entities.c;

import cz.mg.vulkantransformator.EntityType;
import cz.mg.collections.text.Text;


public class CFlags extends CEntity {
    public CFlags(Text name) {
        super(name);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.FLAGS;
    }
}

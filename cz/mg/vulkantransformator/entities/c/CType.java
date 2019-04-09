package cz.mg.vulkantransformator.entities.c;

import cz.mg.vulkantransformator.EntityType;
import cz.mg.collections.text.Text;


public class CType extends CEntity {
    private final Text type;

    public CType(Text name, Text type) {
        super(name);
        this.type = type;
    }

    public Text getType() {
        return type;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.TYPE;
    }
}

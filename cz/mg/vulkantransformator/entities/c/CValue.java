package cz.mg.vulkantransformator.entities.c;

import cz.mg.vulkantransformator.EntityType;
import cz.mg.collections.text.Text;


public class CValue extends CEntity {
    private final Text value;

    public CValue(Text name, Text value) {
        super(name);
        this.value = value;
    }

    public Text getValue() {
        return value;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.VALUE;
    }
}

package cz.mg.vulkantransformator.entities.c;

import cz.mg.vulkantransformator.EntityType;
import cz.mg.collections.text.Text;


public class CDefine extends CEntity {
    private final Text value;

    public CDefine(Text name, Text value) {
        super(name);
        this.value = value;
    }

    public Text getValue() {
        return value;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.DEFINE;
    }
}

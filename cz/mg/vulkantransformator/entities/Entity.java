package cz.mg.vulkantransformator.entities;

import cz.mg.vulkantransformator.EntityType;
import cz.mg.collections.text.Text;


public abstract class Entity {
    private Text name;

    public Entity(Text name) {
        this.name = name;
    }

    public final Text getName() {
        return name;
    }

    public abstract EntityType getEntityType();
}

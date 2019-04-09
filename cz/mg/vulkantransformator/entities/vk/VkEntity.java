package cz.mg.vulkantransformator.entities.vk;

import cz.mg.vulkantransformator.EntityType;
import cz.mg.vulkantransformator.entities.Entity;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.collections.text.Text;


public abstract class VkEntity<C extends CEntity> extends Entity {
    private final C c;

    public VkEntity(C c, Text name) {
        super(name);
        this.c = c;
    }

    public final C getC(){
        return c;
    }

    @Override
    public final EntityType getEntityType() {
        return getC().getEntityType();
    }
}

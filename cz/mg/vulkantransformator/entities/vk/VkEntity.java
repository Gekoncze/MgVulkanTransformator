package cz.mg.vulkantransformator.entities.vk;

import cz.mg.vulkantransformator.EntityType;
import cz.mg.vulkantransformator.entities.Entity;
import cz.mg.vulkantransformator.utilities.StringUtilities;


public interface VkEntity extends Entity {
    @Override
    public default EntityType getEntityType(){
        String prefix = StringUtilities.replaceFirst(getClass().getSimpleName(), "Vk", "");
        String entityTypeName = StringUtilities.cammelCaseToUpperCase(prefix);
        for(EntityType type : EntityType.values()) if(type.name().equals(entityTypeName)) return type;
        throw new RuntimeException("Could not find entity type " + entityTypeName);
    }
}

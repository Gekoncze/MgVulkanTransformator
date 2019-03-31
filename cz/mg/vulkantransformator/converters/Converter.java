package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.EntityType;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.entities.vulkan.VulkanEntity;


public interface Converter<C extends CEntity, VK extends VkEntity, VULKAN extends VulkanEntity> {
    public VK convert(C c);
    public VULKAN convert(VK vk);

    public static Converter create(EntityType type){
        switch(type){
            case SYSTEM_TYPE: return new SystemTypeConverter();
            case TYPE: return new TypeConverter();
            case ENUM: return new EnumConverter();
            case FLAGS: return new FlagsConverter();
            case FLAG_BITS: return new FlagBitsConverter();
            case HANDLE: return new HandleConverter();
            case STRUCTURE: return new StructureConverter();
            case UNION: return new UnionConverter();
            case INFO: return new InfoConverter();
            case CALLBACK: return new CallbackConverter();
            case FUNCTION: return new FunctionConverter();
            case DEFINE: return new DefineConverter();
            default: throw new UnsupportedOperationException();
        }
    }
}

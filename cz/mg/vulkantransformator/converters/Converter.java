package cz.mg.vulkantransformator.converters;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.EntityType;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.vk.VkEntity;


public interface Converter<C extends CEntity, VK extends VkEntity> {
    public VK convert(C c);

    public default ChainList<VK> convert(ChainList<C> cc){
        ChainList<VK> vv = new ChainList<>();
        for(C c : cc) vv.addLast(convert(c));
        return vv;
    }

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

    public static VkEntity convertEntity(CEntity c){
        return create(c.getEntityType()).convert(c);
    }
}

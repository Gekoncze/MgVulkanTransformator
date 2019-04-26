package cz.mg.vulkantransformator.converters;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.converters.utilities.DatatypeConverter;
import cz.mg.vulkantransformator.converters.utilities.TypenameConverter;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CSystemType;
import cz.mg.vulkantransformator.entities.c.CType;
import cz.mg.vulkantransformator.entities.c.CVariable;
import cz.mg.vulkantransformator.entities.vk.VkVariable;


public class VariableConverter implements Converter<CVariable, VkVariable> {
    @Override
    public VkVariable convert(ChainList<CEntity> entities, CVariable c){
        Text[] simplifiedType = getSimplifiedType(entities, c);
        return new VkVariable(
                c,
                c.getName(),
                DatatypeConverter.cDatatypeToVk(c.getTypename(), c.getPointerCount(), c.getArrayCount()),
                c.getPointerCount(),
                c.getArrayCount(),
                simplifiedType[0],
                simplifiedType[1]
        );
    }

    public static Text[] getSimplifiedType(ChainList<CEntity> entities, CVariable c){
        CEntity fieldTypeEntity = findEntity(entities, c.getTypename());
        if(fieldTypeEntity == null) return new Text[]{ null, null };
        if(c.getDatatype().equals("char*")) return new Text[]{ new Text("String"), null };
        if(c.getArrayCount() != null) return new Text[]{ null, null };
        if(c.getPointerCount() > 0) return new Text[]{ null, null };
        switch (fieldTypeEntity.getEntityType()){
            case ENUM: return new Text[]{ new Text("int"), new Text("jint") };
            case FLAGS:return new Text[]{ new Text("int"), new Text("jint") };
            case FLAG_BITS: return new Text[]{ new Text("int"), new Text("jint") };
            case TYPE: return new Text[]{
                    TypenameConverter.cTypenameToJava((((CType)fieldTypeEntity).getType())),
                    TypenameConverter.cTypenameToJni((((CType)fieldTypeEntity).getType()))
            };
            case SYSTEM_TYPE: return new Text[]{
                    TypenameConverter.cTypenameToJava((((CSystemType)fieldTypeEntity).getName())),
                    TypenameConverter.cTypenameToJni((((CSystemType)fieldTypeEntity).getName()))
            };
            default: return new Text[]{ null, null };
        }
    }

    public static CEntity findEntity(ChainList<CEntity> entities, Text typename){
        for(CEntity entity : entities) if(entity.getName() != null) if(entity.getName().equals(typename)) return entity;
        return null;
    }
}

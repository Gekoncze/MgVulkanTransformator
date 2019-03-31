package cz.mg.vulkantransformator.translators.c;

import cz.mg.vulkantransformator.Configuration;
import cz.mg.vulkantransformator.EntityGroup;
import cz.mg.vulkantransformator.EntityType;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.translators.Translator;


public abstract class CTranslator extends Translator {
    public CTranslator() {
        super(EntityGroup.C);
    }

    @Override
    public String genCode(EntityTriplet entity, String template){
        return super.genCode(entity, template
                .replace("%%CVKPACKAGE%%", genPackageCvk())
        );
    }

    public static String genPackageCvk(){
        return Configuration.getPath(EntityGroup.VK).replace("/", "_");
    }

    public static CTranslator create(EntityType type){
        switch(type){
            case SYSTEM_TYPE: return new CSystemTypeTranslator();
            case TYPE: return new CTypeTranslator();
            case ENUM: return new CEnumTranslator();
            case FLAGS: return new CFlagsTranslator();
            case FLAG_BITS: return new CFlagBitsTranslator();
            case HANDLE: return new CHandleTranslator();
            case STRUCTURE: return new CStructureTranslator();
            case UNION: return new CUnionTranslator();
            case INFO: return new CInfoTranslator();
            case CALLBACK: return new CCallbackTranslator();
            case FUNCTION: return new CFunctionTranslator();
            case MISC: return new CMiscTranslator();
            case DEFINE: return new CDefineTranslator();
            default: throw new UnsupportedOperationException("" + type);
        }
    }
}

package cz.mg.vulkantransformator.translators.c;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.Configuration;
import cz.mg.vulkantransformator.EntityGroup;
import cz.mg.vulkantransformator.EntityType;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.translators.Translator;
import cz.mg.collections.text.Text;


public abstract class CTranslator extends Translator {
    public CTranslator() {
        super(EntityGroup.C);
    }

    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity entity, Text template){
        return super.genCode(entities, entity, template
                .replace("%%CVKPACKAGE%%", genPackageCvk())
        );
    }

    public static Text genPackageCvk(){
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

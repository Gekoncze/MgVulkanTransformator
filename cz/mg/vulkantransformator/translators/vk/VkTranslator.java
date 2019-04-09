package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.Configuration;
import cz.mg.vulkantransformator.EntityGroup;
import cz.mg.vulkantransformator.EntityType;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.translators.Translator;
import cz.mg.vulkantransformator.translators.vk.templates.TemplatesVk;
import cz.mg.collections.text.Text;


public abstract class VkTranslator extends Translator {
    public static final Text arrayTemplate = TemplatesVk.load("parts/Array").replaceEnd("\n", "");
    public static final Text pointerTemplate = TemplatesVk.load("parts/Pointer").replaceEnd("\n", "");
    public static final Text constructorTemplate = TemplatesVk.load("parts/Constructor");
    public static final Text constructorSizeofTemplate = TemplatesVk.load("parts/ConstructorSizeof");

    public VkTranslator() {
        super(EntityGroup.VK);
    }

    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity entity, Text template) {
        return super.genCode(entities, entity, template
                .replace("%%PACKAGE%%", genPackage())
                .replace("%%ARRAY%%", arrayTemplate)
                .replace("%%POINTER%%", pointerTemplate)
                .replace("%%CONSTRUCTOR%%", constructorTemplate)
                .replace("%%CONSTRUCTORSIZEOF%%", constructorSizeofTemplate)
        );
    }

    private Text genPackage(){
        return Configuration.getPath(getGroup()).replace("/", ".");
    }

    public static VkTranslator create(EntityType type){
        switch(type){
            case SYSTEM_TYPE: return new VkSystemTypeTranslator();
            case TYPE: return new VkTypeTranslator();
            case ENUM: return new VkEnumTranslator();
            case FLAGS: return new VkFlagsTranslator();
            case FLAG_BITS: return new VkFlagBitsTranslator();
            case HANDLE: return new VkHandleTranslator();
            case STRUCTURE: return new VkStructureTranslator();
            case UNION: return new VkUnionTranslator();
            case INFO: return new VkInfoTranslator();
            case CALLBACK: return new VkCallbackTranslator();
            case FUNCTION: return new VkFunctionTranslator();
            case MISC: return new VkMiscTranslator();
            case DEFINE: return new VkDefineTranslator();
            default: throw new UnsupportedOperationException("" + type);
        }
    }
}

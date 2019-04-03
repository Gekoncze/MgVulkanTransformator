package cz.mg.vulkantransformator.translators.vk;

import cz.mg.vulkantransformator.Configuration;
import cz.mg.vulkantransformator.EntityGroup;
import cz.mg.vulkantransformator.EntityType;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.translators.Translator;
import cz.mg.vulkantransformator.translators.vk.templates.TemplatesVk;
import cz.mg.vulkantransformator.utilities.StringUtilities;


public abstract class VkTranslator extends Translator {
    public static final String documentationTemplate = StringUtilities.replaceLast(TemplatesVk.load("parts/Documentation"), "\n", "");
    public static final String arrayTemplate = StringUtilities.replaceLast(TemplatesVk.load("parts/Array"), "\n", "");
    public static final String pointerTemplate = StringUtilities.replaceLast(TemplatesVk.load("parts/Pointer"), "\n", "");
    public static final String constructorTemplate = TemplatesVk.load("parts/Constructor");
    public static final String constructorSizeofTemplate = TemplatesVk.load("parts/ConstructorSizeof");

    public VkTranslator() {
        super(EntityGroup.VK);
    }

    @Override
    public String genCode(EntityTriplet entity, String template) {
        return super.genCode(entity, template
                .replace("%%PACKAGE%%", genPackage())
                .replace("%%DOCUMENTATION%%", documentationTemplate)
                .replace("%%ARRAY%%", arrayTemplate)
                .replace("%%POINTER%%", pointerTemplate)
                .replace("%%CONSTRUCTOR%%", constructorTemplate)
                .replace("%%CONSTRUCTORSIZEOF%%", constructorSizeofTemplate)
        );
    }

    private String genPackage(){
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

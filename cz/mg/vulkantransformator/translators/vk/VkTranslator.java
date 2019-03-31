package cz.mg.vulkantransformator.translators.vk;

import cz.mg.vulkantransformator.Configuration;
import cz.mg.vulkantransformator.EntityGroup;
import cz.mg.vulkantransformator.EntityType;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.translators.Translator;
import cz.mg.vulkantransformator.translators.vk.templates.TemplatesVk;
import cz.mg.vulkantransformator.utilities.StringUtilities;


public abstract class VkTranslator extends Translator {
    public VkTranslator() {
        super(EntityGroup.VK);
    }

    @Override
    public String genCode(EntityTriplet entity, String template) {
        return super.genCode(entity, template
                .replace("%%PACKAGE%%", genPackage())
                .replace("%%DOCUMENTATION%%", genDocumentation())
                .replace("%%ARRAY%%", genArrayVk(entity))
                .replace("%%POINTER%%", genPointerVk(entity))
                .replace("%%CONSTRUCTOR%%", TemplatesVk.load("Constructor"))
                .replace("%%CONSTRUCTORSIZEOF%%", TemplatesVk.load("ConstructorSizeof"))
        );
    }

    private String genPackage(){
        return Configuration.getPath(getGroup()).replace("/", ".");
    }

    private String genDocumentation(){
        return StringUtilities.replaceLast(TemplatesVk.load("Documentation"), "\n", "");
    }

    private final String genArrayVk(EntityTriplet e){
        String template = TemplatesVk.load("Array").replace("%%VKNAME%%", e.getVk().getName());
        template = StringUtilities.replaceLast(template, "\n", "");
        return template;
    }

    private final String genPointerVk(EntityTriplet e){
        String template = TemplatesVk.load("Pointer").replace("%%VKNAME%%", e.getVk().getName());
        template = StringUtilities.replaceLast(template, "\n", "");
        return template;
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

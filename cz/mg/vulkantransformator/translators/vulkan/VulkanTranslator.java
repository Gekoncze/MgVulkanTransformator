package cz.mg.vulkantransformator.translators.vulkan;

import cz.mg.vulkantransformator.Configuration;
import cz.mg.vulkantransformator.EntityGroup;
import cz.mg.vulkantransformator.EntityType;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.translators.Translator;
import cz.mg.vulkantransformator.translators.vulkan.templates.TemplatesVulkan;


public abstract class VulkanTranslator extends Translator {
    public VulkanTranslator() {
        super(EntityGroup.VULKAN);
    }

    @Override
    public String genCode(EntityTriplet entity, String template) {
        return super.genCode(entity, template
                .replace("%%PACKAGE%%", genPackage())
                .replace("%%DOCUMENTATION%%", genDocumentation())
        );
    }

    private String genPackage(){
        return Configuration.getPath(getGroup()).replace("/", ".");
    }

    private String genDocumentation(){
        return TemplatesVulkan.load("Documentation");
    }

    public static VulkanTranslator create(EntityType type){
        switch(type){
            case SYSTEM_TYPE: return new VulkanSystemTypeTranslator();
            case TYPE: return new VulkanTypeTranslator();
            case ENUM: return new VulkanEnumTranslator();
            case FLAGS: return new VulkanFlagsTranslator();
            case FLAG_BITS: return new VulkanFlagBitsTranslator();
            case HANDLE: return new VulkanHandleTranslator();
            case STRUCTURE: return new VulkanStructureTranslator();
            case UNION: return new VulkanUnionTranslator();
            case INFO: return new VulkanInfoTranslator();
            case CALLBACK: return new VulkanCallbackTranslator();
            case FUNCTION: return new VulkanFunctionTranslator();
            case MISC: return new VulkanMiscTranslator();
            case DEFINE: return new VulkanDefineTranslator();
            default: throw new UnsupportedOperationException("" + type);
        }
    }
}

package cz.mg.vulkantransformator.translators.vulkan;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.Configuration;
import cz.mg.vulkantransformator.EntityGroup;
import cz.mg.vulkantransformator.EntityType;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.translators.Translator;
import cz.mg.vulkantransformator.translators.vulkan.templates.TemplatesVulkan;
import cz.mg.vulkantransformator.utilities.StringUtilities;


public abstract class VulkanTranslator extends Translator {
    private static final String constructorTemplate = StringUtilities.replaceLast(TemplatesVulkan.load("parts/Constructor"), "\n", "");
    private static final String arrayTemplate = StringUtilities.replaceLast(TemplatesVulkan.load("parts/Array"), "\n", "");
    private static final String pointerTemplate = StringUtilities.replaceLast(TemplatesVulkan.load("parts/Pointer"), "\n", "");

    public VulkanTranslator() {
        super(EntityGroup.VULKAN);
    }

    @Override
    public String genCode(ChainList<EntityTriplet> entities, EntityTriplet entity, String template) {
        return super.genCode(entities, entity, template
                .replace("%%PACKAGE%%", genPackage())
                .replace("%%CONSTRUCTOR%%", constructorTemplate)
                .replace("%%ARRAY%%", arrayTemplate)
                .replace("%%POINTER%%", pointerTemplate)
        );
    }

    private String genPackage(){
        return Configuration.getPath(getGroup()).replace("/", ".");
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

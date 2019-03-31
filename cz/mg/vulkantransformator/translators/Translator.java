package cz.mg.vulkantransformator.translators;

import cz.mg.vulkantransformator.EntityGroup;
import cz.mg.vulkantransformator.EntityType;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.translators.c.CTranslator;
import cz.mg.vulkantransformator.translators.c.templates.TemplatesC;
import cz.mg.vulkantransformator.translators.vk.VkTranslator;
import cz.mg.vulkantransformator.translators.vk.templates.TemplatesVk;
import cz.mg.vulkantransformator.translators.vulkan.VulkanTranslator;
import cz.mg.vulkantransformator.translators.vulkan.templates.TemplatesVulkan;
import cz.mg.vulkantransformator.utilities.StringUtilities;


public abstract class Translator {
    private static final String importStaticTemplate = "import static %%IMPORT%%.*;";
    private final EntityGroup group;

    public Translator(EntityGroup group) {
        this.group = group;
    }

    public EntityGroup getGroup() {
        return group;
    }

    public String genCode(EntityTriplet entity, String template) {
        return template
                .replace("%%CNAME%%", entity.getC() != null ? entity.getC().getName() : "")
                .replace("%%VKNAME%%", entity.getVk() != null ? entity.getVk().getName() : "")
                .replace("%%VULKANNAME%%", entity.getVulkan() != null ? entity.getVulkan().getName() : "");
    }

    public String translate(EntityTriplet entity){
        try {
            return genCode(entity, loadHeader() + loadTemplate(entity.getEntityType()));
        } catch (RuntimeException e){
            throw new RuntimeException(getClass().getSimpleName() + " could not translate entity " + entity.getClass().getSimpleName() + " (" + entity.getEntityType() + ")", e);
        }
    }

    private String loadTemplate(EntityType type){
        try {
            String name = StringUtilities.upperCaseToCammelCase(type.name());
            switch(group){
                case C: return TemplatesC.load(name);
                case VK: return TemplatesVk.load(name);
                case VULKAN: return TemplatesVulkan.load(name);
                default: throw new UnsupportedOperationException("" + group);
            }
        } catch (RuntimeException e){
            return "";
        }
    }

    private String loadHeader(){
        switch(group){
            case C: return TemplatesC.load("Header");
            case VK: return TemplatesVk.load("Header");
            case VULKAN: return TemplatesVulkan.load("Header");
            default: throw new UnsupportedOperationException("" + group);
        }
    }

    public static Translator create(EntityGroup group, EntityType type){
        switch(group){
            case C: return CTranslator.create(type);
            case VK: return VkTranslator.create(type);
            case VULKAN: return VulkanTranslator.create(type);
            default: throw new UnsupportedOperationException("" + group);
        }
    }

    public static String translate(EntityGroup group, EntityTriplet entity){
        return Translator.create(group, entity.getEntityType()).translate(entity);
    }
}

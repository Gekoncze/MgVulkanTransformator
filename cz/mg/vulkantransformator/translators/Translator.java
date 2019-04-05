package cz.mg.vulkantransformator.translators;

import cz.mg.collections.list.chainlist.ChainList;
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
import java.util.HashMap;


public abstract class Translator {
    private static final String cHeaderTemplate = TemplatesC.load("parts/Header");
    private static final String vkHeaderTemplate = TemplatesVk.load("parts/Header");
    private static final String vulkanHeaderTemplate = TemplatesVulkan.load("parts/Header");
    private static final HashMap<String, String> entityTemplateC = new HashMap<>();
    private static final HashMap<String, String> entityTemplateVk = new HashMap<>();
    private static final HashMap<String, String> entityTemplateVulkan = new HashMap<>();
    private final EntityGroup group;

    public Translator(EntityGroup group) {
        this.group = group;
    }

    public EntityGroup getGroup() {
        return group;
    }

    public String genCode(ChainList<EntityTriplet> entities, EntityTriplet entity, String template) {
        return template
                .replace("%%CNAME%%", entity.getC() != null ? entity.getC().getName() : "")
                .replace("%%VKNAME%%", entity.getVk() != null ? entity.getVk().getName() : "")
                .replace("%%VULKANNAME%%", entity.getVulkan() != null ? entity.getVulkan().getName() : "");
    }

    public String translate(ChainList<EntityTriplet> entities, EntityTriplet entity){
        try {
            return genCode(entities, entity, getHeaderTemplate() + getEntityTemplate(entity.getEntityType()));
        } catch (RuntimeException e){
            throw new RuntimeException(getClass().getSimpleName() + " could not translate entity " + entity.getClass().getSimpleName() + " (" + entity.getEntityType() + ")", e);
        }
    }

    private String getEntityTemplate(EntityType type){
        String name = "entities/" + StringUtilities.upperCaseToCammelCase(type.name());
        return getOrLoadEntityTemplateC(group, name);
    }

    private String getOrLoadEntityTemplateC(EntityGroup group, String name){
        HashMap<String, String> map = getEntityTemplateMap(group);
        if(!map.containsKey(name)) map.put(name, loadEntityTemplate(group, name));
        return map.get(name);
    }

    private HashMap getEntityTemplateMap(EntityGroup group){
        switch(group){
            case C: return entityTemplateC;
            case VK: return entityTemplateVk;
            case VULKAN: return entityTemplateVulkan;
            default: throw new UnsupportedOperationException("" + group);
        }
    }

    private String loadEntityTemplate(EntityGroup group, String name){
        try {
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

    private String getHeaderTemplate(){
        switch(group){
            case C: return cHeaderTemplate;
            case VK: return vkHeaderTemplate;
            case VULKAN: return vulkanHeaderTemplate;
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

    public static String translate(EntityGroup group, ChainList<EntityTriplet> entities, EntityTriplet entity){
        return Translator.create(group, entity.getEntityType()).translate(entities, entity);
    }
}

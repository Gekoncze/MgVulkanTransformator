package cz.mg.vulkantransformator.translators;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.EntityGroup;
import cz.mg.vulkantransformator.EntityType;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.translators.c.CTranslator;
import cz.mg.vulkantransformator.translators.c.templates.TemplatesC;
import cz.mg.vulkantransformator.translators.vk.VkTranslator;
import cz.mg.vulkantransformator.translators.vk.templates.TemplatesVk;
import cz.mg.collections.text.Text;
import java.util.HashMap;


public abstract class Translator {
    private static final Text cHeaderTemplate = TemplatesC.load("parts/Header");
    private static final Text vkHeaderTemplate = TemplatesVk.load("parts/Header");
    private static final HashMap<Text, Text> entityTemplateC = new HashMap<>();
    private static final HashMap<Text, Text> entityTemplateVk = new HashMap<>();
    private final EntityGroup group;

    public Translator(EntityGroup group) {
        this.group = group;
    }

    public EntityGroup getGroup() {
        return group;
    }

    public Text genCode(ChainList<VkEntity> entities, VkEntity entity, Text template) {
        if(entity.getC().getName() != null) template = template.replace("%%CNAME%%", entity.getC().getName());
        if(entity.getName() != null) template = template.replace("%%VKNAME%%", entity.getName());
        return template;
    }

    public Text translate(ChainList<VkEntity> entities, VkEntity entity){
        try {
            return genCode(entities, entity, getHeaderTemplate().append(getEntityTemplate(entity.getEntityType())));
        } catch (RuntimeException e){
            throw new RuntimeException(getClass().getSimpleName() + " could not translate entity " + entity.getClass().getSimpleName() + " (" + entity.getEntityType() + ")", e);
        }
    }

    private Text getEntityTemplate(EntityType type){
        Text name = new Text("entities/").append(type.getName().upperToCammel());
        return getOrLoadEntityTemplateC(group, name);
    }

    private Text getOrLoadEntityTemplateC(EntityGroup group, Text name){
        HashMap<Text, Text> map = getEntityTemplateMap(group);
        if(!map.containsKey(name)) map.put(name, loadEntityTemplate(group, name));
        return map.get(name);
    }

    private HashMap getEntityTemplateMap(EntityGroup group){
        switch(group){
            case C: return entityTemplateC;
            case VK: return entityTemplateVk;
            default: throw new UnsupportedOperationException("" + group);
        }
    }

    private Text loadEntityTemplate(EntityGroup group, Text name){
        try {
            switch(group){
                case C: return TemplatesC.load(name);
                case VK: return TemplatesVk.load(name);
                default: throw new UnsupportedOperationException("" + group);
            }
        } catch (RuntimeException e){
            return new Text("");
        }
    }

    private Text getHeaderTemplate(){
        switch(group){
            case C: return cHeaderTemplate;
            case VK: return vkHeaderTemplate;
            default: throw new UnsupportedOperationException("" + group);
        }
    }

    public static Translator create(EntityGroup group, EntityType type){
        switch(group){
            case C: return CTranslator.create(type);
            case VK: return VkTranslator.create(type);
            default: throw new UnsupportedOperationException("" + group);
        }
    }

    public static Text translate(EntityGroup group, ChainList<VkEntity> entities, VkEntity entity){
        return Translator.create(group, entity.getEntityType()).translate(entities, entity);
    }
}

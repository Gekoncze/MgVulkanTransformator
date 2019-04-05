package cz.mg.vulkantransformator.translators.vulkan;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.converters.TypenameConverter;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.TypeTriplet;


public class VulkanTypeTranslator extends VulkanTranslator {
    @Override
    public String genCode(ChainList<EntityTriplet> entities, EntityTriplet e, String template) {
        TypeTriplet entity = (TypeTriplet) e;
        return super.genCode(entities, e, template
                .replace("%JAVATYPE%", TypenameConverter.cTypenameToJava(entity.getC().getType()))
                .replace("%BASE%", entity.getVulkan().getBase())
        );
    }
}

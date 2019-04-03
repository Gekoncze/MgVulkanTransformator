package cz.mg.vulkantransformator.translators.vk;

import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.TypeTriplet;


public class VkTypeTranslator extends VkTranslator {
    @Override
    public String genCode(EntityTriplet e, String template) {
        TypeTriplet entity = (TypeTriplet) e;
        return super.genCode(e, template
                .replace("%BASE%", entity.getVk().getBase())
        );
    }
}

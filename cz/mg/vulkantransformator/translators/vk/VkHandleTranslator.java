package cz.mg.vulkantransformator.translators.vk;

import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.HandleTriplet;


public class VkHandleTranslator extends VkTranslator {
    @Override
    public String genCode(EntityTriplet e, String template) {
        HandleTriplet entity = (HandleTriplet) e;
        return super.genCode(e,template
                .replace("%VKBASE%", entity.getC().isDispatchable() ? "VkDispatchableHandle" : "VkNonDispatchableHandle")
        );
    }
}

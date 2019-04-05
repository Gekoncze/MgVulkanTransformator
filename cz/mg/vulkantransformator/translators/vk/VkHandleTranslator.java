package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.HandleTriplet;


public class VkHandleTranslator extends VkTranslator {
    @Override
    public String genCode(ChainList<EntityTriplet> entities, EntityTriplet e, String template) {
        HandleTriplet entity = (HandleTriplet) e;
        return super.genCode(entities, e,template
                .replace("%VKBASE%", entity.getC().isDispatchable() ? "VkDispatchableHandle" : "VkNonDispatchableHandle")
        );
    }
}

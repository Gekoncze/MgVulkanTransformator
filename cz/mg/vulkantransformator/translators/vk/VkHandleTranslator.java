package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.entities.vk.VkHandle;


public class VkHandleTranslator extends VkTranslator {
    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity e, Text template) {
        VkHandle entity = (VkHandle) e;
        return super.genCode(entities, e,template
                .replace("%VKBASE%", entity.getC().isDispatchable() ? "VkDispatchableHandle" : "VkNonDispatchableHandle")
        );
    }
}

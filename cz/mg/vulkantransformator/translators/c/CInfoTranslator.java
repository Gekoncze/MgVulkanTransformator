package cz.mg.vulkantransformator.translators.c;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.entities.vk.VkInfo;


public class CInfoTranslator extends CTranslator {
    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity e, Text template) {
        VkInfo vk = (VkInfo) e;
        return super.genCode(entities, e, template
                .replace("%PROPERTIES%", CStructureTranslator.genPropertiesC(vk.getC().getFields()))
        );
    }
}

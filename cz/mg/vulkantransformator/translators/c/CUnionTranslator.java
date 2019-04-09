package cz.mg.vulkantransformator.translators.c;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.entities.vk.VkUnion;
import cz.mg.collections.text.Text;


public class CUnionTranslator extends CTranslator {
    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity e, Text template) {
        VkUnion entity = (VkUnion) e;
        return super.genCode(entities, e, template
                .replace("%PROPERTIES%", CStructureTranslator.genPropertiesC(entity.getC().getFields()))
        );
    }
}

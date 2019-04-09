package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.converters.utilities.TypenameConverter;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.entities.vk.VkType;


public class VkTypeTranslator extends VkTranslator {
    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity e, Text template) {
        VkType vk = (VkType) e;
        return super.genCode(entities, e, template
                .replace("%BASE%", vk.getBase())
                .replace("%JAVATYPE%", TypenameConverter.cTypenameToJava(vk.getC().getType()))
        );
    }
}

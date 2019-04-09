package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.entities.vk.VkInfo;
import cz.mg.vulkantransformator.entities.vk.VkVariable;


public class VkInfoTranslator extends VkTranslator {
    private static final Text setTemplateVk = new Text("        setSType(new VkStructureType(VkStructureType.VK_STRUCTURE_TYPE_%VKNAMEUPPER%));");

    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity e, Text template) {
        VkInfo vk = (VkInfo) e;
        return super.genCode(entities, e, template
                .replace("%PARAMETERS%", VkStructureTranslator.genParameters(reduce(vk.getFields())))
                .replace("%SET%", genSet(vk))
                .replace("%SETS%", VkStructureTranslator.genSets(reduce(vk.getFields())))
                .replace("%PROPERTIES%", VkStructureTranslator.genPropertiesVk(vk.getFields()))
        );
    }

    private Text genSet(VkInfo e) {
        return setTemplateVk
                .replace("%VKNAMEUPPER%", genVkNameUpper(e));
    }

    private Text genVkNameUpper(VkInfo vk){
        Text vkNameUpper = vk.getName().replaceFirst("Vk", "").cammelToUpper();
        vkNameUpper = vkNameUpper.replaceLast("2", "_2");
        return vkNameUpper;
    }

    private ChainList<VkVariable> reduce(ChainList<VkVariable> fields){
        ChainList<VkVariable> reduced = new CachedChainList<>(fields);
        reduced.removeFirst();
        return reduced;
    }
}

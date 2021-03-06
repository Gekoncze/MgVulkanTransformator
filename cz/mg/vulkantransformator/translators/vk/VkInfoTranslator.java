package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.entities.vk.VkInfo;
import cz.mg.vulkantransformator.translators.vk.templates.TemplatesVk;


public class VkInfoTranslator extends VkTranslator {
    private static final Text setTemplateVk = new Text("setSType(new VkStructureType(VkStructureType.VK_STRUCTURE_TYPE_%VKNAMEUPPER%));");
    private static final Text infoArrayTemplate = TemplatesVk.load("parts/InfoArray");

    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity e, Text template) {
        VkInfo vk = (VkInfo) e;
        return super.genCode(entities, e, template
                .replace("%PROPERTIES%", VkStructureTranslator.genPropertiesVk(entities, vk.getFields()))
                .replace("%%ARRAY%%", infoArrayTemplate)
                .replace("%SET%", genSet(vk))
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
}

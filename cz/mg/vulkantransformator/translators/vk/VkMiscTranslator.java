package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.translators.vk.templates.TemplatesVk;
import cz.mg.collections.text.Text;


public class VkMiscTranslator extends VkTranslator {
    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity e, Text template) {
        if(e.getName() == null) return null;
        Text header = template;
        Text filename = new Text("misc/").append(e.getName().replaceFirst("Vk", ""));
        template = header.append(TemplatesVk.load(filename));
        return super.genCode(entities, e, template);
    }
}

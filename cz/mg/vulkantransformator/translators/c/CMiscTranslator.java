package cz.mg.vulkantransformator.translators.c;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.translators.c.templates.TemplatesC;


public class CMiscTranslator extends CTranslator {
    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity e, Text template) {
        if(e.getC().getName() == null) return null;
        Text header = template;
        Text filename = new Text("misc/").append(e.getC().getName().replaceFirst("Vk", ""));
        template = header.append(TemplatesC.load(filename));
        return super.genCode(entities, e, template);
    }
}

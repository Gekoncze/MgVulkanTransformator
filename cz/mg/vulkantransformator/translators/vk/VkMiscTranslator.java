package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.Configuration;
import cz.mg.vulkantransformator.Transformator;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.translators.vk.templates.TemplatesVk;
import cz.mg.collections.text.Text;


public class VkMiscTranslator extends VkTranslator {
    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity e, Text template) {
        if(e.getName() == null) return null;
        Text header = template;
        Text filename = new Text("misc/").append(e.getName().replaceFirst("Vk", ""));
        template = header.append(loadCode(filename));
        return super.genCode(entities, e, template);
    }

    private Text loadCode(Text filename) {
        Text code = loadCode_1_0(filename);
        if(code == null) code = TemplatesVk.load(filename);
        return code;
    }

    private Text loadCode_1_0(Text filename){
        try {
            if(Configuration.version == Transformator.Version.VERSION_1_0){
                return TemplatesVk.load(filename.append("_1_0_"));
            }
        } catch (RuntimeException e){}
        return null;
    }
}

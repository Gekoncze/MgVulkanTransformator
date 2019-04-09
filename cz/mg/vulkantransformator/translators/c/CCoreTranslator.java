package cz.mg.vulkantransformator.translators.c;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.Configuration;
import cz.mg.vulkantransformator.EntityGroup;
import cz.mg.vulkantransformator.entities.vk.VkDefine;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.translators.vk.VkCoreTranslator;
import cz.mg.vulkantransformator.translators.c.templates.TemplatesC;
import cz.mg.collections.text.Text;


public class CCoreTranslator {
    private static final Text cDefineTemplate = TemplatesC.load("core/Define");
    private static final Text headerTemplate = TemplatesC.load("parts/Header");

    public static Text translateC(ChainList<VkEntity> entities){
        return headerTemplate.append(genDefinesC(entities));
    }

    private static Text genDefinesC(ChainList<VkEntity> entities){
        ChainList<Text> defines = new CachedChainList<>();
        for(VkEntity entity : entities){
            if(entity instanceof VkDefine){
                VkDefine define = (VkDefine) entity;
                if(!define.isString()){
                    defines.addLast(cDefineTemplate
                            .replace("%%CVKPACKAGE%%", genPackageCvk())
                            .replace("%%VKGETNAME%%", VkCoreTranslator.genVkGetName(define))
                            .replace("%%CNAME%%", define.getC().getName())
                    );
                }
            }
        }
        return new Text(defines.toString("\n"));
    }

    public static Text genPackageCvk(){
        return Configuration.getPath(EntityGroup.VK).replace("/", "_");
    }
}

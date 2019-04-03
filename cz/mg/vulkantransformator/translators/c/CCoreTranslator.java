package cz.mg.vulkantransformator.translators.c;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.Configuration;
import cz.mg.vulkantransformator.EntityGroup;
import cz.mg.vulkantransformator.entities.DefineTriplet;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.translators.vk.VkCoreTranslator;
import cz.mg.vulkantransformator.translators.c.templates.TemplatesC;


public class CCoreTranslator {
    private static final String cDefineTemplate = TemplatesC.load("core/Define");
    private static final String headerTemplate = TemplatesC.load("parts/Header");

    public static String translateC(ChainList<EntityTriplet> entities){
        return headerTemplate + genDefinesC(entities);
    }

    private static String genDefinesC(ChainList<EntityTriplet> entities){
        ChainList<String> defines = new CachedChainList<>();
        for(EntityTriplet entity : entities){
            if(entity instanceof DefineTriplet){
                DefineTriplet define = (DefineTriplet) entity;
                if(!define.isString()){
                    defines.addLast(cDefineTemplate
                            .replace("%%CVKPACKAGE%%", genPackageCvk())
                            .replace("%%VKGETNAME%%", VkCoreTranslator.genVkGetName(define))
                            .replace("%%CNAME%%", define.getC().getName())
                    );
                }
            }
        }
        return defines.toString("\n");
    }

    public static String genPackageCvk(){
        return Configuration.getPath(EntityGroup.VK).replace("/", "_");
    }
}

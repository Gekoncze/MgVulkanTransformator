package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.UnionTriplet;
import cz.mg.vulkantransformator.entities.vk.VkVariable;
import cz.mg.vulkantransformator.translators.vk.templates.TemplatesVk;


public class VkUnionTranslator extends VkTranslator {
    private static final String constructorTemplate = TemplatesVk.load("parts/UnionConstructor");

    @Override
    public String genCode(EntityTriplet e, String template) {
        UnionTriplet entity = (UnionTriplet) e;
        return super.genCode(e, template
                .replace("%CONSTRUCTORS%", constructorsToStringVk(entity.getVk().getFields()))
                .replace("%PROPERTIES%", VkStructureTranslator.genPropertiesVk(entity.getVk().getFields()))
        );
    }

    public static String constructorsToStringVk(ChainList<VkVariable> fields){
        ChainList<String> constructors = new CachedChainList<>();
        for(VkVariable field : fields) constructors.addLast(constructorToStringVk(field));
        return constructors.toString("\n");
    }

    public static String constructorToStringVk(VkVariable field){
        return constructorTemplate
                .replace("%VKPROPERTYTYPE%", field.getTypename())
                .replace("%VKPROPERTYNAME%", field.getName())
                .replace("%SETTER%", VkStructureTranslator.genSet(field));
    }
}

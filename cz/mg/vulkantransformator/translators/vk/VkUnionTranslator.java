package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.entities.vk.VkUnion;
import cz.mg.vulkantransformator.entities.vk.VkVariable;
import cz.mg.vulkantransformator.translators.vk.templates.TemplatesVk;


public class VkUnionTranslator extends VkTranslator {
    private static final Text constructorTemplate = TemplatesVk.load("parts/UnionConstructor");

    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity e, Text template) {
        VkUnion vk = (VkUnion) e;
        return super.genCode(entities, e, template
                .replace("%CONSTRUCTORS%", constructorsToTextVk(vk.getFields()))
                .replace("%PROPERTIES%", VkStructureTranslator.genPropertiesVk(vk.getFields()))
        );
    }

    public static Text constructorsToTextVk(ChainList<VkVariable> fields){
        ChainList<Text> constructors = new CachedChainList<>();
        for(VkVariable field : fields) constructors.addLast(constructorToTextVk(field));
        return constructors.toText("\n");
    }

    public static Text constructorToTextVk(VkVariable field){
        return constructorTemplate
                .replace("%VKPROPERTYTYPE%", field.getTypename())
                .replace("%VKPROPERTYNAME%", field.getName())
                .replace("%SETTER%", VkStructureTranslator.genSet(field));
    }
}

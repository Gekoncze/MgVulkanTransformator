package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.InfoTriplet;
import cz.mg.vulkantransformator.entities.vk.VkField;
import cz.mg.vulkantransformator.utilities.StringUtilities;


public class VkInfoTranslator extends VkTranslator {
    private static final String setTemplateVk = "        setSType(new VkStructureType(VkStructureType.VK_STRUCTURE_TYPE_%VKNAMEUPPER%));";

    @Override
    public String genCode(EntityTriplet e, String template) {
        InfoTriplet entity = (InfoTriplet) e;
        return super.genCode(e, template
                .replace("%PARAMETERS%", VkStructureTranslator.genParameters(reduce(entity.getVk().getFields())))
                .replace("%SET%", genSet(entity))
                .replace("%SETS%", VkStructureTranslator.genSets(reduce(entity.getVk().getFields())))
                .replace("%PROPERTIES%", VkStructureTranslator.genPropertiesVk(entity.getVk().getFields()))
        );
    }

    private String genSet(InfoTriplet e) {
        return setTemplateVk
                .replace("%VKNAMEUPPER%", genVkNameUpper(e));
    }

    private String genVkNameUpper(InfoTriplet e){
        String vkNameUpper = StringUtilities.cammelCaseToUpperCase(e.getVk().getName().replaceFirst("Vk", ""));
        vkNameUpper = StringUtilities.replaceLast(vkNameUpper, "2", "_2");
        return vkNameUpper;
    }

    private ChainList<VkField> reduce(ChainList<VkField> fields){
        ChainList<VkField> reduced = new CachedChainList<>(fields);
        reduced.removeFirst();
        return reduced;
    }
}

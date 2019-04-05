package cz.mg.vulkantransformator.translators.vulkan;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.InfoTriplet;
import cz.mg.vulkantransformator.entities.vulkan.VulkanVariable;


public class VulkanInfoTranslator extends VulkanTranslator {
    @Override
    public String genCode(ChainList<EntityTriplet> entities, EntityTriplet e, String template) {
        InfoTriplet entity = (InfoTriplet) e;
        return super.genCode(entities, e, template
                .replace("%PARAMETERS%", VulkanStructureTranslator.genParameters(reduce(entity.getVulkan().getFields())))
                .replace("%ARGUMENTS%", VulkanStructureTranslator.genArguments(reduce(entity.getVulkan().getFields())))
                .replace("%PROPERTIES%", VulkanStructureTranslator.genProperties(entity.getVulkan().getFields()))
        );
    }

    private ChainList<VulkanVariable> reduce(ChainList<VulkanVariable> fields){
        ChainList<VulkanVariable> reduced = new CachedChainList<>(fields);
        reduced.removeFirst();
        return reduced;
    }
}

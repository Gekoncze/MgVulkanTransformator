package cz.mg.vulkantransformator.translators.vulkan;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.EnumTriplet;
import cz.mg.vulkantransformator.entities.vulkan.VulkanValue;


public class VulkanEnumTranslator extends VulkanTranslator {
    private static final String fieldTemplate = "    public static final int %VKVALUENAME% = %VKVALUE%;";
    private static final String caseTemplate =  "        if(getValue() == %VKVALUENAME%) return \"%VKVALUENAME%\";";

    @Override
    public String genCode(EntityTriplet e, String template) {
        return super.genCode(e, template
                .replace("%FIELDS%", genFields((EnumTriplet) e))
                .replace("%CASES%", genCases((EnumTriplet) e))
        );
    }

    private String genFields(EnumTriplet e){
        ChainList<String> fields = new ChainList<>();
        for(VulkanValue value : e.getVulkan().getValues()){
            fields.addLast(fieldTemplate
                    .replace("%VKVALUENAME%", value.getName())
                    .replace("%VKVALUE%", value.getValue())
            );
        }
        return fields.toString("\n");
    }

    private String genCases(EnumTriplet e){
        ChainList<String> cases = new CachedChainList<>();
        for(VulkanValue value : e.getVulkan().getValues()){
            cases.addLast(caseTemplate
                    .replace("%VKVALUENAME%", value.getName())
            );
        }
        return cases.toString("\n");
    }
}

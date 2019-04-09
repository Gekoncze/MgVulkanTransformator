package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.entities.vk.VkFlagBits;
import cz.mg.vulkantransformator.entities.vk.VkValue;


public class VkFlagBitsTranslator extends VkTranslator {
    private static final Text fieldTemplate = new Text("    public static final int %VKVALUENAME% = %VKVALUE%;");
    private static final Text caseTemplate =  new Text("        if(getValue() == %VKVALUENAME%) s += \"%VKVALUENAME%\";");

    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity e, Text template) {
        return super.genCode(entities, e, template
                .replace("%FIELDS%", genFields((VkFlagBits) e))
                .replace("%CASES%", genCases((VkFlagBits) e))
        );
    }

    private Text genFields(VkFlagBits vk){
        ChainList<Text> fields = new CachedChainList<>();
        for(VkValue value : vk.getValues()){
            fields.addLast(fieldTemplate
                    .replace("%VKVALUENAME%", value.getName())
                    .replace("%VKVALUE%", value.getValue())
            );
        }
        return fields.toText("\n");
    }

    private Text genCases(VkFlagBits vk){
        ChainList<Text> cases = new CachedChainList<>();
        for(VkValue value : vk.getValues()){
            cases.addLast(caseTemplate
                    .replace("%VKVALUENAME%", value.getName())
            );
        }
        return cases.toText("\n");
    }
}

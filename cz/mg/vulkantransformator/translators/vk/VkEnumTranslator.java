package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.entities.vk.VkEnum;
import cz.mg.vulkantransformator.entities.vk.VkValue;
import cz.mg.collections.text.Text;


public class VkEnumTranslator extends VkTranslator {
    private static final Text fieldTemplate = new Text("    public static final int %VKVALUENAME% = %VKVALUE%;");
    private static final Text caseTemplate =  new Text("        if(getValue() == %VKVALUENAME%) return \"%VKVALUENAME%\";");

    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity e, Text template) {
        return super.genCode(entities, e, template
                .replace("%FIELDS%", genFields((VkEnum) e))
                .replace("%CASES%", genCases((VkEnum) e))
        );
    }

    private Text genFields(VkEnum vk){
        ChainList<Text> fields = new ChainList<>();
        for(Object value : vk.getValues()){
            VkValue vvalue = (VkValue) value; // quick fix for java or intellij idea bug
            fields.addLast(fieldTemplate
                    .replace("%VKVALUENAME%", vvalue.getName())
                    .replace("%VKVALUE%", vvalue.getValue())
            );
        }
        return fields.toText("\n");
    }

    private Text genCases(VkEnum vk){
        ChainList<Text> cases = new CachedChainList<>();
        for(Object value : vk.getValues()){
            VkValue vvalue = (VkValue) value; // quick fix for java or intellij idea bug
            cases.addLast(caseTemplate
                    .replace("%VKVALUENAME%", vvalue.getName())
            );
        }
        return cases.toText("\n");
    }
}

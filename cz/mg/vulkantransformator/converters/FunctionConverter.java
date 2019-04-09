package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.converters.utilities.TypenameConverter;
import cz.mg.vulkantransformator.entities.c.CFunction;
import cz.mg.vulkantransformator.entities.vk.VkFunction;
import cz.mg.collections.text.Text;


public class FunctionConverter implements Converter<CFunction, VkFunction> {
    private static final VariableConverter CONVERTER = new VariableConverter();

    @Override
    public VkFunction convert(CFunction c) {
        return new VkFunction(
                c,
                TypenameConverter.cTypenameToVk(c.getName()),
                convertCNameToVkCallName(c.getName()),
                CONVERTER.convert(c.getReturnType()),
                CONVERTER.convert(c.getParameters())
        );
    }

    public static Text convertCNameToVkCallName(Text vkName){
        return vkName.replaceBegin("PFN_", "");
    }
}

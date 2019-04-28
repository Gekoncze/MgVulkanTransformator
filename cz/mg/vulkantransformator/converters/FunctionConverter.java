package cz.mg.vulkantransformator.converters;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.converters.utilities.TypenameConverter;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CFunction;
import cz.mg.vulkantransformator.entities.vk.VkFunction;


public class FunctionConverter implements Converter<CFunction, VkFunction> {
    private static final VariableConverter CONVERTER = new VariableConverter();

    @Override
    public VkFunction convert(ChainList<CEntity> entities, CFunction c) {
        return new VkFunction(
                c,
                TypenameConverter.cTypenameToVk(c.getName()),
                c.getCallName(),
                CONVERTER.convert(entities, c.getReturnType()),
                CONVERTER.convert(entities, c.getParameters())
        );
    }
}

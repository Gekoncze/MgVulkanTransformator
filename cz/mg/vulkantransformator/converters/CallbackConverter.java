package cz.mg.vulkantransformator.converters;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.converters.utilities.TypenameConverter;
import cz.mg.vulkantransformator.entities.c.CCallback;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.vk.VkCallback;


public class CallbackConverter implements Converter<CCallback, VkCallback> {
    private static final VariableConverter CONVERTER = new VariableConverter();

    @Override
    public VkCallback convert(ChainList<CEntity> entities, CCallback c) {
        return new VkCallback(
                c,
                TypenameConverter.cTypenameToVk(c.getName()),
                c.getCallName(),
                CONVERTER.convert(entities, c.getReturnType()),
                CONVERTER.convert(entities, c.getParameters())
        );
    }
}

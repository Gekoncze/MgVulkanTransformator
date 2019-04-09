package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.converters.utilities.TypenameConverter;
import cz.mg.vulkantransformator.entities.c.CCallback;
import cz.mg.vulkantransformator.entities.vk.VkCallback;


public class CallbackConverter implements Converter<CCallback, VkCallback> {
    private static final VariableConverter CONVERTER = new VariableConverter();

    @Override
    public VkCallback convert(CCallback c) {
        return new VkCallback(
                c,
                TypenameConverter.cTypenameToVk(c.getName()),
                CONVERTER.convert(c.getReturnType()),
                CONVERTER.convert(c.getParameters())
        );
    }
}

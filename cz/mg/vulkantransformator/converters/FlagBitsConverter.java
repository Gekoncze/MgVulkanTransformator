package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.converters.utilities.TypenameConverter;
import cz.mg.vulkantransformator.entities.c.CFlagBits;
import cz.mg.vulkantransformator.entities.vk.VkFlagBits;


public class FlagBitsConverter implements Converter<CFlagBits, VkFlagBits> {
    private static final ValueConverter CONVERTER = new ValueConverter();

    @Override
    public VkFlagBits convert(CFlagBits c) {
        return new VkFlagBits(
                c,
                TypenameConverter.cTypenameToVk(c.getName()),
                CONVERTER.convert(c.getValues())
        );
    }
}

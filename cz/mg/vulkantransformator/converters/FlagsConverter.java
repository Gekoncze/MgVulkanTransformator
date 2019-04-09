package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.converters.utilities.TypenameConverter;
import cz.mg.vulkantransformator.entities.c.CFlags;
import cz.mg.vulkantransformator.entities.vk.VkFlags;


public class FlagsConverter implements Converter<CFlags, VkFlags> {
    @Override
    public VkFlags convert(CFlags c) {
        return new VkFlags(
                c,
                TypenameConverter.cTypenameToVk(c.getName())
        );
    }
}

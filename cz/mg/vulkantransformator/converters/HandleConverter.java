package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.converters.utilities.TypenameConverter;
import cz.mg.vulkantransformator.entities.c.CHandle;
import cz.mg.vulkantransformator.entities.vk.VkHandle;


public class HandleConverter implements Converter<CHandle, VkHandle> {
    @Override
    public VkHandle convert(CHandle c) {
        return new VkHandle(c, TypenameConverter.cTypenameToVk(c.getName()));
    }
}

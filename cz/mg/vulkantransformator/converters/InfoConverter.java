package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.converters.utilities.TypenameConverter;
import cz.mg.vulkantransformator.entities.c.CInfo;
import cz.mg.vulkantransformator.entities.vk.VkInfo;


public class InfoConverter implements Converter<CInfo, VkInfo> {
    private static final VariableConverter CONVERTER = new VariableConverter();

    @Override
    public VkInfo convert(CInfo c) {
        return new VkInfo(
                c,
                TypenameConverter.cTypenameToVk(c.getName()),
                CONVERTER.convert(c.getFields())
        );
    }
}

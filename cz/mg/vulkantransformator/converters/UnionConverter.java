package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.converters.utilities.TypenameConverter;
import cz.mg.vulkantransformator.entities.c.CUnion;
import cz.mg.vulkantransformator.entities.vk.VkUnion;


public class UnionConverter implements Converter<CUnion, VkUnion> {
    private static final VariableConverter CONVERTER = new VariableConverter();

    @Override
    public VkUnion convert(CUnion c) {
        return new VkUnion(
                c,
                TypenameConverter.cTypenameToVk(c.getName()),
                CONVERTER.convert(c.getFields())
        );
    }
}

package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.converters.utilities.TypenameConverter;
import cz.mg.vulkantransformator.entities.c.CStructure;
import cz.mg.vulkantransformator.entities.vk.VkStructure;


public class StructureConverter implements Converter<CStructure, VkStructure> {
    private static final VariableConverter CONVERTER = new VariableConverter();

    @Override
    public VkStructure convert(CStructure c) {
        return new VkStructure(
                c,
                TypenameConverter.cTypenameToVk(c.getName()),
                CONVERTER.convert(c.getFields())
        );
    }
}

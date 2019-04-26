package cz.mg.vulkantransformator.converters;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.converters.utilities.TypenameConverter;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CStructure;
import cz.mg.vulkantransformator.entities.vk.VkStructure;


public class StructureConverter implements Converter<CStructure, VkStructure> {
    private static final VariableConverter CONVERTER = new VariableConverter();

    @Override
    public VkStructure convert(ChainList<CEntity> entities, CStructure c) {
        return new VkStructure(
                c,
                TypenameConverter.cTypenameToVk(c.getName()),
                CONVERTER.convert(entities, c.getFields())
        );
    }
}

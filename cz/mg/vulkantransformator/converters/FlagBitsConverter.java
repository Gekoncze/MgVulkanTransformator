package cz.mg.vulkantransformator.converters;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.converters.utilities.TypenameConverter;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CFlagBits;
import cz.mg.vulkantransformator.entities.vk.VkFlagBits;


public class FlagBitsConverter implements Converter<CFlagBits, VkFlagBits> {
    private static final ValueConverter CONVERTER = new ValueConverter();

    @Override
    public VkFlagBits convert(ChainList<CEntity> entities, CFlagBits c) {
        return new VkFlagBits(
                c,
                TypenameConverter.cTypenameToVk(c.getName()),
                CONVERTER.convert(entities, c.getValues())
        );
    }
}

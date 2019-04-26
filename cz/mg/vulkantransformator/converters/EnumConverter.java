package cz.mg.vulkantransformator.converters;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.converters.utilities.TypenameConverter;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CEnum;
import cz.mg.vulkantransformator.entities.vk.VkEnum;


public class EnumConverter implements Converter<CEnum, VkEnum> {
    private static final ValueConverter CONVERTER = new ValueConverter();

    @Override
    public VkEnum convert(ChainList<CEntity> entities, CEnum c) {
        return new VkEnum(
                c,
                TypenameConverter.cTypenameToVk(c.getName()),
                CONVERTER.convert(entities, c.getValues())
        );
    }
}

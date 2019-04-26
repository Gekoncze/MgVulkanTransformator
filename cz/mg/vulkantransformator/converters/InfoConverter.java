package cz.mg.vulkantransformator.converters;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.converters.utilities.TypenameConverter;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CInfo;
import cz.mg.vulkantransformator.entities.vk.VkInfo;


public class InfoConverter implements Converter<CInfo, VkInfo> {
    private static final VariableConverter CONVERTER = new VariableConverter();

    @Override
    public VkInfo convert(ChainList<CEntity> entities, CInfo c) {
        return new VkInfo(
                c,
                TypenameConverter.cTypenameToVk(c.getName()),
                CONVERTER.convert(entities, c.getFields())
        );
    }
}

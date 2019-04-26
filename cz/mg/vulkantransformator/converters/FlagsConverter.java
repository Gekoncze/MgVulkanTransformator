package cz.mg.vulkantransformator.converters;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.converters.utilities.TypenameConverter;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CFlags;
import cz.mg.vulkantransformator.entities.vk.VkFlags;


public class FlagsConverter implements Converter<CFlags, VkFlags> {
    @Override
    public VkFlags convert(ChainList<CEntity> entities, CFlags c) {
        return new VkFlags(
                c,
                TypenameConverter.cTypenameToVk(c.getName())
        );
    }
}

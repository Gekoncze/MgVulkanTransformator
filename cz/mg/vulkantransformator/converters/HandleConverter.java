package cz.mg.vulkantransformator.converters;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.converters.utilities.TypenameConverter;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CHandle;
import cz.mg.vulkantransformator.entities.vk.VkHandle;


public class HandleConverter implements Converter<CHandle, VkHandle> {
    @Override
    public VkHandle convert(ChainList<CEntity> entities, CHandle c) {
        return new VkHandle(c, TypenameConverter.cTypenameToVk(c.getName()));
    }
}

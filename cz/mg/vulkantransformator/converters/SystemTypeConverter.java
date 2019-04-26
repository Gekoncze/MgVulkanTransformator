package cz.mg.vulkantransformator.converters;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.converters.utilities.TypenameConverter;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CSystemType;
import cz.mg.vulkantransformator.entities.vk.VkSystemType;


public class SystemTypeConverter implements Converter<CSystemType, VkSystemType> {
    @Override
    public VkSystemType convert(ChainList<CEntity> entities, CSystemType c) {
        return new VkSystemType(c, TypenameConverter.cTypenameToVk(c.getName()));
    }
}

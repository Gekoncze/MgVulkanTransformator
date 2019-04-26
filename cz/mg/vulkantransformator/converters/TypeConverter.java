package cz.mg.vulkantransformator.converters;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.converters.utilities.TypenameConverter;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CType;
import cz.mg.vulkantransformator.entities.vk.VkType;


public class TypeConverter implements Converter<CType, VkType> {
    @Override
    public VkType convert(ChainList<CEntity> entities, CType c) {
        return new VkType(
                c,
                TypenameConverter.cTypenameToVk(c.getName()),
                TypenameConverter.cTypenameToVk(c.getType())
        );
    }
}

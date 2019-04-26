package cz.mg.vulkantransformator.converters;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CDefine;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.vk.VkDefine;


public class DefineConverter implements Converter<CDefine, VkDefine> {
    @Override
    public VkDefine convert(ChainList<CEntity> entities, CDefine c) {
        return new VkDefine(
                c,
                c.getName(),
                c.getValue()
        );
    }
}

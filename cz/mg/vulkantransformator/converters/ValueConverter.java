package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.entities.c.CValue;
import cz.mg.vulkantransformator.entities.vk.VkValue;


public class ValueConverter implements Converter<CValue, VkValue> {
    @Override
    public VkValue convert(CValue c){
        return new VkValue(
                c,
                c.getName(),
                c.getValue()
        );
    }
}

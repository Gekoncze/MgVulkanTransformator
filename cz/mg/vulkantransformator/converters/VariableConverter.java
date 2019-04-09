package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.converters.utilities.DatatypeConverter;
import cz.mg.vulkantransformator.entities.c.CVariable;
import cz.mg.vulkantransformator.entities.vk.VkVariable;


public class VariableConverter implements Converter<CVariable, VkVariable> {
    @Override
    public VkVariable convert(CVariable c){
        return new VkVariable(
                c,
                c.getName(),
                DatatypeConverter.cDatatypeToVk(c.getTypename(), c.getPointerCount(), c.getArrayCount()),
                c.getPointerCount(),
                c.getArrayCount()
        );
    }
}

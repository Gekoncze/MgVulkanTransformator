package cz.mg.vulkantransformator.entities.vk;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CFunction;
import cz.mg.collections.text.Text;


public class VkFunction<C extends CFunction> extends VkEntity<C> {
    private final Text callName;
    private final VkVariable returnType;
    private final ChainList<VkVariable> parameters;

    public VkFunction(C c, Text name, Text callName, VkVariable returnType, ChainList<VkVariable> parameters) {
        super(c, name);
        this.callName = callName;
        this.returnType = returnType;
        this.parameters = parameters;
    }

    public Text getCallName() {
        return callName;
    }

    public VkVariable getReturnType() {
        return returnType;
    }

    public ChainList<VkVariable> getParameters() {
        return parameters;
    }
}

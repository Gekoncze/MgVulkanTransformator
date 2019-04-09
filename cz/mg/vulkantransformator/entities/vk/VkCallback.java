package cz.mg.vulkantransformator.entities.vk;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CCallback;
import cz.mg.collections.text.Text;


public class VkCallback extends VkFunction<CCallback> {
    public VkCallback(CCallback c, Text name, VkVariable returnType, ChainList<VkVariable> parameters) {
        super(c, name, null, returnType, parameters);
    }
}

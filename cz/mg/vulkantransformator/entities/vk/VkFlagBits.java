package cz.mg.vulkantransformator.entities.vk;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CFlagBits;
import cz.mg.collections.text.Text;


public class VkFlagBits extends VkEnum<CFlagBits> {
    public VkFlagBits(CFlagBits cFlagBits, Text name, ChainList<VkValue> values) {
        super(cFlagBits, name, values);
    }
}

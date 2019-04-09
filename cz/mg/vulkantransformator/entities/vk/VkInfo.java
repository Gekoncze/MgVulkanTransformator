package cz.mg.vulkantransformator.entities.vk;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CInfo;
import cz.mg.collections.text.Text;


public class VkInfo extends VkStructure<CInfo> {
    public VkInfo(CInfo c, Text name, ChainList<VkVariable> fields) {
        super(c, name, fields);
    }
}

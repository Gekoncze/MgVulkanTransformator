package cz.mg.vulkantransformator.entities.vk;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CUnion;
import cz.mg.collections.text.Text;


public class VkUnion extends VkStructure<CUnion> {
    public VkUnion(CUnion c, Text name, ChainList<VkVariable> fields) {
        super(c, name, fields);
    }
}

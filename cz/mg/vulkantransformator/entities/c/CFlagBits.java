package cz.mg.vulkantransformator.entities.c;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.EntityType;
import cz.mg.collections.text.Text;


public class CFlagBits extends CEnum {
    public CFlagBits(Text name, ChainList<CValue> values) {
        super(name, values);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.FLAG_BITS;
    }
}

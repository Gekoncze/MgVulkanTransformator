package cz.mg.vulkantransformator.entities.c;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.EntityType;
import cz.mg.collections.text.Text;


public class CInfo extends CStructure {
    public CInfo(Text name, ChainList<CVariable> fields) {
        super(name, fields);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.INFO;
    }
}

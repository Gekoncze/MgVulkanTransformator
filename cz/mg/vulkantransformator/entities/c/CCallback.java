package cz.mg.vulkantransformator.entities.c;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.EntityType;
import cz.mg.collections.text.Text;


public class CCallback extends CFunction {
    public CCallback(Text name, CVariable returnType, ChainList<CVariable> parameters) {
        super(name, returnType, parameters);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.CALLBACK;
    }
}

package cz.mg.vulkantransformator.entities.c;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.EntityType;
import cz.mg.collections.text.Text;


public class CEnum extends CEntity {
    private final ChainList<CValue> values;

    public CEnum(Text name, ChainList<CValue> values) {
        super(name);
        this.values = values;
    }

    public ChainList<CValue> getValues() {
        return values;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ENUM;
    }
}

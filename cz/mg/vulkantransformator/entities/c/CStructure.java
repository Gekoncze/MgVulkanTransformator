package cz.mg.vulkantransformator.entities.c;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.EntityType;
import cz.mg.collections.text.Text;


public class CStructure extends CEntity {
    private final ChainList<CVariable> fields;

    public CStructure(Text name, ChainList<CVariable> fields) {
        super(name);
        this.fields = fields;
    }

    public ChainList<CVariable> getFields() {
        return fields;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.STRUCTURE;
    }
}

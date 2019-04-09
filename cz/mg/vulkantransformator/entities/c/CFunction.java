package cz.mg.vulkantransformator.entities.c;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.EntityType;
import cz.mg.collections.text.Text;


public class CFunction extends CEntity {
    private final CVariable returnType;
    private final ChainList<CVariable> parameters;

    public CFunction(Text name, CVariable returnType, ChainList<CVariable> parameters) {
        super(name);
        this.returnType = returnType;
        this.parameters = parameters;
    }

    public CVariable getReturnType() {
        return returnType;
    }

    public ChainList<CVariable> getParameters() {
        return parameters;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.FUNCTION;
    }
}

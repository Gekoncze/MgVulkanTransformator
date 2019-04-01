package cz.mg.vulkantransformator.entities.c;

import cz.mg.collections.list.chainlist.ChainList;


public class CFunction implements CEntity {
    private final CVariable returnType;
    private final String name;
    private final ChainList<CVariable> parameters = new ChainList<>();

    public CFunction(CVariable returnType, String name) {
        this.returnType = returnType;
        this.name = name;
    }

    public CVariable getReturnType() {
        return returnType;
    }

    @Override
    public String getName() {
        return name;
    }

    public ChainList<CVariable> getParameters() {
        return parameters;
    }
}

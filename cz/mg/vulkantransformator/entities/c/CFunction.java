package cz.mg.vulkantransformator.entities.c;

import cz.mg.collections.list.chainlist.ChainList;


public class CFunction implements CEntity {
    private final CParameter returnType;
    private final String name;
    private final ChainList<CParameter> parameters = new ChainList<>();

    public CFunction(CParameter returnType, String name) {
        this.returnType = returnType;
        this.name = name;
    }

    public CParameter getReturnType() {
        return returnType;
    }

    @Override
    public String getName() {
        return name;
    }

    public ChainList<CParameter> getParameters() {
        return parameters;
    }
}

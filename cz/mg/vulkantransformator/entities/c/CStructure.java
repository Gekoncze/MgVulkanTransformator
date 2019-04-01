package cz.mg.vulkantransformator.entities.c;

import cz.mg.collections.list.chainlist.ChainList;


public class CStructure implements CEntity {
    private final String name;
    private final ChainList<CVariable> fields = new ChainList<>();

    public CStructure(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public ChainList<CVariable> getFields() {
        return fields;
    }
}

package cz.mg.vulkantransformator.entities.c;

import cz.mg.collections.list.chainlist.ChainList;


public class CEnum implements CEntity {
    private final String name;
    private final ChainList<CValue> values = new ChainList<>();

    public CEnum(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public ChainList<CValue> getValues() {
        return values;
    }
}

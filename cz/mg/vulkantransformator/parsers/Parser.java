package cz.mg.vulkantransformator.parsers;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CEntity;


public interface Parser {
    public CEntity parse(ChainList<String> lines, int i);
}

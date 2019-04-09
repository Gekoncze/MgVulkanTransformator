package cz.mg.vulkantransformator.parsers;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.collections.text.Text;


public interface Parser {
    public CEntity parse(ChainList<Text> lines, int i);

    public default ChainList<Text> parseChildren(ChainList<Text> lines, int i){
        ChainList<Text> children = new ChainList<>();
        for(i = i + 1; i < lines.count(); i++){
            Text line = lines.get(i);
            if(line.startsWith("    ")) children.addLast(line);
            else break;
        }
        return children;
    }
}

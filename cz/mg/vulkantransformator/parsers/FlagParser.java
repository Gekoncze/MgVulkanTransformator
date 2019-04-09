package cz.mg.vulkantransformator.parsers;

import cz.mg.collections.array.Array;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CFlags;
import cz.mg.collections.text.Text;


public class FlagParser implements Parser {
    /*
        typedef VkFlags VkImageUsageFlags;
    */
    @Override
    public CEntity parse(ChainList<Text> lines, int i) {
        Text line = lines.get(i);
        if(!line.startsWith("    ")){
            if(line.startsWith("typedef VkFlags ")){
                Array<Text> parts = line.split();
                if(parts.count() == 3){
                    return new CFlags(parts.get(2).replace(";", ""));
                }
            }
        }
        return null;
    }
}

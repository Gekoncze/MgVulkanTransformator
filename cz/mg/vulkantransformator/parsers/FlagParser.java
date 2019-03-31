package cz.mg.vulkantransformator.parsers;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CFlags;
import cz.mg.vulkantransformator.utilities.StringUtilities;


public class FlagParser implements Parser {
    /*
        typedef VkFlags VkImageUsageFlags;
    */
    @Override
    public CEntity parse(ChainList<String> lines, int i) {
        String line = lines.get(i);
        if(!line.startsWith("    ")){
            if(line.startsWith("typedef VkFlags ")){
                String[] parts = StringUtilities.split(line);
                if(parts.length == 3){
                    return new CFlags(parts[2].replace(";", ""));
                }
            }
        }
        return null;
    }
}

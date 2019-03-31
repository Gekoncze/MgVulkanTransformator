package cz.mg.vulkantransformator.parsers;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CType;
import cz.mg.vulkantransformator.utilities.StringUtilities;


public class TypeParser implements Parser {
    /*
        typedef uint32_t VkFlags;
    */
    @Override
    public CEntity parse(ChainList<String> lines, int i) {
        String line = lines.get(i);
        if(!line.startsWith("    ")){
            if(line.startsWith("typedef ")){
                String[] parts = StringUtilities.split(line);
                if(parts.length == 3){
                    if(parts[1].endsWith("_t")){
                        return new CType(parts[1], parts[2].replace(";", ""));
                    }
                }
            }
        }
        return null;
    }
}

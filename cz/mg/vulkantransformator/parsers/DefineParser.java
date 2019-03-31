package cz.mg.vulkantransformator.parsers;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CDefine;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.utilities.StringUtilities;


public class DefineParser implements Parser {
    /*
        #define VK_MAX_PHYSICAL_DEVICE_NAME_SIZE  256
    */
    @Override
    public CEntity parse(ChainList<String> lines, int i) {
        String line = lines.get(i);
        if(!line.startsWith("    ")){
            if(line.startsWith("#define ")){
                String[] parts = StringUtilities.split(line);
                if(parts.length == 3){
                    if(!parts[1].contains("(") && !parts[1].endsWith("_")){
                        return new CDefine(parts[1], parts[2]);
                    }
                }
            }
        }
        return null;
    }
}

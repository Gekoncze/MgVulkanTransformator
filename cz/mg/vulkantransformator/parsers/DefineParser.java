package cz.mg.vulkantransformator.parsers;

import cz.mg.collections.array.Array;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CDefine;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.collections.text.Text;


public class DefineParser implements Parser {
    /*
        #define VK_MAX_PHYSICAL_DEVICE_NAME_SIZE  256
    */
    @Override
    public CEntity parse(ChainList<Text> lines, int i) {
        Text line = lines.get(i);
        if(!line.startsWith("    ")){
            if(line.startsWith("#define ")){
                Array<Text> parts = line.split();
                if(parts.count() == 3){
                    if(!parts.get(1).contains("(") && !parts.get(1).endsWith("_")){
                        Text name = parts.get(1);
                        Text value = parts.get(2);
                        return new CDefine(name, value);
                    }
                }
            }
        }
        return null;
    }
}

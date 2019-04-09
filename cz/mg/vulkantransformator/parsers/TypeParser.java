package cz.mg.vulkantransformator.parsers;

import cz.mg.collections.array.Array;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CType;
import cz.mg.collections.text.Text;


public class TypeParser implements Parser {
    /*
        typedef uint32_t VkFlags;
    */
    @Override
    public CEntity parse(ChainList<Text> lines, int i) {
        Text line = lines.get(i);
        if(!line.startsWith("    ")){
            if(line.startsWith("typedef ")){
                Array<Text> parts = line.split();
                if(parts.count() == 3){
                    if(parts.get(1).endsWith("_t")){
                        Text type = parts.get(1);
                        Text name = parts.get(2).replace(";", "");
                        return new CType(name, type);
                    }
                }
            }
        }
        return null;
    }
}

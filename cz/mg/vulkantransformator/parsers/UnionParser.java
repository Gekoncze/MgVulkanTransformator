package cz.mg.vulkantransformator.parsers;

import cz.mg.collections.array.Array;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CUnion;
import cz.mg.collections.text.Text;


public class UnionParser implements Parser {
    /*
        typedef struct VkSomeUnion {
            uint32_t                someValue;
            uint32_t                someValues[4];
            uint32_t*               somePointer;
            const uint32_t*         someOtherPointer;
            const uint32_t* const * some2DPointer;
        } VkSomeUnion;
    */
    @Override
    public CEntity parse(ChainList<Text> lines, int i) {
        Text line = lines.get(i);
        if(!line.startsWith("    ")){
            if(line.startsWith("typedef union ")){
                Array<Text> parts = line.split();
                Text name = parts.get(2).replace("{", "");
                return new CUnion(name, VariableParser.parseFields(parseChildren(lines, i)));
            }
        }
        return null;
    }
}

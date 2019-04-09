package cz.mg.vulkantransformator.parsers;

import cz.mg.collections.array.Array;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CInfo;
import cz.mg.vulkantransformator.entities.c.CVariable;
import cz.mg.collections.text.Text;

public class InfoParser implements Parser {
    /*
        typedef struct VkSomeInfo {
            uint32_t                someValue;
            uint32_t                someValues[4];
            uint32_t*               somePointer;
            const uint32_t*         someOtherPointer;
            const uint32_t* const * some2DPointer;
        } VkSomeInfo;
    */
    @Override
    public CEntity parse(ChainList<Text> lines, int i) {
        Text line = lines.get(i);
        if(!line.startsWith("    ")){
            Text nextLine = (i+1) < lines.count() ? lines.get(i+1) : new Text("");
            if(line.startsWith("typedef struct ") && nextLine.contains("VkStructureType") && line.contains("Info")){
                Array<Text> parts = line.split();
                Text name = parts.get(2).replace("{", "");
                ChainList<CVariable> fields = VariableParser.parseFields(parseChildren(lines, i));
                return new CInfo(name, fields);
            }
        }
        return null;
    }
}

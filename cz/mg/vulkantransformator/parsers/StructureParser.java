package cz.mg.vulkantransformator.parsers;

import cz.mg.collections.array.Array;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CVariable;
import cz.mg.vulkantransformator.entities.c.CStructure;
import cz.mg.collections.text.Text;


public class StructureParser implements Parser {
    /*
        typedef struct VkSomeStruct {
            uint32_t                someValue;
            uint32_t                someValues[4];
            uint32_t*               somePointer;
            const uint32_t*         someOtherPointer;
            const uint32_t* const * some2DPointer;
        } VkSomeStruct;
    */
    @Override
    public CEntity parse(ChainList<Text> lines, int i) {
        Text line = lines.get(i);
        if(!line.startsWith("    ")){
            Text nextLine = (i+1) < lines.count() ? lines.get(i+1) : new Text("");

            if(line.startsWith("typedef struct VkWriteDescriptorSet")) return null;
            if(line.startsWith("typedef struct VkImageMemoryBarrier")) return null;
            if(line.startsWith("typedef struct VkBufferMemoryBarrier")) return null;
            if(line.startsWith("typedef struct VkMemoryBarrier")) return null;

            if(line.contains("VkWriteDescriptorSet")) return null;
            if(line.startsWith("typedef struct ") && !(nextLine.contains("VkStructureType") && line.contains("Info"))){
                Array<Text> parts = line.split();
                Text name = parts.get(2).replace("{", "");
                ChainList<CVariable> fields = VariableParser.parseFields(parseChildren(lines, i));
                return new CStructure(name, fields);
            }
        }
        return null;
    }
}

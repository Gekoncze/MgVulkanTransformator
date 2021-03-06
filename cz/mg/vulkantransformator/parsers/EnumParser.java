package cz.mg.vulkantransformator.parsers;

import cz.mg.collections.array.Array;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CEnum;
import cz.mg.vulkantransformator.entities.c.CValue;
import cz.mg.collections.text.Text;


public class EnumParser implements Parser {
    /*
        typedef enum VkStencilOp {
            VK_STENCIL_OP_KEEP = 0,
            VK_STENCIL_OP_ZERO = 1,
            VK_STENCIL_OP_REPLACE = 2,
            VK_STENCIL_OP_INCREMENT_AND_CLAMP = 3,
            VK_STENCIL_OP_DECREMENT_AND_CLAMP = 4,
            VK_STENCIL_OP_INVERT = 5,
            VK_STENCIL_OP_INCREMENT_AND_WRAP = 6,
            VK_STENCIL_OP_DECREMENT_AND_WRAP = 7,
            VK_STENCIL_OP_BEGIN_RANGE = VK_STENCIL_OP_KEEP,
            VK_STENCIL_OP_END_RANGE = VK_STENCIL_OP_DECREMENT_AND_WRAP,
            VK_STENCIL_OP_RANGE_SIZE = (VK_STENCIL_OP_DECREMENT_AND_WRAP - VK_STENCIL_OP_KEEP + 1),
            VK_STENCIL_OP_MAX_ENUM = 0x7FFFFFFF
        } VkStencilOp;
    */
    @Override
    public CEntity parse(ChainList<Text> lines, int i) {
        Text line = lines.get(i);
        if(!line.startsWith("    ")){
            if(line.startsWith("typedef enum ") && !line.contains("FlagBits")){
                Array<Text> parts = line.split();
                Text name = parts.get(2).replace("{", "");
                ChainList<CValue> values = ValueParser.parseValues(parseChildren(lines, i));
                return new CEnum(name, values);
            }
        }
        return null;
    }
}

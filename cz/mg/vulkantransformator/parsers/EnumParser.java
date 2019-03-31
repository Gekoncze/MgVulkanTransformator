package cz.mg.vulkantransformator.parsers;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CEnum;
import cz.mg.vulkantransformator.entities.c.CValue;
import cz.mg.vulkantransformator.utilities.StringUtilities;


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
    public CEntity parse(ChainList<String> lines, int i) {
        String line = lines.get(i);
        if(!line.startsWith("    ")){
            if(line.startsWith("typedef enum ") && !line.contains("FlagBits")){
                String[] parts = StringUtilities.split(line);
                CEnum c = new CEnum(parts[2].replace("{", ""));
                for(i = i + 1; i < lines.count(); i++){
                    line = lines.get(i);
                    if(line.startsWith("    ")){
                        if(line.contains("_BEGIN_RANGE") || line.contains("_END_RANGE") || line.contains("_RANGE_SIZE") || line.contains("_MAX_ENUM")) continue;
                        parts = StringUtilities.split(line);
                        if(parts.length == 3){
                            c.getValues().addLast(new CValue(parts[0], parts[2].replace(",", "")));
                        }
                    } else break;
                }
                return c;
            }
        }
        return null;
    }
}

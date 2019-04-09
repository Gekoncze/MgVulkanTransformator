package cz.mg.vulkantransformator.parsers;

import cz.mg.collections.ReadonlyCollection;
import cz.mg.collections.array.Array;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CValue;
import cz.mg.collections.text.Text;


public class ValueParser {
    /*
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
    */
    public static CValue parseValue(Text part){
        Array<Text> parts = part.split();
        if(parts.count() != 3) throw new RuntimeException("" + parts.count());
        Text name = parts.get(0);
        Text value = parts.get(2).replace(",", "");
        return new CValue(name, value);
    }

    public static ChainList<CValue> parseValues(ReadonlyCollection<Text> lines){
        ChainList<CValue> values = new ChainList<>();
        for(Text line : lines){
            if(line.contains("_BEGIN_RANGE") || line.contains("_END_RANGE") || line.contains("_RANGE_SIZE") || line.contains("_MAX_ENUM")) continue;
            values.addLast(parseValue(line));
        }
        return values;
    }
}

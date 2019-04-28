package cz.mg.vulkantransformator.parsers;

import cz.mg.collections.array.Array;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CCallback;
import cz.mg.vulkantransformator.entities.c.CFunction;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CVariable;
import cz.mg.collections.text.Text;


public class FunctionParser implements Parser {
    private static final Text RVAL = new Text("rval");

    /*
        typedef VkResult (VKAPI_PTR *PFN_vkCreateImage)(VkDevice device, const VkImageCreateInfo* pCreateInfo, const VkAllocationCallbacks* pAllocator, VkImage* pImage);

        ## header & parameters:
        typedef VkResult (VKAPI_PTR *PFN_vkCreateImage
        VkDevice device, const VkImageCreateInfo* pCreateInfo, const VkAllocationCallbacks* pAllocator, VkImage* pImage);
    */
    @Override
    public CEntity parse(ChainList<Text> lines, int i) {
        Text line = lines.get(i);
        if(!line.startsWith("    ")){
            if(line.startsWith("typedef ") && line.contains("(VKAPI_PTR *PFN_") && !line.endsWith(")(")){
                return parseFunction(line);
            }
        }
        return null;
    }

    private static CFunction parseFunction(Text line){
        Array<Text> parts = line.divide(")(");
        Text header = parts.get(0);
        Text parameters = parts.get(1);

        ChainList<CVariable> variables = VariableParser.parseParameters(parameters.divide(", "));

        parts = header.split(" ");
        CVariable returnType = parseReturn(parts.get(1));
        Text name = parseName(parts.get(3));
        Text callName = name.replaceBegin("PFN_", "");

        if(name.equals("PFN_vkVoidFunction")) return new CCallback(name, callName, returnType, variables);
        return new CFunction(name, callName, returnType, variables);
    }

    public static CVariable parseReturn(Text part){
        int pointerCount = part.count('*');
        part = part.replace("*", " ").trim();
        return new CVariable(RVAL, part, pointerCount, null);
    }

    public static Text parseName(Text part){
        return part.replaceLast(")", "").replaceFirst("*", "");
    }
}

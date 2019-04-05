package cz.mg.vulkantransformator.parsers;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.converters.DatatypeConverter;
import cz.mg.vulkantransformator.entities.c.CCallback;
import cz.mg.vulkantransformator.entities.c.CFunction;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CVariable;
import cz.mg.vulkantransformator.utilities.StringUtilities;


public class FunctionParser implements Parser {
    /*
        typedef VkResult (VKAPI_PTR *PFN_vkCreateImage)(VkDevice device, const VkImageCreateInfo* pCreateInfo, const VkAllocationCallbacks* pAllocator, VkImage* pImage);

        ## header & parameters:
        typedef VkResult (VKAPI_PTR *PFN_vkCreateImage
        VkDevice device, const VkImageCreateInfo* pCreateInfo, const VkAllocationCallbacks* pAllocator, VkImage* pImage);

        ## parts
        VkDevice device
        const VkImageCreateInfo* pCreateInfo
        const VkAllocationCallbacks* pAllocator
        VkImage* pImage
    */
    @Override
    public CEntity parse(ChainList<String> lines, int i) {
        String line = lines.get(i);
        if(!line.startsWith("    ")){
            if(line.startsWith("typedef ") && line.contains("(VKAPI_PTR *PFN_") && !line.endsWith(")(")){
                String[] parts = StringUtilities.splitToHalf(line, ")(");
                String header = parts[0];
                String parameters = parts[1];
                CFunction c = parseHeader(header);
                ChainList<String> ps = StringUtilities.splitByString(parameters, ", ");
                for(String part : ps){
                    CVariable p = (parseParameter(part));
                    if(p != null) c.getParameters().addLast(p);
                }
                return c;
            }
        }
        return null;
    }

    private static CFunction parseHeader(String part){
        String[] parts = StringUtilities.split(part, " *");
        CVariable r = parseReturn(parts[1]);
        String n = parseName(parts[3]);
        if(n.equals("PFN_vkVoidFunction")) return new CCallback(r, n);
        return new CFunction(r, n);
    }

    public static CVariable parseReturn(String part){
        int pointerCount = StringUtilities.count(part, '*');
        part = part.replace("*", " ").trim();
        return new CVariable(part, "rval", pointerCount, null);
    }

    public static String parseName(String part){
        return StringUtilities.replaceLast(part, ")", "");
    }

    public static CVariable parseParameter(String part){
        int pointerCount = StringUtilities.count(part, '*') + StringUtilities.count(part, '[');
        part = DatatypeConverter.removeConsts(part);
        String[] parts2 = StringUtilities.split(part, " *);,");
        if(parts2.length == 1) return null;
        String[] parts3 = StringUtilities.split(parts2[1], "[");
        return new CVariable(parts2[0], parts3[0], pointerCount, null);
    }
}

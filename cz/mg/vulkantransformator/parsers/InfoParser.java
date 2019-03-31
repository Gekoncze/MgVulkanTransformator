package cz.mg.vulkantransformator.parsers;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.converters.DatatypeConverter;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CField;
import cz.mg.vulkantransformator.entities.c.CInfo;
import cz.mg.vulkantransformator.utilities.StringUtilities;


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
    public CEntity parse(ChainList<String> lines, int i) {
        String line = lines.get(i);
        if(!line.startsWith("    ")){
            String nextLine = (i+1) < lines.count() ? lines.get(i+1) : "";
            if(line.startsWith("typedef struct ") && nextLine.contains("VkStructureType") && line.contains("Info")){
                String[] parts = StringUtilities.split(line);
                CInfo c = new CInfo(parts[2].replace("{", ""));
                for(i = i + 1; i < lines.count(); i++){
                    line = lines.get(i);
                    if(line.startsWith("    ")){
                        line = DatatypeConverter.removeConsts(line);
                        parts = StringUtilities.split(line, "* ;[]");
                        int pointerCount = StringUtilities.count(line, '*');
                        String arrayCount = parts.length == 3 ? parts[2] : null;
                        c.getFields().addLast(new CField(parts[0], parts[1], pointerCount, arrayCount));
                    } else break;
                }
                return c;
            }
        }
        return null;
    }
}

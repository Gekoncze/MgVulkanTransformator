package cz.mg.vulkantransformator.parsers;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CFlagBits;
import cz.mg.vulkantransformator.entities.c.CValue;
import cz.mg.vulkantransformator.utilities.StringUtilities;


public class FlagBitsParser implements Parser {
    /*
        typedef enum VkImageUsageFlagBits {
            VK_IMAGE_USAGE_TRANSFER_SRC_BIT = 0x00000001,
            VK_IMAGE_USAGE_TRANSFER_DST_BIT = 0x00000002,
            VK_IMAGE_USAGE_SAMPLED_BIT = 0x00000004,
            VK_IMAGE_USAGE_STORAGE_BIT = 0x00000008,
            VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT = 0x00000010,
            VK_IMAGE_USAGE_DEPTH_STENCIL_ATTACHMENT_BIT = 0x00000020,
            VK_IMAGE_USAGE_TRANSIENT_ATTACHMENT_BIT = 0x00000040,
            VK_IMAGE_USAGE_INPUT_ATTACHMENT_BIT = 0x00000080,
            ...
            VK_IMAGE_USAGE_FLAG_BITS_MAX_ENUM = 0x7FFFFFFF
        } VkImageUsageFlagBits;
    */
    @Override
    public CEntity parse(ChainList<String> lines, int i) {
        String line = lines.get(i);
        if(!line.startsWith("    ")){
            if(line.startsWith("typedef enum ") && line.contains("FlagBits")){
                String[] parts = StringUtilities.split(line);
                CFlagBits c = new CFlagBits(parts[2].replace("{", ""));
                for(i = i + 1; i < lines.count(); i++){
                    line = lines.get(i);
                    if(line.startsWith("    ")){
                        if(line.contains("_MAX_ENUM")) continue;
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

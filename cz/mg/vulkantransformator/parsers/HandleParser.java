package cz.mg.vulkantransformator.parsers;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CHandle;
import cz.mg.vulkantransformator.utilities.StringUtilities;


public class HandleParser implements Parser {
    /*
        VK_DEFINE_HANDLE(VkCommandBuffer)
        VK_DEFINE_NON_DISPATCHABLE_HANDLE(VkFence)
    */
    @Override
    public CEntity parse(ChainList<String> lines, int i) {
        String line = lines.get(i);
        if(!line.startsWith("    ")){
            if(line.startsWith("VK_DEFINE_HANDLE") || line.startsWith("VK_DEFINE_NON_DISPATCHABLE_HANDLE")){
                String[] parts = StringUtilities.split(line, "()");
                return new CHandle(parts[1], line.startsWith("VK_DEFINE_HANDLE"));
            }
        }
        return null;
    }
}

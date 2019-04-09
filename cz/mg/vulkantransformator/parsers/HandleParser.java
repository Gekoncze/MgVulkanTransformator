package cz.mg.vulkantransformator.parsers;

import cz.mg.collections.array.Array;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CHandle;
import cz.mg.collections.text.Text;


public class HandleParser implements Parser {
    /*
        VK_DEFINE_HANDLE(VkCommandBuffer)
        VK_DEFINE_NON_DISPATCHABLE_HANDLE(VkFence)
    */
    @Override
    public CEntity parse(ChainList<Text> lines, int i) {
        Text line = lines.get(i);
        if(!line.startsWith("    ")){
            if(line.startsWith("VK_DEFINE_HANDLE") || line.startsWith("VK_DEFINE_NON_DISPATCHABLE_HANDLE")){
                Array<Text> parts = line.split("()");
                Text name = parts.get(1);
                boolean dispatchable = line.startsWith("VK_DEFINE_HANDLE");
                return new CHandle(name, dispatchable);
            }
        }
        return null;
    }
}

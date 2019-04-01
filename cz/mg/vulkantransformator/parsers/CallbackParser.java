package cz.mg.vulkantransformator.parsers;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CCallback;
import cz.mg.vulkantransformator.entities.c.CVariable;
import cz.mg.vulkantransformator.utilities.StringUtilities;


public class CallbackParser implements Parser {
    /*
        typedef VkBool32 (VKAPI_PTR *PFN_vkDebugReportCallbackEXT)(
            VkDebugReportFlagsEXT                       flags,
            VkDebugReportObjectTypeEXT                  objectType,
            uint64_t                                    object,
            size_t                                      location,
            int32_t                                     messageCode,
            const char*                                 pLayerPrefix,
            const char*                                 pMessage,
            void*                                       pUserData);

        typedef VkBool32 (VKAPI_PTR *PFN_vkDebugUtilsMessengerCallbackEXT)(
            VkDebugUtilsMessageSeverityFlagBitsEXT           messageSeverity,
            VkDebugUtilsMessageTypeFlagsEXT                  messageType,
            const VkDebugUtilsMessengerCallbackDataEXT*      pCallbackData,
            void*                                            pUserData);

        typedef void* (VKAPI_PTR *PFN_vkAllocationFunction)(
            void*                                       pUserData,
            size_t                                      size,
            size_t                                      alignment,
            VkSystemAllocationScope                     allocationScope);

        typedef void* (VKAPI_PTR *PFN_vkReallocationFunction)(
            void*                                       pUserData,
            void*                                       pOriginal,
            size_t                                      size,
            size_t                                      alignment,
            VkSystemAllocationScope                     allocationScope);

        typedef void (VKAPI_PTR *PFN_vkFreeFunction)(
            void*                                       pUserData,
            void*                                       pMemory);

        typedef void (VKAPI_PTR *PFN_vkInternalAllocationNotification)(
            void*                                       pUserData,
            size_t                                      size,
            VkInternalAllocationType                    allocationType,
            VkSystemAllocationScope                     allocationScope);

        typedef void (VKAPI_PTR *PFN_vkInternalFreeNotification)(
            void*                                       pUserData,
            size_t                                      size,
            VkInternalAllocationType                    allocationType,
            VkSystemAllocationScope                     allocationScope);
    */
    @Override
    public CEntity parse(ChainList<String> lines, int i) {
        String line = lines.get(i);
        if(!line.startsWith("    ")){
            if(line.startsWith("typedef ") && line.contains("VKAPI_PTR") && line.endsWith(")(")){
                CCallback c = parseHeader(line);
                for(i = i + 1; i < lines.count(); i++){
                    line = lines.get(i);
                    if(line.startsWith("    ")){
                        CVariable p = FunctionParser.parseParameter(line);
                        if(p != null) c.getParameters().addLast(p);
                    } else break;
                }
                return c;
            }
        }
        return null;
    }

    private static CCallback parseHeader(String part){
        String[] parts = StringUtilities.split(part, " ()*");
        return new CCallback(FunctionParser.parseReturn(parts[1]), FunctionParser.parseName(parts[3]));
    }
}

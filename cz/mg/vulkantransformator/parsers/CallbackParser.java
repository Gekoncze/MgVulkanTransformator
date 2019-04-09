package cz.mg.vulkantransformator.parsers;

import cz.mg.collections.array.Array;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.c.CCallback;
import cz.mg.vulkantransformator.entities.c.CVariable;
import cz.mg.collections.text.Text;


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
    public CEntity parse(ChainList<Text> lines, int i) {
        Text line = lines.get(i);
        if(!line.startsWith("    ")){
            if(line.startsWith("typedef ") && line.contains("VKAPI_PTR") && line.endsWith(")(")){
                return parseCallback(lines, i);
            }
        }
        return null;
    }

    private CCallback parseCallback(ChainList<Text> lines, int i){
        Array<Text> parts = lines.get(i).split(" ()*");
        CVariable returnType = FunctionParser.parseReturn(parts.get(1));
        Text name = FunctionParser.parseName(parts.get(3));
        ChainList<CVariable> parameters = VariableParser.parseParameters(parseChildren(lines, i));
        return new CCallback(name, returnType, parameters);
    }
}

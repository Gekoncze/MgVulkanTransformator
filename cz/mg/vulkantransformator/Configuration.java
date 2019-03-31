package cz.mg.vulkantransformator;


public class Configuration {
    public static final String DEFAULT_VULKAN_CORE_PATH = "";
    public static final String DEFAULT_OUTPUT_DIRECTORY_PATH = "/home/me/Plocha/Java/MgVulkan/src";

    public static final String PATH_C = "c/cz/mg/vulkan";
    public static final String PATH_Vk = "cz/mg/vulkan/vk";
    public static final String PATH_VULKAN = "cz/mg/vulkan";

    public static final String[] ROOT_DIRECTORIES = new String[]{
            "c",
            "cz"
    };

    public static final String[] SYSTEM_TYPES = new String[]{
            "int8_t",
            "uint8_t",
            "int16_t",
            "uint16_t",
            "int32_t",
            "uint32_t",
            "int64_t",
            "uint64_t",
            "size_t",
            "float",
            "double",
            "char",
            "int",
    };

    public static final String[][] MISC_NAMES = new String[][]{
            new String[]{"Utilities"               , null                      , null},
            new String[]{null                      , "VkResourceManager"       , null},
            new String[]{"VkMemory"                , "VkMemory"                , null},
            new String[]{null                      , "VkObject"                , null},
            new String[]{"VkPointer"               , "VkPointer"               , null},
            new String[]{"VkString"                , "VkString"                , null},
            new String[]{"VkFunctionPointer"       , "VkFunctionPointer"       , null},
            new String[]{"VkDebug"                 , "VkDebug"                 , null},
            new String[]{"VkDispatchableHandle"    , "VkDispatchableHandle"    , null},
            new String[]{"VkNonDispatchableHandle" , "VkNonDispatchableHandle" , null},
    };

    public static final String getPath(EntityGroup group){
        switch(group){
            case C: return PATH_C;
            case VK: return PATH_Vk;
            case VULKAN: return PATH_VULKAN;
            default: throw new RuntimeException("Unsupported entity group " + group);
        }
    }
}

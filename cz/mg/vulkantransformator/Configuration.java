package cz.mg.vulkantransformator;

import cz.mg.collections.text.Text;


public class Configuration {
    public static final Text DEFAULT_VULKAN_CORE_PATH = new Text("");
    public static final Text DEFAULT_OUTPUT_DIRECTORY_PATH = new Text("/home/me/Plocha/Java/MgVulkan/src");

    public static final Text PATH_C = new Text("c/cz/mg/vulkan");
    public static final Text PATH_Vk = new Text("cz/mg/vulkan");

    public static final Text[] ROOT_DIRECTORIES = new Text[]{
            new Text("c"),
            new Text("cz")
    };

    public static final Text[] SYSTEM_TYPES = new Text[]{
            new Text("int8_t"),
            new Text("uint8_t"),
            new Text("int16_t"),
            new Text("uint16_t"),
            new Text("int32_t"),
            new Text("uint32_t"),
            new Text("int64_t"),
            new Text("uint64_t"),
            new Text("size_t"),
            new Text("float"),
            new Text("double"),
            new Text("char"),
            new Text("int"),
    };

    public static final Text[][] MISC_NAMES = new Text[][]{
            new Text[]{ new Text("Utilities")               , null                                },
            new Text[]{ null                                , new Text("VkResourceManager")       },
            new Text[]{ new Text("VkMemory")                , new Text("VkMemory")                },
            new Text[]{ null                                , new Text("VkObject")                },
            new Text[]{ new Text("VkPointer")               , new Text("VkPointer")               },
            new Text[]{ new Text("VkString")                , new Text("VkString")                },
            new Text[]{ new Text("VkFunctionPointer")       , new Text("VkFunctionPointer")       },
            new Text[]{ new Text("VkDebug")                 , new Text("VkDebug")                 },
            new Text[]{ null                                , new Text("VkHandle")                },
            new Text[]{ new Text("VkDispatchableHandle")    , new Text("VkDispatchableHandle")    },
            new Text[]{ new Text("VkNonDispatchableHandle") , new Text("VkNonDispatchableHandle") },
            new Text[]{ null                                , new Text("VkVersion")               },
            new Text[]{ null                                , new Text("VkException")             },
    };

    public static final Text[][] ADITIONAL_TYPES = new Text[][]{
            new Text[]{ new Text("VkEnum"), new Text("int32_t") },
            new Text[]{ new Text("VkFlagBits"), new Text("int32_t") },
    };

    public static final Text[][] DEFINE_VALUES = new Text[][]{
            new Text[]{ new Text("VK_REMAINING_MIP_LEVELS"), new Text("4294967295L") },
            new Text[]{ new Text("VK_REMAINING_ARRAY_LAYERS"), new Text("4294967295L") },
            new Text[]{ new Text("VK_WHOLE_SIZE"), new Text("-1L") },
            new Text[]{ new Text("VK_ATTACHMENT_UNUSED"), new Text("4294967295L") },
            new Text[]{ new Text("VK_QUEUE_FAMILY_IGNORED"), new Text("4294967295L") },
            new Text[]{ new Text("VK_SUBPASS_EXTERNAL"), new Text("4294967295L") },
            new Text[]{ new Text("VK_QUEUE_FAMILY_EXTERNAL"), new Text("4294967294L") },
            new Text[]{ new Text("VK_QUEUE_FAMILY_FOREIGN_EXT"), new Text("4294967293L") },
    };

    public static final Text getPath(EntityGroup group){
        switch(group){
            case C: return PATH_C;
            case VK: return PATH_Vk;
            default: throw new RuntimeException("Unsupported entity group " + group);
        }
    }
}

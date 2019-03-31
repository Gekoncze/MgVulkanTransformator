package cz.mg.vulkantransformator.converters;

import cz.mg.vulkantransformator.utilities.StringUtilities;
import static cz.mg.vulkantransformator.utilities.StringUtilities.replaceFirst;


public class TypenameConverter {
    public static String cTypenameToVk(String cTypename){
        String vkTypename = cSystemTypenameToVk(cTypename);
        if(vkTypename != null) return vkTypename;
        if(!cTypename.startsWith("Vk") && !cTypename.startsWith("PFN")) cTypename = "Vk" + cTypename;
        return StringUtilities.replaceFirst(cTypename, "PFN_", "PFN");
    }

    public static String cTypenameToJava(String cTypename){
        String javaTypename = cSystemTypenameToJava(cTypename);
        if(javaTypename != null) return javaTypename;
        return "long";
    }

    public static String cTypenameToJni(String cTypename){
        String jniTypename = cSystemTypenameToJni(cTypename);
        if(jniTypename != null) return jniTypename;
        return "jlong";
    }

    private static String cSystemTypenameToVk(String cTypename){
        switch(cTypename){
            case "int8_t": return "VkInt8";
            case "uint8_t": return "VkUInt8";
            case "int16_t": return "VkInt16";
            case "uint16_t": return "VkUInt16";
            case "int32_t": return "VkInt32";
            case "uint32_t": return "VkUInt32";
            case "int64_t": return "VkInt64";
            case "uint64_t": return "VkUInt64";
            case "size_t": return "VkSize";
            case "float": return "VkFloat";
            case "double": return "VkDouble";
            case "char": return "VkChar";
            case "int": return "VkInt";
            case "void": return "void";
            default: return null;
        }
    }

    private static String cSystemTypenameToJava(String cTypename){
        switch(cTypename){
            case "int8_t": return "byte";
            case "uint8_t": return "byte";
            case "int16_t": return "short";
            case "uint16_t": return "short";
            case "int32_t": return "int";
            case "uint32_t": return "int";
            case "int64_t": return "long";
            case "uint64_t": return "long";
            case "size_t": return "long";
            case "float": return "float";
            case "double": return "double";
            case "char": return "byte";
            case "int": return "int";
            case "void": return "void";
            default: return null;
        }
    }

    private static String cSystemTypenameToJni(String cTypename){
        switch(cTypename){
            case "int8_t": return "jbyte";
            case "uint8_t": return "jbyte";
            case "int16_t": return "jshort";
            case "uint16_t": return "jshort";
            case "int32_t": return "jint";
            case "uint32_t": return "jint";
            case "int64_t": return "jlong";
            case "uint64_t": return "jlong";
            case "size_t": return "jlong";
            case "float": return "jfloat";
            case "double": return "jdouble";
            case "char": return "jbyte";
            case "int": return "jint";
            case "void": return "void";
            default: return null;
        }
    }

    public static String vkTypenameToV(String cTypename){
        return replaceFirst(cTypename, "Vk", "Vulkan");
    }

    public static String vkEnumnameToVulkan(String vkEnumname, String vkName){
        return vkEnumname.replaceFirst(StringUtilities.cammelCaseToUpperCase(vkName), "").replaceFirst("VK__", "");
    }
}

package cz.mg.vulkantransformator.converters.utilities;

import cz.mg.collections.text.Text;


public class TypenameConverter {
    private static final Text VK = new Text("Vk");
    private static final Text DEFAULT_JAVA_TYPE = new Text("long");
    private static final Text DEFAULT_JNI_TYPE = new Text("jlong");

    public static Text cTypenameToVk(Text cTypename){
        Text vkTypename = cSystemTypenameToVk(cTypename);
        if(vkTypename != null) return vkTypename;
        if(!cTypename.startsWith("Vk") && !cTypename.startsWith("PFN")) cTypename = VK.append(cTypename);
        return cTypename.replaceFirst("PFN_", "PFN");
    }

    public static Text cTypenameToJava(Text cTypename){
        Text javaTypename = cSystemTypenameToJava(cTypename);
        if(javaTypename != null) return javaTypename;
        return DEFAULT_JAVA_TYPE;
    }

    public static Text cTypenameToJni(Text cTypename){
        Text jniTypename = cSystemTypenameToJni(cTypename);
        if(jniTypename != null) return jniTypename;
        return DEFAULT_JNI_TYPE;
    }

    private static Text cSystemTypenameToVk(Text cTypename){
        switch(cTypename.toString()){
            case "int8_t": return new Text("VkInt8");
            case "uint8_t": return new Text("VkUInt8");
            case "int16_t": return new Text("VkInt16");
            case "uint16_t": return new Text("VkUInt16");
            case "int32_t": return new Text("VkInt32");
            case "uint32_t": return new Text("VkUInt32");
            case "int64_t": return new Text("VkInt64");
            case "uint64_t": return new Text("VkUInt64");
            case "size_t": return new Text("VkSize");
            case "float": return new Text("VkFloat");
            case "double": return new Text("VkDouble");
            case "char": return new Text("VkChar");
            case "int": return new Text("VkInt");
            case "void": return new Text("void");
            default: return null;
        }
    }

    private static Text cSystemTypenameToJava(Text cTypename){
        switch(cTypename.toString()){
            case "int8_t": return new Text("byte");
            case "uint8_t": return new Text("byte");
            case "int16_t": return new Text("short");
            case "uint16_t": return new Text("short");
            case "int32_t": return new Text("int");
            case "uint32_t": return new Text("int");
            case "int64_t": return new Text("long");
            case "uint64_t": return new Text("long");
            case "size_t": return new Text("long");
            case "float": return new Text("float");
            case "double": return new Text("double");
            case "char": return new Text("byte");
            case "int": return new Text("int");
            case "void": return new Text("void");
            default: return null;
        }
    }

    private static Text cSystemTypenameToJni(Text cTypename){
        switch(cTypename.toString()){
            case "int8_t": return new Text("jbyte");
            case "uint8_t": return new Text("jbyte");
            case "int16_t": return new Text("jshort");
            case "uint16_t": return new Text("jshort");
            case "int32_t": return new Text("jint");
            case "uint32_t": return new Text("jint");
            case "int64_t": return new Text("jlong");
            case "uint64_t": return new Text("jlong");
            case "size_t": return new Text("jlong");
            case "float": return new Text("jfloat");
            case "double": return new Text("jdouble");
            case "char": return new Text("jbyte");
            case "int": return new Text("jint");
            case "void": return new Text("void");
            default: return null;
        }
    }
}

package cz.mg.vulkantransformator.converters;


public class DatatypeConverter {
    public static String cDatatypeToVk(String cTypename, int pointerCount, String arrayCount){
        if(pointerCount > 0 && arrayCount != null) throw new UnsupportedOperationException("Pointer arrays are not supported.");
        String vkTypename = TypenameConverter.cTypenameToVk(cTypename);
        if(cTypename.equals("void")) vkTypename = "VkObject";
        if(arrayCount != null) return vkTypename;
        if(pointerCount == 0) return vkTypename;
        if(pointerCount == 1) return vkTypename;
        if(pointerCount == 2) return vkTypename + ".Pointer";
        throw new UnsupportedOperationException("Unsupported " + pointerCount + " pointer.");
    }

    public static String vkDatatypeToV(String vkTypename){
        return TypenameConverter.vkTypenameToV(vkTypename);
    }

    public static String removeConsts(String s){
        s = s.replace("const ", " ");
        s = s.replace(" const ", " ");
        s = s.replace("*const ", " ");
        s = s.replace(" const*", " ");
        s = s.replace("*const*", " ");
        s = s.replace("(const ", " ");
        s = s.replace("(const*", " ");
        return s;
    }
}

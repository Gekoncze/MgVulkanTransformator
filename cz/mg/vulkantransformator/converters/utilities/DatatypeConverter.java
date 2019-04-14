package cz.mg.vulkantransformator.converters.utilities;

import cz.mg.collections.text.Text;


public class DatatypeConverter {
    public static Text cDatatypeToVk(Text cTypename, int pointerCount, Text arrayCount){
        if(pointerCount > 0 && arrayCount != null) throw new UnsupportedOperationException("Pointer arrays are not supported.");
        Text vkTypename = TypenameConverter.cTypenameToVk(cTypename);
        if(cTypename.equals("void")) vkTypename = new Text("VkObject");
        if(arrayCount != null) return vkTypename;
        if(pointerCount == 0) return vkTypename;
        if(pointerCount == 1) return vkTypename;
        if(pointerCount == 2) return vkTypename.append(".Pointer");
        throw new UnsupportedOperationException("Unsupported " + pointerCount + " pointer.");
    }

//    public static Text cDatatypeToVk(Text cTypename, int pointerCount, Text arrayCount){
//        if(pointerCount > 0 && arrayCount != null) throw new UnsupportedOperationException("Pointer arrays are not supported.");
//        Text vkTypename = TypenameConverter.cTypenameToVk(cTypename);
//        if(cTypename.equals("void")) vkTypename = new Text("VkObject");
//        if(arrayCount != null) return vkTypename;
//        if(pointerCount == 0) return vkTypename;
//        if(pointerCount == 1) return vkTypename.append(".Array");
//        if(pointerCount == 2) return vkTypename.append(".Pointer.Array");
//        throw new UnsupportedOperationException("Unsupported " + pointerCount + " pointer.");
//    }

    public static Text removeConsts(Text s){
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

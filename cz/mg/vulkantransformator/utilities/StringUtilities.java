package cz.mg.vulkantransformator.utilities;

import cz.mg.collections.array.Array;
import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;


public class StringUtilities {
    public static String[] split(String line){
        line = line.trim();
        while(line.contains("  ")) line = line.replace("  ", " ");
        return line.split(" ");
    }

    public static String[] split(String line, String delims){
        for(int i = 0; i < delims.length(); i++) line = line.replace(delims.charAt(i), ' ');
        return split(line);
    }

    public static String[] splitToHalf(String line, String delim){
        line = line.trim();
        int index = line.indexOf(delim);
        if(index == -1) throw new RuntimeException();
        String before = line.substring(0, index);
        String after = line.substring(index + delim.length());
        return new String[]{before, after};
    }

    public static ChainList<String> splitByString(String line, String delim){
        if(line.indexOf(delim) == -1) return new ChainList<>(line);
        ChainList<String> parts = new ChainList<>();
        String[] halfs = splitToHalf(line, delim);
        parts.addLast(halfs[0]);
        while(halfs[1].indexOf(delim) != -1){
            halfs = splitToHalf(halfs[1], delim);
            parts.addLast(halfs[0]);
        }
        parts.addLast(halfs[1]);
        return parts;
    }

    public static String capitalFirst(String name){
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static String lowerFirst(String name){
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    public static String[] splitCammelCase(String line){
        return line.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
    }

    public static String cammelCaseToUpperCase(String s){
        return new Array<>(splitCammelCase(s)).toString("_").toUpperCase();
    }

    public static String upperCaseToCammelCase(String s){
        String[] parts = split(s, "_");
        ChainList<String> r = new CachedChainList<>();
        for(String part : parts) r.addLast(capitalFirst(part.toLowerCase()));
        return r.toString("");
    }

    public static String replaceFirst(String s, String what, String with){
        int index = s.indexOf(what);
        if(index == -1) return s;
        String before = s.substring(0, index);
        String after = s.substring(index + what.length());
        return before + with + after;
    }

    public static String replaceLast(String s, String what, String with){
        int index = s.lastIndexOf(what);
        if(index == -1) return s;
        String before = s.substring(0, index);
        String after = s.substring(index + what.length());
        return before + with + after;
    }

    public static String replaceBegin(String s, String what, String with){
        if(!s.startsWith(what)) return s;
        return replaceFirst(s, what, with);
    }

    public static String replaceEnd(String s, String what, String with){
        if(!s.endsWith(what)) return s;
        return replaceFirst(s, what, with);
    }

    public static int count(String s, char ch){
        int count = 0;
        for(int i = 0; i < s.length(); i++) if(s.charAt(i) == ch) count++;
        return count;
    }

    public static String repeat(String s, int count){
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < count; i++) result.append(s);
        return result.toString();
    }
}

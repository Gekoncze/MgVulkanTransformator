package cz.mg.vulkantransformator.utilities;

import java.io.PrintWriter;
import java.io.StringWriter;


public class DebugUtilities {
    public static String stackTraceToString(Exception e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}

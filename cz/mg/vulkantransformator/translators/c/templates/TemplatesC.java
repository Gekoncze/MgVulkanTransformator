package cz.mg.vulkantransformator.translators.c.templates;

import cz.mg.vulkantransformator.utilities.FileUtilities;


public class TemplatesC {
    public static String load(String name){
        return FileUtilities.loadFile(TemplatesC.class, name + "Template.txt");
    }
}

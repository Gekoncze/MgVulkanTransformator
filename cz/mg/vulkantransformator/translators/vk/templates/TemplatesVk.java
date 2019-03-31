package cz.mg.vulkantransformator.translators.vk.templates;

import cz.mg.vulkantransformator.utilities.FileUtilities;


public class TemplatesVk {
    public static String load(String name){
        return FileUtilities.loadFile(TemplatesVk.class, name + "Template.txt");
    }
}

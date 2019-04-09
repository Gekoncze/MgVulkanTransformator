package cz.mg.vulkantransformator.translators.vk.templates;

import cz.mg.vulkantransformator.utilities.FileUtilities;
import cz.mg.collections.text.Text;


public class TemplatesVk {
    public static Text load(String name){
        return FileUtilities.loadFile(TemplatesVk.class, name + "Template.txt");
    }

    public static Text load(Text name){
        return FileUtilities.loadFile(TemplatesVk.class, name + "Template.txt");
    }
}

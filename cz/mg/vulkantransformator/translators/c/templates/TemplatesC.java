package cz.mg.vulkantransformator.translators.c.templates;

import cz.mg.vulkantransformator.utilities.FileUtilities;
import cz.mg.collections.text.Text;


public class TemplatesC {
    public static Text load(String name){
        return FileUtilities.loadFile(TemplatesC.class, name + "Template.txt");
    }

    public static Text load(Text name){
        return FileUtilities.loadFile(TemplatesC.class, name + "Template.txt");
    }
}

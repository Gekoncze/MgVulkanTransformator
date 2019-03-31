package cz.mg.vulkantransformator.translators.vulkan.templates;

import cz.mg.vulkantransformator.utilities.FileUtilities;


public class TemplatesVulkan {
    public static String load(String name){
        return FileUtilities.loadFile(TemplatesVulkan.class, name + "Template.txt");
    }
}

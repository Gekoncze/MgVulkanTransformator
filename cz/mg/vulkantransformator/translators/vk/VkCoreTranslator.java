package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.Configuration;
import cz.mg.vulkantransformator.EntityGroup;
import cz.mg.vulkantransformator.entities.vk.*;
import cz.mg.vulkantransformator.translators.vk.templates.TemplatesVk;


public class VkCoreTranslator {
    private static final Text coreTemplate = TemplatesVk.load("core/Core");
    private static final Text headerTemplate = TemplatesVk.load("parts/Header");
    private static final Text defineStringTemplate = new Text("    public static final String %DEFINENAME% = %DEFINEVALUE%;");
    private static final Text defineIntegerTemplate = new Text("    public static final int %DEFINENAME% = %DEFINEVALUE%;");
    private static final Text defineLongTemplate = new Text("    public static final long %DEFINENAME% = %DEFINEVALUE%;");
    private static final Text defineFloatTemplate = new Text("    public static final float %DEFINENAME% = %DEFINEVALUE%;");
    private static final Text valueTemplate = new Text("    public static final int %VALUENAME% = %VALUE%;");
    private static final Text functionFieldTemplate = new Text("    private %FUNCTIONTYPENAME% %FUNCTIONNAMEP% = null;");

    public static Text translate(ChainList<VkEntity> entities){
        return headerTemplate.append(coreTemplate)
                .replace("%DEFINES%", genDefines(entities))
                .replace("%FUNCTIONFIELDS%", genFunctionFields(entities))
                .replace("%FUNCTIONS%", VkCoreFunctionTranslator.genFunctions(entities))
                .replace("%CONSTANTS%", genConstants(entities))
                .replace("%%PACKAGE%%", genPackage());
    }

    public static Text genFunctionFields(ChainList<VkEntity> entities){
        ChainList<Text> fields = new CachedChainList<>();
        for(VkEntity entity : entities) {
            if (entity instanceof VkFunction && !(entity instanceof VkCallback)) {
                VkFunction vk = (VkFunction) entity;
                fields.addLast(
                        functionFieldTemplate
                                .replace("%FUNCTIONTYPENAME%", vk.getName())
                                .replace("%FUNCTIONNAMEP%", vk.getC().getName() + "_f")
                );
            }
        }
        return new Text(fields.toString("\n"));
    }

    private static Text genPackage(){
        return Configuration.getPath(EntityGroup.VK).replace("/", ".");
    }

    private static Text genDefines(ChainList<VkEntity> entities){
        ChainList<Text> defines = new CachedChainList<>();
        for(VkEntity entity : entities) {
            if (entity instanceof VkDefine) {
                VkDefine define = (VkDefine) entity;
                Text template = null;

                if(define.isFloat()) template = defineFloatTemplate;
                else if(define.isString()) template = defineStringTemplate;
                else if(define.isInteger()) template = defineIntegerTemplate;
                else template = defineLongTemplate;

                defines.addLast(template
                        .replace("%DEFINENAME%", define.getName())
                        .replace("%DEFINEVALUE%", define.getValue())
                );
            }
        }
        return defines.toText("\n");
    }

    private static Text genConstants(ChainList<VkEntity> entities){
        ChainList<Text> values = new CachedChainList<>();
        for(VkEntity entity : entities) {
            if(entity instanceof VkFlagBits){
                VkFlagBits f = (VkFlagBits) entity;
                for(VkValue value : f.getValues()){
                    values.addLast(genConstant(value, f.getName()));
                }
            } else if (entity instanceof VkEnum) {
                VkEnum e = (VkEnum) entity;
                for(Object value : e.getValues()){ // quick fix for bug in either java or intellij idea
                    VkValue vvalue = (VkValue) value; // quick fix for bug in either java or intellij idea
                    values.addLast(genConstant(vvalue, e.getName()));
                }
            }
        }
        return values.toText("\n");
    }

    private static Text genConstant(VkValue value, Text name){
        return valueTemplate
                .replace("%VALUENAME%", value.getName())
                .replace("%VALUE%", name + "." + value.getName());
    }
}

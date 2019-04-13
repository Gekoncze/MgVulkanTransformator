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
    private static final Text defineIntegerTemplate = new Text("    public static final long %DEFINENAME% = %DEFINEVALUE%;");
    private static final Text defineFloatTemplate = new Text("    public static final float %DEFINENAME% = %DEFINEVALUE%;");
    private static final Text functionTemplate = TemplatesVk.load("core/Function");
    private static final Text valueTemplate = new Text("    public static final int %VALUENAME% = %VALUE%;");

    public static Text translate(ChainList<VkEntity> entities){
        return headerTemplate.append(coreTemplate)
                .replace("%DEFINES%", genDefines(entities))
                .replace("%FUNCTIONS%", genFunctions(entities))
                .replace("%CONSTANTS%", genConstants(entities))
                .replace("%%PACKAGE%%", genPackage());
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
                else template = defineIntegerTemplate;

                defines.addLast(template
                        .replace("%DEFINENAME%", define.getName())
                        .replace("%DEFINEVALUE%", define.getValue())
                );
            }
        }
        return defines.toText("\n");
    }

    private static Text genFunctions(ChainList<VkEntity> entities){
        ChainList<Text> functions = new CachedChainList<>();
        for(VkEntity entity : entities) {
            if (entity instanceof VkFunction && !(entity instanceof VkCallback)) {
                VkFunction vk = (VkFunction) entity;
                functions.addLast(functionTemplate
                        .replace("%FUNCTIONTYPENAME%", vk.getName())
                        .replace("%FUNCTIONNAME%", vk.getCallName())
                        .replace("%FUNCTIONNAMEP%", vk.getC().getName() + "_p")
                        .replace("%PARAMETERS%", genParameters(vk.getParameters(), vk.getReturnType()))
                        .replace("%ARGUMENTS%", genArguments(vk.getParameters(), vk.getReturnType()))
                );
            }
        }
        return new Text(functions.toString("\n\n"));
    }

    public static Text genParameters(ChainList<VkVariable> parameters, VkVariable returnParameter){
        ChainList<Text> params = new CachedChainList<>();
        for(VkVariable parameter : parameters) params.addLast(genParameter(parameter));
        if(returnParameter != null) if(!returnParameter.isEmpty()) params.addLast(genParameter(returnParameter));
        return params.toText(", ");
    }

    public static Text genParameter(VkVariable parameter){
        return parameter.getTypename().append(" ").append(parameter.getName());
    }

    public static Text genArguments(ChainList<VkVariable> parameters, VkVariable returnParameter){
        ChainList<Text> args = new CachedChainList<>();
        for(VkVariable parameter : parameters) args.addLast(genArgument(parameter));
        if(!returnParameter.isEmpty()) args.addLast(genArgument(returnParameter));
        return args.toText(", ");
    }

    public static Text genArgument(VkVariable parameter){
        return parameter.getName();
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

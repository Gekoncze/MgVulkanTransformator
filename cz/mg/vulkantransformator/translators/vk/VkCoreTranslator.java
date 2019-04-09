package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.Configuration;
import cz.mg.vulkantransformator.EntityGroup;
import cz.mg.vulkantransformator.entities.vk.*;
import cz.mg.vulkantransformator.translators.vk.templates.TemplatesVk;


public class VkCoreTranslator {
    private static final Text vkDefineStringTemplate = new Text("    public static final String %DEFINENAME% = %DEFINEVALUE%;");
    private static final Text vkDefineTemplate = new Text("    public static final long %DEFINENAME% = %%VKGETNAME%%();\n    private static native long %%VKGETNAME%%();");
    private static final Text vkFunctionTemplate = TemplatesVk.load("core/Function");
    private static final Text vkValueTemplate = new Text("    public static final int %VALUENAME% = %VALUE%;");
    private static final Text headerTemplate = TemplatesVk.load("parts/Header");
    private static final Text coreTemplate = TemplatesVk.load("core/Core");

    public static Text translateVk(ChainList<VkEntity> entities){
        return headerTemplate.append(coreTemplate)
                .replace("%DEFINES%", genDefinesVk(entities))
                .replace("%FUNCTIONS%", genFunctionsVk(entities))
                .replace("%CONSTANTS%", genConstantsVk(entities))
                .replace("%%PACKAGE%%", genPackage());
    }

    public static Text genVkGetName(VkDefine define){
        return new Text("get").append(define.getC().getName().upperToCammel());
    }

    private static Text genPackage(){
        return Configuration.getPath(EntityGroup.VK).replace("/", ".");
    }

    private static Text genDefinesVk(ChainList<VkEntity> entities){
        ChainList<Text> defines = new CachedChainList<>();
        for(VkEntity entity : entities) {
            if (entity instanceof VkDefine) {
                VkDefine define = (VkDefine) entity;
                if(define.isString()){
                    defines.addLast(vkDefineStringTemplate
                            .replace("%DEFINENAME%", define.getName())
                            .replace("%DEFINEVALUE%", define.getValue())
                    );
                } else {
                    defines.addLast(vkDefineTemplate
                            .replace("%DEFINENAME%", define.getName())
                            .replace("%%VKGETNAME%%", genVkGetName(define))
                    );
                }
            }
        }
        return defines.toText("\n\n");
    }

    private static Text genFunctionsVk(ChainList<VkEntity> entities){
        ChainList<Text> functions = new CachedChainList<>();
        for(VkEntity entity : entities) {
            if (entity instanceof VkFunction && !(entity instanceof VkCallback)) {
                VkFunction function = (VkFunction) entity;
                VkFunction vk = (VkFunction) function;
                functions.addLast(vkFunctionTemplate
                        .replace("%FUNCTIONTYPENAME%", vk.getName())
                        .replace("%FUNCTIONNAME%", vk.getCallName())
                        .replace("%FUNCTIONNAMEP%", function.getC().getName() + "_p")
                        .replace("%PARAMETERS%", genParameters(vk.getParameters(), vk.getReturnType()))
                        .replace("%ARGUMENTS%", genArguments(vk.getParameters(), vk.getReturnType()))
                );
            }
        }
        return new Text(functions.toString("\n\n"));
    }

    private static Text genParameters(ChainList<VkVariable> parameters, VkVariable returnParameter){
        ChainList<Text> params = new CachedChainList<>();
        for(VkVariable parameter : parameters) params.addLast(genParameter(parameter));
        if(!returnParameter.isEmpty()) params.addLast(genParameter(returnParameter));
        return params.toText(", ");
    }

    private static Text genParameter(VkVariable parameter){
        return parameter.getTypename().append(" ").append(parameter.getName());
    }

    private static Text genArguments(ChainList<VkVariable> parameters, VkVariable returnParameter){
        ChainList<Text> args = new CachedChainList<>();
        for(VkVariable parameter : parameters) args.addLast(genArgument(parameter));
        if(!returnParameter.isEmpty()) args.addLast(genArgument(returnParameter));
        return args.toText(", ");
    }

    private static Text genArgument(VkVariable parameter){
        return parameter.getName();
    }

    private static Text genConstantsVk(ChainList<VkEntity> entities){
        ChainList<Text> values = new CachedChainList<>();
        for(VkEntity entity : entities) {
            if(entity instanceof VkFlagBits){
                VkFlagBits f = (VkFlagBits) entity;
                for(VkValue value : f.getValues()){
                    values.addLast(genConstantVk(value, f.getName()));
                }
            } else if (entity instanceof VkEnum) {
                VkEnum e = (VkEnum) entity;
                for(Object value : e.getValues()){ // quick fix for bug in either java or intellij idea
                    VkValue vvalue = (VkValue) value; // quick fix for bug in either java or intellij idea
                    values.addLast(genConstantVk(vvalue, e.getName()));
                }
            }
        }
        return values.toText("\n");
    }

    private static Text genConstantVk(VkValue value, Text name){
        return vkValueTemplate
                .replace("%VALUENAME%", value.getName())
                .replace("%VALUE%", name + "." + value.getName());
    }
}

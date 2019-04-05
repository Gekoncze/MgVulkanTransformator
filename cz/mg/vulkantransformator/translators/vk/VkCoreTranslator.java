package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.Configuration;
import cz.mg.vulkantransformator.EntityGroup;
import cz.mg.vulkantransformator.entities.*;
import cz.mg.vulkantransformator.entities.vk.VkFunction;
import cz.mg.vulkantransformator.entities.vk.VkValue;
import cz.mg.vulkantransformator.entities.vk.VkVariable;
import cz.mg.vulkantransformator.translators.vk.templates.TemplatesVk;
import cz.mg.vulkantransformator.utilities.StringUtilities;


public class VkCoreTranslator {
    private static final String vkDefineStringTemplate = "    public static final String %DEFINENAME% = %DEFINEVALUE%;";
    private static final String vkDefineTemplate = "    public static final long %DEFINENAME% = %%VKGETNAME%%();\n    private static native long %%VKGETNAME%%();";
    private static final String vkFunctionTemplate = TemplatesVk.load("core/Function");
    private static final String vkValueTemplate = "    public static final int %VALUENAME% = %VALUE%;";
    private static final String headerTemplate = TemplatesVk.load("parts/Header");
    private static final String coreTemplate = TemplatesVk.load("core/Core");

    public static String translateVk(ChainList<EntityTriplet> entities){
        return (headerTemplate + coreTemplate)
                .replace("%DEFINES%", genDefinesVk(entities))
                .replace("%FUNCTIONS%", genFunctionsVk(entities))
                .replace("%CONSTANTS%", genConstantsVk(entities))
                .replace("%%PACKAGE%%", genPackage());
    }

    public static String genVkGetName(DefineTriplet define){
        return "get" + StringUtilities.upperCaseToCammelCase(define.getC().getName());
    }

    private static String genPackage(){
        return Configuration.getPath(EntityGroup.VK).replace("/", ".");
    }

    private static String genDefinesVk(ChainList<EntityTriplet> entities){
        ChainList<String> defines = new CachedChainList<>();
        for(EntityTriplet entity : entities) {
            if (entity instanceof DefineTriplet) {
                DefineTriplet define = (DefineTriplet) entity;
                if(define.isString()){
                    defines.addLast(vkDefineStringTemplate
                            .replace("%DEFINENAME%", define.getVk().getName())
                            .replace("%DEFINEVALUE%", define.getVk().getValue())
                    );
                } else {
                    defines.addLast(vkDefineTemplate
                            .replace("%DEFINENAME%", define.getVk().getName())
                            .replace("%%VKGETNAME%%", genVkGetName(define))
                    );
                }
            }
        }
        return defines.toString("\n\n");
    }

    private static String genFunctionsVk(ChainList<EntityTriplet> entities){
        ChainList<String> functions = new CachedChainList<>();
        for(EntityTriplet entity : entities) {
            if (entity instanceof FunctionTriplet && !(entity instanceof CallbackTriplet)) {
                FunctionTriplet function = (FunctionTriplet) entity;
                VkFunction f = (VkFunction) function.getVk();
                functions.addLast(vkFunctionTemplate
                        .replace("%FUNCTIONTYPENAME%", function.getVk().getName())
                        .replace("%FUNCTIONNAME%", function.getC().getName().replaceFirst("PFN_", ""))
                        .replace("%FUNCTIONNAMEP%", function.getC().getName() + "_p")
                        .replace("%PARAMETERS%", genParameters(f.getParameters(), f.getReturnType()))
                        .replace("%ARGUMENTS%", genArguments(f.getParameters(), f.getReturnType()))
                );
            }
        }
        return functions.toString("\n\n");
    }

    private static String genParameters(ChainList<VkVariable> parameters, VkVariable returnParameter){
        ChainList<String> params = new CachedChainList<>();
        for(VkVariable parameter : parameters) params.addLast(genParameter(parameter));
        if(!returnParameter.isEmpty()) params.addLast(genParameter(returnParameter));
        return params.toString(", ");
    }

    private static String genParameter(VkVariable parameter){
        return parameter.getTypename() + " " + parameter.getName();
    }

    private static String genArguments(ChainList<VkVariable> parameters, VkVariable returnParameter){
        ChainList<String> args = new CachedChainList<>();
        for(VkVariable parameter : parameters) args.addLast(genArgument(parameter));
        if(!returnParameter.isEmpty()) args.addLast(genArgument(returnParameter));
        return args.toString(", ");
    }

    private static String genArgument(VkVariable parameter){
        return parameter.getName();
    }

    private static String genConstantsVk(ChainList<EntityTriplet> entities){
        ChainList<String> values = new CachedChainList<>();
        for(EntityTriplet entity : entities) {
            if (entity instanceof EnumTriplet) {
                EnumTriplet e = (EnumTriplet) entity;
                for(VkValue value : e.getVk().getValues()){
                    values.addLast(genConstantVk(value, e.getVk().getName()));
                }
            }
            if(entity instanceof FlagBitsTriplet){
                FlagBitsTriplet f = (FlagBitsTriplet) entity;
                for(VkValue value : f.getVk().getValues()){
                    values.addLast(genConstantVk(value, f.getVk().getName()));
                }
            }
        }
        return values.toString("\n");
    }

    private static String genConstantVk(VkValue value, String name){
        return vkValueTemplate
                .replace("%VALUENAME%", value.getName())
                .replace("%VALUE%", name + "." + value.getName());
    }
}

package cz.mg.vulkantransformator.translators;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.Configuration;
import cz.mg.vulkantransformator.EntityGroup;
import cz.mg.vulkantransformator.entities.*;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.vk.VkFunction;
import cz.mg.vulkantransformator.entities.vk.VkVariable;
import cz.mg.vulkantransformator.entities.vk.VkValue;
import cz.mg.vulkantransformator.translators.c.templates.TemplatesC;
import cz.mg.vulkantransformator.translators.vk.templates.TemplatesVk;
import cz.mg.vulkantransformator.utilities.StringUtilities;


public class VulkanTranslator {
    private static final String cDefineTemplate = TemplatesC.load("vk/Define");
    private static final String vkDefineStringTemplate = "    public static final String %DEFINENAME% = %DEFINEVALUE%;";
    private static final String vkDefineTemplate = "    public static final long %DEFINENAME% = %%VKGETNAME%%();\n    private static native long %%VKGETNAME%%();";
    private static final String vkFunctionTemplate = TemplatesVk.load("vk/Function");
    private static final String vkValueTemplate = "    public static final int %VALUENAME% = %VALUE%;";
    private static final String documentationTemplate = TemplatesVk.load("vk/Documentation");

    public static String translate(EntityGroup group, ChainList<EntityTriplet> entities){
        switch (group){
            case C: return translateC(entities);
            case VK: return translateVk(entities);
            case VULKAN: return translateVulkan(entities);
            default: throw new UnsupportedOperationException("" + group);
        }
    }

    public static String translateC(ChainList<EntityTriplet> entities){
        return TemplatesC.load("Header") + genDefinesC(entities);
    }

    private static String genDefinesC(ChainList<EntityTriplet> entities){
        ChainList<String> defines = new CachedChainList<>();
        for(EntityTriplet entity : entities){
            if(entity instanceof DefineTriplet){
                DefineTriplet define = (DefineTriplet) entity;
                if(!define.isString()){
                    defines.addLast(cDefineTemplate
                            .replace("%%CVKPACKAGE%%", genPackageCvk())
                            .replace("%%VKGETNAME%%", genVkGetName(define))
                            .replace("%%CNAME%%", define.getC().getName())
                    );
                }
            }
        }
        return defines.toString("\n");
    }

    public static String genPackageCvk(){
        return Configuration.getPath(EntityGroup.VK).replace("/", "_");
    }

    public static String genVkGetName(DefineTriplet define){
        return "get" + StringUtilities.upperCaseToCammelCase(define.getC().getName());
    }

    public static String translateVk(ChainList<EntityTriplet> entities){
        return (TemplatesVk.load("Header") + TemplatesVk.load("vk/Vk"))
                .replace("%DEFINES%", genDefinesVk(entities))
                .replace("%FUNCTIONS%", genFunctionsVk(entities))
                .replace("%CONSTANTS%", genConstantsVk(entities))
                .replace("%%PACKAGE%%", genPackage());
    }

    private static String genPackage(){
        return Configuration.getPath(EntityGroup.VK).replace("/", ".");
    }

    private static String genDefinesVk(ChainList<EntityTriplet> entities){
        ChainList<String> defines = new CachedChainList<>();
        for(EntityTriplet entity : entities) {
            if (entity instanceof DefineTriplet) {
                DefineTriplet define = (DefineTriplet) entity;
                String documentation = genDocumentation(define.getC());
                if(define.isString()){
                    defines.addLast(documentation + "\n" + vkDefineStringTemplate
                            .replace("%DEFINENAME%", define.getVk().getName())
                            .replace("%DEFINEVALUE%", define.getVk().getValue())
                    );
                } else {
                    defines.addLast(documentation + "\n" + vkDefineTemplate
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
                        .replace("%%DOCUMENTATION%%", genDocumentation(function.getC()))
                );
            }
        }
        return functions.toString("\n\n");
    }

    private static String genDocumentation(CEntity entity){
        return StringUtilities.replaceLast(documentationTemplate.replace("%%CNAME%%", entity.getName()), "\n", "");
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

    public static String translateVulkan(ChainList<EntityTriplet> entities){
        return null;
    }
}

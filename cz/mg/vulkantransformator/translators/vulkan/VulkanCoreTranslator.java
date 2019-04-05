package cz.mg.vulkantransformator.translators.vulkan;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.Configuration;
import cz.mg.vulkantransformator.EntityGroup;
import cz.mg.vulkantransformator.entities.CallbackTriplet;
import cz.mg.vulkantransformator.entities.DefineTriplet;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.FunctionTriplet;
import cz.mg.vulkantransformator.entities.c.CEntity;
import cz.mg.vulkantransformator.entities.vk.VkFunction;
import cz.mg.vulkantransformator.entities.vulkan.VulkanFunction;
import cz.mg.vulkantransformator.entities.vulkan.VulkanVariable;
import cz.mg.vulkantransformator.translators.vulkan.templates.TemplatesVulkan;
import cz.mg.vulkantransformator.utilities.StringUtilities;


public class VulkanCoreTranslator {
    private static final String coreTemplate = TemplatesVulkan.load("core/Core");
    private static final String headerTemplate = TemplatesVulkan.load("parts/Header");
    private static final String documentationTemplate = TemplatesVulkan.load("core/Documentation");
    private static final String defineTemplate = "    public static final %DEFINETYPE% %DEFINENAME% = %DEFINEVALUE%;";
    private static final String functionTemplate = TemplatesVulkan.load("core/PlainFunction");
    private static final String protectedFunctionTemplate = TemplatesVulkan.load("core/ProtectedFunction");

    public static String translate(ChainList<EntityTriplet> entities) {
        return (headerTemplate + coreTemplate)
                .replace("%DEFINES%", genDefines(entities))
                .replace("%FUNCTIONS%", genFunctions(entities))
                .replace("%%PACKAGE%%", genPackage());
    }

    private static String genPackage(){
        return Configuration.getPath(EntityGroup.VULKAN).replace("/", ".");
    }

    private static String genDefines(ChainList<EntityTriplet> entities) {
        ChainList<String> defines = new CachedChainList<>();
        for(EntityTriplet entity : entities){
            if (entity instanceof DefineTriplet){
                defines.addLast(genDefine((DefineTriplet) entity));
            }
        }
        return defines.toString("\n\n");
    }

    private static String genDefine(DefineTriplet define){
        String documentation = genDocumentation(define.getC());
        return documentation + "\n" + defineTemplate
                .replace("%DEFINENAME%", define.getVulkan().getName())
                .replace("%DEFINEVALUE%", "Vk." + define.getVk().getName())
                .replace("%DEFINETYPE%", define.isString() ? "String" : "long");
    }

    private static String genFunctions(ChainList<EntityTriplet> entities) {
        ChainList<String> functions = new CachedChainList<>();
        for(EntityTriplet entity : entities) {
            if (entity instanceof FunctionTriplet && !(entity instanceof CallbackTriplet)) {
                functions.addLast(genFunction((FunctionTriplet) entity));
            }
        }
        return functions.toString("\n\n");
    }

    private static String genFunction(FunctionTriplet function){
        VulkanFunction f = (VulkanFunction) function.getVulkan();
        if(f.getReturnType().getTypename().equals("VulkanResult")) return genProtectedFunction(function);
        VulkanFunction v = (VulkanFunction) function.getVulkan();
        return functionTemplate
                .replace("%VULKANFUNCTIONNAME%", genVulkanFunctionName(function))
                .replace("%PARAMETERS%", genParameters(v.getParameters(), v.getReturnType()))
                .replace("%VKFUNCTIONNAME%", genVkFunctionName(function))
                .replace("%ARGUMENTS%", genArguments(v.getParameters(), v.getReturnType()));
    }

    private static String genProtectedFunction(FunctionTriplet function){
        VulkanFunction v = (VulkanFunction) function.getVulkan();
        return protectedFunctionTemplate
                .replace("%VULKANFUNCTIONNAME%", genVulkanFunctionName(function))
                .replace("%PARAMETERS%", genParameters(v.getParameters(), v.getReturnType()))
                .replace("%VKFUNCTIONNAME%", genVkFunctionName(function))
                .replace("%ARGUMENTS%", genArguments(v.getParameters(), v.getReturnType()));
    }

    private static String genParameters(ChainList<VulkanVariable> parameters, VulkanVariable returnParameter){
        ChainList<String> params = new CachedChainList<>();
        for(VulkanVariable parameter : parameters) params.addLast(genParameter(parameter));
        if(!returnParameter.isEmpty()) params.addLast(genParameter(returnParameter));
        return params.toString(", ");
    }

    private static String genParameter(VulkanVariable parameter){
        return parameter.getTypename() + " " + parameter.getName();
    }

    private static String genArguments(ChainList<VulkanVariable> arguments, VulkanVariable returnParameter){
        ChainList<String> args = new CachedChainList<>();
        for(VulkanVariable argument : arguments) args.addLast(genArgument(argument));
        if(!returnParameter.isEmpty()) args.addLast(genArgument(returnParameter));
        return args.toString(", ");
    }

    private static String genArgument(VulkanVariable argument){
        return argument.getName() + ".getVk()";
    }

    private static String genVulkanFunctionName(FunctionTriplet function){
        VulkanFunction v = (VulkanFunction) function.getVulkan();
        String name = StringUtilities.replaceBegin(v.getName(), "PFN", "");
        name = StringUtilities.replaceBegin(name, "vk", "");
        return StringUtilities.lowerFirst(name);
    }

    private static String genVkFunctionName(FunctionTriplet function){
        VkFunction vk = (VkFunction) function.getVk();
        String name = StringUtilities.replaceBegin(vk.getName(), "PFN", "");
        return name;
    }

    private static String genDocumentation(CEntity entity){
        return StringUtilities.replaceLast(documentationTemplate.replace("%%CNAME%%", entity.getName()), "\n", "");
    }
}

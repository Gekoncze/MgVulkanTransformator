package cz.mg.vulkantransformator.translators.vulkan;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.Configuration;
import cz.mg.vulkantransformator.EntityGroup;
import cz.mg.vulkantransformator.entities.CallbackTriplet;
import cz.mg.vulkantransformator.entities.DefineTriplet;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.FunctionTriplet;
import cz.mg.vulkantransformator.entities.vk.VkFunction;
import cz.mg.vulkantransformator.entities.vulkan.VulkanFunction;
import cz.mg.vulkantransformator.entities.vulkan.VulkanVariable;
import cz.mg.vulkantransformator.translators.vulkan.templates.TemplatesVulkan;
import cz.mg.vulkantransformator.utilities.StringUtilities;


public class VulkanCoreTranslator {
    private static final String coreTemplate = TemplatesVulkan.load("core/Core");
    private static final String headerTemplate = TemplatesVulkan.load("parts/Header");
    private static final String defineTemplate = "    public static final %DEFINETYPE% %DEFINENAME% = %DEFINEVALUE%;";
    private static final String functionTemplate = TemplatesVulkan.load("core/Function");
    private static final String protectedFunctionTemplate = TemplatesVulkan.load("core/ProtectedFunction");
    private static final String createFunctionTemplate = TemplatesVulkan.load("core/CreateFunction");
    private static final String enumerateFunctionTemplate = TemplatesVulkan.load("core/EnumerateFunction");

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
        return defines.toString("\n");
    }

    private static String genDefine(DefineTriplet define){
        return defineTemplate
                .replace("%DEFINENAME%", define.getVulkan().getName())
                .replace("%DEFINEVALUE%", "Vk." + define.getVk().getName())
                .replace("%DEFINETYPE%", define.isString() ? "String" : "long");
    }

    private static String genFunctions(ChainList<EntityTriplet> entities) {
        ChainList<String> functions = new CachedChainList<>();
        for(EntityTriplet entity : entities) {
            if (entity instanceof FunctionTriplet && !(entity instanceof CallbackTriplet)) {
                functions.addLast(genFunction(entities, (FunctionTriplet) entity));
            }
        }
        return functions.toString("\n\n");
    }

    private static String genFunction(ChainList<EntityTriplet> entities, FunctionTriplet function){
        VulkanFunction f = (VulkanFunction) function.getVulkan();
        if(f.getReturnType().getTypename().equals("VulkanResult")) return genSpecialFunction(entities, function);
        return functionTemplate
                .replace("%VULKANFUNCTIONNAME%", genVulkanFunctionName(function))
                .replace("%PARAMETERS%", genParameters(f.getParameters(), f.getReturnType()))
                .replace("%VKFUNCTIONNAME%", genVkFunctionName(function))
                .replace("%ARGUMENTS%", genArguments(f.getParameters(), f.getReturnType()));
    }

    private static String genSpecialFunction(ChainList<EntityTriplet> entities, FunctionTriplet function){
        VulkanFunction v = (VulkanFunction) function.getVulkan();
        String name = genVulkanFunctionName(function);
        if(isEnumerateFunction(entities, v, name)) return genEnumerateFunction(function);
        if(isCreateFunction(entities, v, name)) return genCreateFunction(function);
        return genProtectedFunction(function);
    }

    private static boolean isEnumerateFunction(ChainList<EntityTriplet> entities, VulkanFunction v, String name){
        if(v.getParameters().count() >= 2 && name.startsWith("enumerate")){
            boolean isBeforeLastNumber = v.getParameters().getLastItem().getPrevious().getTypename().equals("VulkanUInt32");
            boolean isBeforeLastCount = v.getParameters().getLastItem().getPrevious().getName().endsWith("Count");
            if(isBeforeLastNumber && isBeforeLastCount) return true;
        }
        return false;
    }

    private static boolean isCreateFunction(ChainList<EntityTriplet> entities, VulkanFunction v, String name){
        if(v.getParameters().count() >= 1 && name.startsWith("create")){
            boolean isLastHandle = VulkanHandleTranslator.isHandle(entities, v.getParameters().getLast().getTypename());
            if(isLastHandle) return true;
        }
        return false;
    }

    private static String genProtectedFunction(FunctionTriplet function){
        VulkanFunction v = (VulkanFunction) function.getVulkan();
        return protectedFunctionTemplate
                .replace("%VULKANFUNCTIONNAME%", genVulkanFunctionName(function))
                .replace("%PARAMETERS%", genParameters(v.getParameters(), v.getReturnType()))
                .replace("%VKFUNCTIONNAME%", genVkFunctionName(function))
                .replace("%ARGUMENTS%", genArguments(v.getParameters(), v.getReturnType()));
    }

    private static String genEnumerateFunction(FunctionTriplet function){
        VulkanFunction v = (VulkanFunction) function.getVulkan();
        VulkanVariable handle = v.getParameters().getLast();
        return enumerateFunctionTemplate
                .replace("%VULKANFUNCTIONNAME%", genVulkanFunctionName(function))
                .replace("%PARAMETERS%", genParameters(reduce(v.getParameters(), 2), null))
                .replace("%VKFUNCTIONNAME%", genVkFunctionName(function))
                .replace("%ARGUMENTS%", genArguments(reduce(v.getParameters(), 2), null))
                .replace("%RETURN%", handle.getTypename())
                .replace("%COMMA%", v.getParameters().count() > 2 ? ", " : "");
    }

    private static String genCreateFunction(FunctionTriplet function){
        VulkanFunction v = (VulkanFunction) function.getVulkan();
        VulkanVariable handle = v.getParameters().getLast();
        return createFunctionTemplate
                .replace("%VULKANFUNCTIONNAME%", genVulkanFunctionName(function))
                .replace("%PARAMETERS%", genParameters(reduce(v.getParameters(), 1), null))
                .replace("%VKFUNCTIONNAME%", genVkFunctionName(function))
                .replace("%ARGUMENTS%", genArguments(reduce(v.getParameters(), 1), null))
                .replace("%RETURN%", handle.getTypename());
    }

    public static String genParameters(ChainList<VulkanVariable> parameters, VulkanVariable returnParameter){
        ChainList<String> params = new CachedChainList<>();
        for(VulkanVariable parameter : parameters) params.addLast(genParameter(parameter));
        if(returnParameter != null) if(!returnParameter.isEmpty()) params.addLast(genParameter(returnParameter));
        return params.toString(", ");
    }

    private static String genParameter(VulkanVariable parameter){
        return parameter.getTypename() + " " + parameter.getName();
    }

    private static String genArguments(ChainList<VulkanVariable> arguments, VulkanVariable returnParameter){
        ChainList<String> args = new CachedChainList<>();
        for(VulkanVariable argument : arguments) args.addLast(genArgument(argument));
        if(returnParameter != null) if(!returnParameter.isEmpty()) args.addLast(genArgument(returnParameter));
        return args.toString(", ");
    }

    private static String genArgument(VulkanVariable argument){
        return argument.getName() + " != null ? " + argument.getName() + ".getVk() : null";
    }

    public static String genVulkanFunctionName(FunctionTriplet function){
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

    private static ChainList<VulkanVariable> reduce(ChainList<VulkanVariable> parameters, int count){
        ChainList<VulkanVariable> params = new CachedChainList<>(parameters);
        for(int i = 0; i < count; i++) params.removeLast();
        return params;
    }
}

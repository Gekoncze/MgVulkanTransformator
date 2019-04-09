package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.Configuration;
import cz.mg.vulkantransformator.EntityGroup;
import cz.mg.vulkantransformator.entities.vk.*;
import cz.mg.vulkantransformator.translators.vk.templates.TemplatesVk;


public class VkCoreSimplifiedTranslator {
    private static final Text coreTemplate = TemplatesVk.load("core/simplified/Core");
    private static final Text headerTemplate = TemplatesVk.load("parts/Header");
    private static final Text functionTemplate = TemplatesVk.load("core/simplified/Function");
    private static final Text protectedFunctionTemplate = TemplatesVk.load("core/simplified/ProtectedFunction");
    private static final Text createFunctionTemplate = TemplatesVk.load("core/simplified/CreateFunction");
    private static final Text enumerateFunctionTemplate = TemplatesVk.load("core/simplified/EnumerateFunction");

    public static Text translate(ChainList<VkEntity> entities) {
        return headerTemplate.append(coreTemplate)
                .replace("%FUNCTIONS%", genFunctions(entities))
                .replace("%%PACKAGE%%", genPackage());
    }

    private static Text genPackage(){
        return Configuration.getPath(EntityGroup.VK).replace("/", ".");
    }

    private static Text genFunctions(ChainList<VkEntity> entities) {
        ChainList<Text> functions = new CachedChainList<>();
        for(VkEntity entity : entities) {
            if (entity instanceof VkFunction && !(entity instanceof VkCallback)) {
                functions.addLast(genFunction(entities, (VkFunction) entity));
            }
        }
        return functions.toText("\n\n");
    }

    private static Text genFunction(ChainList<VkEntity> entities, VkFunction vk){
        if(vk.getReturnType().getTypename().equals("VkResult")) return genSpecialFunction(entities, vk);
        return functionTemplate
                .replace("%VULKANFUNCTIONNAME%", vk.getCallName())
                .replace("%PARAMETERS%", genParameters(vk.getParameters(), vk.getReturnType()))
                .replace("%VKFUNCTIONNAME%", vk.getCallName())
                .replace("%ARGUMENTS%", genArguments(vk.getParameters(), vk.getReturnType()));
    }

    private static Text genSpecialFunction(ChainList<VkEntity> entities, VkFunction vk){
        if(isEnumerateFunction(entities, vk)) return genEnumerateFunction(vk);
        if(isCreateFunction(entities, vk)) return genCreateFunction(vk);
        return genProtectedFunction(vk);
    }

    private static boolean isEnumerateFunction(ChainList<VkEntity> entities, VkFunction v){
        if(v.getParameters().count() >= 2 && v.getCallName().startsWith("enumerate")){
            VkVariable beforeLast = (VkVariable) v.getParameters().getLastItem().getPrevious(); // quick fix for java or intellij idea bug
            boolean isBeforeLastNumber = beforeLast.getTypename().equals("VulkanUInt32");
            boolean isBeforeLastCount = beforeLast.getName().endsWith("Count");
            if(isBeforeLastNumber && isBeforeLastCount) return true;
        }
        return false;
    }

    private static boolean isCreateFunction(ChainList<VkEntity> entities, VkFunction v){
        if(v.getParameters().count() >= 1 && v.getCallName().startsWith("create")){
            VkVariable last = (VkVariable) v.getParameters().getLast(); // quick fix for java or intellij idea bug
            boolean isLastHandle = isHandle(entities, last.getTypename());
            if(isLastHandle) return true;
        }
        return false;
    }

    public static boolean isHandle(ChainList<VkEntity> entities, Text name){
        for(VkEntity e : entities) {
            if(e.getName().equals(name)) {
                return e instanceof VkHandle;
            }
        }
        return false;
    }

    private static Text genProtectedFunction(VkFunction vk){
        return protectedFunctionTemplate
                .replace("%VULKANFUNCTIONNAME%", vk.getCallName())
                .replace("%PARAMETERS%", genParameters(vk.getParameters(), vk.getReturnType()))
                .replace("%VKFUNCTIONNAME%", vk.getCallName())
                .replace("%ARGUMENTS%", genArguments(vk.getParameters(), vk.getReturnType()));
    }

    private static Text genEnumerateFunction(VkFunction vk){
        VkVariable handle = (VkVariable) vk.getParameters().getLast(); // quick fix for java or intellij idea bug
        return enumerateFunctionTemplate
                .replace("%VULKANFUNCTIONNAME%", vk.getCallName())
                .replace("%PARAMETERS%", genParameters(reduce(vk.getParameters(), 2), null))
                .replace("%VKFUNCTIONNAME%", vk.getCallName())
                .replace("%ARGUMENTS%", genArguments(reduce(vk.getParameters(), 2), null))
                .replace("%RETURN%", handle.getTypename())
                .replace("%COMMA%", vk.getParameters().count() > 2 ? ", " : "");
    }

    private static Text genCreateFunction(VkFunction vk){
        VkVariable handle = (VkVariable) vk.getParameters().getLast(); // quick fix for java or intellij idea bug
        return createFunctionTemplate
                .replace("%VULKANFUNCTIONNAME%", vk.getCallName())
                .replace("%PARAMETERS%", genParameters(reduce(vk.getParameters(), 1), null))
                .replace("%VKFUNCTIONNAME%", vk.getCallName())
                .replace("%ARGUMENTS%", genArguments(reduce(vk.getParameters(), 1), null))
                .replace("%RETURN%", handle.getTypename());
    }

    public static Text genParameters(ChainList<VkVariable> parameters, VkVariable returnParameter){
        ChainList<Text> params = new CachedChainList<>();
        for(VkVariable parameter : parameters) params.addLast(genParameter(parameter));
        if(returnParameter != null) if(!returnParameter.isEmpty()) params.addLast(genParameter(returnParameter));
        return params.toText(", ");
    }

    private static Text genParameter(VkVariable parameter){
        return parameter.getTypename().append(" ").append(parameter.getName());
    }

    private static Text genArguments(ChainList<VkVariable> arguments, VkVariable returnParameter){
        ChainList<Text> args = new CachedChainList<>();
        for(VkVariable argument : arguments) args.addLast(genArgument(argument));
        if(returnParameter != null) if(!returnParameter.isEmpty()) args.addLast(genArgument(returnParameter));
        return args.toText(", ");
    }

    private static Text genArgument(VkVariable argument){
        return argument.getName().append(" != null ? ").append(argument.getName()).append(".getVk() : null");
    }

    private static ChainList<VkVariable> reduce(ChainList<VkVariable> parameters, int count){
        ChainList<VkVariable> params = new CachedChainList<>(parameters);
        for(int i = 0; i < count; i++) params.removeLast();
        return params;
    }
}

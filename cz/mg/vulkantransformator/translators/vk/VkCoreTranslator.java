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
    private static final Text vkDefineIntegerTemplate = new Text("    public static final long %DEFINENAME% = %DEFINEVALUE%;");
    private static final Text vkDefineFloatTemplate = new Text("    public static final float %DEFINENAME% = %DEFINEVALUE%;");
    private static final Text vkFunctionTemplate = TemplatesVk.load("core/Function");
    private static final Text vkValueTemplate = new Text("    public static final int %VALUENAME% = %VALUE%;");
    private static final Text headerTemplate = TemplatesVk.load("parts/Header");
    private static final Text coreTemplate = TemplatesVk.load("core/Core");

    private static final Text coreSimplifiedTemplate = TemplatesVk.load("core/simplified/Core");
    private static final Text createFunctionSimplifiedTemplate = TemplatesVk.load("core/simplified/CreateFunction");
    private static final Text enumerateFunctionSimplifiedTemplate = TemplatesVk.load("core/simplified/EnumerateFunction");
    private static final Text protectedFunctionSimplifiedTemplate = TemplatesVk.load("core/simplified/ProtectedFunction");

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
                Text template = null;

                if(define.isFloat()) template = vkDefineFloatTemplate;
                else if(define.isString()) template = vkDefineStringTemplate;
                else template = vkDefineIntegerTemplate;

                defines.addLast(template
                        .replace("%DEFINENAME%", define.getName())
                        .replace("%DEFINEVALUE%", define.getValue())
                );
            }
        }
        return defines.toText("\n");
    }

    private static Text genFunctionsVk(ChainList<VkEntity> entities){
        ChainList<Text> functions = new CachedChainList<>();
        for(VkEntity entity : entities) {
            if (entity instanceof VkFunction && !(entity instanceof VkCallback)) {
                VkFunction vk = (VkFunction) entity;
                functions.addLast(vkFunctionTemplate
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

    private static Text genParameters(ChainList<VkVariable> parameters, VkVariable returnParameter){
        ChainList<Text> params = new CachedChainList<>();
        for(VkVariable parameter : parameters) params.addLast(genParameter(parameter));
        if(returnParameter != null) if(!returnParameter.isEmpty()) params.addLast(genParameter(returnParameter));
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


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static Text translateSimplifiedVk(ChainList<VkEntity> entities){
        return headerTemplate.append(coreSimplifiedTemplate)
                .replace("%%PACKAGE%%", genPackage())
                .replace("%FUNCTIONS%", genFunctionsSimplified(entities));
    }

    private static Text genFunctionsSimplified(ChainList<VkEntity> entities) {
        ChainList<Text> functions = new CachedChainList<>();
        for(VkEntity entity : entities) {
            if (entity instanceof VkFunction && !(entity instanceof VkCallback)) {
                Text f = genFunctionSimplified(entities, (VkFunction) entity);
                if(f != null) functions.addLast(f);
            }
        }
        return functions.toText("\n\n");
    }

    private static Text genFunctionSimplified(ChainList<VkEntity> entities, VkFunction vk){
        if(vk.getReturnType().getTypename().equals("VkResult")) return genSpecialFunctionSimplified(entities, vk);
        else return null;
    }

    private static Text genSpecialFunctionSimplified(ChainList<VkEntity> entities, VkFunction vk){
        Text name = genVkFunctionNameSimplified(vk);
        if(isEnumerateFunctionSimplified(vk, name)) return genEnumerateFunctionSimplified(vk);
        if(isCreateFunctionSimplified(entities, vk, name)) return genCreateFunctionSimplified(vk);
        return genProtectedFunctionSimplified(vk);
    }

    private static boolean isEnumerateFunctionSimplified(VkFunction vk, Text name){
        if(vk.getParameters().count() >= 2 && name.startsWith("vkEnumerate")){
            VkVariable beforeLast = (VkVariable) vk.getParameters().getLastItem().getPrevious();
            boolean isBeforeLastNumber = beforeLast.getTypename().equals("VkUInt32");
            boolean isBeforeLastCount = beforeLast.getName().endsWith("Count");
            if(isBeforeLastNumber && isBeforeLastCount) return true;
        }
        return false;
    }

    private static boolean isCreateFunctionSimplified(ChainList<VkEntity> entities, VkFunction vk, Text name){
        if(vk.getParameters().count() >= 1 && name.startsWith("vkCreate")){
            VkVariable last = (VkVariable) vk.getParameters().getLast();
            boolean isLastHandle = isHandle(entities, last.getTypename());
            if(isLastHandle) return true;
        }
        return false;
    }

    private static Text genProtectedFunctionSimplified(VkFunction vk){
        return protectedFunctionSimplifiedTemplate
                .replace("%VULKANFUNCTIONNAME%", genVkFunctionNameSimplified(vk))
                .replace("%PARAMETERS%", genParameters(vk.getParameters(), null))
                .replace("%VKFUNCTIONNAME%", genVkFunctionNameSimplified(vk))
                .replace("%ARGUMENTS%", genArgumentsSimplified(vk.getParameters(), null));
    }

    private static Text genEnumerateFunctionSimplified(VkFunction vk){
        VkVariable last = (VkVariable) vk.getParameters().getLast();
        return enumerateFunctionSimplifiedTemplate
                .replace("%VULKANFUNCTIONNAME%", genVkFunctionNameSimplified(vk))
                .replace("%PARAMETERS%", genParameters(reduceSimplified(vk.getParameters(), 2), null))
                .replace("%VKFUNCTIONNAME%", genVkFunctionNameSimplified(vk))
                .replace("%ARGUMENTS%", genArgumentsSimplified(reduceSimplified(vk.getParameters(), 2), null))
                .replace("%RETURN%", last.getTypename())
                .replace("%COMMA%", vk.getParameters().count() > 2 ? ", " : "");
    }

    private static Text genCreateFunctionSimplified(VkFunction vk){
        VkVariable last = (VkVariable) vk.getParameters().getLast();
        return createFunctionSimplifiedTemplate
                .replace("%VULKANFUNCTIONNAME%", genVkFunctionNameSimplified(vk))
                .replace("%PARAMETERS%", genParameters(reduceSimplified(vk.getParameters(), 2), null))
                .replace("%VKFUNCTIONNAME%", genVkFunctionNameSimplified(vk))
                .replace("%ARGUMENTS%", genArgumentsSimplified(reduceSimplified(vk.getParameters(), 2), null))
                .replace("%RETURN%", last.getTypename());
    }

    private static Text genArgumentsSimplified(ChainList<VkVariable> arguments, VkVariable returnParameter){
        ChainList<Text> args = new CachedChainList<>();
        for(VkVariable argument : arguments) args.addLast(genArgumentSimplified(argument));
        if(returnParameter != null) if(!returnParameter.isEmpty()) args.addLast(genArgumentSimplified(returnParameter));
        return args.toText(", ");
    }

    private static Text genArgumentSimplified(VkVariable argument){
        return argument.getName();
    }

    private static Text genVkFunctionNameSimplified(VkFunction vk){
        return vk.getName().replaceBegin("PFN", "");
    }

    private static ChainList<VkVariable> reduceSimplified(ChainList<VkVariable> parameters, int count){
        ChainList<VkVariable> params = new CachedChainList<>(parameters);
        for(int i = 0; i < count; i++) params.removeLast();
        return params;
    }

    public static boolean isHandle(ChainList<VkEntity> entities, Text name){
        for(VkEntity e : entities) {
            if(e.getName() != null){
                if(e.getName().equals(name)) {
                    return e instanceof VkHandle;
                }
            }
        }
        return false;
    }
}

package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.converters.utilities.TypenameConverter;
import cz.mg.vulkantransformator.entities.vk.*;
import cz.mg.vulkantransformator.translators.vk.templates.TemplatesVk;
import static cz.mg.vulkantransformator.translators.vk.VkCoreTranslator.genParameters;


public class VkCoreSimplifiedTranslator {
    private static final Text createFunctionSimplifiedTemplate = TemplatesVk.load("core/simplified/CreateFunction");
    private static final Text destroyFunctionSimplifiedTemplate = TemplatesVk.load("core/simplified/DestroyFunction");
    private static final Text enumerateFunctionSimplifiedTemplate = TemplatesVk.load("core/simplified/EnumerateFunction");
    private static final Text protectedFunctionSimplifiedTemplate = TemplatesVk.load("core/simplified/ProtectedFunction");
    private static final Text protectedGetFunctionSimplifiedTemplate = TemplatesVk.load("core/simplified/ProtectedGetFunction");
    private static final Text protectedGetArrayFunctionSimplifiedTemplate = TemplatesVk.load("core/simplified/ProtectedGetArrayFunction");
    private static final Text getFunctionSimplifiedTemplate = TemplatesVk.load("core/simplified/GetFunction");
    private static final Text getArrayFunctionSimplifiedTemplate = TemplatesVk.load("core/simplified/GetArrayFunction");
    private static final Text statusFunctionSimplifiedTemplate = TemplatesVk.load("core/simplified/StatusFunction");

    public static Text genFunctionsSimplified(ChainList<VkEntity> entities) {
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
        if(vk.getReturnType().getTypename().equals("VkResult")){
            if(isEnumerateFunctionSimplified(entities, vk)) return genEnumerateFunctionSimplified(vk);
            if(isCreateFunctionSimplified(entities, vk)) return genCreateFunctionSimplified(vk);
            if(isStatusFunctionSimplified(entities, vk)) return genStatusFunctionSimplified(vk);
            if(isProtectedGetArrayFunctionSimplified(entities, vk)) return genProtectedGetArrayFunctionSimplified(vk);
            if(isProtectedGetFunctionSimplified(entities, vk)) return genProtectedGetFunctionSimplified(vk);
            return genProtectedFunctionSimplified(vk);
        } else if(vk.getReturnType().isEmpty()) {
            if(isDestroyFunctionSimplified(entities, vk)) return genDestroyFunctionSimplified(vk);
            if(isGetArrayFunctionSimplified(entities, vk)) return genGetArrayFunctionSimplified(vk);
            if(isGetFunctionSimplified(entities, vk)) return genGetFunctionSimplified(vk);
        }
        return null;
    }

    private static boolean isEnumerateFunctionSimplified(ChainList<VkEntity> entities, VkFunction vk){
        if(vk.getParameters().count() >= 2 && vk.getCallName().startsWith("vkEnumerate")){
            VkVariable last = (VkVariable) vk.getParameters().getLast();
            VkVariable beforeLast = (VkVariable) vk.getParameters().getLastItem().getPrevious();
            boolean isLastOk = !last.getTypename().equals("VkObject");
            boolean isBeforeLastNumber = beforeLast.getTypename().equals("VkUInt32");
            boolean isBeforeLastCount = beforeLast.getName().endsWith("Count");
            if(isLastOk && isBeforeLastNumber && isBeforeLastCount) return true;
        }
        return false;
    }

    private static boolean isCreateFunctionSimplified(ChainList<VkEntity> entities, VkFunction vk){
        if(vk.getParameters().count() >= 1 && (vk.getCallName().startsWith("vkCreate") || vk.getCallName().startsWith("vkAllocateMemory"))){
            VkVariable last = (VkVariable) vk.getParameters().getLast();
            boolean isLastHandle = isHandle(entities, last.getTypename());
            if(isLastHandle) return true;
        }
        return false;
    }

    private static boolean isDestroyFunctionSimplified(ChainList<VkEntity> entities, VkFunction vk){
        if(vk.getParameters().count() >= 2 && (vk.getCallName().startsWith("vkDestroy") || vk.getCallName().startsWith("vkFreeMemory"))){
            VkVariable beforeLast = (VkVariable) vk.getParameters().getLastItem().getPrevious();
            boolean isBeforeLastHandle = isHandle(entities, beforeLast.getTypename());
            if(isBeforeLastHandle) return true;
        }
        return false;
    }

    private static boolean isGetArrayFunctionSimplified(ChainList<VkEntity> entities, VkFunction vk){
        if(vk.getParameters().count() >= 2 && vk.getCallName().startsWith("vkGet")){
            VkVariable last = (VkVariable) vk.getParameters().getLast();
            VkVariable beforeLast = (VkVariable) vk.getParameters().getLastItem().getPrevious();
            boolean isLastOk = !last.getTypename().equals("VkObject");
            boolean isBeforeLastNumber = beforeLast.getTypename().equals("VkUInt32");
            boolean isBeforeLastCount = beforeLast.getName().endsWith("Count");
            if(isLastOk && isBeforeLastNumber && isBeforeLastCount) return true;
        }
        return false;
    }

    private static boolean isGetFunctionSimplified(ChainList<VkEntity> entities, VkFunction vk){
        if(vk.getParameters().count() >= 2 && (vk.getCallName().startsWith("vkGet") || (vk.getCallName().startsWith("vkAllocate") && !vk.getCallName().startsWith("vkAllocateMemory")))){
            VkVariable last = (VkVariable) vk.getParameters().getLast();
            boolean isLastOk = !last.getTypename().equals("VkObject");
            if(isLastOk) return true;
        }
        return false;
    }
    private static boolean isStatusFunctionSimplified(ChainList<VkEntity> entities, VkFunction vk){
        if(vk.getParameters().count() >= 2 && TypenameConverter.stripExtensionName(vk.getCallName()).endsWith("Status") && vk.getCallName().startsWith("vkGet")){
            return true;
        }
        return false;
    }


    private static boolean isProtectedGetArrayFunctionSimplified(ChainList<VkEntity> entities, VkFunction vk){
        if(vk.getParameters().count() >= 2 && vk.getCallName().startsWith("vkGet")){
            VkVariable last = (VkVariable) vk.getParameters().getLast();
            VkVariable beforeLast = (VkVariable) vk.getParameters().getLastItem().getPrevious();
            boolean isLastOk = !last.getTypename().equals("VkObject");
            boolean isBeforeLastNumber = beforeLast.getTypename().equals("VkUInt32");
            boolean isBeforeLastCount = beforeLast.getName().endsWith("Count");
            if(isLastOk && isBeforeLastNumber && isBeforeLastCount) return true;
        }
        return false;
    }

    private static boolean isProtectedGetFunctionSimplified(ChainList<VkEntity> entities, VkFunction vk){
        if(vk.getParameters().count() >= 2 && (vk.getCallName().startsWith("vkGet") || (vk.getCallName().startsWith("vkAllocate") && !vk.getCallName().startsWith("vkAllocateMemory")))){
            VkVariable last = (VkVariable) vk.getParameters().getLast();
            boolean isLastOk = !last.getTypename().equals("VkObject");
            if(isLastOk) return true;
        }
        return false;
    }

    private static Text genProtectedFunctionSimplified(VkFunction vk){
        return protectedFunctionSimplifiedTemplate
                .replace("%VULKANFUNCTIONNAME%", vk.getCallName())
                .replace("%PARAMETERS%", genParameters(vk.getParameters(), null))
                .replace("%VKFUNCTIONNAME%", vk.getCallName())
                .replace("%ARGUMENTS%", genArgumentsSimplified(vk.getParameters(), null));
    }

    private static Text genEnumerateFunctionSimplified(VkFunction vk){
        VkVariable last = (VkVariable) vk.getParameters().getLast();
        return enumerateFunctionSimplifiedTemplate
                .replace("%VULKANFUNCTIONNAME%", vk.getCallName())
                .replace("%PARAMETERS%", genParameters(reduceSimplified(vk.getParameters(), 2), null))
                .replace("%VKFUNCTIONNAME%", vk.getCallName())
                .replace("%ARGUMENTS%", genArgumentsSimplified(reduceSimplified(vk.getParameters(), 2), null))
                .replace("%RETURN%", last.getTypename())
                .replace("%COMMA%", vk.getParameters().count() > 2 ? ", " : "");
    }

    private static Text genCreateFunctionSimplified(VkFunction vk){
        VkVariable last = (VkVariable) vk.getParameters().getLast();
        return createFunctionSimplifiedTemplate
                .replace("%VULKANFUNCTIONNAME%", vk.getCallName())
                .replace("%PARAMETERS%", genParameters(reduceSimplified(vk.getParameters(), 2), null))
                .replace("%VKFUNCTIONNAME%", vk.getCallName())
                .replace("%ARGUMENTS%", genArgumentsSimplified(reduceSimplified(vk.getParameters(), 2), null))
                .replace("%RETURN%", last.getTypename());
    }

    private static Text genDestroyFunctionSimplified(VkFunction vk){
        return destroyFunctionSimplifiedTemplate
                .replace("%VULKANFUNCTIONNAME%", vk.getCallName())
                .replace("%PARAMETERS%", genParameters(reduceSimplified(vk.getParameters(), 1), null))
                .replace("%VKFUNCTIONNAME%", vk.getCallName())
                .replace("%ARGUMENTS%", genArgumentsSimplified(reduceSimplified(vk.getParameters(), 1), null));
    }

    private static Text genGetArrayFunctionSimplified(VkFunction vk){
        VkVariable last = (VkVariable) vk.getParameters().getLast();
        return getArrayFunctionSimplifiedTemplate
                .replace("%VULKANFUNCTIONNAME%", vk.getCallName())
                .replace("%PARAMETERS%", genParameters(reduceSimplified(vk.getParameters(), 2), null))
                .replace("%VKFUNCTIONNAME%", vk.getCallName())
                .replace("%ARGUMENTS%", genArgumentsSimplified(reduceSimplified(vk.getParameters(), 2), null))
                .replace("%RETURN%", last.getTypename())
                .replace("%COMMA%", vk.getParameters().count() > 2 ? ", " : "");
    }

    private static Text genGetFunctionSimplified(VkFunction vk){
        VkVariable last = (VkVariable) vk.getParameters().getLast();
        return getFunctionSimplifiedTemplate
                .replace("%VULKANFUNCTIONNAME%", vk.getCallName())
                .replace("%PARAMETERS%", genParameters(reduceSimplified(vk.getParameters(), 1), null))
                .replace("%VKFUNCTIONNAME%", vk.getCallName())
                .replace("%ARGUMENTS%", genArgumentsSimplified(reduceSimplified(vk.getParameters(), 1), null))
                .replace("%RETURN%", last.getTypename())
                .replace("%COMMA%", vk.getParameters().count() > 1 ? ", " : "");
    }

    private static Text genStatusFunctionSimplified(VkFunction vk){
        return statusFunctionSimplifiedTemplate
                .replace("%VULKANFUNCTIONNAME%", vk.getCallName())
                .replace("%PARAMETERS%", genParameters(reduceSimplified(vk.getParameters(), 0), null))
                .replace("%VKFUNCTIONNAME%", vk.getCallName())
                .replace("%ARGUMENTS%", genArgumentsSimplified(reduceSimplified(vk.getParameters(), 0), null))
                .replace("%COMMA%", vk.getParameters().count() > 1 ? ", " : "");
    }

    private static Text genProtectedGetArrayFunctionSimplified(VkFunction vk){
        VkVariable last = (VkVariable) vk.getParameters().getLast();
        return protectedGetArrayFunctionSimplifiedTemplate
                .replace("%VULKANFUNCTIONNAME%", vk.getCallName())
                .replace("%PARAMETERS%", genParameters(reduceSimplified(vk.getParameters(), 2), null))
                .replace("%VKFUNCTIONNAME%", vk.getCallName())
                .replace("%ARGUMENTS%", genArgumentsSimplified(reduceSimplified(vk.getParameters(), 2), null))
                .replace("%RETURN%", last.getTypename())
                .replace("%COMMA%", vk.getParameters().count() > 2 ? ", " : "");
    }

    private static Text genProtectedGetFunctionSimplified(VkFunction vk){
        VkVariable last = (VkVariable) vk.getParameters().getLast();
        return protectedGetFunctionSimplifiedTemplate
                .replace("%VULKANFUNCTIONNAME%", vk.getCallName())
                .replace("%PARAMETERS%", genParameters(reduceSimplified(vk.getParameters(), 1), null))
                .replace("%VKFUNCTIONNAME%", vk.getCallName())
                .replace("%ARGUMENTS%", genArgumentsSimplified(reduceSimplified(vk.getParameters(), 1), null))
                .replace("%RETURN%", last.getTypename())
                .replace("%COMMA%", vk.getParameters().count() > 1 ? ", " : "");
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

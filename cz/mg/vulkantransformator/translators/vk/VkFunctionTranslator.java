package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.Transformator;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.FunctionTriplet;
import cz.mg.vulkantransformator.entities.HandleTriplet;
import cz.mg.vulkantransformator.entities.c.CParameter;
import cz.mg.vulkantransformator.entities.vk.VkFunction;
import cz.mg.vulkantransformator.entities.vk.VkParameter;


public class VkFunctionTranslator extends VkTranslator {
    @Override
    public String genCode(EntityTriplet e, String template) {
        FunctionTriplet entity = (FunctionTriplet) e;
        VkFunction vk = (VkFunction) entity.getVk();
        return super.genCode(e, template
                .replace("%PARAMETERS%", genParameters(vk.getParameters(), vk.getReturnType()))
                .replace("%ARGUMENTS%", genArguments(vk.getParameters(), vk.getReturnType()))
                .replace("%JAVAPARAMETERS%", genJavaParameters(vk.getParameters(), vk.getReturnType()))
                .replace("%FUNCTION%", entity.getC().getName().replaceFirst("PFN_", ""))
        );
    }

    public static String genParameters(ChainList<VkParameter> parameters, VkParameter rvalParameter){
        ChainList<String> params = new CachedChainList<>();
        for(VkParameter parameter : parameters) params.addLast(genParameter(parameter));
        if(!rvalParameter.isEmpty()) params.addLast(genParameter(rvalParameter));
        return params.toString(", ");
    }

    public static String genParameter(VkParameter parameter){
        return parameter.getTypename() + " " + parameter.getName();
    }

    public static String genArguments(ChainList<VkParameter> parameters, VkParameter rvalParameter){
        ChainList<String> args = new CachedChainList<>();
        for(VkParameter parameter : parameters) args.addLast(genArgument(parameter));
        if(!rvalParameter.isEmpty()) args.addLast(genArgument(rvalParameter));
        String leadingComma = args.count() > 0 ? ", " : "";
        return leadingComma + args.toString(", ");
    }

    public static String genArgument(VkParameter parameter){
        if(isHandle(parameter.getTypename())){
            return parameter.getName() + " != null ? " + parameter.getName() + ".getVkAddress() : " + parameter.getTypename() + ".NULL.getVkAddress()";
        } else {
            return parameter.getName() + " != null ? " + parameter.getName() + ".getVkAddress() : VkPointer.NULL";
        }
    }

    private static boolean isHandle(String typename){
        if(typename.endsWith(".Pointer") || typename.endsWith(".Array")) return false;
        for(EntityTriplet entity : Transformator.LAST_INSTANCE.getEntities()){
            if(entity.getVk() != null){
                if(entity.getVk().getName().equals(typename)){
                    return entity instanceof HandleTriplet;
                }
            }
        }
        throw new RuntimeException("Could not find " + typename);
    }

    public static String genJavaParameters(ChainList<VkParameter> parameters, VkParameter rvalParameter){
        ChainList<String> params = new CachedChainList<>();
        for(VkParameter parameter : parameters) params.addLast(genJavaParameter(parameter));
        if(!rvalParameter.isEmpty()) params.addLast(genJavaParameter(rvalParameter));
        String leadingComma = params.count() > 0 ? ", " : "";
        return leadingComma + params.toString(", ");
    }

    public static String genJavaParameter(VkParameter parameter){
        return "long " + parameter.getName();
    }
}

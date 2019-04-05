package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.FunctionTriplet;
import cz.mg.vulkantransformator.entities.vk.VkFunction;
import cz.mg.vulkantransformator.entities.vk.VkVariable;


public class VkFunctionTranslator extends VkTranslator {
    @Override
    public String genCode(ChainList<EntityTriplet> entities, EntityTriplet e, String template) {
        FunctionTriplet entity = (FunctionTriplet) e;
        VkFunction vk = (VkFunction) entity.getVk();
        return super.genCode(entities, e, template
                .replace("%PARAMETERS%", genParameters(vk.getParameters(), vk.getReturnType()))
                .replace("%ARGUMENTS%", genArguments(vk.getParameters(), vk.getReturnType()))
                .replace("%JAVAPARAMETERS%", genJavaParameters(vk.getParameters(), vk.getReturnType()))
                .replace("%FUNCTION%", entity.getC().getName().replaceFirst("PFN_", ""))
        );
    }

    public static String genParameters(ChainList<VkVariable> parameters, VkVariable rvalParameter){
        ChainList<String> params = new CachedChainList<>();
        for(VkVariable parameter : parameters) params.addLast(genParameter(parameter));
        if(!rvalParameter.isEmpty()) params.addLast(genParameter(rvalParameter));
        return params.toString(", ");
    }

    public static String genParameter(VkVariable parameter){
        return parameter.getTypename() + " " + parameter.getName();
    }

    public static String genArguments(ChainList<VkVariable> parameters, VkVariable rvalParameter){
        ChainList<String> args = new CachedChainList<>();
        for(VkVariable parameter : parameters) args.addLast(genArgument(parameter));
        if(!rvalParameter.isEmpty()) args.addLast(genArgument(rvalParameter));
        String leadingComma = args.count() > 0 ? ", " : "";
        return leadingComma + args.toString(", ");
    }

    public static String genArgument(VkVariable parameter){
        if(parameter.isValue()){
            return parameter.getName() + " != null ? " + parameter.getName() + ".getVkAddress() : VkPointer.NULL_ADDRESS";
        } else {
            return parameter.getName() + " != null ? " + parameter.getName() + ".getVkAddress() : VkPointer.NULL";
        }
    }

    public static String genJavaParameters(ChainList<VkVariable> parameters, VkVariable rvalParameter){
        ChainList<String> params = new CachedChainList<>();
        for(VkVariable parameter : parameters) params.addLast(genJavaParameter(parameter));
        if(!rvalParameter.isEmpty()) params.addLast(genJavaParameter(rvalParameter));
        String leadingComma = params.count() > 0 ? ", " : "";
        return leadingComma + params.toString(", ");
    }

    public static String genJavaParameter(VkVariable parameter){
        return "long " + parameter.getName();
    }
}

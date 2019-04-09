package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.entities.vk.VkFunction;
import cz.mg.vulkantransformator.entities.vk.VkVariable;


public class VkFunctionTranslator extends VkTranslator {
    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity e, Text template) {
        VkFunction vk = (VkFunction) e;
        return super.genCode(entities, e, template
                .replace("%PARAMETERS%", genParameters(vk.getParameters(), vk.getReturnType()))
                .replace("%ARGUMENTS%", genArguments(vk.getParameters(), vk.getReturnType()))
                .replace("%JAVAPARAMETERS%", genJavaParameters(vk.getParameters(), vk.getReturnType()))
                .replace("%FUNCTION%", vk.getC().getName().replaceFirst("PFN_", ""))
        );
    }

    public static Text genParameters(ChainList<VkVariable> parameters, VkVariable rvalParameter){
        ChainList<Text> params = new CachedChainList<>();
        for(VkVariable parameter : parameters) params.addLast(genParameter(parameter));
        if(!rvalParameter.isEmpty()) params.addLast(genParameter(rvalParameter));
        return params.toText(", ");
    }

    public static Text genParameter(VkVariable parameter){
        return parameter.getTypename().append(" ").append(parameter.getName());
    }

    public static Text genArguments(ChainList<VkVariable> parameters, VkVariable rvalParameter){
        ChainList<Text> args = new CachedChainList<>();
        for(VkVariable parameter : parameters) args.addLast(genArgument(parameter));
        if(!rvalParameter.isEmpty()) args.addLast(genArgument(rvalParameter));
        Text leadingComma = args.count() > 0 ? new Text(", ") : new Text("");
        return leadingComma.append(args.toText(", "));
    }

    public static Text genArgument(VkVariable parameter){
        if(parameter.isValue()){
            return parameter.getName().append(" != null ? ").append(parameter.getName()).append(".getVkAddress() : VkPointer.NULL_ADDRESS");
        } else {
            return parameter.getName().append(" != null ? ").append(parameter.getName()).append(".getVkAddress() : VkPointer.NULL");
        }
    }

    public static Text genJavaParameters(ChainList<VkVariable> parameters, VkVariable rvalParameter){
        ChainList<Text> params = new CachedChainList<>();
        for(VkVariable parameter : parameters) params.addLast(genJavaParameter(parameter));
        if(!rvalParameter.isEmpty()) params.addLast(genJavaParameter(rvalParameter));
        Text leadingComma = params.count() > 0 ? new Text(", ") : new Text("");
        return leadingComma.append(params.toText(", "));
    }

    public static Text genJavaParameter(VkVariable parameter){
        return new Text("long ").append(parameter.getName());
    }
}

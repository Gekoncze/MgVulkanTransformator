package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.entities.c.CFunction;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.entities.vk.VkFunction;
import cz.mg.vulkantransformator.entities.vk.VkVariable;
import cz.mg.vulkantransformator.translators.vk.templates.TemplatesVk;


public class VkFunctionTranslator extends VkTranslator {
    private static final Text functionTemplate = TemplatesVk.load("parts/Function");
    private static final Text functionReturnTemplate = TemplatesVk.load("parts/FunctionReturn");

    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity e, Text template) {
        VkFunction vk = (VkFunction) e;
        CFunction c = (CFunction) vk.getC();
        return super.genCode(entities, e, template
                .replace("%FUNCTIONNAME%", c.getCallName())
                .replace("%FUNCTION%", genFunction(vk))
        );
    }

    public static Text genFunction(VkFunction vk){
        if(vk.getReturnType().isVoid()){
            return functionTemplate
                    .replace("%PARAMETERS%", genParameters(vk.getParameters()))
                    .replace("%ARGUMENTS%", genArguments(vk.getParameters()))
                    .replace("%NATIVEPARAMETERS%", genJavaParameters(vk.getParameters()));
        } else {
            return functionReturnTemplate
                    .replace("%RETURNTYPE%", genReturnType(vk.getReturnType()))
                    .replace("%NATIVERETURNTYPE%", genNativeReturnType(vk.getReturnType()))
                    .replace("%PARAMETERS%", genParameters(vk.getParameters()))
                    .replace("%ARGUMENTS%", genArguments(vk.getParameters()))
                    .replace("%NATIVEPARAMETERS%", genJavaParameters(vk.getParameters()));
        }
    }

    public static Text genReturnType(VkVariable r){
        return r.getSimplifiedJavaType();
    }

    public static Text genNativeReturnType(VkVariable r){
        return r.getSimplifiedJavaType();
    }

    public static Text genParameters(ChainList<VkVariable> parameters){
        ChainList<Text> params = new CachedChainList<>();
        for(VkVariable parameter : parameters){
            if(isSimplificable(parameter)){
                params.addLast(genSimplifiedParameter(parameter));
            } else {
                params.addLast(genParameter(parameter));
            }
        }
        return params.toText(", ");
    }

    private static Text genArguments(ChainList<VkVariable> parameters) {
        ChainList<Text> args = new CachedChainList<>();
        for(VkVariable parameter : parameters){
            if(isSimplificable(parameter)){
                args.addLast(genSimplifiedArgument(parameter));
            } else {
                args.addLast(genArgument(parameter));
            }
        }
        Text leadingComma = args.count() > 0 ? new Text(", ") : new Text("");
        return leadingComma.append(args.toText(", "));
    }

    private static Text genJavaParameters(ChainList<VkVariable> parameters) {
        ChainList<Text> params = new CachedChainList<>();
        for(VkVariable parameter : parameters){
            if(isSimplificable(parameter)){
                params.addLast(genSimplifiedJavaParameter(parameter));
            } else {
                params.addLast(genJavaParameter(parameter));
            }
        }
        Text leadingComma = params.count() > 0 ? new Text(", ") : new Text("");
        return leadingComma.append(params.toText(", "));
    }

    public static Text genParameter(VkVariable parameter){
        return parameter.getTypename().append(" ").append(parameter.getName());
    }

    public static Text genSimplifiedParameter(VkVariable parameter){
        return parameter.getSimplifiedJavaType().append(" ").append(parameter.getName());
    }

    public static Text genArgument(VkVariable parameter){
        if(parameter.isValue()){
            return parameter.getName().append(" != null ? ").append(parameter.getName()).append(".getVkAddress() : VkPointer.getNullAddressNative()");
        } else {
            return parameter.getName().append(" != null ? ").append(parameter.getName()).append(".getVkAddress() : VkPointer.NULL");
        }
    }

    public static Text genSimplifiedArgument(VkVariable parameter){
        return parameter.getName();
    }

    public static Text genJavaParameter(VkVariable parameter){
        return new Text("long ").append(parameter.getName());
    }

    public static Text genSimplifiedJavaParameter(VkVariable parameter){
        return parameter.getSimplifiedJavaType().append(" ").append(parameter.getName());
    }

    public static boolean isSimplificable(VkVariable vk){
        if(vk.getSimplifiedJavaType() == null) return false;
        if(vk.getSimplifiedJavaType().equals("String")) return false;
        return true;
    }
}

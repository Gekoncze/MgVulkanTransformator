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
    private static final Text callTemplate = TemplatesVk.load("parts/Call");
    private static final Text callSimplifiedTemplate = TemplatesVk.load("parts/CallSimplified");

    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity e, Text template) {
        VkFunction vk = (VkFunction) e;
        CFunction c = (CFunction) vk.getC();
        return super.genCode(entities, e, template
                .replace("%CALL%", genCall(vk))
                .replace("%SIMPLIFIEDCALL%", genSimplifiedCall(vk))
                .replace("%FUNCTIONNAME%", c.getCallName())
        );
    }

    public static Text genCall(VkFunction vk){
        return callTemplate
                .replace("%PARAMETERS%", genParameters(vk.getParameters(), vk.getReturnType()))
                .replace("%ARGUMENTS%", genArguments(vk.getParameters(), vk.getReturnType()))
                .replace("%NATIVEPARAMETERS%", genJavaParameters(vk.getParameters(), vk.getReturnType()));
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
        if(!rvalParameter.isEmpty()) args.addLast(genReturnArgument(rvalParameter));
        Text leadingComma = args.count() > 0 ? new Text(", ") : new Text("");
        return leadingComma.append(args.toText(", "));
    }

    public static Text genArgument(VkVariable parameter){
        if(parameter.isValue()){
            return parameter.getName().append(" != null ? ").append(parameter.getName()).append(".getVkAddress() : VkPointer.getNullAddressNative()");
        } else {
            return parameter.getName().append(" != null ? ").append(parameter.getName()).append(".getVkAddress() : VkPointer.NULL");
        }
    }

    public static Text genReturnArgument(VkVariable parameter){
        if(parameter.isValue()){
            return parameter.getName().append(" != null ? ").append(parameter.getName()).append(".getVkAddress() : VkPointer.getSinkAddressNative()");
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

    public static Text genSimplifiedCall(VkFunction vk){
        if(!isSimplificable(vk)) return new Text("");
        return callSimplifiedTemplate
                .replace("%RETURN%", genReturn(vk))
                .replace("%RETURNTYPE%", genSimplifiedReturnType(vk.getReturnType()))
                .replace("%NATIVERETURN%", genSimplifiedNativeReturn(vk.getReturnType()))
                .replace("%PARAMETERS%", genParametersSimplified(vk.getParameters()))
                .replace("%ARGUMENTS%", genArgumentsSimplified(vk.getParameters()))
                .replace("%NATIVEPARAMETERS%", genJavaParametersSimplified(vk.getParameters()));
    }

    public static String genReturn(VkFunction vk){
        return vk.getReturnType().getC().getTypename().equals("void") ? "" : "return ";
    }

    public static boolean isSimplificable(VkFunction vk){
        boolean simplificable = false;
        simplificable |= isSimplificable(vk.getReturnType());
        for(Object v : vk.getParameters()) simplificable |= isSimplificable((VkVariable) v);
        if(((CFunction)vk.getC()).getReturnType().getPointerCount() > 0) return false;
        return simplificable;
    }

    public static boolean isSimplificable(VkVariable vk){
        if(vk.getSimplifiedJavaType() == null) return false;
        if(vk.getSimplifiedJavaType().equals("String")) return false;
        return true;
    }

    public static Text genSimplifiedReturnType(VkVariable r){
        if(r == null) return new Text("void");
        if(r.getC().getTypename().equals("void")) return new Text("void");
        if(r.getSimplifiedJavaType() == null) throw new UnsupportedOperationException();
        return r.getSimplifiedJavaType();
    }

    public static Text genSimplifiedNativeReturn(VkVariable r){
        if(r == null) return new Text("void");
        if(r.getC().getTypename().equals("void")) return new Text("void");
        if(r.getSimplifiedJavaType() == null) throw new UnsupportedOperationException();
        return r.getSimplifiedJavaType();
    }

    public static Text genParametersSimplified(ChainList<VkVariable> parameters){
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

    public static Text genSimplifiedParameter(VkVariable parameter){
        return parameter.getSimplifiedJavaType().append(" ").append(parameter.getName());
    }

    private static Text genArgumentsSimplified(ChainList<VkVariable> parameters) {
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

    public static Text genSimplifiedArgument(VkVariable parameter){
        return parameter.getName();
    }

    private static Text genJavaParametersSimplified(ChainList<VkVariable> parameters) {
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

    public static Text genSimplifiedJavaParameter(VkVariable parameter){
        return parameter.getSimplifiedJavaType().append(" ").append(parameter.getName());
    }
}

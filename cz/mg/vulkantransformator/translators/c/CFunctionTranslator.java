package cz.mg.vulkantransformator.translators.c;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.entities.c.CFunction;
import cz.mg.vulkantransformator.entities.c.CVariable;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.entities.vk.VkFunction;
import cz.mg.vulkantransformator.entities.vk.VkVariable;
import cz.mg.vulkantransformator.translators.c.templates.TemplatesC;
import cz.mg.vulkantransformator.translators.vk.VkFunctionTranslator;


public class CFunctionTranslator extends CTranslator {
    private static final Text argumentTempalte = new Text("        %A%((%PARAMETERDATATYPE%*)jniLongToPointer(%PARAMETERNAME%))");
    private static final Text functionTemplate = TemplatesC.load("parts/Function");
    private static final Text functionReturnTemplate = TemplatesC.load("parts/FunctionReturn");
    private static final Text functionSimplifiedTemplate = TemplatesC.load("parts/FunctionSimplified");
    private static final Text functionReturnSimplifiedTemplate = TemplatesC.load("parts/FunctionReturnSimplified");

    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity e, Text template) {
        VkFunction entity = (VkFunction) e;
        CFunction c = (CFunction) entity.getC();
        return super.genCode(entities, e, template
                .replace("%FUNCTION%", genFunction(c))
                .replace("%SIMPLIFIEDFUNCTION%", genFunctionSimplified((VkFunction) e, c))
        );
    }

    public static Text genFunction(CFunction c){
        if(c.getReturnType().isVoid()){
            return functionTemplate
                    .replace("%PARAMETERS%", genParameters(c.getParameters(), c.getReturnType()))
                    .replace("%ARGUMENTS%", genArguments(c.getParameters()));
        } else {
            return functionReturnTemplate
                    .replace("%RETURNDATATYPE%", c.getReturnType().getDatatype())
                    .replace("%PARAMETERS%", genParameters(c.getParameters(), c.getReturnType()))
                    .replace("%ARGUMENTS%", genArguments(c.getParameters()));
        }
    }

    public static Text genParameters(ChainList<CVariable> parameters, CVariable returnParameter){
        ChainList<Text> params = new CachedChainList<>();
        for(CVariable parameter : parameters) params.addLast(genParameter(parameter));
        if(!returnParameter.isVoid()) params.addLast(genParameter(returnParameter));
        Text leadingComma = params.count() > 0 ? new Text(", ") : new Text("");
        return leadingComma.append(params.toText(", "));
    }

    public static Text genParameter(CVariable parameter){
        return new Text("jlong ").append(parameter.getName());
    }

    public static Text genArguments(ChainList<CVariable> parameters){
        ChainList<Text> args = new CachedChainList<>();
        for(CVariable parameter : parameters) args.addLast(genArgument(parameter));
        return args.toText(",\n");
    }

    public static Text genArgument(CVariable parameter){
        return argumentTempalte
                .replace("%A%", genA(parameter))
                .replace("%PARAMETERDATATYPE%", parameter.getDatatype().replaceLast("*", ""))
                .replace("%PARAMETERNAME%", parameter.getName());
    }

    public static Text genA(CVariable parameter){
        if(parameter.getPointerCount() == 0 && parameter.getArrayCount() == null) return new Text("*");
        if(parameter.getPointerCount() == 1 && parameter.getArrayCount() == null) return new Text("");
        if(parameter.getPointerCount() == 2 && parameter.getArrayCount() == null) return new Text("");
        throw new UnsupportedOperationException("" + parameter.getDatatype());
    }

    public static Text genFunctionSimplified(VkFunction vk, CFunction c){
        if(!VkFunctionTranslator.isSimplificable(vk)) return new Text("");
        if(c.getReturnType().isVoid()){
            return functionSimplifiedTemplate
                    .replace("%PARAMETERS%", genParametersSimplified(vk.getParameters()))
                    .replace("%ARGUMENTS%", genArgumentsSimplified(vk.getParameters()));
        } else {
            return functionReturnSimplifiedTemplate
                    .replace("%RETURNTYPE%", genReturnTypeSimplified(vk.getReturnType()))
                    .replace("%PARAMETERS%", genParametersSimplified(vk.getParameters()))
                    .replace("%ARGUMENTS%", genArgumentsSimplified(vk.getParameters()));
        }
    }

    public static Text genReturnTypeSimplified(VkVariable parameter){
        return parameter.getSimplifiedJniType();
    }

    public static Text genParametersSimplified(ChainList<VkVariable> parameters){
        ChainList<Text> params = new CachedChainList<>();
        for(VkVariable parameter : parameters){
            if(VkFunctionTranslator.isSimplificable(parameter)){
                params.addLast(genParameterSimplified(parameter));
            } else {
                params.addLast(genParameter(parameter.getC()));
            }
        }
        Text leadingComma = params.count() > 0 ? new Text(", ") : new Text("");
        return leadingComma.append(params.toText(", "));
    }

    public static Text genParameterSimplified(VkVariable parameter){
        return parameter.getSimplifiedJniType().append(" ").append(parameter.getName());
    }

    public static Text genArgumentsSimplified(ChainList<VkVariable> parameters){
        ChainList<Text> args = new CachedChainList<>();
        for(VkVariable parameter : parameters){
            if(VkFunctionTranslator.isSimplificable(parameter)){
                args.addLast(genArgumentSimplified(parameter));
            } else {
                args.addLast(genArgument(parameter.getC()));
            }
        }
        return args.toText(",\n");
    }

    public static Text genArgumentSimplified(VkVariable parameter){
        return new Text("        (").append(parameter.getC().getTypename()).append(")").append(parameter.getC().getName());
    }
}

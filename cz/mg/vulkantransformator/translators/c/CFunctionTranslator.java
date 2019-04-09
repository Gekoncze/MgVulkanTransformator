package cz.mg.vulkantransformator.translators.c;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.entities.c.CFunction;
import cz.mg.vulkantransformator.entities.c.CVariable;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.entities.vk.VkFunction;


public class CFunctionTranslator extends CTranslator {
    private static final Text parameterTempalteC = new Text("jlong %PARAMETERNAME%");
    private static final Text argumentTempalteC = new Text("        %A%((%PARAMETERDATATYPE%*)jniLongToPointer(%PARAMETERNAME%))");
    private static final Text returnTemplateC = new Text("%RETURNDATATYPE%* rvalAddress = jniLongToPointer(rval);\n    *rvalAddress = ");

    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity e, Text template) {
        VkFunction entity = (VkFunction) e;
        CFunction c = (CFunction) entity.getC();
        return super.genCode(entities, e, template
                .replace("%RETURN%", genReturn(c.getReturnType()))
                .replace("%PARAMETERS%", genParameters(c.getParameters(), c.getReturnType()))
                .replace("%ARGUMENTS%", genArguments(c.getParameters()))
        );
    }

    public static Text genReturn(CVariable returnType){
        if(returnType.isVoid()) return new Text("");
        return returnTemplateC.replace("%RETURNDATATYPE%", returnType.getDatatype());
    }

    public static Text genParameters(ChainList<CVariable> parameters, CVariable returnParameter){
        ChainList<Text> params = new CachedChainList<>();
        for(CVariable parameter : parameters) params.addLast(genParameter(parameter));
        if(!returnParameter.isVoid()) params.addLast(genParameter(returnParameter));
        Text leadingComma = params.count() > 0 ? new Text(", ") : new Text("");
        return leadingComma.append(params.toText(", "));
    }

    public static Text genParameter(CVariable parameter){
        return parameterTempalteC
                .replace("%PARAMETERNAME%", parameter.getName());
    }

    public static Text genArguments(ChainList<CVariable> parameters){
        ChainList<Text> args = new CachedChainList<>();
        for(CVariable parameter : parameters) args.addLast(genArgument(parameter));
        return args.toText(",\n");
    }

    public static Text genArgument(CVariable parameter){
        return argumentTempalteC
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
}

package cz.mg.vulkantransformator.translators.c;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.FunctionTriplet;
import cz.mg.vulkantransformator.entities.c.CFunction;
import cz.mg.vulkantransformator.entities.c.CParameter;
import cz.mg.vulkantransformator.utilities.StringUtilities;


public class CFunctionTranslator extends CTranslator {
    private static final String parameterTempalteC = "jlong %PARAMETERNAME%";
    private static final String argumentTempalteC = "        %A%((%PARAMETERDATATYPE%*)jniLongToPointer(%PARAMETERNAME%))";
    private static final String returnTemplateC = "%RETURNDATATYPE%* rvalAddress = jniLongToPointer(rval);\n    *rvalAddress = ";

    @Override
    public String genCode(EntityTriplet e, String template) {
        FunctionTriplet entity = (FunctionTriplet) e;
        CFunction c = (CFunction) entity.getC();
        return super.genCode(e, template
                .replace("%RETURN%", genReturn(c.getReturnType()))
                .replace("%PARAMETERS%", genParameters(c.getParameters(), c.getReturnType()))
                .replace("%ARGUMENTS%", genArguments(c.getParameters()))
        );
    }

    public static String genReturn(CParameter returnType){
        if(returnType.isVoid()) return "";
        return returnTemplateC.replace("%RETURNDATATYPE%", returnType.getDatatype());
    }

    public static String genParameters(ChainList<CParameter> parameters, CParameter returnParameter){
        ChainList<String> params = new CachedChainList<>();
        for(CParameter parameter : parameters) params.addLast(genParameter(parameter));
        if(!returnParameter.isVoid()) params.addLast(genParameter(returnParameter));
        String leadingComma = params.count() > 0 ? ", " : "";
        return leadingComma + params.toString(", ");
    }

    public static String genParameter(CParameter parameter){
        return parameterTempalteC
                .replace("%PARAMETERNAME%", parameter.getName());
    }

    public static String genArguments(ChainList<CParameter> parameters){
        ChainList<String> args = new CachedChainList<>();
        for(CParameter parameter : parameters) args.addLast(genArgument(parameter));
        return args.toString(",\n");
    }

    public static String genArgument(CParameter parameter){
        return argumentTempalteC
                .replace("%A%", genA(parameter))
                .replace("%PARAMETERDATATYPE%", StringUtilities.replaceLast(parameter.getDatatype(), "*", ""))
                .replace("%PARAMETERNAME%", parameter.getName());
    }

    public static String genA(CParameter parameter){
        if(parameter.getPointerCount() == 0) return "*";
        if(parameter.getPointerCount() == 1) return "";
        if(parameter.getPointerCount() == 2) return "";
        throw new UnsupportedOperationException("" + parameter.getDatatype());
    }
}

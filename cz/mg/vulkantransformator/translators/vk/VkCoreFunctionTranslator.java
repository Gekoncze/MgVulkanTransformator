package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.converters.utilities.TypenameConverter;
import cz.mg.vulkantransformator.entities.vk.VkCallback;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.entities.vk.VkFunction;
import cz.mg.vulkantransformator.entities.vk.VkVariable;
import cz.mg.vulkantransformator.translators.vk.VkFunctionTranslator;
import cz.mg.vulkantransformator.translators.vk.templates.TemplatesVk;


public class VkCoreFunctionTranslator {
    private static final Text functionTemplate = TemplatesVk.load("core/Function");
    private static final Text functionReturnTemplate = TemplatesVk.load("core/FunctionReturn");
    private static final Text functionProtectedTemplate = TemplatesVk.load("core/FunctionProtected");

    public static Text genFunctions(ChainList<VkEntity> entities) {
        ChainList<Text> functions = new CachedChainList<>();
        for(VkEntity entity : entities) {
            if (entity instanceof VkFunction && !(entity instanceof VkCallback)) {
                VkFunction vk = (VkFunction) entity;
                if(vk.getReturnType().isVoid()){
                    functions.addLast(functionTemplate
                            .replace("%FUNCTIONTYPENAME%", vk.getName())
                            .replace("%FUNCTIONNAME%", vk.getCallName())
                            .replace("%FUNCTIONNAMEP%", vk.getC().getName() + "_f")
                            .replace("%PARAMETERS%", genParameters(vk.getParameters()))
                            .replace("%ARGUMENTS%", genArguments(vk.getParameters()))
                    );
                } else {
                    functions.addLast(functionReturnTemplate
                            .replace("%FUNCTIONTYPENAME%", vk.getName())
                            .replace("%FUNCTIONNAME%", vk.getCallName())
                            .replace("%FUNCTIONNAMEP%", vk.getC().getName() + "_f")
                            .replace("%PARAMETERS%", genParameters(vk.getParameters()))
                            .replace("%ARGUMENTS%", genArguments(vk.getParameters()))
                            .replace("%RETURNTYPE%", vk.getReturnType().getSimplifiedJavaType())
                    );
                }

                if(canBeProtected(vk)) {
                    functions.addLast(functionProtectedTemplate
                            .replace("%FUNCTIONTYPENAME%", vk.getName())
                            .replace("%FUNCTIONNAME%", vk.getCallName() + "P")
                            .replace("%FUNCTIONNAMEP%", vk.getC().getName() + "_f")
                            .replace("%PARAMETERS%", genParameters(vk.getParameters()))
                            .replace("%ARGUMENTS%", genArguments(vk.getParameters()))
                    );
                }
            }
        }
        return new Text(functions.toString("\n\n"));
    }

    private static boolean canBeProtected(VkFunction vk){
        return vk.getReturnType().getTypename().equals("VkResult") && !isStatusFunction(vk);
    }

    private static boolean isStatusFunction(VkFunction vk){
        Text callName = TypenameConverter.stripExtensionName(vk.getCallName());
        return callName.startsWith("vkGet") && callName.endsWith("Status");
    }

    public static Text genParameters(ChainList<VkVariable> parameters){
        ChainList<Text> params = new CachedChainList<>();
        for(VkVariable parameter : parameters){
            if(VkFunctionTranslator.isSimplificable(parameter)){
                params.addLast(genParameterSimplified(parameter));
            } else {
                params.addLast(genParameter(parameter));
            }
        }
        return params.toText(", ");
    }

    public static Text genArguments(ChainList<VkVariable> parameters){
        ChainList<Text> args = new CachedChainList<>();
        for(VkVariable parameter : parameters){
            if(VkFunctionTranslator.isSimplificable(parameter)){
                args.addLast(genArgumentSimplified(parameter));
            } else {
                args.addLast(genArgument(parameter));
            }
        }
        return args.toText(", ");
    }

    public static Text genParameter(VkVariable parameter){
        return parameter.getTypename().append(" ").append(parameter.getName());
    }

    public static Text genParameterSimplified(VkVariable parameter){
        return parameter.getSimplifiedJavaType().append(" ").append(parameter.getName());
    }

    public static Text genArgument(VkVariable parameter){
        return parameter.getName();
    }

    public static Text genArgumentSimplified(VkVariable parameter){
        return parameter.getName();
    }
}

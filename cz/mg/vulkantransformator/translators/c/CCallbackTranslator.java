package cz.mg.vulkantransformator.translators.c;

import cz.mg.vulkantransformator.entities.CallbackTriplet;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.c.CCallback;


public class CCallbackTranslator extends CTranslator {
    @Override
    public String genCode(EntityTriplet e, String template) {
        CallbackTriplet entity = (CallbackTriplet) e;
        CCallback c = entity.getC();
        return super.genCode(e, template
                .replace("%RETURN%", CFunctionTranslator.genReturn(c.getReturnType()))
                .replace("%JNIPARAMETERS%", CFunctionTranslator.genParameters(c.getParameters(), c.getReturnType()))
                .replace("%ARGUMENTS%", CFunctionTranslator.genArguments(c.getParameters()))
        );
    }
}

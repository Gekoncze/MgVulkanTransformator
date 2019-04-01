package cz.mg.vulkantransformator.translators.c;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.StructureTriplet;
import cz.mg.vulkantransformator.entities.c.CVariable;
import cz.mg.vulkantransformator.translators.c.templates.TemplatesC;
import cz.mg.vulkantransformator.utilities.StringUtilities;


public class CStructureTranslator extends CTranslator {
    private static final String propertyTemplate = TemplatesC.load("Property");

    @Override
    public String genCode(EntityTriplet e, String template) {
        StructureTriplet entity = (StructureTriplet) e;
        return super.genCode(e, template
                .replace("%PROPERTIES%", genPropertiesC(entity.getC().getFields()))
        );
    }

    public static String genPropertiesC(ChainList<CVariable> fields){
        String s = "";
        for(CVariable field : fields) s += genPropertyC(field);
        return s;
    }

    public static String genPropertyC(CVariable field){
        return propertyTemplate
                .replace("%VKPROPERTYNAME%", StringUtilities.capitalFirst(field.getName()))
                .replace("%CPROPERTYNAME%", field.getName())
                .replace("%A1%", genA1(field))
                .replace("%A2%", genA2(field))
                .replace("%A3%", genA3(field));
    }

    public static String genA1(CVariable field){
        if(field.getPointerCount() == 0 && field.getArrayCount() == null) return "&";
        if(field.getPointerCount() == 1 && field.getArrayCount() == null) return "";
        if(field.getPointerCount() == 2 && field.getArrayCount() == null) return "";
        if(field.getPointerCount() == 0 && field.getArrayCount() != null) return "";
        throw new UnsupportedOperationException("" + field.getDatatype());
    }

    public static String genA2(CVariable field){
        if(field.getPointerCount() == 0 && field.getArrayCount() == null) return "&";
        if(field.getPointerCount() == 1 && field.getArrayCount() == null) return "&";
        if(field.getPointerCount() == 2 && field.getArrayCount() == null) return "&";
        if(field.getPointerCount() == 0 && field.getArrayCount() != null) return "";
        throw new UnsupportedOperationException("" + field.getDatatype());
    }

    public static String genA3(CVariable field){
        if(field.getPointerCount() == 0 && field.getArrayCount() == null) return "";
        if(field.getPointerCount() == 1 && field.getArrayCount() == null) return "&";
        if(field.getPointerCount() == 2 && field.getArrayCount() == null) return "&";
        if(field.getPointerCount() == 0 && field.getArrayCount() != null) return "";
        throw new UnsupportedOperationException("" + field.getDatatype());
    }
}

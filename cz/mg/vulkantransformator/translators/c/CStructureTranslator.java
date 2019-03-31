package cz.mg.vulkantransformator.translators.c;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.StructureTriplet;
import cz.mg.vulkantransformator.entities.c.CField;
import cz.mg.vulkantransformator.translators.c.templates.TemplatesC;
import cz.mg.vulkantransformator.utilities.StringUtilities;


public class CStructureTranslator extends CTranslator {
    @Override
    public String genCode(EntityTriplet e, String template) {
        StructureTriplet entity = (StructureTriplet) e;
        return super.genCode(e, template
                .replace("%PROPERTIES%", genPropertiesC(entity.getC().getFields()))
        );
    }

    public static String genPropertiesC(ChainList<CField> fields){
        String s = "";
        for(CField field : fields) s += genPropertyC(field);
        return s;
    }

    public static String genPropertyC(CField field){
        return TemplatesC.load("Property")
                .replace("%VKPROPERTYNAME%", StringUtilities.capitalFirst(field.getName()))
                .replace("%CPROPERTYNAME%", field.getName())
                .replace("%A%", field.getArrayCount() == null ? "&" : "");
    }
}

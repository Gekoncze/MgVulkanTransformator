package cz.mg.vulkantransformator.translators.c;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.entities.c.CStructure;
import cz.mg.vulkantransformator.entities.c.CVariable;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.entities.vk.VkStructure;
import cz.mg.vulkantransformator.translators.c.templates.TemplatesC;


public class CStructureTranslator extends CTranslator {
    private static final Text propertyTemplate = TemplatesC.load("parts/Property");

    @Override
    public Text genCode(ChainList<VkEntity> entities, VkEntity e, Text template) {
        VkStructure vk = (VkStructure) e;
        CStructure c = (CStructure) vk.getC(); // quick fix for either java or intellij idea bug
        return super.genCode(entities, e, template
                .replace("%PROPERTIES%", genPropertiesC(c.getFields()))
        );
    }

    public static Text genPropertiesC(ChainList<CVariable> fields){
        Text s = new Text("");
        for(CVariable field : fields) s = s.append(genPropertyC(field));
        return s;
    }

    public static Text genPropertyC(CVariable field){
        return propertyTemplate
                .replace("%VKPROPERTYNAME%", field.getName().upperFirst())
                .replace("%CPROPERTYNAME%", field.getName())
                .replace("%A1%", genA1(field))
                .replace("%A2%", genA2(field))
                .replace("%A3%", genA3(field));
    }

    public static Text genA1(CVariable field){
        if(field.getPointerCount() == 0 && field.getArrayCount() == null) return new Text("&");
        if(field.getPointerCount() == 1 && field.getArrayCount() == null) return new Text("");
        if(field.getPointerCount() == 2 && field.getArrayCount() == null) return new Text("");
        if(field.getPointerCount() == 0 && field.getArrayCount() != null) return new Text("");
        throw new UnsupportedOperationException("" + field.getDatatype());
    }

    public static Text genA2(CVariable field){
        if(field.getPointerCount() == 0 && field.getArrayCount() == null) return new Text("&");
        if(field.getPointerCount() == 1 && field.getArrayCount() == null) return new Text("&");
        if(field.getPointerCount() == 2 && field.getArrayCount() == null) return new Text("&");
        if(field.getPointerCount() == 0 && field.getArrayCount() != null) return new Text("");
        throw new UnsupportedOperationException("" + field.getDatatype());
    }

    public static Text genA3(CVariable field){
        if(field.getPointerCount() == 0 && field.getArrayCount() == null) return new Text("");
        if(field.getPointerCount() == 1 && field.getArrayCount() == null) return new Text("&");
        if(field.getPointerCount() == 2 && field.getArrayCount() == null) return new Text("&");
        if(field.getPointerCount() == 0 && field.getArrayCount() != null) return new Text("");
        throw new UnsupportedOperationException("" + field.getDatatype());
    }
}

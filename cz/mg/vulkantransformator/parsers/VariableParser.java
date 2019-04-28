package cz.mg.vulkantransformator.parsers;

import cz.mg.collections.ReadonlyCollection;
import cz.mg.collections.array.Array;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.converters.utilities.DatatypeConverter;
import cz.mg.vulkantransformator.entities.c.CVariable;
import cz.mg.collections.text.Text;


public class VariableParser {
    /*
            Typename name,
            const Typename* name,
            const Typename* const ** name,
            Typename* name);
    */
    public static CVariable parseParameter(Text line){
        int pointerCount = line.count('*') + line.count('[');
        line = DatatypeConverter.removeModifiers(line);
        Array<Text> parts = line.split(" *);,[");
        if(parts.count() == 1) return null;
        Text typename = parts.get(0);
        Text name = parts.get(1);
        return new CVariable(name, typename, pointerCount, null, CVariable.Usage.PARAMETER);
    }

    public static ChainList<CVariable> parseParameters(ReadonlyCollection<Text> lines){
        ChainList<CVariable> params = new ChainList<>();
        for(Text line : lines){
            CVariable p = parseParameter(line);
            if(p != null) params.addLast(p);
        }
        return params;
    }

    /*
            uint32_t                someValue;
            uint32_t                someValues[4];
            uint32_t*               somePointer;
            const uint32_t*         someOtherPointer;
            const uint32_t* const * some2DPointer;
    */
    public static CVariable parseField(Text line){
        int pointerCount = line.count('*');
        line = DatatypeConverter.removeModifiers(line);
        Array<Text> parts = line.split("* ;[]");
        Text arrayCount = parts.count() == 3 ? parts.get(2) : null;
        Text typename = parts.get(0);
        Text name = parts.get(1);
        return new CVariable(name, typename, pointerCount, arrayCount, CVariable.Usage.FIELD);
    }

    public static ChainList<CVariable> parseFields(ReadonlyCollection<Text> lines){
        ChainList<CVariable> fields = new ChainList<>();
        for(Text line : lines) fields.addLast(parseField(line));
        return fields;
    }
}

package cz.mg.vulkantransformator.translators.vk;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.StructureTriplet;
import cz.mg.vulkantransformator.entities.vk.VkField;
import cz.mg.vulkantransformator.translators.vk.templates.TemplatesVk;
import cz.mg.vulkantransformator.utilities.StringUtilities;


public class VkStructureTranslator extends VkTranslator {
    private static final String setTemplateVk =   "        set%VKPROPERTYNAMEC%(%VKPROPERTYNAME%);";

    @Override
    public String genCode(EntityTriplet e, String template) {
        StructureTriplet entity = (StructureTriplet) e;
        return super.genCode(e, template
                .replace("%PARAMETERS%", genParameters(entity.getVk().getFields()))
                .replace("%SETS%", genSets(entity.getVk().getFields()))
                .replace("%PROPERTIES%", genPropertiesVk(entity.getVk().getFields()))
        );
    }

    public static String genParameters(ChainList<VkField> fields){
        ChainList<String> parameters = new CachedChainList<>();
        for(VkField field : fields) parameters.addLast(genParameter(field));
        return parameters.toString(", ");
    }

    public static String genParameter(VkField field){
        return field.getType() + " " + field.getName();
    }

    public static String genSets(ChainList<VkField> fields){
        ChainList<String> sets = new CachedChainList<>();
        for(VkField field : fields) sets.addLast(genSet(field));
        return sets.toString("\n");
    }

    public static String genSet(VkField field){
        return setTemplateVk
                .replace("%VKPROPERTYNAME%", field.getName())
                .replace("%VKPROPERTYNAMEC%", StringUtilities.capitalFirst(field.getName()));
    }

    public static String genPropertiesVk(ChainList<VkField> fields){
        ChainList<String> properties = new CachedChainList<>();
        VkField previousField = null;
        for(VkField field : fields){
            properties.addLast(genPropertyVk(field, previousField));
            previousField = field;
        }
        return properties.toString("\n");
    }

    public static String genPropertyVk(VkField field, VkField previousField){
        return TemplatesVk.load("Property")
                .replace("%VKPROPERTYTYPE%", field.getType())
                .replace("%VKPROPERTYNAME%", field.getName())
                .replace("%VKPROPERTYNAMEC%", StringUtilities.capitalFirst(field.getName()))
                .replace("%VKPROPERTYTYPEGET%", genVkPropertyNameGet(field, previousField))
                .replace("%COUNT%", genFieldCount(previousField));
    }

    public static String genVkPropertyNameGet(VkField field, VkField previousField){
        if(isFieldCount(previousField)){
            if(field.getType().equals("VkString.Array")) return "VkPointer.Array";
            return field.getType() + ".Array";
        } else {
            return field.getType();
        }
    }

    public static String genFieldCount(VkField previousField){
        if(isFieldCount(previousField)){
            return ", get" + StringUtilities.capitalFirst(previousField.getName()) + "().getValue()";
        } else {
            return "";
        }
    }

    private static boolean isFieldCount(VkField field){
        if(field == null) return false;
        if(field.getName().endsWith("Count") && field.getType().equals("VkUInt32")) return true;
        return false;
    }
}

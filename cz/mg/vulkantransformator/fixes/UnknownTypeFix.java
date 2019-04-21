package cz.mg.vulkantransformator.fixes;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.text.Text;
import cz.mg.vulkantransformator.entities.vk.VkEntity;
import cz.mg.vulkantransformator.entities.vk.VkFunction;
import cz.mg.vulkantransformator.entities.vk.VkStructure;
import cz.mg.vulkantransformator.entities.vk.VkVariable;
import java.util.HashMap;


public class UnknownTypeFix {
    private final HashMap<String, Boolean> cache = new HashMap<>();
    private final ChainList<VkEntity> entities;

    public UnknownTypeFix(ChainList<VkEntity> entities) {
        this.entities = entities;
    }

    public void fix(){
        for(VkEntity entity : entities){
            try {
                if(entity instanceof VkStructure){
                    VkStructure structure = (VkStructure) entity;
                    for(Object v : structure.getFields()){ // quick fix for java or intellij idea bug
                        VkVariable variable = (VkVariable) v; // quick fix for java or intellij idea bug
                        fix(variable);
                    }
                }
                if(entity instanceof VkFunction){
                    VkFunction function = (VkFunction) entity;
                    for(Object v : function.getParameters()){ // quick fix for java or intellij idea bug
                        VkVariable variable = (VkVariable) v; // quick fix for java or intellij idea bug
                        if(!isDefined(variable.getTypename())){
                            fix(variable);
                        }
                    }
                }
            } catch (UnsupportedOperationException e){
                throw new UnsupportedOperationException("" + entity.getName(), e);
            }
        }
    }

    private void fix(VkVariable variable){
        if(!isDefined(variable.getTypename())){
            if(variable.getArrayCount() != null) throw new UnsupportedOperationException(variable.getTypename() + "");
            if(variable.getPointerCount() <= 0) throw new UnsupportedOperationException(variable.getTypename() + "");
            variable.setTypename(new Text("VkObject"));
            variable.getC().setTypename(new Text("void"));
        }
    }

    private boolean isDefined(Text name){
        if(name == null) return true;
        Boolean defined = cache.get(name.toString());
        if(defined == null) defined = checkIsDefined(name);
        return defined;
    }

    private boolean checkIsDefined(Text name){
        for(VkEntity entity : entities) if(entity.getName() != null) if(entity.getName().equals(name)) return true;
        return false;
    }
}

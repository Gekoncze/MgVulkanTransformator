package cz.mg.vulkantransformator.translators.vulkan;

import cz.mg.collections.list.chainlist.CachedChainList;
import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.vulkantransformator.entities.EntityTriplet;
import cz.mg.vulkantransformator.entities.FunctionTriplet;
import cz.mg.vulkantransformator.entities.HandleTriplet;
import cz.mg.vulkantransformator.entities.vulkan.VulkanFunction;
import cz.mg.vulkantransformator.translators.vulkan.templates.TemplatesVulkan;


public class VulkanHandleTranslator extends VulkanTranslator {
    private static final String arrayTemplate = TemplatesVulkan.load("parts/HandleArray");
    private static final String constructorTemplate = TemplatesVulkan.load("parts/HandleConstructor");

    @Override
    public String genCode(ChainList<EntityTriplet> entities, EntityTriplet e, String template) {
        HandleTriplet entity = (HandleTriplet) e;
        return super.genCode(entities, e, template
                .replace("%EXTRAS%", genExtras(entities, entity))
                .replace("%%ARRAY%%", arrayTemplate)
                .replace("%%CONSTRUCTOR%%", constructorTemplate)
        );
    }

    public static String genExtras(ChainList<EntityTriplet> entities, HandleTriplet entity){
        VulkanFunction constructor = findConstructor(entities, entity);
        VulkanFunction destructor = findDestructor(entities, entity);
        ChainList<VulkanFunction> related = findRelated(entities, entity);

        System.out.println("HANDLE " + entity.getVulkan().getName());
        if(constructor != null) System.out.println("    CONSTRUCTOR " + constructor.getName() + ": " + VulkanCoreTranslator.genParameters(constructor.getParameters(), constructor.getReturnType()));
        if(destructor != null) System.out.println("    DESTRUCTOR " + destructor.getName() + ": " + VulkanCoreTranslator.genParameters(destructor.getParameters(), destructor.getReturnType()));
        for(VulkanFunction r : related) System.out.println("    RELATED " + r.getName() + ": " + VulkanCoreTranslator.genParameters(r.getParameters(), r.getReturnType()));
        return "";
    }

    private static VulkanFunction findConstructor(ChainList<EntityTriplet> entities, HandleTriplet entity){
        for(EntityTriplet e : entities){
            if(e instanceof FunctionTriplet){
                VulkanFunction function = (VulkanFunction) e.getVulkan();
                String name = VulkanCoreTranslator.genVulkanFunctionName((FunctionTriplet) e);
                if(name.startsWith("create") && function.getParameters().count() >= 3){
                    if(function.getReturnType().getTypename().equals("VulkanResult")){
                        if(function.getParameters().getLast().getTypename().equals(entity.getVulkan().getName())){
                            if(function.getParameters().getLastItem().getPrevious().getTypename().equals("VulkanAllocationCallbacks")){
                                if(function.getParameters().getLastItem().getPreviousItem().getPrevious().getTypename().contains("CreateInfo")){
                                    return (VulkanFunction) e.getVulkan();
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private static VulkanFunction findDestructor(ChainList<EntityTriplet> entities, HandleTriplet entity){
        for(EntityTriplet e : entities){
            if(e instanceof FunctionTriplet){
                VulkanFunction function = (VulkanFunction) (e).getVulkan();
                String name = VulkanCoreTranslator.genVulkanFunctionName((FunctionTriplet) e);
                if(name.startsWith("destroy") && function.getParameters().count() >= 2){
                    if(function.getParameters().getLast().getTypename().equals("VulkanAllocationCallbacks")){
                        if(function.getParameters().getLastItem().getPrevious().getTypename().equals(entity.getVulkan().getName())){
                            return (VulkanFunction) e.getVulkan();
                        }
                    }
                }
            }
        }
        return null;
    }

    private static ChainList<VulkanFunction> findRelated(ChainList<EntityTriplet> entities, HandleTriplet entity){
        ChainList<VulkanFunction> related = new CachedChainList<>();
        for(EntityTriplet e : entities){
            if(e instanceof FunctionTriplet){
                VulkanFunction function = (VulkanFunction) e.getVulkan();
                String name = VulkanCoreTranslator.genVulkanFunctionName((FunctionTriplet) e);
                if(!name.startsWith("create") && !name.startsWith("destroy") && function.getParameters().count() >= 2){
                    boolean firstDevice = function.getParameters().getFirst().getTypename().equals("VulkanDevice");
                    boolean firstMatch = function.getParameters().getFirst().getTypename().equals(entity.getVulkan().getName());
                    boolean secondMatch = function.getParameters().getFirstItem().getNext().getTypename().equals(entity.getVulkan().getName());;
                    if(entity.getVulkan().getName().equals("VulkanDevice")){
                        if(firstMatch && !isHandle(entities, function.getParameters().getFirstItem().getNext().getTypename())){
                            related.addLast(function);
                        }
                    } else {
                        if(firstMatch){
                            related.addLast(function);
                        } else if(firstDevice && secondMatch) {
                            related.addLast(function);
                        }
                    }
                }
            }
        }
        return related;
    }

    public static boolean isHandle(ChainList<EntityTriplet> entities, String vulkanName){
        for(EntityTriplet e : entities) {
            if(e.getVulkan() != null){
                if (e.getVulkan().getName().equals(vulkanName)) {
                    return e instanceof HandleTriplet;
                }
            }
        }
        return false;
    }
}

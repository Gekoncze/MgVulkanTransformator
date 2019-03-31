package cz.mg.vulkantransformator.entities;

import cz.mg.vulkantransformator.EntityGroup;
import cz.mg.vulkantransformator.EntityType;
import cz.mg.vulkantransformator.entities.c.*;
import cz.mg.vulkantransformator.entities.vk.*;
import cz.mg.vulkantransformator.entities.vulkan.*;


public abstract class EntityTriplet<C extends CEntity, VK extends VkEntity, VULKAN extends VulkanEntity> {
    private final C c;
    private final VK vk;
    private final VULKAN vulkan;

    public EntityTriplet(C c, VK vk, VULKAN vulkan) {
        this.c = c;
        this.vk = vk;
        this.vulkan = vulkan;
    }

    public C getC() {
        return c;
    }

    public VK getVk() {
        return vk;
    }

    public VULKAN getVulkan() {
        return vulkan;
    }

    public Entity get(EntityGroup group) {
        switch(group){
            case C: return c;
            case VK: return vk;
            case VULKAN: return vulkan;
            default: throw new UnsupportedOperationException("" + group);
        }
    }

    public EntityType getEntityType(){
        if(c != null) return c.getEntityType();
        if(vk != null) return vk.getEntityType();
        if(vulkan != null) return vulkan.getEntityType();
        throw new RuntimeException();
    }

    public String getEntityName(){
        if(c != null) return c.getName();
        if(vk != null) return vk.getName();
        if(vulkan != null) return vulkan.getName();
        return "?";
    }

    public static EntityTriplet create(CEntity c, VkEntity vk, VulkanEntity vulkan){
        switch(c.getEntityType()){
            case MISC: return new MiscTriplet((CMisc) c, (VkMisc) vk, (VulkanMisc) vulkan);
            case CALLBACK: return new CallbackTriplet((CCallback) c, (VkCallback) vk, (VulkanCallback) vulkan);
            case ENUM: return new EnumTriplet((CEnum) c, (VkEnum) vk, (VulkanEnum) vulkan);
            case FLAGS: return new FlagsTriplet((CFlags) c, (VkFlags) vk, (VulkanFlags) vulkan);
            case INFO: return new InfoTriplet((CInfo) c, (VkInfo) vk, (VulkanInfo) vulkan);
            case DEFINE: return new DefineTriplet((CDefine) c, (VkDefine) vk, (VulkanDefine) vulkan);
            case TYPE: return new TypeTriplet((CType) c, (VkType) vk, (VulkanType) vulkan);
            case UNION: return new UnionTriplet((CUnion) c, (VkUnion) vk, (VulkanUnion) vulkan);
            case HANDLE: return new HandleTriplet((CHandle) c, (VkHandle) vk, (VulkanHandle) vulkan);
            case FUNCTION: return new FunctionTriplet((CFunction) c, (VkFunction) vk, (VulkanFunction) vulkan);
            case FLAG_BITS: return new FlagBitsTriplet((CFlagBits) c, (VkFlagBits) vk, (VulkanFlagBits) vulkan);
            case STRUCTURE: return new StructureTriplet((CStructure) c, (VkStructure) vk, (VulkanStructure) vulkan);
            case SYSTEM_TYPE: return new SystemTypeTriplet((CSystemType) c, (VkSystemType) vk, (VulkanSystemType) vulkan);
            default: throw new UnsupportedOperationException("" + c.getEntityType());
        }
    }
}

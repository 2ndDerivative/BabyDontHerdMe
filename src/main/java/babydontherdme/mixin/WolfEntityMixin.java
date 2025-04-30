package babydontherdme.mixin;

import babydontherdme.entity.ai.goal.Herding;
import babydontherdme.access.WolfEntityMixinInterface;
import babydontherdme.entity.ai.goal.WolfHerdingGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.WolfSoundVariant;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WolfEntity.class)
public abstract class WolfEntityMixin extends TameableEntity implements WolfEntityMixinInterface, Herding {

    protected WolfEntityMixin(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    private static final TrackedData<Integer> baby_dont_herd_me$HerdingTime = DataTracker.registerData(WolfEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> baby_dont_herd_me$isScary = DataTracker.registerData(WolfEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    @Shadow
    public RegistryEntry<WolfSoundVariant> getSoundVariant() {
        return null;
    };
    
    @ModifyArg(method = "initGoals()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V"))
    private int changePriorities(int i, Goal goal){
        return i > 2 && !(goal instanceof TrackTargetGoal) ? i+1 : i;
    }
    @Inject(method = "initGoals()V", at = @At(value = "RETURN"))
    private void insertGoal(CallbackInfo ci){
        this.goalSelector.add(3, new WolfHerdingGoal((WolfEntity)(Object)this));
    }

    @Inject(method = "initDataTracker(Lnet/minecraft/entity/data/DataTracker$Builder;)V", at = @At(value = "RETURN"))
    private void insertDataTracker(DataTracker.Builder builder, CallbackInfo ci){
        builder.add(baby_dont_herd_me$HerdingTime, 0);
        builder.add(baby_dont_herd_me$isScary, false);
    }

    public void setHerdingTime(int i){
        this.dataTracker.set(baby_dont_herd_me$HerdingTime, i);
    }
    public int getHerdingTime(){
        return this.dataTracker.get(baby_dont_herd_me$HerdingTime);
    }
    public boolean isHerding(){ return getHerdingTime() > 0;}

    public void setScary(boolean scary){this.dataTracker.set(baby_dont_herd_me$isScary, scary);}
    public boolean isScary(){ return this.dataTracker.get(baby_dont_herd_me$isScary);}

    @Inject(method = "tick()V", at = @At(value = "RETURN"))
    private void lowerHerdingTime(CallbackInfo ci){
        int herdingTime = this.getHerdingTime();
        if(herdingTime > 0){
            ((WolfEntityMixinInterface)this).setHerdingTime(herdingTime-1);
        }
    }
}

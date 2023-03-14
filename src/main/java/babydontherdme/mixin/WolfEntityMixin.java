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
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WolfEntity.class)
abstract public class WolfEntityMixin extends TameableEntity implements WolfEntityMixinInterface, Herding {
    private static final TrackedData<Integer> dontherdme$HerdingTime = DataTracker.registerData(WolfEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> dontherdme$isScary = DataTracker.registerData(WolfEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    protected WolfEntityMixin(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }
    @ModifyArg(method = "initGoals()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V"))
    private int changePriorities(int i, Goal goal){
        return i > 2 && !(goal instanceof TrackTargetGoal) ? i+1 : i;
    }
    @Inject(method = "initGoals()V", at = @At(value = "RETURN"))
    private void insertGoal(CallbackInfo ci){
        this.goalSelector.add(3, new WolfHerdingGoal((WolfEntity)(Object)this));
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ((WolfEntity)(Object)this).createChild(world, entity);
    }

    @Inject(method = "initDataTracker()V", at = @At(value = "RETURN"))
    private void insertDataTracker(CallbackInfo ci){
        this.getDataTracker().startTracking(dontherdme$HerdingTime, 0);
        this.getDataTracker().startTracking(dontherdme$isScary,false);
    }

    public void setHerdingTime(int i){
        this.dataTracker.set(dontherdme$HerdingTime, i);
    }
    public int getHerdingTime(){
        return this.dataTracker.get(dontherdme$HerdingTime);
    }
    public boolean isHerding(){ return getHerdingTime() > 0;}

    public void setScary(boolean scary){this.dataTracker.set(dontherdme$isScary, scary);}
    public boolean isScary(){ return this.dataTracker.get(dontherdme$isScary);}

    @Inject(method = "tick()V", at = @At(value = "RETURN"))
    private void lowerHerdingTime(CallbackInfo ci){
        int herdingTime = this.getHerdingTime();
        if(herdingTime > 0){
            ((WolfEntityMixinInterface)this).setHerdingTime(herdingTime-1);
        }
        //this.setCustomName(Text.of(String.valueOf(herdingTime)));
    }
}

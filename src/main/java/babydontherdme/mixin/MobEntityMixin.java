package babydontherdme.mixin;

import babydontherdme.entity.ai.goal.FlockHerdingGoal;
import babydontherdme.tag.ModEntityTypeTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
abstract public class MobEntityMixin extends LivingEntity {

    @Shadow
    private GoalSelector goalSelector;

    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V", at = @At(value = "TAIL"))
    private void insertGoal(EntityType<? extends MobEntity> type, World world, CallbackInfo ci){
        if (world instanceof ServerWorld) {
            Integer escapeDangerGoal = getEscapeDangerGoalIndex();
            if (this.getType().isIn(ModEntityTypeTags.HERDABLE) && escapeDangerGoal != null) {
                this.goalSelector.add(escapeDangerGoal, new FlockHerdingGoal((MobEntity)(Object)this));
            }
        }
    }

    private Integer getEscapeDangerGoalIndex() {
        for (PrioritizedGoal prioritizedGoal: this.goalSelector.getGoals()) {
            if (prioritizedGoal.getGoal() instanceof EscapeDangerGoal) {
                return prioritizedGoal.getPriority();
            }
        };
        return null;
    }
}

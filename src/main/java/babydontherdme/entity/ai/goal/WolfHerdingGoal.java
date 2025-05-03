package babydontherdme.entity.ai.goal;

import babydontherdme.access.WolfEntityMixinInterface;
import babydontherdme.advancement.criterion.ModCriteria;
import babydontherdme.math.SheepHelper;
import babydontherdme.mixin.WolfEntitySoundVariantInvoker;
import babydontherdme.tag.ModEntityTypeTags;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

import java.util.EnumSet;
import java.util.List;


public class WolfHerdingGoal extends Goal {
    //TODO Co-op herding

    //TODO moving herd's CoM
    //Sheep Acquisition
    private final WolfEntity dog;
    private List<MobEntity> herdableList;
    private static final double VISION_RANGE = 30.0;

    //Action cooldowns
    private int acquireCooldown;
    private static final int ACQUIRE_COOLDOWN = 100;
    private int barkCooldown;
    private static final int BARK_COOLDOWN = 8;

    //Movement parameters
    private static final double herdingSpeed = 1.2;
    private static final double CIRCLE_TOLERANCE = 1.5;
    private static final double BARK_ANGLE = Math.PI/180 * 5;
    private static final double SPACING = 0.9* FlockHerdingGoal.WOLF_VISION_RANGE;

    public WolfHerdingGoal(WolfEntity dog){
        this.dog = dog;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    public boolean canStart() {
        return ((WolfEntityMixinInterface)this.dog).isHerding() && !this.dog.isSitting();
    }

    public void start(){
        this.herdableList = getNearbyFlock(VISION_RANGE);
        this.acquireCooldown = ACQUIRE_COOLDOWN;
        this.barkCooldown = BARK_COOLDOWN;
    }
    public void stop(){
        this.dog.playSound(((WolfEntitySoundVariantInvoker)(Object) this.dog).invokeGetSoundVariant().value().pantSound().value(), 1.3f, 1.0f);
        ((Herding)this.dog).setScary(false);
    }

    public void tick() {
        if(this.barkCooldown<=0){
            if (((Herding)this.dog).isScary() && Random.create().nextInt(10) > 2) {
                this.dog.playSound(((WolfEntitySoundVariantInvoker) (Object) this.dog).invokeGetSoundVariant().value().ambientSound().value(), 1.2f, 1.0f);
            }
            this.barkCooldown=BARK_COOLDOWN;
        }
        this.barkCooldown--;
        if(this.acquireCooldown == 0){
            this.herdableList = getNearbyFlock(VISION_RANGE);
            this.acquireCooldown=ACQUIRE_COOLDOWN;
        }

        if(herdableList.size() > 1){
            ModCriteria.HERDED_ANIMALS_WITH_WOLF.trigger((ServerPlayerEntity) this.dog.getOwner(), herdableList.size());
            MobEntity outer = SheepHelper.furthestAnimal(herdableList);
            double acceptableSpread = 1.0+Math.sqrt(herdableList.size());
            this.dog.lookAtEntity(outer,0.0f,0.0f);

            Vec3d circleCenter = SheepHelper.CenterOfMass(herdableList);
            double circleRadius = Math.sqrt(outer.squaredDistanceTo(circleCenter));

            //Math basics for setting up herd system
            Vec3d radialUnitVector = this.dog.getPos().subtract(circleCenter).normalize();
            Vec3d furthestUnitVector = outer.getPos().subtract(circleCenter).normalize();
            boolean dogLinedUp = BARK_ANGLE*BARK_ANGLE > 2-2*furthestUnitVector.dotProduct(radialUnitVector);

            //actual movement
            Vec3d target = dog.getPos();
            double speed = herdingSpeed;
            if(circleRadius > acceptableSpread*Math.sqrt(herdableList.size())){
                if(dogOnCircle(circleCenter,circleRadius + SPACING) && dogLinedUp) {
                    ((Herding)this.dog).setScary(true);
                    target = circleCenter.subtract(radialUnitVector.multiply(circleRadius + SPACING));
                    speed = herdingSpeed*0.8;
                } else {
                    ((Herding)this.dog).setScary(false);
                    target = circleCenter.add(furthestUnitVector.multiply(circleRadius+SPACING));
                    List<MobEntity> closeHerdables = getNearbyFlock(FlockHerdingGoal.WOLF_VISION_RANGE+0.5);
                    if(!closeHerdables.isEmpty()){closeHerdables.remove(outer);}
                    if(!closeHerdables.isEmpty()){
                        Vec3d localCoM = SheepHelper.CenterOfMass(closeHerdables);
                        Vec3d furthestLocalUnit = outer.getPos().subtract(localCoM).normalize();
                        Vec3d dogLocalUnit = this.dog.getPos().subtract(localCoM).normalize();
                        double localAngleRepr = furthestLocalUnit.dotProduct(dogLocalUnit);
                        boolean shouldWalkAround = localAngleRepr < 0.5 && localAngleRepr > -0.2 && closeHerdables.size() < 3;
                        if(shouldWalkAround){
                            ((Herding)this.dog).setScary(true);
                            Vec3d localNormal = furthestLocalUnit.crossProduct(dogLocalUnit).normalize();
                            boolean counterclockwise = localNormal.dotProduct(new Vec3d(0,1,0)) < 0;
                            Vec3d circular = dogLocalUnit.crossProduct(new Vec3d(0,1,0));
                            if(counterclockwise){circular=circular.multiply(-1);}
                            target = dogLocalUnit.multiply(FlockHerdingGoal.WOLF_VISION_RANGE+0.5).add(circular);
                        }
                    }
                }
            }
            this.dog.getNavigation().startMovingTo(target.getX(), target.getY(), target.getZ(), speed);
        }
    }

    private List<MobEntity> getNearbyFlock(double range){
        return this.dog.getWorld().getEntitiesByClass(MobEntity.class,
                dog.getBoundingBox().expand(range,4,range),
                (e) -> (e.getType().isIn(ModEntityTypeTags.HERDABLE)));
    }
    
    private boolean dogOnCircle(Vec3d circleCenter, double radius){
        double dogDistance = Math.sqrt(this.dog.squaredDistanceTo(circleCenter));
        return Math.abs(dogDistance-radius) < CIRCLE_TOLERANCE;
    }

    public boolean shouldContinue() {
        return canStart();
    }
}

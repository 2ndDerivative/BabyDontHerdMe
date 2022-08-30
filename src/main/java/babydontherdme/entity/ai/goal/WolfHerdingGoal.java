package babydontherdme.entity.ai.goal;

import babydontherdme.access.WolfEntityMixinInterface;
import babydontherdme.math.SheepHelper;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

import java.util.EnumSet;
import java.util.List;


public class WolfHerdingGoal extends Goal {
    private final WolfEntity dog;
    private List<SheepEntity> sheepList;
    private SheepEntity outer1;
    private SheepEntity outer2;
    private int aquiringNewTicks;
    private double ACCEPTABLE_SPREAD = 1.0;

    private static final double VISION_RANGE = 30.0;
    private static final double herdingSpeed = 1.2;
    private static final double CIRCLE_TOLERANCE = 1.5;
    private static final double SPACING = 0.9* FlockHerdingGoal.WOLF_VISION_RANGE;
    private static final int checkEvery = 8;

    public WolfHerdingGoal(WolfEntity dog){
        this.dog = dog;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    public boolean canStart() {
        return ((WolfEntityMixinInterface)this.dog).isHerding() && !this.dog.isSitting();
    }

    public void start(){
        updateSheep();
        this.aquiringNewTicks = checkEvery;
    }
    public void stop(){
        ((WolfEntityMixinInterface)this.dog).setScary(false);
        this.dog.playSound(SoundEvents.ENTITY_WOLF_PANT, 1.2f, 1.0f);
    }

    public void tick() {
        if(this.aquiringNewTicks<=0){
            if (!getNearbySheep(3).isEmpty() && ((WolfEntityMixinInterface)this.dog).isScary() && Random.create().nextInt(10) > 3) {
                this.dog.playSound(SoundEvents.ENTITY_WOLF_AMBIENT, 1.2f, 1.0f);
            }
            updateSheep();
            this.aquiringNewTicks=checkEvery;
        }
        this.aquiringNewTicks--;
        if(sheepList.size() > 1){

            //Sheep Protection Mechanic Test
            /*for(SheepEntity sheep : sheepList){
                if(sheep.getAttacker()!=null&&sheep.getAttacker().equals(this.dog.getOwner())){
                    this.dog.setTarget(sheep.getAttacker());
                    break;
                }
            }*/
            this.dog.lookAtEntity(outer1,0.0f,0.0f);

            Vec3d circleCenter = outer1.getPos().add(outer2.getPos()).multiply(0.5);
            double circleRadius = outer1.distanceTo(outer2)/2.0;

            //Math basics for setting up herd system
            Vec3d radialUnitVector = this.dog.getPos().subtract(circleCenter).normalize();
            Vec3d furthestUnitVector = outer1.getPos().subtract(circleCenter).normalize();
            Vec3d normalUnitVector = radialUnitVector.crossProduct(furthestUnitVector);
            double dogAngle = Math.acos(radialUnitVector.dotProduct(furthestUnitVector));

            //actual movement
            Vec3d target = dog.getPos();
            double speed = herdingSpeed;
            if(circleRadius > ACCEPTABLE_SPREAD){
                if(!dogOnCircle(circleCenter, circleRadius + SPACING)){
                    ((WolfEntityMixinInterface)this.dog).setScary(false);
                    target = circleCenter.add(radialUnitVector.multiply(circleRadius+SPACING));
                } else if(dogOnCircle(circleCenter,circleRadius + SPACING) && dogAngle < 2.0/circleRadius) {
                    ((WolfEntityMixinInterface)this.dog).setScary(true);
                    target = circleCenter.subtract(radialUnitVector.multiply(circleRadius + SPACING));
                    speed = herdingSpeed*0.8;
                } else {
                    ((WolfEntityMixinInterface)this.dog).setScary(true);
                    boolean shouldDecreaseAngle = dogAngle < Math.PI*2/3.0;
                    target = this.dog.getPos().add(radialUnitVector.multiply(circleRadius).crossProduct(normalUnitVector.multiply(shouldDecreaseAngle ? -1.0 : 1.0)));
                }
            }
            this.dog.getNavigation().startMovingTo(target.getX(), target.getY(), target.getZ(), speed);
        }
    }

    private List<SheepEntity> getNearbySheep(double range){
        return this.dog.getWorld().getEntitiesByClass(SheepEntity.class,
                dog.getBoundingBox().expand(range,4,range), EntityPredicates.VALID_ENTITY);
    }

    private void updateSheep(){
        List<SheepEntity> sheep = getNearbySheep(VISION_RANGE);
        this.sheepList = sheep;
        if(sheep.size() > 1){
            this.outer1 = SheepHelper.furthestAnimal(sheep);
            this.outer2 = SheepHelper.furthestFromThis(sheep, outer1);
            this.ACCEPTABLE_SPREAD = 1.0+Math.sqrt(sheep.size());
        }
    }

    private boolean dogOnCircle(Vec3d circleCenter, double radius){
        double dogDistance = Math.sqrt(this.dog.squaredDistanceTo(circleCenter));
        return Math.abs(dogDistance-radius) < CIRCLE_TOLERANCE;
    }

    public boolean shouldContinue() {
        return canStart();
    }
}

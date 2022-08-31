package babydontherdme.entity.ai.goal;

import babydontherdme.math.SheepHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;
import java.util.List;

public class FlockHerdingGoal extends Goal {
    private static final double SHEEP_VISION_RANGE = 6.0;
    public static final double WOLF_VISION_RANGE = 3.0;
    private static final double MOVEMENT_MULTIPLIER =1.6;
    private static final double COHESION = 1.5;
    private static final double COHESION_BIAS = 0.3;
    public static final double speed = 1.1;
    private final SheepEntity mob;
    public FlockHerdingGoal(SheepEntity sheep){
        this.mob = sheep;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    public boolean canStart() {
        return areHerdDogsNearby();
    }

    public boolean shouldContinue() {
        return canStart();
    }

    private boolean areHerdDogsNearby(){
        return !findDogs().isEmpty();
    }

    private List<LivingEntity> findDogs(){
        return mob.getWorld().getEntitiesByClass(LivingEntity.class,
                this.mob.getBoundingBox().expand(WOLF_VISION_RANGE, 2,WOLF_VISION_RANGE),
                (herder)->(herder instanceof Herding && ((Herding)herder).isScary()));
    }

    public void tick(){
        Vec3d dogCenter = SheepHelper.CenterOfMass(findDogs());
        Vec3d sheepPos = this.mob.getPos();
        Vec3d separation = sheepPos.subtract(dogCenter);
        double sepD = separation.length();
        separation = separation.multiply(Math.max(1.0,Math.min(WOLF_VISION_RANGE/(sepD*sepD),1.3)));
        List<SheepEntity> sheepList = nearbySheep();
        int sheepCount = sheepList.size();
        Vec3d comseparation = SheepHelper.CenterOfMass(nearbySheep()).subtract(sheepPos).multiply(sheepCount*COHESION);
        Vec3d influenceTotal = separation.multiply(1-COHESION_BIAS).add(comseparation.multiply(COHESION_BIAS)).multiply(MOVEMENT_MULTIPLIER);
        Vec3d target = sheepPos.add(influenceTotal);
        this.mob.getNavigation().startMovingTo(target.getX(), target.getY(), target.getZ(), speed*WOLF_VISION_RANGE/sepD);
    }

    private List<SheepEntity> nearbySheep(){
        return this.mob.getWorld().getEntitiesByClass(SheepEntity.class,
                this.mob.getBoundingBox().expand(SHEEP_VISION_RANGE,4,SHEEP_VISION_RANGE), EntityPredicates.VALID_LIVING_ENTITY);
    }
}

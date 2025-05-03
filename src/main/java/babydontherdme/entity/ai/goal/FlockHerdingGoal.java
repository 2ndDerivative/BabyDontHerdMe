package babydontherdme.entity.ai.goal;

import babydontherdme.math.SheepHelper;
import babydontherdme.tag.ModEntityTypeTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Position;
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
    private final MobEntity mob;
    public FlockHerdingGoal(MobEntity flockEntity){
        this.mob = flockEntity;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    public boolean canStart() {
        return areHerdDogsNearby();
    }

    public boolean shouldContinue() {
        return canStart();
    }

    private boolean areHerdDogsNearby(){
        return !findHerders().isEmpty();
    }

    private List<LivingEntity> findHerders(){
        return mob.getWorld().getEntitiesByClass(LivingEntity.class,
                getOneByOneBox().expand(WOLF_VISION_RANGE, 2, WOLF_VISION_RANGE),
                (herder)->(herder instanceof Herding h && h.isScary()));
    }

    public void tick(){
        Vec3d herderCenter = SheepHelper.CenterOfMass(findHerders());
        Vec3d sheepPos = this.mob.getPos();
        Vec3d separation = sheepPos.subtract(herderCenter);
        double sepD = separation.length();
        separation = separation.multiply(Math.max(1.0,Math.min(WOLF_VISION_RANGE/(sepD*sepD),1.3)));
        List<MobEntity> sheepList = nearbyFlock();
        int sheepCount = sheepList.size();
        Vec3d comseparation = SheepHelper.CenterOfMass(nearbyFlock()).subtract(sheepPos).multiply(sheepCount*COHESION);
        Vec3d influenceTotal = separation.multiply(1-COHESION_BIAS).add(comseparation.multiply(COHESION_BIAS)).multiply(MOVEMENT_MULTIPLIER);
        Vec3d target = sheepPos.add(influenceTotal);
        this.mob.getNavigation().startMovingTo(target.getX(), target.getY(), target.getZ(), speed*WOLF_VISION_RANGE/sepD);
    }

    private List<MobEntity> nearbyFlock(){
        return this.mob.getWorld().getEntitiesByClass(MobEntity.class,
                getOneByOneBox().expand(SHEEP_VISION_RANGE,4,SHEEP_VISION_RANGE), 
                (e) -> (e.getType().isIn(ModEntityTypeTags.HERDABLE)));
    }

    private Box getOneByOneBox() {
        Position pos = this.mob.getPos();
        Box box = new Box(new Vec3d(pos.getX() - 0.5, pos.getY() - 0.5, pos.getZ() - 0.5) , new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5));
        return box;
    }
}

package babydontherdme.math;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class SheepHelper {
    public static <T extends AnimalEntity> Vec3d CenterOfMass(List<T> animals){
        if(!animals.isEmpty()){
            Vec3d com = new Vec3d(0.0,0.0,0.0);
            for(T animal : animals){
                com = com.add(animal.getPos());
            }
            com = com.multiply(1.0/animals.size());
            return com;
        }   else throw new RuntimeException("Illegal operation for zero-element List!");
    }

    public static <T extends AnimalEntity> T furthestAnimal(List<T> animals){
        if(!animals.isEmpty()){
            Vec3d com = CenterOfMass(animals);
            double maxDistance = 0.0;
            T furthest = null;
            for(T a : animals){
                double sqd = a.squaredDistanceTo(com);
                if(sqd > maxDistance){
                    furthest = a;
                    maxDistance = sqd;
                }
            }
            return furthest;
        }   else throw new RuntimeException("Illegal operation for zero-element List!");
    }

    public static <T extends AnimalEntity> T furthestFromThis(List<T> animals, T subject){
        if(animals.size() > 1){
            if(!animals.remove(subject)) throw new RuntimeException("couldnt remove Entity");
            double maxDisSquared = 0.0;
            T furthestPartner = null;
            for(T a : animals){
                double sqd = subject.squaredDistanceTo(a);
                if(subject.squaredDistanceTo(a) > maxDisSquared){
                    furthestPartner = a;
                    maxDisSquared = sqd;
                }
            }
            if(subject.equals(furthestPartner)) throw new RuntimeException("furthest and partner equal");
            return furthestPartner;
        } else throw new RuntimeException("Illegal operation for singleton or empty List!");
    }
}

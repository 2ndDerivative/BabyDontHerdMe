package babydontherdme.mixin;

import babydontherdme.entity.ai.goal.Herding;
import babydontherdme.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements Herding {

    public boolean isScary() {
        return babydontherdme$holdsCrook();
    }

    private boolean babydontherdme$holdsCrook(){
        return ((PlayerEntity)(Object)this).getMainHandStack().isOf(ModItems.CROOK) ||
                ((PlayerEntity)(Object)this).getOffHandStack().isOf(ModItems.CROOK);
    }

    public void setScary(boolean scare) {
    }
}

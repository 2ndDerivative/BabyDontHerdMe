package babydontherdme.mixin;

import babydontherdme.Herding;
import babydontherdme.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements Herding {

    public boolean isScary() {
        return ((PlayerEntity)(Object)this).getMainHandStack().isOf(ModItems.CROOK);
    }

    public void setScary(boolean scare) {
    }
}

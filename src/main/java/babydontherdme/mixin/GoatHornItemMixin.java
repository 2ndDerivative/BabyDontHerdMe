package babydontherdme.mixin;

import babydontherdme.access.WolfEntityMixinInterface;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GoatHornItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(GoatHornItem.class)
public class GoatHornItemMixin {
    private static final double dontherdme$WOLF_RANGE = 30;
    private static final int dontherdme$hornSetting = 1200;
    @Inject(method = "use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/GoatHornItem;playSound(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/Instrument;)V"))
    private void setWolvesHerding(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir){
        if(!world.isClient()){
            List<WolfEntity> dogs =  world.getEntitiesByClass(WolfEntity.class, user.getBoundingBox().expand(dontherdme$WOLF_RANGE, 5, dontherdme$WOLF_RANGE),
                    (wolf)->wolf.isOwner(user));
            for(WolfEntity dog : dogs) {
                ((WolfEntityMixinInterface) dog).setHerdingTime(dontherdme$hornSetting);
            }
        }
    }
}

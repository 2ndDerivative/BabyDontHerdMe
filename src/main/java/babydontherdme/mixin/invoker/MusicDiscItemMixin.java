package babydontherdme.mixin.invoker;

import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MusicDiscItem.class)
public interface MusicDiscItemMixin {
    @Invoker("<init>")
    static MusicDiscItem newDisk(int comparatorOutput, SoundEvent sound, Item.Settings settings, int length) {throw new AssertionError();}
}

package babydontherdme.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.WolfSoundVariant;
import net.minecraft.registry.entry.RegistryEntry;

@Mixin(WolfEntity.class)
public interface WolfEntitySoundVariantInvoker {
    @Invoker("getSoundVariant")
    public RegistryEntry<WolfSoundVariant> invokeGetSoundVariant();
}

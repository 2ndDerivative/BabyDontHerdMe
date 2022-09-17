package babydontherdme.mixin;

import babydontherdme.loot.ModLootTables;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CowEntity.class)
public class CowEntityMixin {
    protected boolean shouldDropLoot(){
        return true;
    }

    public Identifier getLootTableId(){
        return ((CowEntity)(Object)this).isBaby() ? ModLootTables.BABY_COW : new Identifier("minecraft", "entities/cow");
    }
}

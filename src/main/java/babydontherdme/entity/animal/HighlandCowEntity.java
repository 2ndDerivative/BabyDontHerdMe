package babydontherdme.entity.animal;

import babydontherdme.entity.ModEntityType;
import babydontherdme.tag.ModBiomeTags;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class HighlandCowEntity extends CowEntity implements Shearable {
    private static final TrackedData<Boolean> SHEARED = DataTracker.registerData(HighlandCowEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public HighlandCowEntity(EntityType<? extends CowEntity> entityType, World world) {
        super(entityType, world);
    }
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setSheared(false);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SHEARED, false);
    }
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Sheared", this.isSheared());
    }
    public boolean isSheared() {
        return this.dataTracker.get(SHEARED);
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.SHEARS)) {
            if (!this.world.isClient && this.isShearable()) {
                this.sheared(SoundCategory.PLAYERS);
                this.emitGameEvent(GameEvent.SHEAR, player);
                itemStack.damage(1, player, (playerx) -> playerx.sendToolBreakStatus(hand));
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.CONSUME;
            }
        } else {
            return super.interactMob(player, hand);
        }
    }
    public HighlandCowEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        return ModEntityType.HIGHLAND_COW.create(serverWorld);
    }

    public boolean isComfortable(){
        return this.world.getBiome(this.getBlockPos()).isIn(ModBiomeTags.SPAWNS_HIGHLAND_CATTLE);
    }

    public boolean isShearable() {
        return !isSheared() && !isBaby();
    }

    public void sheared(SoundCategory shearedSoundCategory) {
        this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
        int i = 1 + (this.isComfortable() ? this.random.nextInt(2) : 0) ;
        for(int j = 0; j < i; ++j) {
            ItemEntity itemEntity = this.dropItem(this.random.nextBoolean() ? Items.BROWN_WOOL : Items.ORANGE_WOOL, 1);
            if (itemEntity != null) {
                itemEntity.setVelocity(itemEntity.getVelocity().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F));
            }
        }
    }
    public void setSheared(boolean sheared) {
        this.dataTracker.set(SHEARED, sheared);
    }
}

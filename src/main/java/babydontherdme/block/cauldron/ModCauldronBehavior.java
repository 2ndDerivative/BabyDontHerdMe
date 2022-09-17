package babydontherdme.block.cauldron;

import babydontherdme.block.MilkCauldronBlock;
import babydontherdme.block.ModBlocks;
import babydontherdme.item.ModItems;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;

import java.util.Map;

public interface ModCauldronBehavior {
    Map<Item, CauldronBehavior> MILK_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
    CauldronBehavior FILL_WITH_MILK = (state, world, pos, player, hand, stack) -> CauldronBehavior.fillCauldron(world, pos, player, hand, stack, ModBlocks.MILK_CAULDRON.getDefaultState(), SoundEvents.ITEM_BUCKET_EMPTY_LAVA);
    CauldronBehavior PUT_IN_RENNET = MilkCauldronBlock::enterRennet;
    static void registerCauldronBehaviour(){
        CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.put(Items.MILK_BUCKET, FILL_WITH_MILK);
        MILK_CAULDRON_BEHAVIOR.put(Items.BUCKET, (state, world, pos, player, hand, stack) -> CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(Items.MILK_BUCKET), (statex) -> true, SoundEvents.ITEM_BUCKET_FILL_LAVA));
        MILK_CAULDRON_BEHAVIOR.put(ModItems.RENNET, PUT_IN_RENNET);
    }
}

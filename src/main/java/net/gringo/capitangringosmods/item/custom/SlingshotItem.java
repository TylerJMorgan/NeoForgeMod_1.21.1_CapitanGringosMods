package net.gringo.capitangringosmods.item.custom;

import net.gringo.capitangringosmods.entity.PebbleEntity;
import net.gringo.capitangringosmods.entity.PoisonPebbleEntity;
import net.gringo.capitangringosmods.entity.StrongPoisonPebbleEntity;
import net.gringo.capitangringosmods.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class SlingshotItem extends BowItem {
    public SlingshotItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!findAmmo(player).isEmpty() || player.getAbilities().instabuild) {
            player.startUsingItem(hand); // starts pull animation
            return InteractionResultHolder.consume(stack);
        }
        return InteractionResultHolder.fail(stack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity living, int timeLeft) {
        if (!(living instanceof Player player)) return;

        boolean creative = player.getAbilities().instabuild;
        ItemStack ammoStack = findAmmo(player);
        Item ammoItem = ammoStack.getItem();
        int charge = this.getUseDuration(stack, living) - timeLeft;
        float power = getPowerForTime(charge);

        if (power < 0.1F || (ammoStack.isEmpty() && !creative)) return;

        if (!level.isClientSide) {
            // Determine which pebble entity to spawn
            PebbleEntity pebbleEntity;
            if (ammoItem == ModItems.POISON_PEBBLE.get()) {
                pebbleEntity = new PoisonPebbleEntity(level, player, ammoStack.copy());
            } else if (ammoItem == ModItems.STRONG_POISON_PEBBLE.get()) {
                pebbleEntity = new StrongPoisonPebbleEntity(level, player, ammoStack.copy());
            } else {
                pebbleEntity = new PebbleEntity(level, player, ammoStack.copy());
            }

            // Shoot the pebble
            pebbleEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, power * 2.5F, 1.0F);

            // Look up the FLAME enchantment holder and check its level on the slingshot
            var enchLookup = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            var flameHolderOpt = enchLookup.get(Enchantments.FLAME);
            int flameLevel = flameHolderOpt
                    .map(h -> EnchantmentHelper.getTagEnchantmentLevel(h, stack)) // reads from the item’s enchantment component
                    .orElse(0);
            if (flameLevel > 0) {
                // make the projectile itself burning; on-hit will pass this to the victim
                pebbleEntity.setSecondsOnFire(10);
            }

            level.addFreshEntity(pebbleEntity);

            stack.hurtAndBreak(this.getDurabilityUse(ammoStack), living, EquipmentSlot.MAINHAND);
        }

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F,
                1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + power * 0.5F);

        if (!creative) {
            ammoStack.shrink(1);
            if (ammoStack.isEmpty()) {
                player.getInventory().removeItem(ammoStack);
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));
    }

    private ItemStack findAmmo(Player player) {
        if (player.getOffhandItem().is(ModItems.PEBBLE.get())
            || player.getOffhandItem().is(ModItems.POISON_PEBBLE.get())
            || player.getOffhandItem().is(ModItems.STRONG_POISON_PEBBLE.get())) {
            return player.getOffhandItem();
        }

        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(ModItems.PEBBLE.get())
                || stack.is(ModItems.POISON_PEBBLE.get())
                || stack.is(ModItems.STRONG_POISON_PEBBLE.get())) {
                return stack;
            }
        }

        return ItemStack.EMPTY;
    }
}

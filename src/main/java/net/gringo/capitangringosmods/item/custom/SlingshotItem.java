package net.gringo.capitangringosmods.item.custom;

import net.gringo.capitangringosmods.entity.PebbleEntity;
import net.gringo.capitangringosmods.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SlingshotItem extends BowItem {
    public SlingshotItem(Properties properties) {
        super(properties);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity living, int timeLeft) {
        if (living instanceof Player player) {
            boolean creative = player.getAbilities().instabuild;
            ItemStack pebbleStack = findAmmo(player);

            int charge = this.getUseDuration(stack, living) - timeLeft;
            float power = getPowerForTime(charge);

            if (power >= 0.1F) {
                if (!pebbleStack.isEmpty() || creative) {
                    if (!level.isClientSide) {
                        // give the entity the actual pebble item so it renders properly
                        PebbleEntity pebble = new PebbleEntity(level, player, new ItemStack(ModItems.PEBBLE.get()));
                        pebble.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, power * 2.5F, 1.0F);
                        level.addFreshEntity(pebble);
                    }

                    level.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F,
                            1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + power * 0.5F);

                    if (!creative) {
                        pebbleStack.shrink(1);
                        if (pebbleStack.isEmpty()) {
                            player.getInventory().removeItem(pebbleStack);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    private ItemStack findAmmo(Player player) {
        if (player.getOffhandItem().is(ModItems.PEBBLE.get())) {
            return player.getOffhandItem();
        }

        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(ModItems.PEBBLE.get())) {
                return stack;
            }
        }

        return ItemStack.EMPTY;
    }
}

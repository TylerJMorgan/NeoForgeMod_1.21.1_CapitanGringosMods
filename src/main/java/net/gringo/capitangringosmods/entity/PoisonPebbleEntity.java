package net.gringo.capitangringosmods.entity;

import net.gringo.capitangringosmods.item.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class PoisonPebbleEntity extends PebbleEntity {
    public PoisonPebbleEntity(EntityType<? extends PebbleEntity> type, Level level) {
        super(type, level);
    }

    public PoisonPebbleEntity(Level level, LivingEntity shooter, ItemStack stack) {
        super(level, shooter, stack);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.POISON_PEBBLE.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult hit) {
        super.onHitEntity(hit);

        if (!level().isClientSide && hit.getEntity() instanceof LivingEntity target) {
            // Apply poison for 5 seconds (100 ticks) at amplifier 0 (Poison I)
            target.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0));
        }
    }
}

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

public class StrongPoisonPebbleEntity extends PoisonPebbleEntity {
    public StrongPoisonPebbleEntity(EntityType<? extends StrongPoisonPebbleEntity> type, Level level) {
        super(type, level);
    }

    public StrongPoisonPebbleEntity(Level level, LivingEntity shooter, ItemStack stack) {
        super(level, shooter, stack);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.STRONG_POISON_PEBBLE.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult hit) {
        super.onHitEntity(hit);
        // Add a stronger poison effect
        if (!level().isClientSide && hit.getEntity() instanceof LivingEntity target) {
            // Apply poison for 5 seconds (100 ticks) at amplifier 1 (Poison II)
            target.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 1));
        }
    }
}

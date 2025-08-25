package net.gringo.capitangringosmods.entity;

import net.gringo.capitangringosmods.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class PebbleEntity extends ThrowableItemProjectile {

    public PebbleEntity(EntityType<? extends PebbleEntity> type, Level level) {
        super(type, level);
    }

    public PebbleEntity(Level level, LivingEntity shooter, ItemStack stack) {
        super(ModEntities.PEBBLE.get(), shooter, level);
        this.setItem(stack); // makes the entity render as the pebble item, very important: this tells MC what to render
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.PEBBLE.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult hit) {
        super.onHitEntity(hit);

        if (!level().isClientSide) {
            // Deal damage
            hit.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 3.0F);

            // If the pebble itself is burning, set the target on fire
            if (this.isOnFire()) {
                hit.getEntity().setRemainingFireTicks(100); // just like flame arrows
            }

            this.discard();
        }
    }

    @Override
    protected void onHit(HitResult hit) {
        super.onHit(hit);
        if (!level().isClientSide) {
            level().playSound(null, blockPosition(), SoundEvents.STONE_HIT, SoundSource.PLAYERS, 1.0F, 1.0F);
            this.discard();
        }
    }

    public void setSecondsOnFire(int seconds) {
        this.setRemainingFireTicks(seconds * 20);
    }
}

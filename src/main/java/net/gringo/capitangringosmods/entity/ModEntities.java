package net.gringo.capitangringosmods.entity;

import net.gringo.capitangringosmods.CapitanGringosMods;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, CapitanGringosMods.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<PebbleEntity>> PEBBLE =
            ENTITY_TYPES.register("pebble",
                    () -> EntityType.Builder.<PebbleEntity>of(PebbleEntity::new, MobCategory.MISC)
                            .sized(0.25f, 0.25f)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build(CapitanGringosMods.MOD_ID + ":pebble"));

    public static final DeferredHolder<EntityType<?>, EntityType<PoisonPebbleEntity>> POISON_PEBBLE =
            ENTITY_TYPES.register("poison_pebble",
                    () -> EntityType.Builder.<PoisonPebbleEntity>of(PoisonPebbleEntity::new, MobCategory.MISC)
                            .sized(0.25f, 0.25f)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build(CapitanGringosMods.MOD_ID + ":poison_pebble"));

    private ModEntities() {}
}

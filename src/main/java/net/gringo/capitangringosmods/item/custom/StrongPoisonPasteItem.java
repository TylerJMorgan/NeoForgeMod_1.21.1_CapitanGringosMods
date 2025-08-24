package net.gringo.capitangringosmods.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class StrongPoisonPasteItem extends PoisonPasteItem {
    public StrongPoisonPasteItem(Properties properties) {
        super(properties); // Inherits crafting behavior from base class
    }

    public static final FoodProperties STRONG_POISON_PASTE_FOOD = new FoodProperties.Builder()
            .nutrition(6) // how much hunger to restore
            .saturationModifier(0.2F)
            .usingConvertsTo(Items.BOWL) // return bowl after use/crafting
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 200, 1), 1.0F) // always poisons
            .alwaysEdible() // so you can eat it even when full
            .build();
}

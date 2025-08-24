package net.gringo.capitangringosmods.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class PoisonPasteItem extends Item {
    public PoisonPasteItem(Properties properties) {
        super(properties.food(POISON_PASTE_FOOD));
    }

    public static final FoodProperties POISON_PASTE_FOOD = new FoodProperties.Builder()
            .nutrition(6) // how much hunger to restore
            .saturationModifier(0.2F)
            .usingConvertsTo(Items.BOWL) // return bowl after use/crafting
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 200, 1), 1.0F) // always poisons
            .alwaysEdible() // so you can eat it even when full
            .build();

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return new ItemStack(Items.BOWL);
    }
}

package net.gringo.capitangringosmods.item;

import net.gringo.capitangringosmods.CapitanGringosMods;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CapitanGringosMods.MOD_ID);

    public static final Supplier<CreativeModeTab> SLINGSHOT_ITEMS_TAB = CREATIVE_MODE_TAB.register("slingshot_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SLINGSHOT.get()))
                    .title(Component.translatable("creativetab.capitangringosmods.slingshot_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.SLINGSHOT);
                        output.accept(ModItems.PEBBLE);
                        output.accept(ModItems.POISON_PEBBLE);
                        output.accept(ModItems.POISON_PASTE);
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}

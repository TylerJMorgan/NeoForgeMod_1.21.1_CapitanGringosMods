package net.gringo.capitangringosmods.item;

import net.gringo.capitangringosmods.CapitanGringosMods;
import net.gringo.capitangringosmods.item.custom.PoisonPasteItem;
import net.gringo.capitangringosmods.item.custom.SlingshotItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

// Some Notes: It is not necessary to create a ModItems class (1/4),
// The en_us.json gives the registered item a proper name (2/4)
// The models/item/bismuth.json makes sure to point to the correct texture (3/4)
// Make sure the textures (assets/capitangringosmods/textures) are 16x16 pngs (if you want it to match other Minecraft stuff)
public class ModItems {
    // A deferred register helps to register objects to modded and vanilla registries, when the time comes it will
    // tell Minecraft to also add these items under our Mod ID.
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CapitanGringosMods.MOD_ID);

    // This line takes care of the registration of the item/it registers a new item (4/4)
    public static final DeferredItem<Item> BISMUTH = ITEMS.register("bismuth", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_BISMUTH = ITEMS.register("raw_bismuth", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PEBBLE = ITEMS.register("pebble", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SLINGSHOT = ITEMS.register("slingshot",
            () -> new SlingshotItem(new Item.Properties().durability(150)));
    public static final DeferredItem<Item> POISON_PEBBLE = ITEMS.register("poison_pebble",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> POISON_PASTE = ITEMS.register("poison_paste",
            () -> new PoisonPasteItem(new Item.Properties().stacksTo(16)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

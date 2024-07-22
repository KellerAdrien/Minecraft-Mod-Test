package name.modid.item;

import name.modid.ImmersivePunching;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {
    public static final Item NAVI = register("Navi", new Item(new Item.Settings()));

    public static final Item EXAMPLE_FOOD = register("example_food", new Item(new Item.Settings().food(null)));

    private static <T extends Item> T register(String name, T item) {
        return Registry.register(Registries.ITEM, ImmersivePunching.id(name), item);
    }

    public static void load() {
    }
}
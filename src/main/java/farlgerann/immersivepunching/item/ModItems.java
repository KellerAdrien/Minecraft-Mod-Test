package farlgerann.immersivepunching.item;

import farlgerann.immersivepunching.ImmersivePunching;
import farlgerann.immersivepunching.list.FoodList;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {
    public static final Navi NAVI = register("navi", new Navi(new Item.Settings()));

    public static final Item EXAMPLE_FOOD = register("example_food",
            new Item(new Item.Settings().food(FoodList.EXEMPLE_FOOD_COMPONENT)));

    private static <T extends Item> T register(String name, T item) {
        return Registry.register(Registries.ITEM, ImmersivePunching.id(name), item);
    }

    public static void load() {
    }
}
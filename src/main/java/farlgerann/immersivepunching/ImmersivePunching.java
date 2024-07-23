package farlgerann.immersivepunching;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import farlgerann.immersivepunching.entity.ModEntities;
import farlgerann.immersivepunching.item.ModItems;

public class ImmersivePunching implements ModInitializer {
	public static final String MOD_ID = "immersivepunching";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.load();
		ModEntities.load();
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
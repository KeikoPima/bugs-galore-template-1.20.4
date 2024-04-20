package keiko.bugsgalore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BugsGalore implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("bugsgalore");

	private static final Identifier DIRT_LOOT_TABLE_ID = Blocks.DIRT.getLootTableId();
 
	public static final Item BEETLE =
		Registry.register(Registries.ITEM, new Identifier("bugsgalore", "beetle"),
		new Item(new FabricItemSettings()));

	public static final Item WORMS =
		Registry.register(Registries.ITEM, new Identifier("bugsgalore", "worms"),
		new Item(new FabricItemSettings()));

	public static final ItemGroup BUGS_GALORE = FabricItemGroup.builder()
			.icon(() -> new ItemStack(BEETLE))
			.displayName(Text.translatable("itemGroup.bugsgalore.bugsgaloreitemgroup"))
			.entries((context, entires) -> {
					entires.add(BEETLE);
					entires.add(WORMS);
			})
			.build();
	
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if (source.isBuiltin() && DIRT_LOOT_TABLE_ID.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
						.with(ItemEntry.builder(Items.WORMS));
		 
				tableBuilder.pool(poolBuilder);
			}
		});

		Registry.register(Registries.ITEM_GROUP, new Identifier("bugsgalore", "bugsgaloreitemgroup"), BUGS_GALORE);
	}
}
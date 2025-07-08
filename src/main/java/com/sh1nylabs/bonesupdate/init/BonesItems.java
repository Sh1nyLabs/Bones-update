package com.sh1nylabs.bonesupdate.init;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.items.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BonesItems {
    public static final DeferredRegister<Item> BU_ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, BonesUpdate.MODID);
    public static final TagKey<Item> GRABBER_STEALS = ItemTags.create(new ResourceLocation(BonesUpdate.MODID,"grabber_steals"));
    public static final TagKey<Item> NECROMANCY_ENCHANTABLE = ItemTags.create(new ResourceLocation(BonesUpdate.MODID,"necromancy_enchantable"));
    public static final TagKey<Item> SKELETON_QUEST_ENCHANTABLE = ItemTags.create(new ResourceLocation(BonesUpdate.MODID,"skeleton_quest_enchantable"));

    public static final DeferredHolder<Item, Item> SKELETON_SOUL = BU_ITEMS.register("skeleton_soul",()-> new SoulItem(new Item.Properties()));
    public static final DeferredHolder<Item, Item> SOUL_ORB = BU_ITEMS.register("soul_orb",
            () -> new Item(new Item.Properties().stacksTo(16)));
    public static final DeferredHolder<Item, Item> NECRO_SCEPTER = BU_ITEMS.register("necromancer_scepter",
            ()-> new NecroScepterItem(new Item.Properties().durability(50)));
    public static final DeferredHolder<Item, Item> AMULET = BU_ITEMS.register("amulet",
            ()-> new AmuletItem(new Item.Properties().durability(100)));
    public static final DeferredHolder<Item, Item> MINION_SWORD = BU_ITEMS.register("minion_sword",
            ()->new Item(new Item.Properties().durability(100)));
    public static final DeferredHolder<Item, Item> BLADE = BU_ITEMS.register("blade",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredHolder<Item, Item> HAUNTER_BLADE = BU_ITEMS.register("haunter_blade",
            () -> new Item(new Item.Properties().stacksTo(64).attributes(HaunterSpearItem.createAttributes())));
    public static final DeferredHolder<Item, Item> HAUNTER_SPEAR = BU_ITEMS.register("haunter_spear",
            ()-> new HaunterSpearItem(new Item.Properties().durability(100)));

    /** Register all block items using the function 'registerBlockItem' */
    public static final DeferredHolder<Item, Item> GRAVE_BLOCK_ITEM = registerBlockItem(BonesBlocks.GRAVE_BLOCK,"grave");
    public static final DeferredHolder<Item, Item> CURSED_LANTERN_ITEM = registerBlockItem(BonesBlocks.CURSED_LANTERN,"cursed_lantern");
    public static final DeferredHolder<Item, Item> WEEPING_WILLOW_VINES_ITEM = registerBlockItem(BonesBlocks.WEEPING_WILLOW_SMALL_VINES ,"weeping_willow_vines");
    public static final DeferredHolder<Item, Item> WEEPING_WILLOW_LEAVES_ITEM = registerBlockItem(BonesBlocks.WEEPING_WILLOW_LEAVES,"weeping_willow_leaves");

    public static final DeferredHolder<Item, Item> ERODED_FOSSIL_ITEM = registerBlockItem(BonesBlocks.ERODED_FOSSIL,"eroded_fossil");
    public static final DeferredHolder<Item, Item> GUARDIAN_FOSSIL_ITEM = registerBlockItem(BonesBlocks.GUARDIAN_FOSSIL,"guardian_fossil");

    /** Register all spawn eggs using the function 'registerSpawnEggs' */
    public static final DeferredHolder<Item, SpawnEggItem> GRABBER_SPAWN_EGG = registerSpawnEgg(BonesEntities.GRABBER,"grabber_spawn_egg",0xC1C1C1,0xA46826);
    public static final DeferredHolder<Item, SpawnEggItem> HAUNTER_SPAWN_EGG = registerSpawnEgg(BonesEntities.HAUNTER_SKELETON,"haunter_skeleton_spawn_egg",0xC1C1C1, 0x5642B3);
    public static final DeferredHolder<Item, SpawnEggItem> KNIGHT_SKELETON_SPAWN_EGG = registerSpawnEgg(BonesEntities.KNIGHT_SKELETON,"knight_skeleton_spawn_egg",0x2E2E2E,0x5642B3);
    public static final DeferredHolder<Item, SpawnEggItem> MINION_SPAWN_EGG = registerSpawnEgg(BonesEntities.MINION,"minion_spawn_egg",0xC1C1C1,0xC5C5C5);
    public static final DeferredHolder<Item, SpawnEggItem> NECROMANCER_SPAWN_EGG = registerSpawnEgg(BonesEntities.NECROMANCER,"necromancer_spawn_egg",0x9B7A63,0xFFFFFF);
    public static final DeferredHolder<Item, SpawnEggItem> REAPER_SPAWN_EGG = registerSpawnEgg(BonesEntities.REAPER,"reaper_spawn_egg",0x343244,0xBC5E5E);

    /** Register other items, with no use */
    public static final DeferredHolder<Item, Item> NECRO_SCEPTER_INVENTORY = BU_ITEMS.register("necromancer_scepter_inventory",
            ()-> new Item(new Item.Properties()));

    /** Functions used */
    private static DeferredHolder<Item, SpawnEggItem> registerSpawnEgg(Supplier<? extends EntityType<? extends Mob>> type, String name, int backgroundColor, int highlightColor) {
        return BU_ITEMS.register(name,
                ()-> new DeferredSpawnEggItem(type, FastColor.ARGB32.opaque(backgroundColor),FastColor.ARGB32.opaque(highlightColor),new Item.Properties()));
    }

    private static DeferredHolder<Item, Item> registerBlockItem(DeferredHolder<Block, Block> blockType, String name) {
        return BU_ITEMS.register(name, () -> new BlockItem(blockType.get(), new Item.Properties()));
    }


}

package com.sh1nylabs.bonesupdate.init;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.items.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BonesItems {
    public static final DeferredRegister<Item> BU_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BonesUpdate.MODID);

    public static final RegistryObject<Item> SKELETON_SOUL = BU_ITEMS.register("skeleton_soul",()-> new SoulItem(new Item.Properties()));
    public static final RegistryObject<Item> SOUL_ORB = BU_ITEMS.register("soul_orb",
            () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> NECRO_SCEPTER = BU_ITEMS.register("necromancer_scepter",
            ()-> new NecroScepterItem(new Item.Properties().durability(50)));
    public static final RegistryObject<Item> AMULET = BU_ITEMS.register("amulet",
            ()-> new AmuletItem(new Item.Properties().durability(100)));
    public static final RegistryObject<Item> MINION_SWORD = BU_ITEMS.register("minion_sword",
            ()->new Item(new Item.Properties().durability(100)));
    public static final RegistryObject<Item> HAUNTER_SPEAR = BU_ITEMS.register("haunter_spear",
            ()-> new HaunterSpearItem(new Item.Properties().durability(100)));
    public static final RegistryObject<Item> REAPER_SCYTHE = BU_ITEMS.register("reaper_scythe",
            ()-> new Item(new Item.Properties().durability(100)));

    /** Register all block items using the function 'registerBlockItem' */
    public static final RegistryObject<Item> GRAVE_BLOCK_ITEM = registerBlockItem(BonesBlocks.GRAVE_BLOCK,"grave");
    public static final RegistryObject<Item> SPOOKY_LANTERN_ITEM = registerBlockItem(BonesBlocks.SPOOKY_LANTERN,"spooky_lantern");
    public static final RegistryObject<Item> WEEPING_WILLOW_VINES_ITEM = registerBlockItem(BonesBlocks.WEEPING_WILLOW_SMALL_VINES ,"weeping_willow_vines");
    public static final RegistryObject<Item> WEEPING_WILLOW_LEAVES_ITEM = registerBlockItem(BonesBlocks.WEEPING_WILLOW_LEAVES,"weeping_willow_leaves");

    /** Register all spawn eggs using the function 'registerSpawnEggs' */
    public static final RegistryObject<ForgeSpawnEggItem> MINION_SPAWN_EGG = registerSpawnEgg(BonesEntities.MINION,"minion_spawn_egg",0xC1C1C1,0xC5C5C5);
    public static final RegistryObject<ForgeSpawnEggItem> NECROMANCER_SPAWN_EGG = registerSpawnEgg(BonesEntities.NECROMANCER,"necromancer_spawn_egg",0x9B7A63,0xFFFFFF);
    public static final RegistryObject<ForgeSpawnEggItem> REAPER_SPAWN_EGG = registerSpawnEgg(BonesEntities.REAPER,"reaper_spawn_egg",0xFFFFFF,0xFFFFFF);
    public static final RegistryObject<ForgeSpawnEggItem> KNIGHT_SKELETON_SPAWN_EGG = registerSpawnEgg(BonesEntities.KNIGHT_SKELETON,"knight_skeleton_spawn_egg",0xD5D5D5,0xBF3939);
    public static final RegistryObject<ForgeSpawnEggItem> HAUNTER_SPAWN_EGG = registerSpawnEgg(BonesEntities.HAUNTER_SKELETON,"haunter_skeleton_spawn_egg",0xD5D5D5,0xBF3939);

    /** Register other items, with no use */
    public static final RegistryObject<Item> NECRO_SCEPTER_INVENTORY = BU_ITEMS.register("necromancer_scepter_inventory",
            ()-> new Item(new Item.Properties()));

    /** Functions used */
    private static RegistryObject<ForgeSpawnEggItem> registerSpawnEgg(Supplier<? extends EntityType<? extends Mob>> type, String name, int backgroundColor, int highlightColor) {
        return BU_ITEMS.register(name,
                ()-> new ForgeSpawnEggItem(type,backgroundColor,highlightColor,new Item.Properties()));
    }

    private static RegistryObject<Item> registerBlockItem(RegistryObject<Block> blockType, String name) {
        return BU_ITEMS.register(name, () -> new BlockItem(blockType.get(), new Item.Properties()));
    }


}

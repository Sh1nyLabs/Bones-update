package com.sh1nylabs.bonesupdate;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.sh1nylabs.bonesupdate.init.BonesParticles;
import com.sh1nylabs.bonesupdate.init.*;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(BonesUpdate.MODID)
public class BonesUpdate {
    /** version 1.2: what is new:
     * - new mob: the Haunter
     * - new tool: the Haunter spear (no recipe, obtained by drop from the haunter)
     * - now broken skeletons can be hit by Explosions
     * - now broken skeletons take a longer time to revive
    */
    public static final String MODID = "bonesupdate";

    public BonesUpdate() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BonesBlocks.BU_BLOCKS.register(modEventBus);
        BonesItems.BU_ITEMS.register(modEventBus);
        BonesEntities.BU_ENTITIES.register(modEventBus);
        BonesBlocks.BU_BLOCK_ENTITIES.register(modEventBus);
        BonesEnchantments.BU_ENCHANTMENTS.register(modEventBus);
        BonesParticles.BU_PARTICLES.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        // Register the item to a creative tab
        modEventBus.addListener(this::addItemsInCreativeTab);
    }

    private void addItemsInCreativeTab(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(BonesItems.GRAVE_BLOCK_ITEM);
        } else if (event.getTab() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(BonesItems.WEEPING_WILLOW_LEAVES_ITEM);
            event.accept(BonesItems.WEEPING_WILLOW_VINES_ITEM);
        } else if (event.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(BonesItems.AMULET);
        } else if (event.getTab() == CreativeModeTabs.COMBAT) {
            event.accept(BonesItems.NECRO_SCEPTER);
            event.accept(BonesItems.HAUNTER_SPEAR);
            event.accept(BonesItems.MINION_SWORD);
        } else if (event.getTab() == CreativeModeTabs.INGREDIENTS) {
            event.accept(BonesItems.SKELETON_SOUL);
            event.accept(BonesItems.SOUL_ORB);
        } else if (event.getTab() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(BonesItems.MINION_SPAWN_EGG);
            event.accept(BonesItems.KNIGHT_SKELETON_SPAWN_EGG);
            event.accept(BonesItems.HAUNTER_SPAWN_EGG);
            event.accept(BonesItems.NECROMANCER_SPAWN_EGG);
        }
    }
}


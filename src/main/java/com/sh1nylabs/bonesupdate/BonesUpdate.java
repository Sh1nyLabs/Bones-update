package com.sh1nylabs.bonesupdate;

/* Java class written by sh1nylabs' team. All rights reserved. */

import com.mojang.logging.LogUtils;
import com.sh1nylabs.bonesupdate.init.BonesParticles;
import com.sh1nylabs.bonesupdate.init.*;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(BonesUpdate.MODID)
public class BonesUpdate {
    /** version 2.0: what is new:
     * -- added content --
     * - now vanilla skeletons can become broken,
     * - added recipes using skeleton soul to create crying obsidian or soul sand.
     * - added various sound events for added entities
     * - new decoration blocks: various fossils
     * - new Mob: The Grabber
     *
     * -- small updates --
     * - broken skeletons code has been reworked,
     * - friendly skeleton code has been reworked.
     *
     * -- bug fixes --
     * -
    */
    public static final String MODID = "bonesupdate";
    public static final Logger LOGGER = LogUtils.getLogger();


    public BonesUpdate() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BonesBlocks.BU_BLOCKS.register(modEventBus);
        BonesItems.BU_ITEMS.register(modEventBus);
        BonesEntities.BU_ENTITIES.register(modEventBus);
        BonesBlocks.BU_BLOCK_ENTITIES.register(modEventBus);
        BonesEnchantments.BU_ENCHANTMENTS.register(modEventBus);
        BonesParticles.BU_PARTICLES.register(modEventBus);
        BonesSounds.BU_SOUNDS.register(modEventBus);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        // Register the item to a creative tab
        modEventBus.addListener(this::addItemsInCreativeTab);
    }

    private void addItemsInCreativeTab(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(BonesItems.GRAVE_BLOCK_ITEM);
            event.accept(BonesItems.WEEPING_WILLOW_LEAVES_ITEM);
            event.accept(BonesItems.WEEPING_WILLOW_VINES_ITEM);
            event.accept(BonesItems.ERODED_FOSSIL_ITEM);
            event.accept(BonesItems.GUARDIAN_FOSSIL_ITEM);
        } else if (event.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(BonesItems.AMULET);
        } else if (event.getTab() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(BonesItems.CURSED_LANTERN_ITEM);
        } else if (event.getTab() == CreativeModeTabs.COMBAT) {
            event.accept(BonesItems.NECRO_SCEPTER);
            event.accept(BonesItems.HAUNTER_SPEAR);
            event.accept(BonesItems.MINION_SWORD);
        } else if (event.getTab() == CreativeModeTabs.INGREDIENTS) {
            event.accept(BonesItems.SKELETON_SOUL);
            event.accept(BonesItems.SOUL_ORB);
            event.accept(BonesItems.BLADE);
            event.accept(BonesItems.HAUNTER_BLADE);
        } else if (event.getTab() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(BonesItems.GRABBER_SPAWN_EGG);
            event.accept(BonesItems.MINION_SPAWN_EGG);
            event.accept(BonesItems.KNIGHT_SKELETON_SPAWN_EGG);
            event.accept(BonesItems.HAUNTER_SPAWN_EGG);
            event.accept(BonesItems.NECROMANCER_SPAWN_EGG);
            event.accept(BonesItems.REAPER_SPAWN_EGG);
        }
    }
}


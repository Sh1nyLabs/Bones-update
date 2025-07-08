package com.sh1nylabs.bonesupdate;

import com.mojang.logging.LogUtils;
import com.sh1nylabs.bonesupdate.init.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BonesUpdate.MODID)
public class BonesUpdate
{
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
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    /**
    public static final DeferredRegister<CreativeModeTab> BONES_CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);


    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = BONES_CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> BonesItems.SKELETON_SOUL.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(BonesItems.HAUNTER_BLADE.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());
    */
    public BonesUpdate()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        //modEventBus.addListener(this::commonSetup);

        BonesBlocks.BU_BLOCKS.register(modEventBus);
        BonesBlocks.BU_BLOCK_ENTITIES.register(modEventBus);
        BonesEntities.BU_ENTITIES.register(modEventBus);
        BonesItems.BU_ITEMS.register(modEventBus);
        BonesEnchantments.BU_ENCHANTMENTS.register(modEventBus);
        BonesParticles.BU_PARTICLES.register(modEventBus);
        BonesSounds.BU_SOUNDS.register(modEventBus);

        //CREATIVE_MODE_TABS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BonesUpdateConfig.SPEC);
    }

    /**
    private void commonSetup(final FMLCommonSetupEvent event)
    {
        BonesUpdateConfig.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }*/

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(BonesItems.GRAVE_BLOCK_ITEM);
            event.accept(BonesItems.WEEPING_WILLOW_LEAVES_ITEM);
            event.accept(BonesItems.WEEPING_WILLOW_VINES_ITEM);
            event.accept(BonesItems.ERODED_FOSSIL_ITEM);
            event.accept(BonesItems.GUARDIAN_FOSSIL_ITEM);
        } else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(BonesItems.AMULET);
        } else if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(BonesItems.CURSED_LANTERN_ITEM);
        } else if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(BonesItems.NECRO_SCEPTER);
            event.accept(BonesItems.HAUNTER_SPEAR);
            event.accept(BonesItems.MINION_SWORD);
        } else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(buildEnchantedBook(BonesEnchantments.LEADER.get()));
            event.accept(buildEnchantedBook(BonesEnchantments.SERENITY.get()));
            event.accept(buildEnchantedBook(BonesEnchantments.SUBALTERN.get()));
            event.accept(BonesItems.SKELETON_SOUL);
            event.accept(BonesItems.SOUL_ORB);
            event.accept(BonesItems.BLADE);
            event.accept(BonesItems.HAUNTER_BLADE);
        } else if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(BonesItems.GRABBER_SPAWN_EGG);
            event.accept(BonesItems.MINION_SPAWN_EGG);
            event.accept(BonesItems.KNIGHT_SKELETON_SPAWN_EGG);
            event.accept(BonesItems.HAUNTER_SPAWN_EGG);
            event.accept(BonesItems.NECROMANCER_SPAWN_EGG);
            event.accept(BonesItems.REAPER_SPAWN_EGG);
        }
    }

    private static ItemStack buildEnchantedBook(Enchantment enchantment) {
        return EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, enchantment.getMaxLevel()));
    }
}

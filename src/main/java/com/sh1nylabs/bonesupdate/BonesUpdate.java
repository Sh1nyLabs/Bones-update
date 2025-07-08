package com.sh1nylabs.bonesupdate;

import com.sh1nylabs.bonesupdate.init.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(BonesUpdate.MODID)
public class BonesUpdate
{
    public static final String MODID = "bonesupdate";
    private static final Logger LOGGER = LogUtils.getLogger();
    //public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    /**
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.bonesupdate")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());*/

    public BonesUpdate(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);

        BonesBlocks.BU_BLOCKS.register(modEventBus);
        BonesBlocks.BU_BLOCK_ENTITIES.register(modEventBus);
        BonesEntities.BU_ENTITIES.register(modEventBus);
        BonesItems.BU_ITEMS.register(modEventBus);
        BonesEnchantments.BU_ENCHANTMENTS.register(modEventBus);
        BonesParticles.BU_PARTICLES.register(modEventBus);
        BonesSounds.BU_SOUNDS.register(modEventBus);
        //CREATIVE_MODE_TABS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, BonesUpdateConfig.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        BonesEntities.registerWaveMembers();
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(BonesItems.GRAVE_BLOCK_ITEM.get());
            event.accept(BonesItems.WEEPING_WILLOW_LEAVES_ITEM.get());
            event.accept(BonesItems.WEEPING_WILLOW_VINES_ITEM.get());
            event.accept(BonesItems.ERODED_FOSSIL_ITEM.get());
            event.accept(BonesItems.GUARDIAN_FOSSIL_ITEM.get());
        } else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(BonesItems.AMULET.get());
        } else if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(BonesItems.CURSED_LANTERN_ITEM.get());
        } else if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(BonesItems.NECRO_SCEPTER.get());
            event.accept(BonesItems.HAUNTER_SPEAR.get());
            event.accept(BonesItems.MINION_SWORD.get());
        } else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(buildEnchantedBook(BonesEnchantments.LEADER.get()));
            event.accept(buildEnchantedBook(BonesEnchantments.SERENITY.get()));
            event.accept(buildEnchantedBook(BonesEnchantments.SUBALTERN.get()));
            event.accept(BonesItems.SKELETON_SOUL.get());
            event.accept(BonesItems.SOUL_ORB.get());
            event.accept(BonesItems.BLADE.get());
            event.accept(BonesItems.HAUNTER_BLADE.get());
        } else if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(BonesItems.GRABBER_SPAWN_EGG.get());
            event.accept(BonesItems.MINION_SPAWN_EGG.get());
            event.accept(BonesItems.KNIGHT_SKELETON_SPAWN_EGG.get());
            event.accept(BonesItems.HAUNTER_SPAWN_EGG.get());
            event.accept(BonesItems.NECROMANCER_SPAWN_EGG.get());
            event.accept(BonesItems.REAPER_SPAWN_EGG.get());
        }
    }


    private static ItemStack buildEnchantedBook(Enchantment enchantment) {
        return EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, enchantment.getMaxLevel()));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}

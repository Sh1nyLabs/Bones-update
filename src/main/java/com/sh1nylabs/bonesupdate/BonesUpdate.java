package com.sh1nylabs.bonesupdate;

import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.function.Supplier;

@Mod(BonesUpdate.MODID)
public class BonesUpdate
{
    /**
     * TODO: before mod publication
     * - minions follow better the necromancer (especially during raids)
     * - add new skeletons decorations (pillager, villager...)
     * - check wether the bogged can become broken
     * - add wither effect to reaper hits
     * - better grabber movement / goals
     * - better reaper loot
     * - increased loot + exp for regular skeletons
     * - upgrade necromancer goals (more graves to place, more minions to spawn)
     * - skeletons souls lootable in lost graves? (1 per grave max)
     */
    public static final String MODID = "bonesupdate";
    public static final Logger LOGGER = LogUtils.getLogger();
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
        //modEventBus.addListener(this::commonSetup);

        BonesRegistry.BU_BLOCKS.register(modEventBus);
        BonesRegistry.BU_BLOCK_ENTITIES.register(modEventBus);
        BonesRegistry.BU_ENTITIES.register(modEventBus);
        BonesRegistry.BU_ITEMS.register(modEventBus);
        BonesRegistry.BU_PARTICLES.register(modEventBus);
        BonesRegistry.BU_SOUNDS.register(modEventBus);
        //CREATIVE_MODE_TABS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, BonesUpdateConfig.SPEC);
    }
/**
    private void commonSetup(final FMLCommonSetupEvent event)
    {
        Raid.RaiderType.create("NECROMANCER", BonesRegistry.NECROMANCER.type(), new int[]{0, 0, 1, 0, 1, 1, 2, 1}); //FIX_VALUES
    }*/

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(BonesRegistry.GRAVE_BLOCK.item());
            event.accept(BonesRegistry.WEEPING_WILLOW_LEAVES.item());
            event.accept(BonesRegistry.WEEPING_WILLOW_VINES.item());
            event.accept(BonesRegistry.ERODED_FOSSIL.item());
            event.accept(BonesRegistry.GUARDIAN_FOSSIL.item());
        } else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(BonesRegistry.AMULET.item());
        } else if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(BonesRegistry.CURSED_LANTERN.item());
        } else if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(BonesRegistry.NECRO_SCEPTER.item());
            event.accept(BonesRegistry.HAUNTER_SPEAR.item());
            event.accept(BonesRegistry.MINION_SWORD.item());
        } else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(BonesRegistry.SKELETON_SOUL.item());
            event.accept(BonesRegistry.SOUL_ORB.item());
            event.accept(BonesRegistry.BLADE.item());
            event.accept(BonesRegistry.HAUNTER_BLADE.item());
        } else if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(BonesRegistry.GRABBER.egg());
            event.accept(BonesRegistry.MINION.egg());
            event.accept(BonesRegistry.KNIGHT_SKELETON.egg());
            event.accept(BonesRegistry.HAUNTER_SKELETON.egg());
            event.accept(BonesRegistry.NECROMANCER.egg());
            event.accept(BonesRegistry.REAPER.egg());
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        //RegistryAccess registryAccess = event.getServer().registryAccess();

        // Do something when the server starts
        BonesUpdate.LOGGER.info("HELLO from server starting");
    }

    public class BonesRaiderTypes {
        public static final EnumProxy<Raid.RaiderType> NECROMANCER = new EnumProxy<>(Raid.RaiderType.class, (Supplier<EntityType<?>>)(BonesRegistry.NECROMANCER::type), new int[]{0, 0, 1, 0, 1, 1, 2, 1});

    }
}

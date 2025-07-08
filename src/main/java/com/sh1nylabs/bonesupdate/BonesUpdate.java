package com.sh1nylabs.bonesupdate;

import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.function.Supplier;

@Mod(BonesUpdate.MODID)
public class BonesUpdate
{
    /**
     * TODO: before mod publication
     * - better grabber movement / goals
     * - better reaper loot
     * - skeletons souls lootable in lost graves? (1 per grave max)
     */
    public static final String MODID = "bonesupdate";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<CreativeModeTab> BONESUPDATE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BONESUPDATE_TAB = BONESUPDATE_TABS.register("bonesupdate_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.bonesupdate"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> BonesRegistry.SKELETON_SOUL.item().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(BonesRegistry.GRAVE_BLOCK.item());
                output.accept(BonesRegistry.WEEPING_WILLOW_LEAVES.item());
                output.accept(BonesRegistry.WEEPING_WILLOW_VINES.item());
                output.accept(BonesRegistry.ERODED_FOSSIL.item());
                output.accept(BonesRegistry.BROKEN_SKELETON_BLOCK.item());
                output.accept(BonesRegistry.GUARDIAN_FOSSIL.item());
                output.accept(BonesRegistry.PILLAGER_SK_HEAD.item());
                output.accept(BonesRegistry.PILLAGER_SK_BODY.item());
                output.accept(BonesRegistry.AMULET.item());
                output.accept(BonesRegistry.CURSED_LANTERN.item());
                output.accept(BonesRegistry.NECRO_SCEPTER.item());
                output.accept(BonesRegistry.HAUNTER_SPEAR.item());
                output.accept(BonesRegistry.MINION_SWORD.item());
                output.accept(BonesRegistry.SKELETON_SOUL.item());
                output.accept(BonesRegistry.SOUL_ORB.item());
                output.accept(BonesRegistry.BLADE.item());
                output.accept(BonesRegistry.HAUNTER_BLADE.item());
                output.accept(BonesRegistry.GRABBER.egg());
                output.accept(BonesRegistry.MINION.egg());
                output.accept(BonesRegistry.BROKEN_SKELETON.egg());
                output.accept(BonesRegistry.KNIGHT_SKELETON.egg());
                output.accept(BonesRegistry.HAUNTER_SKELETON.egg());
                output.accept(BonesRegistry.NECROMANCER.egg());
                output.accept(BonesRegistry.REAPER.egg());
            }).build());

    public BonesUpdate(IEventBus modEventBus, ModContainer modContainer)
    {
        //modEventBus.addListener(this::commonSetup);

        BonesRegistry.BU_BLOCKS.register(modEventBus);
        BonesRegistry.BU_BLOCK_ENTITIES.register(modEventBus);
        BonesRegistry.BU_ENTITIES.register(modEventBus);
        BonesRegistry.BU_ITEMS.register(modEventBus);
        BonesRegistry.BU_PARTICLES.register(modEventBus);
        BonesRegistry.BU_SOUNDS.register(modEventBus);
        BONESUPDATE_TABS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, BonesUpdateConfig.SPEC);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(BonesRegistry.GRAVE_BLOCK.item());
            event.accept(BonesRegistry.WEEPING_WILLOW_LEAVES.item());
            event.accept(BonesRegistry.WEEPING_WILLOW_VINES.item());
            event.accept(BonesRegistry.ERODED_FOSSIL.item());
            event.accept(BonesRegistry.BROKEN_SKELETON_BLOCK.item());
            event.accept(BonesRegistry.GUARDIAN_FOSSIL.item());
            event.accept(BonesRegistry.PILLAGER_SK_HEAD.item());
            event.accept(BonesRegistry.PILLAGER_SK_BODY.item());
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
            event.accept(BonesRegistry.BROKEN_SKELETON.egg());
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

package com.sh1nylabs.bonesupdate;

import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.BrokenSkeleton;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Grabber;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.Minion;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.Difficulty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

@Mod(BonesUpdate.MODID)
public class BonesUpdate
{
    /**
     * TODO: before mod publication
     * - better grabber movement / goals
     * - better reaper loot
     * Mod 1.2.2: what is new:
     * Mecanics: skeletons break less often, and will break only after a certain time left inside a chunk.
     *
     */
    public static final String MODID = "bonesupdate";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<CreativeModeTab> BONESUPDATE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static List<EntityType<? extends AbstractSkeleton>> SQUAD_SKELETONS;
    public static HashMap<EntityType<? extends AbstractSkeleton>, Integer> SKELETONS_PER_SQUAD = new HashMap<>();

    public static final RegistryObject<CreativeModeTab> BONESUPDATE_TAB = BONESUPDATE_TABS.register("bonesupdate_tab", () -> CreativeModeTab.builder()
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

    public BonesUpdate(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        BonesRegistry.BU_BLOCKS.register(modEventBus);
        BonesRegistry.BU_BLOCK_ENTITIES.register(modEventBus);
        BonesRegistry.BU_ENTITIES.register(modEventBus);
        BonesRegistry.BU_ITEMS.register(modEventBus);
        BonesRegistry.BU_PARTICLES.register(modEventBus);
        BonesRegistry.BU_SOUNDS.register(modEventBus);
        BONESUPDATE_TABS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        context.registerConfig(ModConfig.Type.COMMON, BUConfig.SPEC);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
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

    @Nullable
    public static BlockPos randomValidPosForSpawn(ServerLevel level, BlockPos pos, int maxX, int maxY, int maxZ, double inflation, EntityType<? extends LivingEntity> entityType, int nbTries) {
        RandomSource rdmSource = level.getRandom();
        for (int i = 0; i< nbTries; i++) {
            double newX = pos.getX() + (rdmSource.nextDouble() - 0.5) * 2 * maxX;
            double newY = pos.getY() + (rdmSource.nextDouble() - 0.5) * 2 * maxY;
            double newZ = pos.getZ() + (rdmSource.nextDouble() - 0.5) * 2 * maxZ;
            BlockPos randomPos;
            for (int j=-1; j <= 1; j++) { // Tries to get a valid pos by locally varying height
                randomPos = BlockPos.containing(newX, newY + j, newZ);
                if (level.noCollision(AABB.unitCubeFromLowerCorner(new Vec3(newX, newY + j, newZ)).inflate(inflation)) && level.getBlockState(randomPos.below()).isValidSpawn(level, randomPos.below(), entityType)) {
                    return randomPos;
                }
            }
        }
        return null;
    }

    public static boolean skeletonAllowedToBecomeBroken(AbstractSkeleton skeleton, DifficultyInstance difficultyInstance) {
        boolean validEntity = !(skeleton instanceof BrokenSkeleton)  && !(skeleton instanceof Minion) && !(skeleton instanceof Grabber);
        int value = Mth.ceil(100.0F * difficultyInstance.getSpecialMultiplier());
        int value2 = skeleton.getRandom().nextInt(100);
        LOGGER.info("values: {}, {}, {}", value, BUConfig.skeletonBreakDifficultyMin, value2);
        boolean validDifficulty = difficultyInstance.getSpecialMultiplier() > BUConfig.skeletonBreakDifficultyMin && value2 <= value;
        return validEntity && validDifficulty;
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

        // Do something when the server starts
        //BonesUpdate.LOGGER.info("HELLO from server starting");
        if (event.getServer().getLevel(Level.OVERWORLD).getDifficulty() == Difficulty.HARD) {
            SKELETONS_PER_SQUAD.put(EntityType.SKELETON, BUConfig.ENTITY_NUMBER_PER_SQUAD_HARD.get().get(0));
            SKELETONS_PER_SQUAD.put(BonesRegistry.KNIGHT_SKELETON.type(), BUConfig.ENTITY_NUMBER_PER_SQUAD_HARD.get().get(1));
            SKELETONS_PER_SQUAD.put(BonesRegistry.HAUNTER_SKELETON.type(), BUConfig.ENTITY_NUMBER_PER_SQUAD_HARD.get().get(2));
            SKELETONS_PER_SQUAD.put(BonesRegistry.MINION.type(), BUConfig.ENTITY_NUMBER_PER_SQUAD_HARD.get().get(3));
        } else {
            SKELETONS_PER_SQUAD.put(EntityType.SKELETON, BUConfig.ENTITY_NUMBER_PER_SQUAD.get().get(0));
            SKELETONS_PER_SQUAD.put(BonesRegistry.KNIGHT_SKELETON.type(), BUConfig.ENTITY_NUMBER_PER_SQUAD.get().get(1));
            SKELETONS_PER_SQUAD.put(BonesRegistry.HAUNTER_SKELETON.type(), BUConfig.ENTITY_NUMBER_PER_SQUAD.get().get(2));
            SKELETONS_PER_SQUAD.put(BonesRegistry.MINION.type(), BUConfig.ENTITY_NUMBER_PER_SQUAD.get().get(3));
        }

    }

    public class BonesRaiderTypes {
        //public static final EnumProxy<Raid.RaiderType> NECROMANCER = new EnumProxy<>(Raid.RaiderType.class, (Supplier<EntityType<?>>)(BonesRegistry.NECROMANCER::type), new int[]{0, 0, 1, 0, 1, 1, 2, 1});
    }
}

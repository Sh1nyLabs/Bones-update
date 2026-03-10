package com.sh1nylabs.bonesupdate;

import java.util.HashMap;
import java.util.List;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.Difficulty;
import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;

public class BUConfig {
    private static final Builder BUILDER = new Builder();

    private static final IntValue SQUAD_SPAWN_CHANCE = BUILDER
            .comment("Chance of a skeleton squad to spawn with a skeleton (in the form 1/X")
            .defineInRange("squadSpawnChance", 500, 1, Integer.MAX_VALUE);

    private static final ConfigValue<Double> MIN_DIFFICULTY_FOR_SQUAD_TO_SPAWN = BUILDER
            .comment("Minimal difficulty for a squad to spawn")
            .define("squadDifficultyMin", 3.5);
    protected static final ConfigValue<List<? extends Integer>> ENTITY_NUMBER_PER_SQUAD = BUILDER
            .comment("Entities to spawn in a squad, in the following order: Skeleton, Knight Skeleton, Haunter Skeleton, Minion.")
            .defineList("entityNbList", List.of(2, 1, 2, 6), (value)-> {return value instanceof Integer integ && integ >= 0;});
    protected static final ConfigValue<List<? extends Integer>> ENTITY_NUMBER_PER_SQUAD_HARD = BUILDER
            .comment("Entities to spawn in a squad (HARD difficulty), in the following order: Skeleton, Knight Skeleton, Haunter Skeleton, Minion.")
            .defineList("entityNbListHard", List.of(4, 2, 2, 10), (value)-> {return value instanceof Integer integ && integ >= 0;});

    private static final ConfigValue<Double> MIN_DIFFICULTY_FOR_SKELETON_TO_BREAK = BUILDER
            .comment("Minimal difficulty for any skeleton to break")
            .define("skeletonBreakDifficultyMin", 2.5);
    private static final IntValue SKELETON_BREAK_RANDOM_CHANCE_MIN = BUILDER
            .comment("Likelihood (minimum, if difficulty is greater than minimum defined) for a skeleton to become broken")
            .defineInRange("skeletonBreakRandomChanceMin", 50, 1, 100);
    private static final IntValue SKELETON_BREAK_RANDOM_CHANCE_MAX = BUILDER
            .comment("Likelihood (maximum, for difficulty 5.5 and more) for a skeleton to become broken")
            .defineInRange("skeletonBreakRandomChanceMax", 100, 1, 100);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int squadSpawnChance;
    public static double squadDifficultyMin;
    public static double skeletonBreakDifficultyMin;
    public static int skeletonBreakRandomChanceMin;
    public static int skeletonBreakRandomChanceMax;

    public static double grabber_ceil_tilt_angle = 0.8;
    public static double grabber_ceil_heading_angle = 0.9;
    public static double grabber_vision_max_dist = 50.0;

    public static HashMap<EntityType<? extends AbstractSkeleton>, Integer> SKELETONS_PER_SQUAD_HARD = new HashMap<>();
    public static HashMap<EntityType<? extends AbstractSkeleton>, Integer> SKELETONS_PER_SQUAD = new HashMap<>();

    public static void loadConfig()
    {
        squadSpawnChance = SQUAD_SPAWN_CHANCE.get();
        squadDifficultyMin = MIN_DIFFICULTY_FOR_SQUAD_TO_SPAWN.get();
        skeletonBreakDifficultyMin = Math.max(0.0F, Math.min(MIN_DIFFICULTY_FOR_SKELETON_TO_BREAK.get(), 5.5F));
        skeletonBreakRandomChanceMin = Math.max(1, Math.min(SKELETON_BREAK_RANDOM_CHANCE_MIN.get(), 100));
        skeletonBreakRandomChanceMax = Math.max(SKELETON_BREAK_RANDOM_CHANCE_MIN.get(), Math.min(SKELETON_BREAK_RANDOM_CHANCE_MAX.get(), 100));

        SKELETONS_PER_SQUAD_HARD.put(EntityType.SKELETON, BUConfig.ENTITY_NUMBER_PER_SQUAD_HARD.get().get(0));
        SKELETONS_PER_SQUAD_HARD.put(BonesRegistry.KNIGHT_SKELETON.type(), BUConfig.ENTITY_NUMBER_PER_SQUAD_HARD.get().get(1));
        SKELETONS_PER_SQUAD_HARD.put(BonesRegistry.HAUNTER_SKELETON.type(), BUConfig.ENTITY_NUMBER_PER_SQUAD_HARD.get().get(2));
        SKELETONS_PER_SQUAD_HARD.put(BonesRegistry.MINION.type(), BUConfig.ENTITY_NUMBER_PER_SQUAD_HARD.get().get(3));

        SKELETONS_PER_SQUAD.put(EntityType.SKELETON, BUConfig.ENTITY_NUMBER_PER_SQUAD.get().get(0));
        SKELETONS_PER_SQUAD.put(BonesRegistry.KNIGHT_SKELETON.type(), BUConfig.ENTITY_NUMBER_PER_SQUAD.get().get(1));
        SKELETONS_PER_SQUAD.put(BonesRegistry.HAUNTER_SKELETON.type(), BUConfig.ENTITY_NUMBER_PER_SQUAD.get().get(2));
        SKELETONS_PER_SQUAD.put(BonesRegistry.MINION.type(), BUConfig.ENTITY_NUMBER_PER_SQUAD.get().get(3));
    }

    public static int getSkeletonNbPerSquad(Difficulty difficulty, EntityType<? extends AbstractSkeleton> entityType)
    {
        return difficulty == Difficulty.HARD ? SKELETONS_PER_SQUAD_HARD.get(entityType) : SKELETONS_PER_SQUAD.get(entityType);
    }

}

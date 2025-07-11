package com.sh1nylabs.bonesupdate;

import java.util.List;

import net.neoforged.neoforge.common.ModConfigSpec;

public class BUConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue SQUAD_SPAWN_CHANCE = BUILDER
            .comment("Chance of a skeleton squad to spawn with a skeleton (in the form 1/X")
            .defineInRange("squadSpawnChance", 500, 1, Integer.MAX_VALUE);

    private static final ModConfigSpec.ConfigValue<Float> MIN_DIFFICULTY_FOR_SQUAD_TO_SPAWN = BUILDER
            .comment("Minimal difficulty for a squad to spawn")
            .define("squadDifficultyMin", 3.5F);
    protected static final ModConfigSpec.ConfigValue<List<? extends Integer>> ENTITY_NUMBER_PER_SQUAD = BUILDER
            .comment("Entities to spawn in a squad, in the following order: Skeleton, Knight Skeleton, Haunter Skeleton, Minion.")
            .defineList("entityNbList", List.of(2, 1, 2, 6), (value)-> {return value instanceof Integer integ && integ >= 0;});
    protected static final ModConfigSpec.ConfigValue<List<? extends Integer>> ENTITY_NUMBER_PER_SQUAD_HARD = BUILDER
            .comment("Entities to spawn in a squad (HARD difficulty), in the following order: Skeleton, Knight Skeleton, Haunter Skeleton, Minion.")
            .defineList("entityNbListHard", List.of(4, 2, 2, 10), (value)-> {return value instanceof Integer integ && integ >= 0;});

    private static final ModConfigSpec.ConfigValue<Float> MIN_DIFFICULTY_FOR_SKELETON_TO_BREAK = BUILDER
            .comment("Minimal difficulty for any skeleton to break")
            .define("skeletonBreakDifficultyMin", 2.5F);
    private static final ModConfigSpec.IntValue SKELETON_BREAK_RANDOM_CHANCE_MIN = BUILDER
            .comment("Likelihood (minimum, if difficulty is greater than minimum defined) for a skeleton to become broken")
            .defineInRange("skeletonBreakRandomChanceMin", 50, 1, 100);
    private static final ModConfigSpec.IntValue SKELETON_BREAK_RANDOM_CHANCE_MAX = BUILDER
            .comment("Likelihood (maximum, for difficulty 5.5 and more) for a skeleton to become broken")
            .defineInRange("skeletonBreakRandomChanceMax", 100, 1, 100);


    static final ModConfigSpec SPEC = BUILDER.build();

    public static int squadSpawnChance;
    public static float squadDifficultyMin;
    public static double skeletonBreakDifficultyMin;
    public static int skeletonBreakRandomChanceMin;
    public static int skeletonBreakRandomChanceMax;

    public static double grabber_ceil_tilt_angle = 0.8;
    public static double grabber_ceil_heading_angle = 0.9;
    public static double grabber_vision_max_dist = 50.0;

    static void loadConfig()
    {
        squadSpawnChance = SQUAD_SPAWN_CHANCE.get();
        squadDifficultyMin = MIN_DIFFICULTY_FOR_SQUAD_TO_SPAWN.get();
        skeletonBreakDifficultyMin = Math.max(0.0F, Math.min(MIN_DIFFICULTY_FOR_SKELETON_TO_BREAK.get(), 5.5F));
        skeletonBreakRandomChanceMin = Math.max(1, Math.min(SKELETON_BREAK_RANDOM_CHANCE_MIN.get(), 100));
        skeletonBreakRandomChanceMax = Math.max(SKELETON_BREAK_RANDOM_CHANCE_MIN.get(), Math.min(SKELETON_BREAK_RANDOM_CHANCE_MAX.get(), 100));
    }
}

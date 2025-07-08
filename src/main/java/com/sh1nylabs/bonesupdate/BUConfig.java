package com.sh1nylabs.bonesupdate;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.sh1nylabs.bonesupdate.registerer.BonesRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = BonesUpdate.MODID, bus = EventBusSubscriber.Bus.MOD)
public class BUConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue SQUAD_SPAWN_CHANCE = BUILDER
            .comment("Chance of a skeleton squad to spawn with a skeleton")
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

    // a list of strings that are treated as resource locations for items. TODO: change it for creative tabs
    private static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
            .comment("A list of items to log on common setup.")
            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), BUConfig::validateItemName);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static int squadSpawnChance;
    public static float squadDifficultyMin;


    public static Set<Item> items;

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        BonesUpdate.LOGGER.info("config loading done");
        squadSpawnChance = SQUAD_SPAWN_CHANCE.get();
        squadDifficultyMin = MIN_DIFFICULTY_FOR_SQUAD_TO_SPAWN.get();
        // convert the list of strings into a set of items
        items = ITEM_STRINGS.get().stream()
                .map(itemName -> BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemName)))
                .collect(Collectors.toSet());
    }
}

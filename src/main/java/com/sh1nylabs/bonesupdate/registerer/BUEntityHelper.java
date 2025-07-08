package com.sh1nylabs.bonesupdate.registerer;

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.BrokenSkeleton;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.world.entity.MobCategory;

import static com.sh1nylabs.bonesupdate.registerer.BonesRegistry.*;

public class BUEntityHelper<T extends Mob> {
    private final DeferredHolder<EntityType<?>, EntityType<T>> entityType;
    private final DeferredHolder<Item, SpawnEggItem> spawnEgg;

    private DeferredHolder<SoundEvent, SoundEvent> step;
    private DeferredHolder<SoundEvent, SoundEvent> hurt;
    private DeferredHolder<SoundEvent, SoundEvent> death;
    private DeferredHolder<SoundEvent, SoundEvent> ambient;

    public BUEntityHelper(String name, int backgroundColor, int highlightColor, EntityType.Builder<T> builder) {
        entityType = BU_ENTITIES.register(name, () -> builder.build(BonesUpdate.MODID+":"+name));
        spawnEgg = BU_ITEMS.register(name + "_spawn_egg",
                ()-> new DeferredSpawnEggItem(entityType, FastColor.ARGB32.opaque(backgroundColor),FastColor.ARGB32.opaque(highlightColor),new Item.Properties()));
        step = BU_SOUNDS.register(name + "_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(BonesUpdate.MODID, name + "_step")));
        hurt = BU_SOUNDS.register(name + "_hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(BonesUpdate.MODID, name + "_hurt")));
        death = BU_SOUNDS.register(name + "_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(BonesUpdate.MODID, name + "_death")));
        ambient = BU_SOUNDS.register(name + "_ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(BonesUpdate.MODID, name + "_ambient")));
    }

    public EntityType<T> type() {
        return entityType.get();
    }

    public SpawnEggItem egg() { return spawnEgg.get();}

    public SoundEvent step() {return step.get();}
    public SoundEvent hurt() {return hurt.get();}
    public SoundEvent death() {return death.get();}
    public SoundEvent ambient() {return ambient.get();}
}

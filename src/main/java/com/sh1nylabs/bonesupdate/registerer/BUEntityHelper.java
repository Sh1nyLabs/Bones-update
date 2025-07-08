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
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.RegistryObject;

import static com.sh1nylabs.bonesupdate.registerer.BonesRegistry.*;

public class BUEntityHelper<T extends Mob> {
    private final RegistryObject<EntityType<T>> entityType;
    private final RegistryObject<ForgeSpawnEggItem> spawnEgg;

    private RegistryObject<SoundEvent> step;
    private RegistryObject<SoundEvent> hurt;
    private RegistryObject<SoundEvent> death;
    private RegistryObject<SoundEvent> ambient;

    public BUEntityHelper(String name, int backgroundColor, int highlightColor, EntityType.Builder<T> builder) {
        entityType = BU_ENTITIES.register(name, () -> builder.build(BonesUpdate.MODID+":"+name));
        spawnEgg = BU_ITEMS.register(name + "_spawn_egg",
                ()-> new ForgeSpawnEggItem(entityType, FastColor.ARGB32.opaque(backgroundColor),FastColor.ARGB32.opaque(highlightColor),new Item.Properties()));
        step = BU_SOUNDS.register(name + "_step", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, name + "_step")));
        hurt = BU_SOUNDS.register(name + "_hurt", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, name + "_hurt")));
        death = BU_SOUNDS.register(name + "_death", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, name + "_death")));
        ambient = BU_SOUNDS.register(name + "_ambient", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, name + "_ambient")));
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

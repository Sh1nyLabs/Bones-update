package com.sh1nylabs.bonesupdate.registerer;

import com.sh1nylabs.bonesupdate.BonesUpdate;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;

import static com.sh1nylabs.bonesupdate.registerer.BonesRegistry.*;

public class BUEntityHelper<T extends Mob>
{
    private final DeferredHolder<EntityType<?>, EntityType<T>> entityType;
    private final String entityName;
    private final ModelLayerLocation modelLocation;
    private final DeferredHolder<Item, SpawnEggItem> spawnEgg;

    private DeferredHolder<SoundEvent, SoundEvent> step;
    private DeferredHolder<SoundEvent, SoundEvent> hurt;
    private DeferredHolder<SoundEvent, SoundEvent> death;
    private DeferredHolder<SoundEvent, SoundEvent> ambient;

    private static String eggName(String name) {return name + "_spawn_egg";}
    public BUEntityHelper(String name, int backgroundColor, int highlightColor, EntityType.Builder<T> builder) {
        entityType = BU_ENTITIES.register(name, () -> builder.build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, name))));
        entityName = name;
        modelLocation = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, entityName + "model"), "main");
        spawnEgg = BU_ITEMS.register(eggName(name),
                ()-> new SpawnEggItem(entityType.get(), new Item.Properties().setId(ResourceKey.create(BU_ITEMS.getRegistryKey(),ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, eggName(name))))));
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

    public final ResourceLocation textureLocation() {return ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID,"textures/entity/" + entityName + ".png");}
    public final ModelLayerLocation modelLayerLocation() {return modelLocation;}
}

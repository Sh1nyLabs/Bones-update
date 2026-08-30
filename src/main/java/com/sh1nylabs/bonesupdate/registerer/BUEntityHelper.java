package com.sh1nylabs.bonesupdate.registerer;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.registries.RegistryObject;

import static com.sh1nylabs.bonesupdate.registerer.BonesRegistry.*;

public class BUEntityHelper<T extends Mob>
{
    private final RegistryObject<EntityType<T>> entityType;
    private final String entityName;
    private final RegistryObject<SpawnEggItem> spawnEgg;

    private RegistryObject<SoundEvent> step;
    private RegistryObject<SoundEvent> hurt;
    private RegistryObject<SoundEvent> death;
    private RegistryObject<SoundEvent> ambient;

    private static String eggName(String name) {return name + "_spawn_egg";}
    public BUEntityHelper(String name, EntityType.Builder<T> builder) {
        entityType = BU_ENTITIES.register(name, () -> builder.build(ResourceKey.create(Registries.ENTITY_TYPE, BUModIdentifier.fromModNamespace(name))));
        entityName = name;
        spawnEgg = BU_ITEMS.register(eggName(name),
                ()-> new SpawnEggItem(new Item.Properties().spawnEgg(entityType.get()).setId(ResourceKey.create(BU_ITEMS.getRegistryKey(),BUModIdentifier.fromModNamespace(eggName(name))))));
        step = BU_SOUNDS.register(name + "_step", () -> SoundEvent.createVariableRangeEvent(BUModIdentifier.fromModNamespace(name + "_step")));
        hurt = BU_SOUNDS.register(name + "_hurt", () -> SoundEvent.createVariableRangeEvent(BUModIdentifier.fromModNamespace(name + "_hurt")));
        death = BU_SOUNDS.register(name + "_death", () -> SoundEvent.createVariableRangeEvent(BUModIdentifier.fromModNamespace(name + "_death")));
        ambient = BU_SOUNDS.register(name + "_ambient", () -> SoundEvent.createVariableRangeEvent(BUModIdentifier.fromModNamespace(name + "_ambient")));
    }

    public EntityType<T> type() {
        return entityType.get();
    }
    public String name() { return entityName; }

    public SpawnEggItem egg() { return spawnEgg.get();}

    public SoundEvent step() {return step.get();}
    public SoundEvent hurt() {return hurt.get();}
    public SoundEvent death() {return death.get();}
    public SoundEvent ambient() {return ambient.get();}

    public final Identifier textureLocation() {return BUModIdentifier.fromModNamespace("textures/entity/" + entityName + ".png");}
}

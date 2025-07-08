package com.sh1nylabs.bonesupdate.init;

import com.sh1nylabs.bonesupdate.BonesUpdate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BonesSounds {
    //TODO: Sounds to add: reaper, necromancer, minion -> walk, hurt, death. probably some hit sounds also (ex reaper?)
    //TODO: add reviving sound for skeletons. Add sound for knight hitting?
    public static final DeferredRegister<SoundEvent> BU_SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, BonesUpdate.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> KNIGHT_SKELETON_STEP = registerSoundEvent("knight_skeleton_step");
    public static final DeferredHolder<SoundEvent, SoundEvent> KNIGHT_SKELETON_HURT = registerSoundEvent("knight_skeleton_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> KNIGHT_SKELETON_DEATH = registerSoundEvent("knight_skeleton_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> KNIGHT_SKELETON_AMBIENT = registerSoundEvent("knight_skeleton_ambient");

    public static final DeferredHolder<SoundEvent, SoundEvent> HAUNTER_SKELETON_STEP = registerSoundEvent("haunter_skeleton_step");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAUNTER_SKELETON_HURT = registerSoundEvent("haunter_skeleton_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAUNTER_SKELETON_DEATH = registerSoundEvent("haunter_skeleton_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> HAUNTER_SKELETON_AMBIENT = registerSoundEvent("haunter_skeleton_ambient");

    public static final DeferredHolder<SoundEvent, SoundEvent> BROKEN_SKELETON_REVIVES = registerSoundEvent("broken_skeleton_revives");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
        return BU_SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(BonesUpdate.MODID, name)));

    }
}

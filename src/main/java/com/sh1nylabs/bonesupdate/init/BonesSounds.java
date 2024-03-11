package com.sh1nylabs.bonesupdate.init;

import com.sh1nylabs.bonesupdate.BonesUpdate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BonesSounds {
    //TODO: Sounds to add: reaper, necromancer, minion -> walk, hurt, death. probably some hit sounds also (ex reaper?)
    //TODO: add reviving sound for skeletons. Add sound for knight hitting?
    public static final DeferredRegister<SoundEvent> BU_SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BonesUpdate.MODID);

    public static final RegistryObject<SoundEvent> KNIGHT_SKELETON_STEP = registerSoundEvent("knight_skeleton_step");
    public static final RegistryObject<SoundEvent> KNIGHT_SKELETON_HURT = registerSoundEvent("knight_skeleton_hurt");
    public static final RegistryObject<SoundEvent> KNIGHT_SKELETON_DEATH = registerSoundEvent("knight_skeleton_death");
    public static final RegistryObject<SoundEvent> KNIGHT_SKELETON_AMBIENT = registerSoundEvent("knight_skeleton_ambient");

    public static final RegistryObject<SoundEvent> HAUNTER_SKELETON_STEP = registerSoundEvent("haunter_skeleton_step");
    public static final RegistryObject<SoundEvent> HAUNTER_SKELETON_HURT = registerSoundEvent("haunter_skeleton_hurt");
    public static final RegistryObject<SoundEvent> HAUNTER_SKELETON_DEATH = registerSoundEvent("haunter_skeleton_death");
    public static final RegistryObject<SoundEvent> HAUNTER_SKELETON_AMBIENT = registerSoundEvent("haunter_skeleton_ambient");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return BU_SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(BonesUpdate.MODID, name)));

    }
}

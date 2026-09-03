package com.sh1nylabs.bonesupdate.registerer;

import com.sh1nylabs.bonesupdate.common.blocks.*;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.*;
import com.sh1nylabs.bonesupdate.common.entities.necromancy.Necromancer;
import com.sh1nylabs.bonesupdate.common.entities.necromancy.Reaper;
import com.sh1nylabs.bonesupdate.common.items.AmuletItem;
import com.sh1nylabs.bonesupdate.common.items.NecroScepterItem;
import com.sh1nylabs.bonesupdate.common.items.SoulItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.EitherHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwingAnimationType;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;


public class BonesRegistry {


    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Registries %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% //

    public static final DeferredRegister<Block> BU_BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, BUModIdentifier.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BU_BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, BUModIdentifier.MODID);
    public static final DeferredRegister<EntityType<?>> BU_ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, BUModIdentifier.MODID);
    public static final DeferredRegister<Item> BU_ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, BUModIdentifier.MODID);
    public static final DeferredRegister<ParticleType<?>> BU_PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, BUModIdentifier.MODID);
    public static final DeferredRegister<SoundEvent> BU_SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, BUModIdentifier.MODID);

    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Blocks %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% //

    public static final BUBlockHelper<GraveBlockEntity> GRAVE_BLOCK = new BUBlockHelper<>("grave", GraveBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0F), GraveBlockEntity::new);
    public static final BUBlockHelper<?> CURSED_LANTERN = new BUBlockHelper<>("cursed_lantern", CursedLanternBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).noOcclusion().lightLevel((light) -> {return 10;}));
    public static final BUBlockHelper<?> WEEPING_WILLOW_SMALL_VINES = new BUBlockHelper<>("weeping_willow_small_vines", WeepingWillowSmallVinesBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.VINE).randomTicks().noCollision().instabreak());
    public static final BUBlockHelper<?> WEEPING_WILLOW_VINES = new BUBlockHelper<>("weeping_willow_vines", WeepingWillowVinesBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.VINE).noCollision().instabreak());
    public static final BUBlockHelper<?> WEEPING_WILLOW_LEAVES = new BUBlockHelper<>("weeping_willow_leaves", properties -> new TintedParticleLeavesBlock(0.01F, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).randomTicks().noOcclusion());
    public static final BUBlockHelper<?> ERODED_FOSSIL = new BUBlockHelper<>("eroded_fossil", (properties) -> new BonesOrientableBlock(properties,
                Block.box(4.0D, 0.0D, 4.0D, 12.0D, 4.0D, 15.0D),
                Block.box(4.0D, 0.0D, 1.0D, 12.0D, 4.0D, 12.0D),
                Block.box(1.0D, 0.0D, 4.0D, 12.0D, 4.0D, 12.0D),
                Block.box(4.0D, 0.0D, 4.0D, 15.0D, 4.0D, 12.0D)),
            BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SKELETON).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.STONE).noOcclusion());
    public static final BUBlockHelper<?> BROKEN_SKELETON_BLOCK = new BUBlockHelper<>("broken_skeleton", (properties) -> new BonesOrientableBlock(properties,
                Block.box(1.0D, 0.0D, 2.0D, 16.0D, 8.0D, 15.0D),
                Block.box(0.0D, 0.0D, 1.0D, 15.0D, 8.0D, 14.0D),
                Block.box(2.0D, 0.0D, 1.0D, 15.0D, 8.0D, 16.0D),
                Block.box(1.0D, 0.0D, 0.0D, 14.0D, 8.0D, 15.0D)),
            BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SKELETON).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.STONE).noOcclusion());
    public static final BUBlockHelper<?> GUARDIAN_FOSSIL = new BUBlockHelper<>("guardian_fossil", GuardianFossilBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SKELETON).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.STONE).noOcclusion());
    public static final BUBlockHelper<?> PILLAGER_SK_HEAD = new BUBlockHelper<>("pillager_skeleton_head", (properties) -> new BonesOrientableBlock(properties,
                Block.box(2.0D, 0.0D, 5.0D, 13.0D, 8.0D, 16.0D),
                Block.box(3.0D, 0.0D, 0.0D, 14.0D, 8.0D, 11.0D),
                Block.box(0.0D, 0.0D, 2.0D, 11.0D, 8.0D, 13.0D),
                Block.box(5.0D, 0.0D, 3.0D, 16.0D, 8.0D, 14.0D)),
            BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SKELETON).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.STONE).noOcclusion());
    public static final BUBlockHelper<?> PILLAGER_SK_BODY = new BUBlockHelper<>("pillager_skeleton_body", (properties) -> new BonesOrientableBlock(properties,
                Block.box(6.0D, 0.0D, 0.0D, 15.0D, 6.0D, 11.0D),
                Block.box(1.0D, 0.0D, 5.0D, 10.0D, 6.0D, 16.0D),
                Block.box(5.0D, 0.0D, 6.0D, 16.0D, 6.0D, 15.0D),
                Block.box(0.0D, 0.0D, 1.0D, 11.0D, 6.0D, 10.0D)),
            BlockBehaviour.Properties.ofFullCopy(Blocks.BONE_BLOCK).noOcclusion());


    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Entities %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% //

    public static final BUEntityHelper<BrokenSkeleton> BROKEN_SKELETON = new BUEntityHelper<>("broken_skeleton", EntityType.Builder.of(BrokenSkeleton::new,MobCategory.MONSTER).sized(0.6F,0.5F));
    public static final BUEntityHelper<BrokenWitherSkeleton> BROKEN_WITHER_SKELETON = new BUEntityHelper<>("broken_wither_skeleton", EntityType.Builder.of(BrokenWitherSkeleton::new,MobCategory.MONSTER).sized(0.6F,0.5F));
    public static final BUEntityHelper<BrokenParched> BROKEN_PARCHED = new BUEntityHelper<>("broken_parched", EntityType.Builder.of(BrokenParched::new,MobCategory.MONSTER).sized(0.6F,0.5F));
    public static final BUEntityHelper<HaunterSkeleton> HAUNTER_SKELETON = new BUEntityHelper<>("haunter_skeleton", EntityType.Builder.of(HaunterSkeleton::new,MobCategory.MONSTER).sized(0.6F, 1.99F));
    public static final BUEntityHelper<KnightSkeleton> KNIGHT_SKELETON = new BUEntityHelper<>("knight_skeleton", EntityType.Builder.of(KnightSkeleton::new,MobCategory.MONSTER).sized(0.6F, 1.99F));
    public static final BUEntityHelper<Grabber> GRABBER = new BUEntityHelper<>("grabber", EntityType.Builder.of(Grabber::new,MobCategory.MONSTER).sized(0.7F ,1.6F).eyeHeight(1.5F));
    public static final BUEntityHelper<Minion> MINION = new BUEntityHelper<>("minion", EntityType.Builder.of(Minion::new,MobCategory.MONSTER).sized(0.35F, 0.85F).eyeHeight(0.85F * 0.85F));
    public static final BUEntityHelper<Necromancer> NECROMANCER = new BUEntityHelper<>("necromancer", EntityType.Builder.of( Necromancer::new,MobCategory.MONSTER));
    public static final BUEntityHelper<Reaper> REAPER = new BUEntityHelper<>("reaper", EntityType.Builder.of(Reaper::new,MobCategory.MONSTER).sized(0.6F, 1.4F));


    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Enchantments %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% //

    public static final BUEnchantmentHelper SERENITY = new BUEnchantmentHelper( "serenity");
    public static final BUEnchantmentHelper SUBALTERN = new BUEnchantmentHelper( "subaltern");
    public static final BUEnchantmentHelper LEADER = new BUEnchantmentHelper( "leader");


    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% ItemTags %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% //

    public static final TagKey<Item> GRABBER_STEALS = ItemTags.create(BUModIdentifier.fromModNamespace("grabber_steals"));
    public static final TagKey<Item> NECROMANCY = ItemTags.create(BUModIdentifier.fromModNamespace("necromancy"));
    public static final TagKey<Item> SKELETON_QUEST = ItemTags.create(BUModIdentifier.fromModNamespace("skeleton_quest"));
    public static final TagKey<Enchantment> SUBALTERN_INCOMPATIBLE = TagKey.create(Registries.ENCHANTMENT,BUModIdentifier.fromModNamespace("exclusive_set/subaltern_incompatible"));

    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Items %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% //
    public static final BUItemHelper SKELETON_SOUL = new BUItemHelper("skeleton_soul", SoulItem::new, new Item.Properties());
    public static final BUItemHelper SOUL_ORB = new BUItemHelper("soul_orb", Item::new,
            new Item.Properties().stacksTo(16));
    public static final BUItemHelper NECRO_SCEPTER = new BUItemHelper("necromancer_scepter", NecroScepterItem::new,
            new Item.Properties().durability(50));
    public static final BUItemHelper AMULET = new BUItemHelper("amulet", AmuletItem::new,
            new Item.Properties().durability(100));
    public static final BUItemHelper MINION_SWORD = new BUItemHelper("minion_sword", Item::new,
            new Item.Properties().durability(100));
    public static final BUItemHelper BLADE = new BUItemHelper("blade", Item::new,
            new Item.Properties().stacksTo(64));
    public static final BUItemHelper HAUNTER_BLADE = new BUItemHelper("haunter_blade", Item::new,
            new Item.Properties().stacksTo(64));

    public static Item.Properties haunterSpear(
            float swingDuration,
            float damageMultiplier,
            float delay,
            float dismountMaxDuration,
            float dismountMinSpeed,
            float knockbackMaxDuration,
            float knockbackMinSpeed,
            float damageMaxDuration,
            float damageMinSpeed
    ) {
        return new Item.Properties().durability(ToolMaterial.IRON.durability())
                .component(DataComponents.DAMAGE_TYPE, new EitherHolder<>(DamageTypes.SPEAR))
                .component(
                        DataComponents.KINETIC_WEAPON,
                        new KineticWeapon(
                                10,
                                (int)(delay * 20.0F),
                                KineticWeapon.Condition.ofAttackerSpeed((int)(dismountMaxDuration * 20.0F), dismountMinSpeed),
                                KineticWeapon.Condition.ofAttackerSpeed((int)(knockbackMaxDuration * 20.0F), knockbackMinSpeed),
                                KineticWeapon.Condition.ofRelativeSpeed((int)(damageMaxDuration * 20.0F), damageMinSpeed),
                                0.38F,
                                damageMultiplier,
                                Optional.of(SoundEvents.SPEAR_USE),
                                Optional.of(SoundEvents.SPEAR_HIT)
                        )
                )
                .component(DataComponents.ATTACK_RANGE, new AttackRange(2.0F, 4.5F, 2.0F, 6.5F, 0.125F, 0.5F))
                .component(DataComponents.MINIMUM_ATTACK_CHARGE, 1.0F)
                .component(DataComponents.SWING_ANIMATION, new SwingAnimation(SwingAnimationType.STAB, (int)(swingDuration * 20.0F)))
                .attributes(
                        ItemAttributeModifiers.builder()
                                .add(
                                        Attributes.ATTACK_DAMAGE,
                                        new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, 3.0D, AttributeModifier.Operation.ADD_VALUE),
                                        EquipmentSlotGroup.MAINHAND
                                )
                                .add(
                                        Attributes.ATTACK_SPEED,
                                        new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, 1.0F / swingDuration - 4.0, AttributeModifier.Operation.ADD_VALUE),
                                        EquipmentSlotGroup.MAINHAND
                                )
                                .add(
                                        Attributes.ATTACK_KNOCKBACK,
                                        new AttributeModifier(BUModIdentifier.fromModNamespace("base_knockback"),3.0D, AttributeModifier.Operation.ADD_VALUE),
                                        EquipmentSlotGroup.MAINHAND)
                                .build()
                )
                .component(DataComponents.USE_EFFECTS, new UseEffects(true, false, 1.0F))
                .component(DataComponents.WEAPON, new Weapon(1));
    }
    public static final BUItemHelper HAUNTER_SPEAR = new BUItemHelper("haunter_spear", Item::new,
            haunterSpear(0.95F, 0.95F, 0.6F, 2.5F, 8.0F, 6.75F, 5.1F, 11.25F, 4.6F));

    public static final BUItemHelper RED_BONE = new BUItemHelper("red_bone", Item::new,
            new Item.Properties());

    public static final BUItemHelper GRABBER_HOLD_TOTEM = new BUItemHelper("grabber_hold_totem", Item::new,
            new Item.Properties());


    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Particles %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% //

    public static final BUParticleHelper PURPLE_BAR = new BUParticleHelper("purple_bar");
    public static final BUParticleHelper PURPLE_SOUL = new BUParticleHelper("purple_soul");


    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Sounds %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% //

    public static final DeferredHolder<SoundEvent, SoundEvent> BROKEN_SKELETON_REVIVES =
            BU_SOUNDS.register("broken_skeleton_revives",
                    () -> SoundEvent.createVariableRangeEvent(BUModIdentifier.fromModNamespace("broken_skeleton_revives")));


    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% EntityTags %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% //

    public static class BonesEntityTagsProvider extends EntityTypeTagsProvider {

        public BonesEntityTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
            super(output, provider);
        }

        public void addTags(HolderLookup.Provider provider) {
            this.tag(EntityTypeTags.ILLAGER).add(NECROMANCER.type());
            this.tag(EntityTypeTags.SKELETONS)
                    .add(REAPER.type())
                    .add(MINION.type())
                    .add(GRABBER.type())
                    .add(HAUNTER_SKELETON.type())
                    .add(KNIGHT_SKELETON.type());
        }
    }
}

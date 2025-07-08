package com.sh1nylabs.bonesupdate.registerer;

import com.sh1nylabs.bonesupdate.BonesUpdate;
import com.sh1nylabs.bonesupdate.common.blocks.*;
import com.sh1nylabs.bonesupdate.common.entities.custom_skeletons.*;
import com.sh1nylabs.bonesupdate.common.entities.necromancy.Necromancer;
import com.sh1nylabs.bonesupdate.common.entities.necromancy.Reaper;
import com.sh1nylabs.bonesupdate.common.items.AmuletItem;
import com.sh1nylabs.bonesupdate.common.items.HaunterSpearItem;
import com.sh1nylabs.bonesupdate.common.items.NecroScepterItem;
import com.sh1nylabs.bonesupdate.common.items.SoulItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.concurrent.CompletableFuture;


public class BonesRegistry {


    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Registries %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% //

    public static final DeferredRegister<Block> BU_BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, BonesUpdate.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BU_BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, BonesUpdate.MODID);
    public static final DeferredRegister<EntityType<?>> BU_ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, BonesUpdate.MODID);
    public static final DeferredRegister<Item> BU_ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, BonesUpdate.MODID);
    public static final DeferredRegister<ParticleType<?>> BU_PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, BonesUpdate.MODID);
    public static final DeferredRegister<SoundEvent> BU_SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, BonesUpdate.MODID);


    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Blocks %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% //

    public static final BUBlockHelper<GraveBlockEntity> GRAVE_BLOCK = new BUBlockHelper<>("grave", () -> new GraveBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).requiresCorrectToolForDrops().strength(3.0F)), GraveBlockEntity::new);
    public static final BUBlockHelper<?> CURSED_LANTERN = new BUBlockHelper<>("cursed_lantern", () -> new CursedLanternBlock((BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).noOcclusion().lightLevel((light) -> {return 10;}))));
    public static final BUBlockHelper<?> WEEPING_WILLOW_SMALL_VINES = new BUBlockHelper<>("weeping_willow_small_vines",() -> new WeepingWillowSmallVinesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.VINE).randomTicks().noCollission().instabreak()));
    public static final BUBlockHelper<?> WEEPING_WILLOW_VINES = new BUBlockHelper<>("weeping_willow_vines",() -> new WeepingWillowVinesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.VINE).noCollission().instabreak()));
    public static final BUBlockHelper<?> WEEPING_WILLOW_LEAVES = new BUBlockHelper<>("weeping_willow_leaves",() -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).randomTicks().noOcclusion()));
    public static final BUBlockHelper<?> ERODED_FOSSIL = new BUBlockHelper<>("eroded_fossil", () -> new BonesOrientableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SKELETON).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.STONE),
            Block.box(4.0D, 0.0D, 4.0D, 12.0D, 4.0D, 15.0D),
            Block.box(4.0D, 0.0D, 1.0D, 12.0D, 4.0D, 12.0D),
            Block.box(1.0D, 0.0D, 4.0D, 12.0D, 4.0D, 12.0D),
            Block.box(4.0D, 0.0D, 4.0D, 15.0D, 4.0D, 12.0D)));
    public static final BUBlockHelper<?> GUARDIAN_FOSSIL = new BUBlockHelper<>("guardian_fossil", () -> new GuardianFossilBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SKELETON).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.STONE).noOcclusion()));
    public static final BUBlockHelper<?> PILLAGER_SK_HEAD = new BUBlockHelper<>("pillager_skeleton_head", () -> new BonesOrientableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.SKELETON).requiresCorrectToolForDrops().strength(2.0F).sound(SoundType.STONE).noOcclusion(),
            Block.box(2.0D, 0.0D, 5.0D, 13.0D, 8.0D, 16.0D),
            Block.box(3.0D, 0.0D, 0.0D, 14.0D, 8.0D, 11.0D),
            Block.box(0.0D, 0.0D, 2.0D, 11.0D, 8.0D, 13.0D),
            Block.box(5.0D, 0.0D, 3.0D, 16.0D, 8.0D, 14.0D)));
    public static final BUBlockHelper<?> PILLAGER_SK_BODY = new BUBlockHelper<>("pillager_skeleton_body", () -> new BonesOrientableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BONE_BLOCK).noOcclusion(),
            Block.box(6.0D, 0.0D, 0.0D, 15.0D, 6.0D, 11.0D),
            Block.box(1.0D, 0.0D, 5.0D, 10.0D, 6.0D, 16.0D),
            Block.box(5.0D, 0.0D, 6.0D, 16.0D, 6.0D, 15.0D),
            Block.box(0.0D, 0.0D, 1.0D, 11.0D, 6.0D, 10.0D)));


    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Entities %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% //

    public static final BUEntityHelper<BrokenSkeleton> BROKEN_SKELETON = new BUEntityHelper<>("broken_skeleton", 0xC1C1C1, 0x5642B3, EntityType.Builder.of(BrokenSkeleton::new,MobCategory.MONSTER).sized(0.6F,0.5F));
    public static final BUEntityHelper<HaunterSkeleton> HAUNTER_SKELETON = new BUEntityHelper<>("haunter_skeleton", 0xC1C1C1, 0x5642B3, EntityType.Builder.of(HaunterSkeleton::new,MobCategory.MONSTER).sized(0.6F, 1.99F));
    public static final BUEntityHelper<KnightSkeleton> KNIGHT_SKELETON = new BUEntityHelper<>("knight_skeleton", 0x2E2E2E,0x5642B3, EntityType.Builder.of(KnightSkeleton::new,MobCategory.MONSTER).sized(0.6F, 1.99F));
    public static final BUEntityHelper<Grabber> GRABBER = new BUEntityHelper<>("grabber", 0xC1C1C1,0xA46826, EntityType.Builder.of(Grabber::new,MobCategory.MONSTER).sized(0.7F ,1.6F).eyeHeight(1.5F));
    public static final BUEntityHelper<Minion> MINION = new BUEntityHelper<>("minion", 0xC1C1C1,0xC5C5C5, EntityType.Builder.of(Minion::new,MobCategory.MONSTER).sized(0.35F, 0.85F).eyeHeight(0.85F * 0.85F));
    public static final BUEntityHelper<Necromancer> NECROMANCER = new BUEntityHelper<>("necromancer", 0x9B7A63,0xFFFFFF, EntityType.Builder.of( Necromancer::new,MobCategory.MONSTER));
    public static final BUEntityHelper<Reaper> REAPER = new BUEntityHelper<>("reaper", 0x343244,0xBC5E5E, EntityType.Builder.of(Reaper::new,MobCategory.MONSTER).sized(0.6F, 1.4F));


    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Enchantments %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% //

    public static final BUEnchantmentHelper SERENITY = new BUEnchantmentHelper( "serenity");
    public static final BUEnchantmentHelper SUBALTERN = new BUEnchantmentHelper( "subaltern");
    public static final BUEnchantmentHelper LEADER = new BUEnchantmentHelper( "leader");


    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% ItemTags %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% //

    public static final TagKey<Item> GRABBER_STEALS = ItemTags.create(ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID,"grabber_steals"));
    public static final TagKey<Item> NECROMANCY = ItemTags.create(ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID,"necromancy"));
    public static final TagKey<Item> SKELETON_QUEST = ItemTags.create(ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID,"skeleton_quest"));
    public static final TagKey<Enchantment> SUBALTERN_INCOMPATIBLE = TagKey.create(Registries.ENCHANTMENT,ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, "exclusive_set/subaltern_incompatible"));

    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Items %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% //

    public static final BUItemHelper SKELETON_SOUL = new BUItemHelper("skeleton_soul",()-> new SoulItem(new Item.Properties()));
    public static final BUItemHelper SOUL_ORB = new BUItemHelper("soul_orb",
            () -> new Item(new Item.Properties().stacksTo(16)));
    public static final BUItemHelper NECRO_SCEPTER = new BUItemHelper("necromancer_scepter",
            ()-> new NecroScepterItem(new Item.Properties().durability(50)));
    public static final BUItemHelper AMULET = new BUItemHelper("amulet",
            ()-> new AmuletItem(new Item.Properties().durability(100)));
    public static final BUItemHelper MINION_SWORD = new BUItemHelper("minion_sword",
            ()->new Item(new Item.Properties().durability(100)));
    public static final BUItemHelper BLADE = new BUItemHelper("blade",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final BUItemHelper HAUNTER_BLADE = new BUItemHelper("haunter_blade",
            () -> new Item(new Item.Properties().stacksTo(64).attributes(HaunterSpearItem.createAttributes())));
    public static final BUItemHelper HAUNTER_SPEAR = new BUItemHelper("haunter_spear",
            ()-> new HaunterSpearItem(new Item.Properties().durability(100)));
    public static final BUItemHelper NECRO_SCEPTER_INVENTORY = new BUItemHelper("necromancer_scepter_inventory",
            ()-> new Item(new Item.Properties()));

    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Particles %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% //

    public static final BUParticleHelper PURPLE_BAR = new BUParticleHelper("purple_bar");
    public static final BUParticleHelper PURPLE_SOUL = new BUParticleHelper("purple_soul");


    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Sounds %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% //

    public static final DeferredHolder<SoundEvent, SoundEvent> BROKEN_SKELETON_REVIVES = BU_SOUNDS.register("broken_skeleton_revives", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, "broken_skeleton_revives")));


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

    public static class BonesEnchantmentTagsProvider extends EnchantmentTagsProvider {

        public BonesEnchantmentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
            super(output, provider);
        }

        public void addTags(HolderLookup.Provider provider) {
            this.tag(SUBALTERN_INCOMPATIBLE).add(SUBALTERN.ench());
        }
    }
}

package com.sh1nylabs.bonesupdate.registerer;

import com.sh1nylabs.bonesupdate.BonesUpdate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Set;
import java.util.function.Function;

import static com.sh1nylabs.bonesupdate.registerer.BonesRegistry.*;

public class BUBlockHelper<T extends BlockEntity> {
    private final DeferredHolder<Block, Block> block;
    private final DeferredHolder<Item, BlockItem> item;
    private final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends BlockEntity>> blockEntity;

    public BUBlockHelper(String name, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties blockProperties) {
        block = BU_BLOCKS.register(name, () -> function.apply(blockProperties.setId(ResourceKey.create(BU_BLOCKS.getRegistryKey(),ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, name)))));
        item = BU_ITEMS.register(name,
                () -> new BlockItem(block.get(), new Item.Properties().setId(ResourceKey.create(BU_ITEMS.getRegistryKey(),ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, name)))));
        blockEntity = null;
    }

    public BUBlockHelper(String name, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties blockProperties, BlockEntityType.BlockEntitySupplier<? extends BlockEntity> blockEntity) {
        block = BU_BLOCKS.register(name, () -> function.apply(blockProperties.setId(ResourceKey.create(BU_BLOCKS.getRegistryKey(),ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, name)))));
        item = BU_ITEMS.register(name,
                () -> new BlockItem(block.get(), new Item.Properties().setId(ResourceKey.create(BU_ITEMS.getRegistryKey(), ResourceLocation.fromNamespaceAndPath(BonesUpdate.MODID, name)))));
        this.blockEntity = BU_BLOCK_ENTITIES.register(name+"_entity", () -> new BlockEntityType<>(blockEntity, Set.of(block.get())));
    }

    public Block block() {
        return block.get();
    }

    public BlockItem item() { return item.get();}
    public BlockEntityType<? extends BlockEntity> blockEntity() { return blockEntity.get();}


}

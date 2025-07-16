package com.sh1nylabs.bonesupdate.registerer;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;
import java.util.function.Function;

import static com.sh1nylabs.bonesupdate.registerer.BonesRegistry.*;

public class BUBlockHelper<T extends BlockEntity> {
    private final RegistryObject<Block> block;
    private final RegistryObject<BlockItem> item;
    private final RegistryObject<BlockEntityType<? extends BlockEntity>> blockEntity;

    public BUBlockHelper(String name, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties blockProperties) {
        block = BU_BLOCKS.register(name, () -> function.apply(blockProperties.setId(BU_BLOCKS.key(name))));
        item = BU_ITEMS.register(name,
                () -> new BlockItem(block.get(), new Item.Properties().setId(BU_ITEMS.key(name))));
        blockEntity = null;
    }

    public BUBlockHelper(String name, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties blockProperties, BlockEntityType.BlockEntitySupplier<? extends BlockEntity> blockEntity) {
        block = BU_BLOCKS.register(name, () -> function.apply(blockProperties.setId(BU_BLOCKS.key(name))));
        item = BU_ITEMS.register(name,
                () -> new BlockItem(block.get(), new Item.Properties().setId(BU_ITEMS.key(name))));
        this.blockEntity = BU_BLOCK_ENTITIES.register(name+"_entity", () -> new BlockEntityType<>(blockEntity, Set.of(block.get())));
    }

    public Block block() {
        return block.get();
    }

    public BlockItem item() { return item.get();}
    public BlockEntityType<? extends BlockEntity> blockEntity() { return blockEntity.get();}


}

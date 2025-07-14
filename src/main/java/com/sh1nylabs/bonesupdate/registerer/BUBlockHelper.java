package com.sh1nylabs.bonesupdate.registerer;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.sh1nylabs.bonesupdate.registerer.BonesRegistry.*;

public class BUBlockHelper<T extends BlockEntity> {
    private final RegistryObject<Block> block;
    private final RegistryObject<BlockItem> item;
    private final RegistryObject<BlockEntityType<? extends BlockEntity>> blockEntity;

    public BUBlockHelper(String name, Supplier<? extends Block> supplier) {
        block = BU_BLOCKS.register(name, supplier);
        item = BU_ITEMS.register(name,
                () -> new BlockItem(block.get(), new Item.Properties()));
        blockEntity = null;
    }

    public BUBlockHelper(String name, Supplier<? extends Block> supplier, BlockEntityType.BlockEntitySupplier<BlockEntity> blockClass) {
        block = BU_BLOCKS.register(name, supplier);
        item = BU_ITEMS.register(name,
                () -> new BlockItem(block.get(), new Item.Properties()));
        blockEntity = BU_BLOCK_ENTITIES.register(name, () -> BlockEntityType.Builder.of(blockClass, block.get()).build(null));
    }

    public Block block() {
        return block.get();
    }

    public BlockItem item() { return item.get();}
    public BlockEntityType<? extends BlockEntity> blockEntity() { return blockEntity.get();}


}

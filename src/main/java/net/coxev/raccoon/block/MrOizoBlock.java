package net.coxev.raccoon.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class MrOizoBlock extends HorizontalFacingBlock {

    public static final MapCodec<MrOizoBlock> CODEC = MrOizoBlock.createCodec(MrOizoBlock::new);
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(6.5, 0.0, 5.0, 12.5, 14.0, 11.0);


    public MapCodec<? extends MrOizoBlock> getCodec() {
        return CODEC;
    }

    public MrOizoBlock(Settings settings){
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(FACING, Direction.NORTH));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }


    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}

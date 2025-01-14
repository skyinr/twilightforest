package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class IronLadderBlock extends LadderBlock {
    public static final BooleanProperty LEFT = BooleanProperty.create("left");
	public static final BooleanProperty RIGHT = BooleanProperty.create("right");

    IronLadderBlock(Properties props) {
        super(props);
        this.setDefaultState(this.getDefaultState().with(LEFT, false).with(RIGHT, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(LEFT, RIGHT);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction direction, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		Direction facing = state.get(LadderBlock.FACING);
		BlockState superUpdated = super.updatePostPlacement(state, direction, facingState, worldIn, currentPos, facingPos);
		if (superUpdated.getBlock() != this) {
			return superUpdated;
		}

		BlockState leftState  = worldIn.getBlockState(currentPos.offset(facing.rotateYCCW()));
		BlockState rightState = worldIn.getBlockState(currentPos.offset(facing.rotateY()));

		return superUpdated.with(LEFT, leftState.getBlock() instanceof IronLadderBlock && leftState.get(LadderBlock.FACING) == facing)
						.with(RIGHT, rightState.getBlock() instanceof IronLadderBlock && rightState.get(LadderBlock.FACING) == facing);
    }
}

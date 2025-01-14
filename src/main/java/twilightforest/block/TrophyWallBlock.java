package twilightforest.block;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import twilightforest.enums.BossVariant;

//[VanillaCopy] of WallSkullBlock except we use BossVariants instead of ISkullType
public class TrophyWallBlock extends AbstractTrophyBlock {
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	private static final Map<Direction, VoxelShape> SHAPES = Maps
			.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.makeCuboidShape(4.0D, 4.0D, 8.0D, 12.0D, 12.0D, 16.0D), Direction.SOUTH, Block.makeCuboidShape(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 8.0D), Direction.EAST, Block.makeCuboidShape(0.0D, 4.0D, 4.0D, 8.0D, 12.0D, 12.0D), Direction.WEST, Block.makeCuboidShape(8.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D)));
	private static final Map<Direction, VoxelShape> YETI_SHAPES = Maps
			.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.makeCuboidShape(3.25D, 4.0D, 8.5D, 12.75D, 14.5D, 16.0D), Direction.SOUTH, Block.makeCuboidShape(3.25D, 4.0D, 0.0D, 12.75D, 14.5D, 7.5D), Direction.EAST, Block.makeCuboidShape(0.0D, 4.0D, 3.25D, 7.5D, 14.5D, 12.75D), Direction.WEST, Block.makeCuboidShape(8.5D, 4.0D, 3.25D, 16.0D, 14.5D, 12.75D)));

	public TrophyWallBlock(BossVariant variant) {
		super(variant, 0, Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance());
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
	}

	@Override
	public String getTranslationKey() {
		return this.asItem().getTranslationKey();
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if (((AbstractTrophyBlock) state.getBlock()).getVariant() == BossVariant.UR_GHAST) {
			return TrophyBlock.GHAST_SHAPE;
		} else if (((AbstractTrophyBlock) state.getBlock()).getVariant() == BossVariant.ALPHA_YETI) {
			return YETI_SHAPES.get(state.get(FACING));
		}
		return SHAPES.get(state.get(FACING));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState blockstate = this.getDefaultState();
		IBlockReader iblockreader = context.getWorld();
		BlockPos blockpos = context.getPos();
		Direction[] adirection = context.getNearestLookingDirections();

		for (Direction direction : adirection) {
			if (direction.getAxis().isHorizontal()) {
				Direction direction1 = direction.getOpposite();
				blockstate = blockstate.with(FACING, direction1);
				if (!iblockreader.getBlockState(blockpos.offset(direction)).isReplaceable(context)) {
					return blockstate;
				}
			}
		}

		return null;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, POWERED);
	}
}

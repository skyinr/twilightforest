package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import twilightforest.enums.BossVariant;

//[VanillaCopy] of SkullBlock but we add a shape for the ur-ghast and we use BossVariant instead of ISkullType
public class TrophyBlock extends AbstractTrophyBlock {

	public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_0_15;
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);
	public static final VoxelShape GHAST_SHAPE = Block.makeCuboidShape(4.0D, 8.0D, 4.0D, 12.0D, 16.0D, 12.0D);
	protected static final VoxelShape YETI_X_SHAPE = Block.makeCuboidShape(3.25D, 0.0D, 4.5D, 12.75D, 10.0D, 11.5D);
	protected static final VoxelShape YETI_Z_SHAPE = Block.makeCuboidShape(4.5D, 0.0D, 3.25D, 11.5D, 10.0D, 12.75D);
	protected static final VoxelShape YETI_CORNER_SHAPE = Block.makeCuboidShape(4.5D, 0.0D, 4.5D, 11.5D, 10.0D, 11.5D);

	public TrophyBlock(BossVariant variant, int value) {
		super(variant, value, Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance());
		setDefaultState(stateContainer.getBaseState().with(TrophyBlock.ROTATION, 0));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if (((AbstractTrophyBlock) state.getBlock()).getVariant() == BossVariant.UR_GHAST) {
			return GHAST_SHAPE;
		} else if (((AbstractTrophyBlock) state.getBlock()).getVariant() == BossVariant.ALPHA_YETI) {
			switch (state.get(ROTATION)) {
				case 0:
				case 1:
				case 7:
				case 8:
				case 9:
				case 15:
				default:
					return YETI_X_SHAPE;
				case 3:
				case 4:
				case 5:
				case 11:
				case 12:
				case 13:
					return YETI_Z_SHAPE;
				case 2:
				case 6:
				case 10:
				case 14:
					return YETI_CORNER_SHAPE;
			}
		}
		return SHAPE;
	}

	@Override
	public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return VoxelShapes.empty();
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(ROTATION, Integer.valueOf(MathHelper.floor(context.getPlacementYaw() * 16.0F / 360.0F + 0.5D) & 15));
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(ROTATION, Integer.valueOf(rot.rotate(state.get(ROTATION), 16)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.with(ROTATION, Integer.valueOf(mirrorIn.mirrorRotation(state.get(ROTATION), 16)));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(ROTATION, POWERED);
	}
}

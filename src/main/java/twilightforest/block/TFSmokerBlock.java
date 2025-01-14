package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import twilightforest.tileentity.TFSmokerTileEntity;

import javax.annotation.Nullable;

public class TFSmokerBlock extends Block {

	protected TFSmokerBlock(Properties props) {
		super(props);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TFSmokerTileEntity();
	}
}

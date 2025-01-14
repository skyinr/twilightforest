package twilightforest.structures.lichtower;

import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;

import java.util.List;
import java.util.Random;

public class TowerBridgeComponent extends TowerWingComponent {

	int dSize;
	int dHeight;

	public TowerBridgeComponent(TemplateManager manager, CompoundNBT nbt) {
		super(LichTowerPieces.TFLTBri, nbt);
	}

	protected TowerBridgeComponent(TFFeature feature, int i, int x, int y, int z, int pSize, int pHeight, Direction direction) {
		super(LichTowerPieces.TFLTBri, feature, i, x, y, z, 3, 3, direction);

		this.dSize = pSize;
		this.dHeight = pHeight;
	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random rand) {
		int[] dest = new int[]{2, 1, 1};//getValidOpening(rand, 0);
		makeTowerWing(list, rand, 1, dest[0], dest[1], dest[2], dSize, dHeight, Rotation.NONE);
	}

	/**
	 * Gets the bounding box of the tower wing we would like to make.
	 *
	 * @return
	 */
	public MutableBoundingBox getWingBB() {
		int[] dest = offsetTowerCoords(2, 1, 1, dSize, this.getCoordBaseMode());
		return feature.getComponentToAddBoundingBox(dest[0], dest[1], dest[2], 0, 0, 0, dSize - 1, dHeight - 1, dSize - 1, this.getCoordBaseMode());
	}

	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		// make walls
		for (int x = 0; x < 3; x++) {
			setBlockState(world, Blocks.OAK_FENCE.getDefaultState(), x, 2, 0, sbb);
			setBlockState(world, Blocks.OAK_FENCE.getDefaultState(), x, 2, 2, sbb);
			setBlockState(world, Blocks.STONE_BRICKS.getDefaultState(), x, 1, 0, sbb);
			setBlockState(world, Blocks.STONE_BRICKS.getDefaultState(), x, 1, 2, sbb);
			setBlockState(world, Blocks.STONE_BRICKS.getDefaultState(), x, 0, 0, sbb);
			setBlockState(world, Blocks.STONE_BRICKS.getDefaultState(), x, 0, 1, sbb);
			setBlockState(world, Blocks.STONE_BRICKS.getDefaultState(), x, 0, 2, sbb);
			setBlockState(world, Blocks.STONE_BRICKS.getDefaultState(), x, -1, 1, sbb);
		}

		// try two blocks outside the boundries
		setBlockState(world, Blocks.STONE_BRICKS.getDefaultState(), -1, -1, 1, sbb);
		setBlockState(world, Blocks.STONE_BRICKS.getDefaultState(), 3, -1, 1, sbb);

		// clear bridge walkway
		this.fillWithAir(world, sbb, 0, 1, 1, 2, 2, 1);

		// marker blocks
//        setBlockState(world, Blocks.WOOL, this.coordBaseMode, size / 2, 2, size / 2, sbb);
//        setBlockState(world, Blocks.GOLD_BLOCK, 0, 0, 0, 0, sbb);

		// door opening?
//        makeDoorOpening(world, sbb);

		return true;
	}
}

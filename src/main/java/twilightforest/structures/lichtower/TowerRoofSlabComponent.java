package twilightforest.structures.lichtower;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;

import java.util.Random;

public class TowerRoofSlabComponent extends TowerRoofComponent {

	public TowerRoofSlabComponent(TemplateManager manager, CompoundNBT nbt) {
		super(LichTowerPieces.TFLTRS, nbt);
	}

	public TowerRoofSlabComponent(IStructurePieceType piece, CompoundNBT nbt) {
		super(piece, nbt);
	}

	public TowerRoofSlabComponent(IStructurePieceType piece, TFFeature feature, int i, TowerWingComponent wing) {
		super(piece, feature, i);

		// same alignment
		this.setCoordBaseMode(wing.getCoordBaseMode());
		// same size
		this.size = wing.size; // assuming only square towers and roofs right now.
		this.height = size / 2;

		// just hang out at the very top of the tower
		makeCapBB(wing);
	}

	/**
	 * Makes a flat, pyramid-shaped roof
	 */
	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		return makePyramidCap(world, Blocks.BIRCH_SLAB.getDefaultState(), Blocks.BIRCH_PLANKS.getDefaultState(), sbb);
	}

	protected boolean makePyramidCap(ISeedReader world, BlockState slabType, BlockState woodType, MutableBoundingBox sbb) {
		for (int y = 0; y <= height; y++) {
			int min = 2 * y;
			int max = size - (2 * y) - 1;
			for (int x = min; x <= max; x++) {
				for (int z = min; z <= max; z++) {
					if (x == min || x == max || z == min || z == max) {

						setBlockState(world, slabType, x, y, z, sbb);
					} else {
						setBlockState(world, woodType, x, y, z, sbb);
					}
				}
			}
		}
		return true;
	}

	protected boolean makeConnectedCap(ISeedReader world, BlockState slabType, BlockState woodType, MutableBoundingBox sbb) {
		for (int y = 0; y < height; y++) {
			int min = 2 * y;
			int max = size - (2 * y) - 1;
			for (int x = 0; x <= max; x++) {
				for (int z = min; z <= max; z++) {
					if (x == max || z == min || z == max) {
						setBlockState(world, slabType, x, y, z, sbb);
					} else {
						setBlockState(world, woodType, x, y, z, sbb);
					}
				}
			}
		}
		return true;
	}
}

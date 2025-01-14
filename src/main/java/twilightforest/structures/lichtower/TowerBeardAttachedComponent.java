package twilightforest.structures.lichtower;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.structures.TFStructureComponentOld;

import java.util.Random;

public class TowerBeardAttachedComponent extends TowerBeardComponent {

	public TowerBeardAttachedComponent(TemplateManager manager, CompoundNBT nbt) {
		super(LichTowerPieces.TFLTBA, nbt);
	}

	public TowerBeardAttachedComponent(TFFeature feature, int i, TowerWingComponent wing) {
		super(LichTowerPieces.TFLTBA, feature, i, wing);

		// just hang out at the very bottom of the tower
		this.boundingBox = new MutableBoundingBox(wing.getBoundingBox().minX, wing.getBoundingBox().minY - this.height - 1, wing.getBoundingBox().minZ, wing.getBoundingBox().maxX, wing.getBoundingBox().minY - 1, wing.getBoundingBox().maxZ);
	}

	/**
	 * Makes a pyramid-shaped beard
	 */
	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		return makeAttachedBeard(world, rand, sbb);
	}

	private boolean makeAttachedBeard(ISeedReader world, Random rand, MutableBoundingBox sbb) {
		for (int y = 0; y <= height; y++) {
			int min = y + 1;
			int max = size - y;

			fillWithRandomizedBlocks(world, sbb, 0, height - y, min, max, height - y, max, false, rand, TFStructureComponentOld.getStrongholdStones());
		}
		return true;
	}
}

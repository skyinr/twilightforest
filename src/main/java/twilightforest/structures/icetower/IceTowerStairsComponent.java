package twilightforest.structures.icetower;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.structures.TFStructureComponentOld;
import twilightforest.structures.lichtower.TowerWingComponent;

import java.util.List;
import java.util.Random;

public class IceTowerStairsComponent extends TowerWingComponent {

	public IceTowerStairsComponent(TemplateManager manager, CompoundNBT nbt) {
		super(IceTowerPieces.TFITSt, nbt);
	}

	public IceTowerStairsComponent(TFFeature feature, int index, int x, int y, int z, int size, int height, Direction direction) {
		super(IceTowerPieces.TFITSt, feature, index, x, y, z, size, height, direction);
	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random rand) {
		if (parent != null && parent instanceof TFStructureComponentOld) {
			this.deco = ((TFStructureComponentOld) parent).deco;
		}
	}

	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		for (int x = 1; x < this.size; x++) {

			this.placeStairs(world, sbb, x, 1 - x, 5);

			for (int z = 0; z <= x; z++) {

				if (z > 0 && z <= this.size / 2) {
					this.placeStairs(world, sbb, x, 1 - x, 5 - z);
					this.placeStairs(world, sbb, x, 1 - x, 5 + z);
				}

				if (x <= this.size / 2) {
					this.placeStairs(world, sbb, z, 1 - x, 5 - x);
					this.placeStairs(world, sbb, z, 1 - x, 5 + x);
				}
			}
		}

		this.setBlockState(world, deco.blockState, 0, 0, 5, sbb);

		return true;
	}

	private void placeStairs(ISeedReader world, MutableBoundingBox sbb, int x, int y, int z) {
		if (this.getBlockStateFromPos(world, x, y, z, sbb).getMaterial().isReplaceable()) {
			this.setBlockState(world, deco.blockState, x, y, z, sbb);
			this.setBlockState(world, deco.blockState, x, y - 1, z, sbb);
		}
	}
}

package twilightforest.structures.finalcastle;

import net.minecraft.nbt.CompoundNBT;
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
import twilightforest.structures.TFStructureComponentOld;
import twilightforest.util.RotationUtil;

import java.util.List;
import java.util.Random;

public class FinalCastleRoof13PeakedComponent extends TFStructureComponentOld {

	public FinalCastleRoof13PeakedComponent(TemplateManager manager, CompoundNBT nbt) {
		super(FinalCastlePieces.TFFCRo13Pk, nbt);
	}

	//TODO: Parameter "rand" is unused. Remove?
	public FinalCastleRoof13PeakedComponent(TFFeature feature, Random rand, int i, TFStructureComponentOld sideTower) {
		super(FinalCastlePieces.TFFCRo13Pk, feature, i);

		int height = 18;

		this.setCoordBaseMode(sideTower.getCoordBaseMode());
		this.boundingBox = new MutableBoundingBox(sideTower.getBoundingBox().minX - 2, sideTower.getBoundingBox().maxY - 1, sideTower.getBoundingBox().minZ - 2, sideTower.getBoundingBox().maxX + 2, sideTower.getBoundingBox().maxY + height - 1, sideTower.getBoundingBox().maxZ + 2);
	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random rand) {
		if (parent != null && parent instanceof TFStructureComponentOld) {
			this.deco = ((TFStructureComponentOld) parent).deco;
		}
	}

	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random randomIn, MutableBoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		// peaky roof, loop unrolled as it was getting dumb
		for (int i = 0; i < 3; i++) {
			this.fillWithBlocks(world, sbb, 1, i, i, 15, i, i, deco.roofState, deco.roofState, false);
			this.fillWithBlocks(world, sbb, 1, i, 16 - i, 15, i, 16 - i, deco.roofState, deco.roofState, false);
		}

		for (int i = 0; i < 3; i++) {
			int dz = 3 + i;
			this.fillWithBlocks(world, sbb, 2, 5 + ((i - 1) * 2), dz, 14, 4 + (i * 2), dz, deco.roofState, deco.roofState, false);
			this.fillWithBlocks(world, sbb, 1, 1, dz, 1, 5 + ((i - 1) * 2), dz, deco.roofState, deco.roofState, false);
			this.fillWithBlocks(world, sbb, 1, 5 + ((i - 1) * 2), dz - 1, 1, 4 + (i * 2), dz, deco.blockState, deco.blockState, false);
			this.fillWithBlocks(world, sbb, 15, 1, dz, 15, 5 + ((i - 1) * 2), dz, deco.roofState, deco.roofState, false);
			this.fillWithBlocks(world, sbb, 15, 5 + ((i - 1) * 2), dz - 1, 15, 4 + (i * 2), dz, deco.blockState, deco.blockState, false);

			dz = 13 - i;
			this.fillWithBlocks(world, sbb, 2, 5 + ((i - 1) * 2), dz, 14, 4 + (i * 2), dz, deco.roofState, deco.roofState, false);
			this.fillWithBlocks(world, sbb, 1, 1, dz, 1, 5 + ((i - 1) * 2), dz, deco.roofState, deco.roofState, false);
			this.fillWithBlocks(world, sbb, 1, 5 + ((i - 1) * 2), dz, 1, 4 + (i * 2), dz + 1, deco.blockState, deco.blockState, false);
			this.fillWithBlocks(world, sbb, 15, 1, dz, 15, 5 + ((i - 1) * 2), dz, deco.roofState, deco.roofState, false);
			this.fillWithBlocks(world, sbb, 15, 5 + ((i - 1) * 2), dz, 15, 4 + (i * 2), dz + 1, deco.blockState, deco.blockState, false);
		}

		for (int i = 0; i < 3; i++) {
			int dz = 6 + i;
			this.fillWithBlocks(world, sbb, 2, 12 + ((i - 1) * 3), dz, 14, 11 + (i * 3), dz, deco.roofState, deco.roofState, false);
			this.fillWithBlocks(world, sbb, 1, 1, dz, 1, 12 + ((i - 1) * 3), dz, deco.roofState, deco.roofState, false);
			this.fillWithBlocks(world, sbb, 1, 12 + ((i - 1) * 3), dz - 1, 1, 11 + (i * 3), dz, deco.blockState, deco.blockState, false);
			this.fillWithBlocks(world, sbb, 15, 1, dz, 15, 12 + ((i - 1) * 3), dz, deco.roofState, deco.roofState, false);
			this.fillWithBlocks(world, sbb, 15, 12 + ((i - 1) * 3), dz - 1, 15, 11 + (i * 3), dz, deco.blockState, deco.blockState, false);

			dz = 10 - i;
			this.fillWithBlocks(world, sbb, 2, 12 + ((i - 1) * 3), dz, 14, 11 + (i * 3), dz, deco.roofState, deco.roofState, false);
			this.fillWithBlocks(world, sbb, 1, 1, dz, 1, 12 + ((i - 1) * 3), dz, deco.roofState, deco.roofState, false);
			this.fillWithBlocks(world, sbb, 1, 12 + ((i - 1) * 3), dz, 1, 11 + (i * 3), dz + 1, deco.blockState, deco.blockState, false);
			this.fillWithBlocks(world, sbb, 15, 1, dz, 15, 12 + ((i - 1) * 3), dz, deco.roofState, deco.roofState, false);
			this.fillWithBlocks(world, sbb, 15, 12 + ((i - 1) * 3), dz, 15, 11 + (i * 3), dz + 1, deco.blockState, deco.blockState, false);
		}

		// top roof bobbles
		this.fillWithBlocks(world, sbb, 1, 18, 8, 5, 18, 8, deco.roofState, deco.roofState, false);
		this.fillWithBlocks(world, sbb, 11, 18, 8, 14, 18, 8, deco.roofState, deco.roofState, false);
		this.fillWithBlocks(world, sbb, 0, 17, 8, 1, 19, 8, deco.roofState, deco.roofState, false);
		this.fillWithBlocks(world, sbb, 15, 17, 8, 16, 19, 8, deco.roofState, deco.roofState, false);

		for (Rotation rotation : new Rotation[]{Rotation.CLOCKWISE_90, Rotation.COUNTERCLOCKWISE_90}) {
			// this might be one of my more confusing instances of code recycling
			this.fillBlocksRotated(world, sbb, 4, 0, 1, 12, 1, 1, deco.blockState, rotation);
			// more teeny crenellations
			for (int i = 3; i < 13; i += 2) {
				this.fillBlocksRotated(world, sbb, i, -1, 1, i, 2, 1, deco.blockState, rotation);
			}
		}

		// corners
		for (Rotation rotation : RotationUtil.ROTATIONS) {
			this.fillBlocksRotated(world, sbb, 0, -1, 0, 3, 2, 3, deco.blockState, rotation);
			this.setBlockStateRotated(world, deco.blockState, 1, -2, 2, rotation, sbb);
			this.setBlockStateRotated(world, deco.blockState, 1, -2, 1, rotation, sbb);
			this.setBlockStateRotated(world, deco.blockState, 2, -2, 1, rotation, sbb);
		}

		return true;
	}
}

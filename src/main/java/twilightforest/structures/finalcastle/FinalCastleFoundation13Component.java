package twilightforest.structures.finalcastle;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;
import twilightforest.structures.TFStructureComponentOld;
import twilightforest.util.RotationUtil;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class FinalCastleFoundation13Component extends TFStructureComponentOld {

	protected int groundLevel = -1;

	public FinalCastleFoundation13Component(TemplateManager manager, CompoundNBT nbt) {
		this(FinalCastlePieces.TFFCToF13, nbt);
	}

	public FinalCastleFoundation13Component(IStructurePieceType piece, CompoundNBT nbt) {
		super(piece, nbt);
	}

	public FinalCastleFoundation13Component(IStructurePieceType type, TFFeature feature, Random rand, int i, TFStructureComponentOld sideTower) {
		super(type, feature, i);

		this.setCoordBaseMode(sideTower.getCoordBaseMode());
		this.boundingBox = new MutableBoundingBox(sideTower.getBoundingBox().minX - 2, sideTower.getBoundingBox().minY - 1, sideTower.getBoundingBox().minZ - 2, sideTower.getBoundingBox().maxX + 2, sideTower.getBoundingBox().minY, sideTower.getBoundingBox().maxZ + 2);
	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random rand) {
		if (parent instanceof TFStructureComponentOld) {
			this.deco = ((TFStructureComponentOld) parent).deco;
		}
	}

	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random randomIn, MutableBoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		// offset bounding box to average ground level
		if (this.groundLevel < 0) {
			this.groundLevel = this.findGroundLevel(world, sbb, 150, isDeadrock); // is 150 a good place to start? :)

			if (this.groundLevel < 0) {
				return true;
			}
		}

		// how tall are we
		int height = this.boundingBox.maxY - this.groundLevel;
		int mid = height / 2;

		// assume square
		int size = this.boundingBox.maxX - this.boundingBox.minX;

		for (Rotation rotation : RotationUtil.ROTATIONS) {
			// do corner
			this.replaceAirAndLiquidDownwardsRotated(world, deco.blockState, 1, -1, 1, rotation, sbb);
			this.replaceAirAndLiquidDownwardsRotated(world, deco.blockState, 2, -1, 1, rotation, sbb);
			this.replaceAirAndLiquidDownwardsRotated(world, deco.blockState, 2, -mid, 0, rotation, sbb);
			this.replaceAirAndLiquidDownwardsRotated(world, deco.blockState, 1, -1, 2, rotation, sbb);
			this.replaceAirAndLiquidDownwardsRotated(world, deco.blockState, 0, -mid, 2, rotation, sbb);

			for (int x = 6; x < (size - 3); x += 4) {
				this.replaceAirAndLiquidDownwardsRotated(world, deco.blockState, x, -1, 1, rotation, sbb);
				this.replaceAirAndLiquidDownwardsRotated(world, deco.blockState, x, -mid, 0, rotation, sbb);
			}
		}

		return true;
	}

	protected static final Predicate<BlockState> isDeadrock = state -> state.getBlock() == TFBlocks.deadrock.get();
}

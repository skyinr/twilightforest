package twilightforest.structures.finalcastle;

import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
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
import twilightforest.structures.TFStructureComponentOld;

import java.util.List;
import java.util.Random;

public class FinalCastleDungeonStepsComponent extends TFStructureComponentOld {

	public FinalCastleDungeonStepsComponent(TemplateManager manager, CompoundNBT nbt) {
		super(FinalCastlePieces.TFFCDunSt, nbt);
	}

	public FinalCastleDungeonStepsComponent(TFFeature feature, Random rand, int i, int x, int y, int z, Direction rotation) {
		super(FinalCastlePieces.TFFCDunSt, feature, i);
		this.spawnListIndex = 2; // dungeon monsters

		this.setCoordBaseMode(rotation);
		this.boundingBox = TFStructureComponentOld.getComponentToAddBoundingBox2(x, y, z, -2, -15, -3, 5, 15, 20, rotation);
	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random rand) {
		if (parent != null && parent instanceof TFStructureComponentOld) {
			this.deco = ((TFStructureComponentOld) parent).deco;
		}
	}

	/**
	 * build more steps towards the specified direction
	 */
	public FinalCastleDungeonStepsComponent buildMoreStepsTowards(StructurePiece parent, List<StructurePiece> list, Random rand, Rotation rotation) {

		Direction direction = getStructureRelativeRotation(rotation);

		int sx = 2;
		int sy = 0;
		int sz = 17;

		switch (rotation) {
			case NONE:
				sz -= 5;
				break;
			case CLOCKWISE_90:
				sx -= 5;
				break;
			case CLOCKWISE_180:
				sz += 5;
				break;
			case COUNTERCLOCKWISE_90:
				sx += 6;
				break;
		}

		// find center of landing
		int dx = this.getXWithOffset(sx, sz);
		int dy = this.getYWithOffset(sy);
		int dz = this.getZWithOffset(sx, sz);


		// build a new stairway there
		FinalCastleDungeonStepsComponent steps = new FinalCastleDungeonStepsComponent(getFeatureType(), rand, this.componentType + 1, dx, dy, dz, direction);
		list.add(steps);
		steps.buildComponent(this, list, rand);

		return steps;
	}

	/**
	 * build a new level under the exit
	 */
	public FinalCastleDungeonEntranceComponent buildLevelUnder(StructurePiece parent, List<StructurePiece> list, Random rand, int level) {
		// find center of landing
		int dx = this.getXWithOffset(2, 19);
		int dy = this.getYWithOffset(-7);
		int dz = this.getZWithOffset(2, 19);

		// build a new dungeon level under there
		FinalCastleDungeonEntranceComponent room = new FinalCastleDungeonEntranceComponent(getFeatureType(), rand, 8, dx, dy, dz, getCoordBaseMode(), level);
		list.add(room);
		room.buildComponent(this, list, rand);

		return room;
	}

	/**
	 * build the boss room
	 */
	public FinalCastleDungeonForgeRoomComponent buildBossRoomUnder(StructurePiece parent, List<StructurePiece> list, Random rand) {
		// find center of landing
		int dx = this.getXWithOffset(2, 19);
		int dy = this.getYWithOffset(-31);
		int dz = this.getZWithOffset(2, 19);

		// build a new dungeon level under there
		FinalCastleDungeonForgeRoomComponent room = new FinalCastleDungeonForgeRoomComponent(getFeatureType(), rand, 8, dx, dy, dz, this.getCoordBaseMode());
		list.add(room);
		room.buildComponent(this, list, rand);

		return room;
	}

	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		final BlockState stairState = deco.stairState.with(StairsBlock.FACING, Direction.SOUTH);
		for (int z = 0; z < 15; z++) {
			int y = 14 - z;

			this.fillWithBlocks(world, sbb, 0, y, z, 4, y, z, stairState, stairState, false);
			this.fillWithAir(world, sbb, 0, y + 1, z, 4, y + 6, z);
		}
		this.fillWithAir(world, sbb, 0, 0, 15, 4, 5, 19);

		return true;
	}
}

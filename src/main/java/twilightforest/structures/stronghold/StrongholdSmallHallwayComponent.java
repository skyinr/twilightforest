package twilightforest.structures.stronghold;

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

public class StrongholdSmallHallwayComponent extends StructureTFStrongholdComponent {

	public StrongholdSmallHallwayComponent(TemplateManager manager, CompoundNBT nbt) {
		super(StrongholdPieces.TFSSH, nbt);
	}

	public StrongholdSmallHallwayComponent(TFFeature feature, int i, Direction facing, int x, int y, int z) {
		super(StrongholdPieces.TFSSH, feature, i, facing, x, y, z);
	}

	@Override
	public MutableBoundingBox generateBoundingBox(Direction facing, int x, int y, int z) {
		return StructureTFStrongholdComponent.getComponentToAddBoundingBox(x, y, z, -4, -1, 0, 9, 7, 18, facing);
	}

	@Override
	public void buildComponent(StructurePiece parent, List<StructurePiece> list, Random random) {
		super.buildComponent(parent, list, random);

		// entrance
		this.addDoor(4, 1, 0);

		// make a random component at the end
		addNewComponent(parent, list, random, Rotation.NONE, 4, 1, 18);
	}

	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		placeStrongholdWalls(world, sbb, 0, 0, 0, 8, 6, 17, rand, deco.randomBlocks);

		// statues
		this.placeWallStatue(world, 1, 1, 9, Rotation.CLOCKWISE_90, sbb);
		this.placeWallStatue(world, 7, 1, 9, Rotation.COUNTERCLOCKWISE_90, sbb);

		// doors
		placeDoors(world, sbb);

		return true;
	}
}

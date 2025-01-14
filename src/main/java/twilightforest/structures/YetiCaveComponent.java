package twilightforest.structures;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;
import twilightforest.world.TFGenerationSettings;

import java.util.Random;

public class YetiCaveComponent extends HollowHillComponent {

	public YetiCaveComponent(TemplateManager manager, CompoundNBT nbt) {
		super(TFFeature.TFYeti, nbt);
	}

	public YetiCaveComponent(TFFeature feature, Random rand, int i, int x, int y, int z) {
		super(TFFeature.TFYeti, feature, i, 2, x, y, z);
	}

	/**
	 * @param cx
	 * @param cz
	 * @return true if the coordinates would be inside the hill on the "floor" of the hill
	 */
	@Override
	boolean isInHill(int cx, int cz) {
		// yeti cave is square
		return cx < this.radius * 2 && cx > 0 && cz < this.radius * 2 && cz > 0;
	}

	/**
	 * @return true if the coordinates are inside the hill in 3D
	 */
	@Override
	boolean isInHill(int mapX, int mapY, int mapZ) {
		// yeti cave is square and 16 blocks tall
		return mapX < this.radius * 2 && mapX > 0 && mapZ < this.radius * 2 && mapZ > 0 && mapY > TFGenerationSettings.SEALEVEL && mapY < TFGenerationSettings.SEALEVEL + 20;
	}

	/**
	 * Add in all the blocks we're adding.
	 */
	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {

		int sn = 128;

		// fill in features

//		// ore or glowing stalactites! (smaller, less plentiful)
//		for (int i = 0; i < sn; i++)
//		{
//			int[] dest = getCoordsInHill2D(rand);
//			generateOreStalactite(world, dest[0], 1, dest[1], sbb);
//		}
		// stone stalactites!
		for (int i = 0; i < sn; i++) {
			int[] dest = getCoordsInHill2D(rand);
			generateBlockStalactite(world, generator, manager, Blocks.STONE, 1.0F, true, dest[0], 1, dest[1], sbb);
		}
		// ice stalactites!
		for (int i = 0; i < sn; i++) {
			int[] dest = getCoordsInHill2D(rand);
			generateBlockStalactite(world, generator, manager, Blocks.ICE, 1.0F, true, dest[0], 1, dest[1], sbb);
		}
		// packed ice stalactites!
		for (int i = 0; i < sn; i++) {
			int[] dest = getCoordsInHill2D(rand);
			generateBlockStalactite(world, generator, manager, Blocks.PACKED_ICE, 0.9F, true, dest[0], 1, dest[1], sbb);
		}

		// spawn alpha yeti
		final BlockState yetiSpawner = TFBlocks.boss_spawner_alpha_yeti.get().getDefaultState();
		setBlockStateRotated(world, yetiSpawner, radius, 1, radius, Rotation.NONE, sbb);

		return true;
	}
}

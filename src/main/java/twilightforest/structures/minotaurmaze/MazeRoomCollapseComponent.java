package twilightforest.structures.minotaurmaze;

import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.block.TFBlocks;

import java.util.Random;

public class MazeRoomCollapseComponent extends MazeRoomComponent {

	public MazeRoomCollapseComponent(TemplateManager manager, CompoundNBT nbt) {
		super(MinotaurMazePieces.TFMMRC, nbt);
	}

	public MazeRoomCollapseComponent(TFFeature feature, int i, Random rand, int x, int y, int z) {
		super(MinotaurMazePieces.TFMMRC, feature, i, rand, x, y, z);
	}

	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		super.func_230383_a_(world, manager, generator, rand, sbb, chunkPosIn, blockPos);

		//
		for (int x = 1; x < 14; x++) {
			for (int z = 1; z < 14; z++) {
				// calculate distance from middle
				int dist = (int) Math.round(7 / Math.sqrt((7.5 - x) * (7.5 - x) + (7.5 - z) * (7.5 - z)));
				int gravel = rand.nextInt(dist);
				int root = rand.nextInt(dist);

				if (gravel > 0) {
					gravel++; // get it out of the floor
					this.fillWithBlocks(world, sbb, x, 1, z, x, gravel, z, Blocks.GRAVEL.getDefaultState(), AIR, false);
					this.fillWithAir(world, sbb, x, gravel, z, x, gravel + 5, z);
				} else if (root > 0) {
					this.fillWithBlocks(world, sbb, x, 5, z, x, 5 + root, z, Blocks.DIRT.getDefaultState(), AIR, true);
					this.fillWithBlocks(world, sbb, x, 5 - rand.nextInt(5), z, x, 5, z, TFBlocks.root_strand.get().getDefaultState(), AIR, false);
				} else if (rand.nextInt(dist + 1) > 0) {
					// remove ceiling
					this.fillWithAir(world, sbb, x, 5, z, x, 5, z);
				}
			}
		}

		return true;
	}
}

package twilightforest.structures.lichtower;

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

import java.util.Random;

public class TowerRoofAttachedSlabComponent extends TowerRoofSlabComponent {

	public TowerRoofAttachedSlabComponent(TemplateManager manager, CompoundNBT nbt) {
		super(LichTowerPieces.TFLTRAS, nbt);
	}

	public TowerRoofAttachedSlabComponent(TFFeature feature, int i, TowerWingComponent wing) {
		super(LichTowerPieces.TFLTRAS, feature, i, wing);
	}

	/**
	 * Makes a flat, pyramid-shaped roof that is connected to the parent tower
	 */
	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random rand, MutableBoundingBox sbb, ChunkPos chunkPosIn, BlockPos blockPos) {
		return makeConnectedCap(world, Blocks.BIRCH_SLAB.getDefaultState(), Blocks.BIRCH_PLANKS.getDefaultState(), sbb);
	}
}

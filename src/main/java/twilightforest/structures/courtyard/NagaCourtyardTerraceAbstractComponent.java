package twilightforest.structures.courtyard;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.structures.TFStructureComponentTemplate;

import java.util.Random;

public abstract class NagaCourtyardTerraceAbstractComponent extends TFStructureComponentTemplate {

    private final ResourceLocation TERRACE;

    public NagaCourtyardTerraceAbstractComponent(TemplateManager manager, IStructurePieceType piece, CompoundNBT nbt, ResourceLocation terrace) {
        super(manager, piece, nbt);
        TERRACE = terrace;
    }

    public NagaCourtyardTerraceAbstractComponent(IStructurePieceType type, TFFeature feature, int i, int x, int y, int z, Rotation rotation, ResourceLocation terrace) {
        super(type, feature, i, x, y, z, rotation);
        TERRACE = terrace;
    }

    @Override
    protected void loadTemplates(TemplateManager templateManager) {
        TEMPLATE = templateManager.getTemplate(TERRACE);
    }

	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random random, MutableBoundingBox structureBoundingBox, ChunkPos chunkPosIn, BlockPos blockPos) {
		placeSettings.setBoundingBox(structureBoundingBox).clearProcessors().addProcessor(new CourtyardTerraceTemplateProcessor(0.0F));
		TEMPLATE.func_237146_a_(world, rotatedPosition, rotatedPosition, placeSettings, random, 18);
		return true;
	}
}

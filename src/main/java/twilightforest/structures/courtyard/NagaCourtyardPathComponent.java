package twilightforest.structures.courtyard;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.TemplateManager;
import twilightforest.TFFeature;
import twilightforest.TwilightForestMod;
import twilightforest.structures.TFStructureComponentTemplate;

import java.util.Random;

public class NagaCourtyardPathComponent extends TFStructureComponentTemplate {

    private static final ResourceLocation PATH = new ResourceLocation(TwilightForestMod.ID, "courtyard/pathway");

    public NagaCourtyardPathComponent(TemplateManager manager, CompoundNBT nbt) {
        super(manager, NagaCourtyardPieces.TFNCPa, nbt);
    }

    public NagaCourtyardPathComponent(TFFeature feature, int i, int x, int y, int z) {
        super(NagaCourtyardPieces.TFNCPa, feature, i, x, y, z, Rotation.NONE);
    }

    @Override
    protected void loadTemplates(TemplateManager templateManager) {
        TEMPLATE = templateManager.getTemplate(PATH);
    }

	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random random, MutableBoundingBox structureBoundingBox, ChunkPos chunkPosIn, BlockPos blockPos) {
		placeSettings.setBoundingBox(structureBoundingBox).addProcessor(new CourtyardWallTemplateProcessor(0.0F));
		TEMPLATE.func_237146_a_(world, templatePosition, templatePosition, placeSettings, random, 18);
		return true;
	}
}

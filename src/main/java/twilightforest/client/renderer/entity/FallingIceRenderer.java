package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import twilightforest.entity.boss.FallingIceEntity;

import java.util.Random;

public class FallingIceRenderer extends EntityRenderer<FallingIceEntity> {
	public FallingIceRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
		this.shadowSize = 0.5F;
	}

	/**
	 * [VanillaCopy] {@link net.minecraft.client.renderer.entity.FallingBlockRenderer}, but scaled by 3
	 */
	@Override
	public void render(FallingIceEntity entity, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int light) {
		BlockState blockstate = entity.getBlockState();
		if (blockstate.getRenderType() == BlockRenderType.MODEL) {
			World world = entity.getWorldObj();
			if (blockstate != world.getBlockState(entity.getPosition()) && blockstate.getRenderType() != BlockRenderType.INVISIBLE) {
				stack.push();
				BlockPos blockpos = new BlockPos(entity.getPosX(), entity.getBoundingBox().maxY, entity.getPosZ());
				stack.translate(-0.5D, 0.0D, -0.5D);
				stack.scale(3, 3, 3); // TF - scale 3
				BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
				for (net.minecraft.client.renderer.RenderType type : net.minecraft.client.renderer.RenderType.getBlockRenderTypes()) {
					if (RenderTypeLookup.canRenderInLayer(blockstate, type)) {
						net.minecraftforge.client.ForgeHooksClient.setRenderLayer(type);
						blockrendererdispatcher.getBlockModelRenderer().renderModel(world, blockrendererdispatcher.getModelForState(blockstate), blockstate, blockpos, stack, buffer.getBuffer(type), false, new Random(), blockstate.getPositionRandom(entity.getOrigin()), OverlayTexture.NO_OVERLAY);
					}
				}
				net.minecraftforge.client.ForgeHooksClient.setRenderLayer(null);
				stack.pop();
				super.render(entity, entityYaw, partialTicks, stack, buffer, light);
			}
		}
	}

	@Override
	public ResourceLocation getEntityTexture(FallingIceEntity entity) {
		return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
	}
}

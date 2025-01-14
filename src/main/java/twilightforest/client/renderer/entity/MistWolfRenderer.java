package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import twilightforest.TwilightForestMod;

public class MistWolfRenderer extends WolfRenderer {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("mistwolf.png");

	public MistWolfRenderer(EntityRendererManager manager) {
		super(manager);
		this.shadowSize = 1.0F;
	}

	@Override
	protected void preRenderCallback(WolfEntity entity, MatrixStack stack, float partialTicks) {
		float wolfScale = 1.9F;
		stack.scale(wolfScale, wolfScale, wolfScale);

		RenderSystem.enableBlend();
		RenderSystem.disableAlphaTest();
		//GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE);
		RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		//GlStateManager.blendFunc(GL11.GL_ONE_MINUS_DST_ALPHA, GL11.GL_DST_ALPHA);

		float misty = entity.getBrightness() * 3F + 0.25F;
		misty = Math.min(1F, misty);

		float smoky = entity.getBrightness() * 2F + 0.6F;

		RenderSystem.color4f(misty, misty, misty, smoky);
	}

	/**
	 * Queries whether should render the specified pass or not.
	 */

    @Override
	public ResourceLocation getEntityTexture(WolfEntity entity) {
		return textureLoc;
	}
}

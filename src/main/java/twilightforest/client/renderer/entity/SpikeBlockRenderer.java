package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import twilightforest.TwilightForestMod;

public class SpikeBlockRenderer<T extends Entity, M extends EntityModel<T>> extends EntityRenderer<T> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("blockgoblin.png");

	private final Model model;

	public SpikeBlockRenderer(EntityRendererManager manager, M model) {
		super(manager);
		this.model = model;
	}

	@Override
	public void render(T goblin, float yaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int light) {

		stack.push();
		stack.scale(-1.0F, -1.0F, 1.0F);

		IVertexBuilder ivertexbuilder = buffer.getBuffer(this.model.getRenderType(textureLoc));

		stack.rotate(Vector3f.ZP.rotationDegrees(yaw));

		this.model.render(stack, ivertexbuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		stack.pop();
		super.render(goblin, yaw, partialTicks, stack, buffer, light);
	}

	@Override
	public ResourceLocation getEntityTexture(T entity) {
		return textureLoc;
	}
}

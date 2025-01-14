package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.CubeOfAnnihilationModel;
import twilightforest.entity.RovingCubeEntity;

public class RovingCubeRenderer<T extends RovingCubeEntity> extends EntityRenderer<T> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("cubeofannihilation.png");
	private final Model model = new CubeOfAnnihilationModel();

	public RovingCubeRenderer(EntityRendererManager manager) {
		super(manager);
	}

	@Override
	public void render(T entity, float yaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int light) {
		stack.push();

		IVertexBuilder builder = buffer.getBuffer(model.getRenderType(textureLoc));

		stack.scale(2.0F, 2.0F, 2.0F);
		stack.rotate(Vector3f.YP.rotationDegrees(MathHelper.wrapDegrees(entity.ticksExisted + partialTicks) * 11F));
		stack.translate(0F, 0.75F, 0F);
		this.model.render(stack, builder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

		stack.pop();
	}

	@Override
	public ResourceLocation getEntityTexture(T entity) {
		return textureLoc;
	}
}

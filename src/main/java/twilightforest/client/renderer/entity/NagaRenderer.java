package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.entity.NagaModel;
import twilightforest.entity.boss.NagaEntity;

//added charging texture for new renderer
//FIXME legacy: scaling was commented out since textures are bigger now
public class NagaRenderer<M extends NagaModel<NagaEntity>> extends MobRenderer<NagaEntity, M> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("nagahead.png");
	private static final ResourceLocation textureLocDazed = TwilightForestMod.getModelTexture("nagahead_dazed.png");
	private static final ResourceLocation textureLocCharging = TwilightForestMod.getModelTexture("nagahead_charge.png");

	public NagaRenderer(EntityRendererManager manager, M modelbase, float shadowSize) {
		super(manager, modelbase, shadowSize);
	}

	@Override
	public void render(NagaEntity entity, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int light) {
		super.render(entity, entityYaw, partialTicks, stack, buffer, light);
		if (!Minecraft.getInstance().isGamePaused() && entity.isDazed()) {
			Vector3d pos = new Vector3d(entity.getPosX(), entity.getPosY() + 3.15D, entity.getPosZ()).add(new Vector3d(1.5D, 0, 0).rotateYaw((float) Math.toRadians(entity.getRNG().nextInt(360))));
			Minecraft.getInstance().world.addParticle(ParticleTypes.CRIT, pos.x, pos.y, pos.z, 0, 0, 0);
		}
	}

//	@Override
//	protected void preRenderCallback(EntityTFNaga entity, MatrixStack stack, float p_225620_3_) {
//		super.preRenderCallback(entity, stack, p_225620_3_);
//		//make size adjustment
//		stack.translate(0.0F, 1.75F, 0.0F);
//		stack.scale(2.0F, 2.0F, 2.0F);
//	}

	@Override
	public ResourceLocation getEntityTexture(NagaEntity entity) {
		if (entity.isDazed()) {
			return textureLocDazed;
		} else if (entity.isCharging()) {
			return textureLocCharging;
		} else {
			return textureLoc;
		}
	}
}

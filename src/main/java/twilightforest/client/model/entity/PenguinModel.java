// Date: 3/3/2012 11:56:45 PM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import twilightforest.entity.passive.PenguinEntity;

public class PenguinModel extends AgeableModel<PenguinEntity> {
	//fields
	ModelRenderer body;
	ModelRenderer rightarm;
	ModelRenderer leftarm;
	ModelRenderer rightleg;
	ModelRenderer leftleg;
	ModelRenderer head;
	ModelRenderer beak;

	public PenguinModel() {
		textureWidth = 64;
		textureHeight = 32;

		body = new ModelRenderer(this, 32, 0);
		body.addBox(-4F, 0F, -4F, 8, 9, 8);
		body.setRotationPoint(0F, 14F, 0F);

		rightarm = new ModelRenderer(this, 34, 18);
		rightarm.addBox(-1F, -1F, -2F, 1, 8, 4);
		rightarm.setRotationPoint(-4F, 15F, 0F);

		leftarm = new ModelRenderer(this, 24, 18);
		leftarm.addBox(0F, -1F, -2F, 1, 8, 4);
		leftarm.setRotationPoint(4F, 15F, 0F);

		leftarm.mirror = true;

		rightleg = new ModelRenderer(this, 0, 16);
		rightleg.addBox(-2F, 0F, -5F, 4, 1, 8);
		rightleg.setRotationPoint(-2F, 23F, 0F);
		rightleg.setTextureSize(64, 32);

		leftleg = new ModelRenderer(this, 0, 16);
		leftleg.addBox(-2F, 0F, -5F, 4, 1, 8);
		leftleg.setRotationPoint(2F, 23F, 0F);


		head = new ModelRenderer(this, 0, 0);
		head.addBox(-3.5F, -4F, -3.5F, 7, 5, 7);
		head.setRotationPoint(0F, 13F, 0F);

		beak = new ModelRenderer(this, 0, 13);
		beak.addBox(-1F, 0F, -1F, 2, 1, 2);
		beak.setRotationPoint(0F, -1F, -4F);

		head.addChild(beak);
	}

	@Override
	protected Iterable<ModelRenderer> getHeadParts() {
		return ImmutableList.of(this.head);
	}

	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(
				body,
				rightleg,
				leftleg,
				rightarm,
				leftarm
		);
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	@Override
	public void render(MatrixStack stack, IVertexBuilder builder, int light, int overlay, float red, float green, float blue, float scale) {
		if (isChild) {
			float f = 2.0F;
			stack.push();
			stack.scale(1.0F / f, 1.0F / f, 1.0F / f);
			stack.translate(0.0F, 1.5F * scale, 0.0F);
			this.getHeadParts().forEach((renderer) -> renderer.render(stack, builder, light, overlay, red, green, blue, scale));
			stack.pop();

			stack.push();
			stack.scale(1.0F / f, 1.0F / f, 1.0F / f);
			stack.translate(0.0F, 1.5F * scale, 0.0F);
			this.getBodyParts().forEach((renderer) -> renderer.render(stack, builder, light, overlay, red, green, blue, scale));
			stack.pop();
		} else {
			this.getHeadParts().forEach((renderer) -> renderer.render(stack, builder, light, overlay, red, green, blue, scale));
			this.getBodyParts().forEach((renderer) -> renderer.render(stack, builder, light, overlay, red, green, blue, scale));
		}
	}

	@Override
	public void setRotationAngles(PenguinEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		head.rotateAngleX = headPitch / (180F / (float) Math.PI);
		head.rotateAngleY = netHeadYaw / (180F / (float) Math.PI);

		rightleg.rotateAngleX = MathHelper.cos(limbSwing) * 0.7F * limbSwingAmount;
		leftleg.rotateAngleX = MathHelper.cos(limbSwing + (float) Math.PI) * 0.7F * limbSwingAmount;

		rightarm.rotateAngleZ = ageInTicks;
		leftarm.rotateAngleZ = -ageInTicks;
	}
}

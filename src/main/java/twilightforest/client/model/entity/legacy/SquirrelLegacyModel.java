// Date: 4/27/2012 9:49:06 PM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package twilightforest.client.model.entity.legacy;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import twilightforest.entity.passive.SquirrelEntity;

public class SquirrelLegacyModel extends SegmentedModel<SquirrelEntity> {
	//fields
	ModelRenderer body;
	ModelRenderer leg1;
	ModelRenderer leg2;
	ModelRenderer leg3;
	ModelRenderer leg4;
	ModelRenderer head;
	ModelRenderer tail;
	ModelRenderer fluff1;
	ModelRenderer fluff2;
	ModelRenderer fluff3;

	public SquirrelLegacyModel() {
		textureWidth = 32;
		textureHeight = 32;

//		setTextureOffset("tail.fluff1", 0, 20);
//		setTextureOffset("tail.fluff2", 0, 20);
//		setTextureOffset("tail.fluff3", 0, 26);

		body = new ModelRenderer(this, 0, 8);
		body.addBox(-2F, -1F, -2F, 4, 3, 5);
		body.setRotationPoint(0F, 21F, 0F);
		body.setTextureSize(32, 32);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		leg1 = new ModelRenderer(this, 0, 16);
		leg1.addBox(0F, 0F, 0F, 1, 1, 1);
		leg1.setRotationPoint(-2F, 23F, 2F);
		leg1.setTextureSize(32, 32);
		leg1.mirror = true;
		setRotation(leg1, 0F, 0F, 0F);
		leg2 = new ModelRenderer(this, 0, 16);
		leg2.addBox(0F, 0F, 0F, 1, 1, 1);
		leg2.setRotationPoint(1F, 23F, 2F);
		leg2.setTextureSize(32, 32);
		leg2.mirror = true;
		setRotation(leg2, 0F, 0F, 0F);
		leg3 = new ModelRenderer(this, 0, 16);
		leg3.addBox(0F, 0F, 0F, 1, 1, 1);
		leg3.setRotationPoint(-2F, 23F, -2F);
		leg3.setTextureSize(32, 32);

		setRotation(leg3, 0F, 0F, 0F);
		leg4 = new ModelRenderer(this, 0, 16);
		leg4.addBox(0F, 0F, 0F, 1, 1, 1);
		leg4.setRotationPoint(1F, 23F, -2F);
		leg4.setTextureSize(32, 32);

		setRotation(leg4, 0F, 0F, 0F);
		head = new ModelRenderer(this/*, "head"*/);
		head.setRotationPoint(0F, 22F, -2F);
		setRotation(head, 0F, 0F, 0F);

		head.setTextureOffset(0, 0).addBox(-2F, -5F, -3F, 4, 4, 4);
		head.setTextureOffset(16, 0).addBox(-2F, -6F, -0.5F, 1, 1, 1);
		head.setTextureOffset(16, 0).addBox(1F, -6F, -0.5F, 1, 1, 1);

		tail = new ModelRenderer(this/*, "tail"*/);
		tail.setRotationPoint(0F, 21F, 2F);
		tail.setTextureOffset(0, 18).addBox(-0.5F, -1.5F, 0.5F, 1, 1, 1);

		fluff1 = new ModelRenderer(this, 0, 20);
		fluff1.addBox(-1.5F, -4F, 1F, 3, 3, 3);
		tail.addChild(fluff1);

		fluff2 = new ModelRenderer(this, 0, 20);
		fluff2.addBox(0F, -3F, -1.5F, 3, 3, 3);
		fluff2.setRotationPoint(-1.5F, -4F, 2.5F);
		fluff1.addChild(fluff2);

		fluff3 = new ModelRenderer(this, 0, 26);
		fluff3.addBox(1.5F, -3F, -1.5F, 3, 3, 3);
		fluff3.setRotationPoint(-1.5F, -3F, 0F);
		fluff2.addChild(fluff3);
	}

//	@Override
//	public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
//		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
//		setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
//	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(
				body,
				leg1,
				leg2,
				leg3,
				leg4,
				head,
				tail
		);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setLivingAnimations(SquirrelEntity entity, float limbSwing, float limbSwingAmount, float partialTickTime) {
		//EntityTFSquirrel squirrel = (EntityTFSquirrel)entity;
	}

	@Override
	public void setRotationAngles(SquirrelEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.rotateAngleX = headPitch / (180F / (float) Math.PI);
		this.head.rotateAngleY = netHeadYaw / (180F / (float) Math.PI);
		this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

		if (limbSwingAmount > 0.2) {
			float wiggle = Math.min(limbSwingAmount, 0.6F);
			this.tail.rotateAngleX = (MathHelper.cos(ageInTicks * 0.6662F) - (float) Math.PI / 3) * wiggle;
			this.fluff2.rotateAngleX = MathHelper.cos(ageInTicks * 0.7774F) * 1.2F * wiggle;
			this.fluff3.rotateAngleX = MathHelper.cos(ageInTicks * 0.8886F + (float) Math.PI / 2) * 1.4F * wiggle;
		} else {
			this.tail.rotateAngleX = 0.2F + MathHelper.cos(ageInTicks * 0.3335F) * 0.15F;
			this.fluff2.rotateAngleX = 0.1F + MathHelper.cos(ageInTicks * 0.4445F) * 0.20F;
			this.fluff3.rotateAngleX = 0.1F + MathHelper.cos(ageInTicks * 0.5555F) * 0.25F;
		}
	}
}

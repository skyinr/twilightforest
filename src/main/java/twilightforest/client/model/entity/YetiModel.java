package twilightforest.client.model.entity;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import twilightforest.entity.YetiEntity;

public class YetiModel<T extends YetiEntity> extends BipedModel<T> {

	public ModelRenderer mouth;
	public ModelRenderer leftEye;
	public ModelRenderer rightEye;
	public ModelRenderer angryLeftEye;
	public ModelRenderer angryRightEye;

	public YetiModel() {
		super(0.0F, 0.0F, 128, 64);

        this.bipedHead = new ModelRenderer(this, 0, 0);
		this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 0, 0, 0);
		this.bipedHeadwear = new ModelRenderer(this, 32, 0);
		this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 0, 0, 0);

		this.bipedBody = new ModelRenderer(this, 32, 0);
		this.bipedBody.addBox(-10.0F, 0.0F, -6.0F, 20, 26, 12);
		this.bipedBody.setRotationPoint(0.0F, -14.0F, 0.0F);

		this.mouth = new ModelRenderer(this, 96, 6);
		this.mouth.addBox(-7.0F, -5.0F, -0.5F, 14, 10, 1);
		this.mouth.setRotationPoint(0.0F, 12.0F, -6.0F);
		this.bipedBody.addChild(mouth);

		this.rightEye = new ModelRenderer(this, 96, 0);
		this.rightEye.addBox(-2.5F, -2.5F, -0.5F, 5, 5, 1);
		this.rightEye.setRotationPoint(-5.5F, 4.5F, -6.0F);
		this.bipedBody.addChild(rightEye);

		this.leftEye = new ModelRenderer(this, 96, 0);
		this.leftEye.addBox(-2.5F, -2.5F, -0.5F, 5, 5, 1);
		this.leftEye.setRotationPoint(5.5F, 4.5F, -6.0F);
		this.bipedBody.addChild(leftEye);

		this.angryRightEye = new ModelRenderer(this, 109, 0);
		this.angryRightEye.addBox(-2.5F, -2.5F, -0.5F, 5, 5, 1);
		this.angryRightEye.setRotationPoint(5.5F, 4.5F, -6.0F);
		this.bipedBody.addChild(angryRightEye);

		this.angryLeftEye = new ModelRenderer(this, 109, 0);
		this.angryLeftEye.addBox(-2.5F, -2.5F, -0.5F, 5, 5, 1);
		this.angryLeftEye.setRotationPoint(-5.5F, 4.5F, -6.0F);
		this.bipedBody.addChild(angryLeftEye);

		this.bipedRightArm = new ModelRenderer(this, 0, 0);
		this.bipedRightArm.addBox(-5.0F, -2.0F, -3.0F, 6, 16, 6);
		this.bipedRightArm.setRotationPoint(-11.0F, -4.0F, 0.0F);
		this.bipedLeftArm = new ModelRenderer(this, 0, 0);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.addBox(-1.0F, -2.0F, -3.0F, 6, 16, 6);
		this.bipedLeftArm.setRotationPoint(11.0F, -4.0F, 0.0F);
		this.bipedRightLeg = new ModelRenderer(this, 0, 22);
		this.bipedRightLeg.addBox(-4.0F, 0.0F, -4.0F, 8, 12, 8);
		this.bipedRightLeg.setRotationPoint(-6.0F, 12.0F, 0.0F);
		this.bipedLeftLeg = new ModelRenderer(this, 0, 22);
		this.bipedLeftLeg.mirror = true;
		this.bipedLeftLeg.addBox(-4.0F, 0.0F, -4.0F, 8, 12, 8);
		this.bipedLeftLeg.setRotationPoint(6.0F, 12.0F, 0.0F);
	}

	/**
	 * Sets the model's various rotation angles. For bipeds, limbSwing and limbSwingAmount are used for animating the movement of arms
	 * and legs, where limbSwing represents the time(so that arms and legs swing back and forth) and limbSwingAmount represents how
	 * "far" arms and legs can swing at most.
	 */
	@Override
	public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.bipedHead.rotateAngleY = netHeadYaw / (180F / (float) Math.PI);
		this.bipedHead.rotateAngleX = headPitch / (180F / (float) Math.PI);
		this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
		this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
		this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
		this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
		this.bipedRightArm.rotateAngleZ = 0.0F;
		this.bipedLeftArm.rotateAngleZ = 0.0F;
		this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.bipedRightLeg.rotateAngleY = 0.0F;
		this.bipedLeftLeg.rotateAngleY = 0.0F;

		if (entity.isBeingRidden()) {
			// arms up!
			this.bipedRightArm.rotateAngleX += Math.PI;
			this.bipedLeftArm.rotateAngleX += Math.PI;
		}

		if (this.leftArmPose != ArmPose.EMPTY) {
			this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F);
		}

		if (this.rightArmPose != ArmPose.EMPTY) {
			this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F);
		}

		this.bipedRightArm.rotateAngleY = 0.0F;
		this.bipedLeftArm.rotateAngleY = 0.0F;

		this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
		this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;

		// if yeti is angry, hold arms forwards like a zombie
		if (entity.isAngry()) {
			float f6 = MathHelper.sin(this.swingProgress * (float) Math.PI);
			float f7 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float) Math.PI);
			this.bipedRightArm.rotateAngleZ = 0.0F;
			this.bipedLeftArm.rotateAngleZ = 0.0F;
			this.bipedRightArm.rotateAngleY = -(0.1F - f6 * 0.6F);
			this.bipedLeftArm.rotateAngleY = 0.1F - f6 * 0.6F;
			this.bipedRightArm.rotateAngleX = -((float) Math.PI / 2F);
			this.bipedLeftArm.rotateAngleX = -((float) Math.PI / 2F);
			this.bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
			this.bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
			this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
			this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
			this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
			this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
		}
	}

	/**
	 * Change eye color if yeti is angry
	 */
	@Override
	public void setLivingAnimations(T entity, float limbSwing, float limbSwingAmount, float partialTicks) {
		if (entity.isAngry()) {
			this.rightEye.showModel = false;
			this.leftEye.showModel = false;
			this.angryRightEye.showModel = true;
			this.angryLeftEye.showModel = true;
		} else {
			this.rightEye.showModel = true;
			this.leftEye.showModel = true;
			this.angryRightEye.showModel = false;
			this.angryLeftEye.showModel = false;
		}
	}
}

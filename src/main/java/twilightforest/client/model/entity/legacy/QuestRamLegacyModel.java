// Date: 5/18/2012 11:45:03 PM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package twilightforest.client.model.entity.legacy;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.util.math.MathHelper;
import twilightforest.entity.passive.QuestRamEntity;

public class QuestRamLegacyModel extends SegmentedModel<QuestRamEntity> {
	//fields
	ModelRenderer frontbody;
	ModelRenderer rearbody;
	ModelRenderer leg1;
	ModelRenderer haunch1;
	ModelRenderer leg2;
	ModelRenderer haunch2;
	ModelRenderer leg3;
	ModelRenderer haunch3;
	ModelRenderer leg4;
	ModelRenderer haunch4;
	ModelRenderer neck;
	ModelRenderer nose;
	public ModelRenderer head;

	ModelRenderer[] segments;

	int[] colorOrder = new int[]{0, 8, 7, 15, 14, 1, 4, 5, 13, 3, 9, 11, 10, 2, 6, 12};

	public QuestRamLegacyModel() {
		textureWidth = 128;
		textureHeight = 128;


		frontbody = new ModelRenderer(this, 0, 0);
		frontbody.addBox(-9F, -7.5F, -15F, 18, 15, 15);
		frontbody.setRotationPoint(0F, -1F, 2F);

		rearbody = new ModelRenderer(this, 0, 30);
		rearbody.addBox(-9F, -7.5F, 0F, 18, 15, 15);
		rearbody.setRotationPoint(0F, -1F, 4F);

		leg1 = new ModelRenderer(this, 66, 0);
		leg1.addBox(-3F, 10F, -3F, 6, 12, 6);
		leg1.setRotationPoint(-6F, 2F, 13F);

		haunch1 = new ModelRenderer(this, 90, 0);
		haunch1.addBox(-3.5F, 0F, -6F, 7, 10, 10);
		haunch1.setRotationPoint(-6F, 2F, 13F);

		leg2 = new ModelRenderer(this, 66, 0);
		leg2.addBox(-3F, 10F, -3F, 6, 12, 6);
		leg2.setRotationPoint(6F, 2F, 13F);

		haunch2 = new ModelRenderer(this, 90, 0);
		haunch2.addBox(-3.5F, 0F, -6F, 7, 10, 10);
		haunch2.setRotationPoint(6F, 2F, 13F);

		leg3 = new ModelRenderer(this, 66, 18);
		leg3.addBox(-3F, 10F, -3F, 6, 13, 6);
		leg3.setRotationPoint(-6F, 1F, -8F);

		haunch3 = new ModelRenderer(this, 90, 20);
		haunch3.addBox(-3.5F, 0F, -4F, 7, 10, 7);
		haunch3.setRotationPoint(-6F, 1F, -8F);

		leg4 = new ModelRenderer(this, 66, 18);
		leg4.addBox(-3F, 10F, -3F, 6, 13, 6);
		leg4.setRotationPoint(6F, 1F, -8F);

		haunch4 = new ModelRenderer(this, 90, 20);
		haunch4.addBox(-3.5F, 0F, -4F, 7, 10, 7);
		haunch4.setRotationPoint(6F, 1F, -8F);

		neck = new ModelRenderer(this, 66, 37);
		neck.addBox(-5.5F, -8F, -8F, 11, 14, 12);
		neck.setRotationPoint(0F, -8F, -7F);

		setRotation(neck, 0.2617994F, 0F, 0F);

		head = new ModelRenderer(this/*, "head"*/);
		head.setRotationPoint(0F, -13F, -5F);

		head.setTextureOffset(0, 70).addBox(-6F, -4.5F, -15F, 12, 9, 15);
		head.setTextureOffset(0, 94).addBox(5F, -9F, -7F, 4, 4, 6);
		head.setTextureOffset(20, 96).addBox(7F, -8F, -2F, 3, 4, 4);
		head.setTextureOffset(34, 95).addBox(8F, -6F, 0F, 3, 6, 3);
		head.setTextureOffset(46, 98).addBox(9.5F, -2F, -2F, 3, 3, 3);
		head.setTextureOffset(58, 95).addBox(11F, 0F, -7F, 3, 3, 6);
		head.setTextureOffset(76, 95).addBox(12F, -4F, -9F, 3, 6, 3);
		head.setTextureOffset(88, 97).addBox(13F, -6F, -7F, 3, 3, 4);
		head.setTextureOffset(0, 94).addBox(-9F, -9F, -7F, 4, 4, 6);
		head.setTextureOffset(20, 96).addBox(-10F, -8F, -2F, 3, 4, 4);
		head.setTextureOffset(34, 95).addBox(-11F, -6F, 0F, 3, 6, 3);
		head.setTextureOffset(46, 98).addBox(-12.5F, -2F, -2F, 3, 3, 3);
		head.setTextureOffset(58, 95).addBox(-14F, 0F, -7F, 3, 3, 6);
		head.setTextureOffset(76, 95).addBox(-15F, -4F, -9F, 3, 6, 3);
		head.setTextureOffset(88, 97).addBox(-16F, -6F, -7F, 3, 3, 4);

		nose = new ModelRenderer(this, 54, 73);
		nose.addBox(-5.5F, -5F, -13F, 11, 9, 12);
		nose.setRotationPoint(0F, -7F, -1F);
		nose.setTextureSize(128, 128);
		setRotation(nose, 0.5235988F, 0F, 0F);
		head.addChild(nose);

		segments = new ModelRenderer[16];
		for (int i = 0; i < 16; i++) {
			segments[i] = new ModelRenderer(this, 0, 104);
			segments[i].addBox(-9F, -7.5F, 0F, 18, 15, 2);
			segments[i].setRotationPoint(0F, -1F, 2F);
			segments[i].showModel = false;
		}
	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(
				frontbody,
				rearbody,
				leg1,
				haunch1,
				leg2,
				haunch2,
				leg3,
				haunch3,
				leg4,
				haunch4,
				neck,
				head
		);
	}

	@Override
	public void render(MatrixStack stack, IVertexBuilder builder, int light, int overlay, float red, float green, float blue, float alpha) {
		super.render(stack, builder, light, overlay, red, green, blue, alpha);

		for (int i = 0; i < 16; i++) {
			final float[] dyeRgb = SheepEntity.getDyeRgb(DyeColor.byId(i));
			segments[i].render(stack, builder, light, overlay, dyeRgb[0], dyeRgb[1], dyeRgb[2], alpha);
		}
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(QuestRamEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.rotateAngleX = headPitch / (180F / (float) Math.PI);
		this.head.rotateAngleY = netHeadYaw / (180F / (float) Math.PI);

		this.neck.rotateAngleY = this.head.rotateAngleY;

		this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
		this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
		this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
		this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
		this.haunch1.rotateAngleX = this.leg1.rotateAngleX;
		this.haunch2.rotateAngleX = this.leg2.rotateAngleX;
		this.haunch3.rotateAngleX = this.leg3.rotateAngleX;
		this.haunch4.rotateAngleX = this.leg4.rotateAngleX;
	}

	@Override
	public void setLivingAnimations(QuestRamEntity entity, float limbSwing, float limbSwingAmount, float partialTicks) {

		// how many colors should we display?
		int count = entity.countColorsSet();

		this.rearbody.rotationPointZ = 2 + 2 * count;
		this.leg1.rotationPointZ = 11 + 2 * count;
		this.leg2.rotationPointZ = 11 + 2 * count;
		this.haunch1.rotationPointZ = 11 + 2 * count;
		this.haunch2.rotationPointZ = 11 + 2 * count;

		// set up the colors displayed in color order
		int segmentOffset = 2;
		for (int color : colorOrder) {
			if (entity.isColorPresent(DyeColor.byId(color))) {
				segments[color].showModel = true;
				segments[color].rotationPointZ = segmentOffset;

				segmentOffset += 2;
			} else {
				segments[color].showModel = false;
			}
		}
	}
}

package twilightforest.client.model.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.entity.LowerGoblinKnightEntity;

/**
 * ModelTFGoblinKnightLower - MCVinnyq
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class LowerGoblinKnightModel extends BipedModel<LowerGoblinKnightEntity> {
    public ModelRenderer tunic;

    public LowerGoblinKnightModel() {
        super(0, 0, 128, 64);
        this.bipedRightArm = new ModelRenderer(this, 48, 48);
        this.bipedRightArm.setRotationPoint(-3.5F, 10.0F, 0.0F);
        this.bipedRightArm.addBox(-2.0F, -2.0F, -1.5F, 2.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(bipedRightArm, 0.0F, 0.0F, 0.10000000116728046F);
        this.tunic = new ModelRenderer(this, 64, 19);
        this.tunic.setRotationPoint(0.0F, 7.5F, 0.0F);
        this.tunic.addBox(-6.0F, 0.0F, -3.0F, 12.0F, 9.0F, 6.0F, 0.0F, 0.0F, 0.0F);
        this.bipedHead = new ModelRenderer(this, 0, 30);
        this.bipedHead.setRotationPoint(0.0F, 10.0F, 1.0F);
        this.bipedHead.addBox(-2.5F, -5.0F, -3.5F, 5.0F, 5.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 38, 48);
        this.bipedLeftArm.setRotationPoint(3.5F, 10.0F, 0.0F);
        this.bipedLeftArm.addBox(0.0F, -2.0F, -1.5F, 2.0F, 8.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(bipedLeftArm, 0.0F, 0.0F, -0.10000736647217022F);
        this.bipedLeftLeg = new ModelRenderer(this, 0, 52);
        this.bipedLeftLeg.setRotationPoint(2.5F, 16.0F, 0.0F);
        this.bipedLeftLeg.addBox(-1.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.bipedRightLeg = new ModelRenderer(this, 0, 40);
        this.bipedRightLeg.setRotationPoint(-2.5F, 16.0F, 0.0F);
        this.bipedRightLeg.addBox(-3.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.bipedHeadwear = new ModelRenderer(this, 0, 0);
        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHeadwear.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        this.bipedBody = new ModelRenderer(this, 16, 48);
        this.bipedBody.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.bipedBody.addBox(-3.5F, 0.0F, -2.0F, 7.0F, 8.0F, 4.0F, 0.0F, 0.0F, 0.0F);
    }

    @Override
    public void render(MatrixStack stack, IVertexBuilder builder, int light, int overlay, float red, float green, float blue, float scale) {
        super.render(stack, builder, light, overlay, red, green, blue, scale);
        this.tunic.render(stack, builder, light, overlay, red, green, blue, scale);
    }

    @Override
    public void setRotationAngles(LowerGoblinKnightEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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
        this.bipedRightLeg.rotateAngleZ = 0.0F;
        this.bipedLeftLeg.rotateAngleZ = 0.0F;

        if (this.isSitting) {
            this.bipedRightArm.rotateAngleX += (-(float)Math.PI / 5F);
            this.bipedLeftArm.rotateAngleX += (-(float)Math.PI / 5F);
            this.bipedRightLeg.rotateAngleX = -1.4137167F;
            this.bipedRightLeg.rotateAngleY = ((float)Math.PI / 10F);
            this.bipedRightLeg.rotateAngleZ = 0.07853982F;
            this.bipedLeftLeg.rotateAngleX = -1.4137167F;
            this.bipedLeftLeg.rotateAngleY = (-(float)Math.PI / 10F);
            this.bipedLeftLeg.rotateAngleZ = -0.07853982F;
        }

        if (entity.isBeingRidden()) {
            this.bipedHead.rotateAngleY = 0;
            this.bipedHead.rotateAngleX = 0;
            this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
            this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
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

        this.tunic.showModel = entity.hasArmor();
    }
    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

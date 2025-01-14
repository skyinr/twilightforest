package twilightforest.client.model.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.model.ModelRenderer;

//This model doesnt require a legacy as the tongue will only show up in newer versions
public class NagaTrophyModel extends GenericTrophyModel {

	public final ModelRenderer head;
	public final ModelRenderer tongue;

	public NagaTrophyModel() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-8F, -16F, -8F, 16, 16, 16, 0.0F);
		this.head.setRotationPoint(0F, -4F, 0F);
		this.tongue = new ModelRenderer(this, 0, 0);
		this.tongue.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.tongue.setTextureOffset(42, 0).addBox(-3.0F, -3.0F, -14.0F, 6.0F, 0.0F, 6.0F, 0.0F, 0.0F, 0.0F);
		this.head.addChild(this.tongue);
	}

	@Override
	public void setRotations(float x, float y, float z) {
		super.setRotations(x, y, z);
		this.head.rotateAngleY = y * ((float) Math.PI / 180F);
		this.head.rotateAngleX = x * ((float) Math.PI / 180F);
	}

	@Override
	public void render(MatrixStack matrix, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.head.render(matrix, buffer, packedLight, packedOverlay, red, green, blue, alpha); 
	}
}

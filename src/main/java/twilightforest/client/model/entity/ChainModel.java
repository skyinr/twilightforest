package twilightforest.client.model.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ChainModel extends SegmentedModel<Entity> {
	ModelRenderer chain;

	public ChainModel() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		chain = new ModelRenderer(this, 56, 36);
		chain.addBox(-1F, -1F, -1F, 2, 2, 2, 0F);
		chain.setRotationPoint(0F, 0F, 0F);

	}

	@Override
	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(chain);
	}

	@Override
	public void setRotationAngles(Entity entity, float v, float v1, float v2, float v3, float v4) { }
}

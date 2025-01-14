package twilightforest.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.TFConfig;
import twilightforest.TwilightForestMod;
import twilightforest.block.KeepsakeCasketBlock;
import twilightforest.block.AbstractTrophyBlock;
import twilightforest.client.renderer.tileentity.TrophyTileEntityRenderer;
import twilightforest.enums.BossVariant;
import twilightforest.tileentity.KeepsakeCasketTileEntity;

public class ISTER extends ItemStackTileEntityRenderer {
	private final ResourceLocation typeId;
	private TileEntity dummy;

	// When this is called from Item register, TEType register has not run yet so we can't pass the actual object
	public ISTER(ResourceLocation typeId) {
		this.typeId = typeId;
	}

	@Override
	public void func_239207_a_(ItemStack stack, ItemCameraTransforms.TransformType camera, MatrixStack ms, IRenderTypeBuffer buffers, int light, int overlay) {

		if (dummy == null) {
			dummy = ForgeRegistries.TILE_ENTITIES.getValue(typeId).create();
		}
		Item item = stack.getItem();
		if (item instanceof BlockItem) {
			Block block = ((BlockItem) item).getBlock();
			if (block instanceof AbstractTrophyBlock) {
				if(camera == ItemCameraTransforms.TransformType.GUI) {

					ModelResourceLocation back = new ModelResourceLocation(TwilightForestMod.prefix(((AbstractTrophyBlock) block).getVariant().getTrophyType().getModelName()), "inventory");
					IBakedModel modelBack = Minecraft.getInstance().getItemRenderer().getItemModelMesher().getModelManager().getModel(back);

					ms.push();
					ms.translate(0.5F, 0.5F, -1.5F);
					Minecraft.getInstance().getItemRenderer().renderItem(TrophyTileEntityRenderer.stack, ItemCameraTransforms.TransformType.GUI, false, ms, buffers, 240, overlay, ForgeHooksClient.handleCameraTransforms(ms, modelBack, camera, false));
					ms.pop();

					ms.push();
					ms.translate(0.5F, 0.5F, 0.5F);
					if(((AbstractTrophyBlock) block).getVariant() == BossVariant.HYDRA || ((AbstractTrophyBlock) block).getVariant() == BossVariant.QUEST_RAM) ms.scale(0.9F, 0.9F, 0.9F);
					ms.rotate(Vector3f.XP.rotationDegrees(30));
					ms.rotate(Vector3f.YN.rotationDegrees(TFConfig.CLIENT_CONFIG.rotateTrophyHeadsGui.get() ? TFClientEvents.rotationTicker : -45));
					ms.translate(-0.5F, -0.5F, -0.5F);
					ms.translate(0.0F, 0.25F, 0.0F);
					if(((AbstractTrophyBlock) block).getVariant() == BossVariant.UR_GHAST) ms.translate(0.0F, 0.5F, 0.0F);
					if(((AbstractTrophyBlock) block).getVariant() == BossVariant.ALPHA_YETI) ms.translate(0.0F, -0.15F, 0.0F);
					TrophyTileEntityRenderer.render((Direction) null, 180.0F, ((AbstractTrophyBlock) block).getVariant(), 0.0F, ms, buffers, light, camera);
					ms.pop();

				} else {
					TrophyTileEntityRenderer.render((Direction) null, 180.0F, ((AbstractTrophyBlock) block).getVariant(), 0.0F, ms, buffers, light, camera);
				}
			} else if (block instanceof KeepsakeCasketBlock) {
				TileEntityRendererDispatcher.instance.renderItem(new KeepsakeCasketTileEntity(), ms, buffers, light, overlay);
			} else {
				TileEntityRenderer<TileEntity> renderer = TileEntityRendererDispatcher.instance.getRenderer(dummy);
				renderer.render(null, 0, ms, buffers, light, overlay);
			}
		}
	}
}

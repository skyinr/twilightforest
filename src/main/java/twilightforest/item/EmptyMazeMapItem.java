package twilightforest.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AbstractMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EmptyMazeMapItem extends AbstractMapItem {
	boolean mapOres;

	protected EmptyMazeMapItem(boolean mapOres, Properties props) {
		super(props);
		this.mapOres = mapOres;
	}

	// [VanillaCopy] MapItem.onItemRightClick calling own setup method
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = MazeMapItem.setupNewMap(worldIn, MathHelper.floor(playerIn.getPosX()), MathHelper.floor(playerIn.getPosZ()), (byte) 0, true, false, MathHelper.floor(playerIn.getPosY()), this.mapOres);
		ItemStack itemstack1 = playerIn.getHeldItem(handIn);
		if (!playerIn.abilities.isCreativeMode) {
			itemstack1.shrink(1);
		}

		if (itemstack1.isEmpty()) {
			return ActionResult.resultSuccess(itemstack);
		} else {
			if (!playerIn.inventory.addItemStackToInventory(itemstack.copy())) {
				playerIn.dropItem(itemstack, false);
			}

			playerIn.addStat(Stats.ITEM_USED.get(this));
			return ActionResult.resultSuccess(itemstack1);
		}
	}
}

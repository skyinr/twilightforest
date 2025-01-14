package twilightforest.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import twilightforest.entity.projectile.IceArrowEntity;

public class IceBowItem extends BowItem {

	public IceBowItem(Properties props) {
		super(props);
	}

	@Override
	public AbstractArrowEntity customArrow(AbstractArrowEntity arrow) {
		if (arrow.getShooter() instanceof LivingEntity) {
			return new IceArrowEntity(arrow.world, (LivingEntity) arrow.getShooter());
		}
		return arrow;
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repairWith) {
		return repairWith.getItem() == Blocks.ICE.asItem() || super.getIsRepairable(toRepair, repairWith);
	}
}

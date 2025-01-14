package twilightforest.item;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;

public class IronwoodAxeItem extends AxeItem {

	protected IronwoodAxeItem(IItemTier material, Properties props) {
		super(material, 6F, material.getEfficiency() * 0.05f - 3.4f, props);
	}

	@Override
	public void fillItemGroup(ItemGroup tab, NonNullList<ItemStack> list) {
		if (isInGroup(tab)) {
			ItemStack istack = new ItemStack(this);
			istack.addEnchantment(Enchantments.FORTUNE, 1);
			list.add(istack);
		}
	}
}

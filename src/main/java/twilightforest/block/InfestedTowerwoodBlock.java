package twilightforest.block;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.server.ServerWorld;
import twilightforest.entity.TowerwoodBorerEntity;
import twilightforest.entity.TFEntities;

public class InfestedTowerwoodBlock extends FlammableBlock {

	public InfestedTowerwoodBlock(int flammability, int spreadSpeed, Properties props) {
		super(flammability, spreadSpeed, props);
	}

	@Override
	@Deprecated
	public void spawnAdditionalDrops(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
		super.spawnAdditionalDrops(state, world, pos, stack);
		if (!world.isRemote && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
			TowerwoodBorerEntity termite = new TowerwoodBorerEntity(TFEntities.tower_termite, world);
			termite.setLocationAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 0.0F, 0.0F);
			world.addEntity(termite);
			termite.spawnExplosionParticle();
		}

	}
}

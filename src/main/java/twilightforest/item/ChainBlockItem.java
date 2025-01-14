package twilightforest.item;

import com.google.common.collect.Sets;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.UseAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import twilightforest.TFSounds;
import twilightforest.entity.ChainBlockEntity;
import twilightforest.entity.TFEntities;
import twilightforest.enums.TwilightItemTier;

import javax.annotation.Nullable;
import java.util.UUID;

public class ChainBlockItem extends ToolItem {

	private static final String THROWN_UUID_KEY = "chainEntity";

	protected ChainBlockItem(Properties props) {
		super(6, -3.0F, TwilightItemTier.TOOL_KNIGHTLY, Sets.newHashSet(Blocks.STONE), props);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity holder, int slot, boolean isSelected) {
		if (!world.isRemote && getThrownUuid(stack) != null && getThrownEntity(world, stack) == null) {
			stack.getTag().remove(THROWN_UUID_KEY);
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getHeldItem(hand);

		if (getThrownUuid(stack) != null)
			return new ActionResult<>(ActionResultType.PASS, stack);

		player.playSound(TFSounds.BLOCKCHAIN_FIRED, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F));

		if (!world.isRemote) {
			ChainBlockEntity launchedBlock = new ChainBlockEntity(TFEntities.chain_block, world, player, hand);
			world.addEntity(launchedBlock);
			setThrownEntity(stack, launchedBlock);

			stack.damageItem(1, player, (user) -> user.sendBreakAnimation(hand));
		}

		player.setActiveHand(hand);
		return new ActionResult<>(ActionResultType.SUCCESS, stack);
	}

	@Nullable
	protected static UUID getThrownUuid(ItemStack stack) {
		if (stack.hasTag() && stack.getTag().hasUniqueId(THROWN_UUID_KEY)) {
			return stack.getTag().getUniqueId(THROWN_UUID_KEY);
		}

		return null;
	}

	@Nullable
	private static ChainBlockEntity getThrownEntity(World world, ItemStack stack) {
		if (world instanceof ServerWorld) {
			UUID id = getThrownUuid(stack);
			if (id != null) {
				Entity e = ((ServerWorld) world).getEntityByUuid(id);
				if (e instanceof ChainBlockEntity) {
					return (ChainBlockEntity) e;
				}
			}
		}

		return null;
	}

	private static void setThrownEntity(ItemStack stack, ChainBlockEntity cube) {
		if (!stack.hasTag()) {
			stack.setTag(new CompoundNBT());
		}
		stack.getTag().putUniqueId(THROWN_UUID_KEY, cube.getUniqueID());
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BLOCK;
	}

	@Override
	public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
		return true;
	}

	@Override
	public int getHarvestLevel(ItemStack stack, ToolType tool, @Nullable PlayerEntity player, @Nullable BlockState blockState) {
		if (tool == ToolType.PICKAXE) {
			return 2;
		} else {
			return -1;
		}
	}
}

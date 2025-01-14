package twilightforest.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import twilightforest.entity.CubeOfAnnihilationEntity;
import twilightforest.entity.TFEntities;

import javax.annotation.Nullable;
import java.util.UUID;

public class CubeOfAnnihilationItem extends Item {

	private static final String THROWN_UUID_KEY = "cubeEntity";

	protected CubeOfAnnihilationItem(Properties props) {
		super(props);
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

		if (!world.isRemote) {
			CubeOfAnnihilationEntity launchedCube = new CubeOfAnnihilationEntity(TFEntities.cube_of_annihilation, world, player);
			world.addEntity(launchedCube);
			setThrownEntity(stack, launchedCube);
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
	private static CubeOfAnnihilationEntity getThrownEntity(World world, ItemStack stack) {
		if (world instanceof ServerWorld) {
			UUID id = getThrownUuid(stack);
			if (id != null) {
				Entity e = ((ServerWorld) world).getEntityByUuid(id);
				if (e instanceof CubeOfAnnihilationEntity) {
					return (CubeOfAnnihilationEntity) e;
				}
			}
		}

		return null;
	}

	private static void setThrownEntity(ItemStack stack, CubeOfAnnihilationEntity cube) {
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
}

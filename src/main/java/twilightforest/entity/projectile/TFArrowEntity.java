package twilightforest.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class TFArrowEntity extends AbstractArrowEntity implements ITFProjectile {

	public TFArrowEntity(EntityType<? extends TFArrowEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public TFArrowEntity(EntityType<? extends TFArrowEntity> type, World worldIn, LivingEntity shooter) {
		super(type, shooter, worldIn);
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected ItemStack getArrowStack() {
		return new ItemStack(Items.ARROW);
	}
}

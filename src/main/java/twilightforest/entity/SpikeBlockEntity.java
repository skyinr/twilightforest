package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.Pose;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;

public class SpikeBlockEntity extends BlockChainGoblinEntity.MultipartGenericsAreDumb {

	@Override
	public EntitySize getSize(Pose pos) {
		return EntitySize.flexible(0.75F, 0.75F);
	}

	public SpikeBlockEntity(Entity goblin) {
		super(goblin);
		realSize = EntitySize.flexible(0.75F, 0.75F);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public boolean isEntityEqual(Entity entity) {
		return this == entity || getParent() == entity;
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void registerData() {
	}

	@Override
	protected boolean canBeRidden(Entity entityIn) {
		return false;
	}

	@Override
	protected void readAdditional(CompoundNBT compound) {
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
	}
}

package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ProtectionBoxEntity extends Entity {

	public int lifeTime;

	public final int sizeX;
	public final int sizeY;
	public final int sizeZ;

	private final MutableBoundingBox sbb;

	public ProtectionBoxEntity(EntityType<?> type, World world) {
		super(type, world);
		sizeX = sizeY = sizeZ = 0;
		sbb = null;
	}

	public ProtectionBoxEntity(World world, MutableBoundingBox sbb) {
		super(TFEntities.protection_box, world);

		this.sbb = new MutableBoundingBox(sbb);

		this.setLocationAndAngles(sbb.minX, sbb.minY, sbb.minZ, 0.0F, 0.0F);

		sizeX = sbb.getXSize();
		sizeY = sbb.getYSize();
		sizeZ = sbb.getZSize();

		this.size = EntitySize.fixed(Math.max(sizeX, sizeZ), sizeY);

		lifeTime = 60;
	}

	@Override
	public void tick() {
		super.tick();

		if (lifeTime <= 1) {
			remove();
		} else {
			lifeTime--;
		}
	}

	public boolean matches(MutableBoundingBox sbb) {
		return this.sbb.minX == sbb.minX && this.sbb.minY == sbb.minY && this.sbb.minZ == sbb.minZ
				&& this.sbb.maxX == sbb.maxX && this.sbb.maxY == sbb.maxY && this.sbb.maxZ == sbb.maxZ;
	}

	public void resetLifetime() {
		lifeTime = 60;
	}

	@Override
	public float getBrightness() {
		return 1.0F;
	}

	@Override
	protected void registerData() {}

	@Override
	protected void readAdditional(CompoundNBT compound) {}

	@Override
	protected void writeAdditional(CompoundNBT compound) {}

	@OnlyIn(Dist.CLIENT)
	@Override
	public boolean canRenderOnFire() {
		return false;
	}

	@Override
	protected boolean canBeRidden(Entity entityIn) {
		return false;
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		throw new IllegalStateException("should never be spawned on server");
	}
}

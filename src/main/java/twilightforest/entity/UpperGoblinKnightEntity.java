package twilightforest.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TFSounds;
import twilightforest.entity.ai.HeavySpearAttackGoal;

import java.util.List;

public class UpperGoblinKnightEntity extends MonsterEntity {

	private static final int SHIELD_DAMAGE_THRESHOLD = 10;
	private static final DataParameter<Byte> DATA_EQUIP = EntityDataManager.createKey(UpperGoblinKnightEntity.class, DataSerializers.BYTE);
	private static final AttributeModifier ARMOR_MODIFIER = new AttributeModifier("Armor boost", 20, AttributeModifier.Operation.ADDITION);
	private static final AttributeModifier DAMAGE_MODIFIER = new AttributeModifier("Heavy spear attack boost", 12, AttributeModifier.Operation.ADDITION);
	public static final int HEAVY_SPEAR_TIMER_START = 60;

	private int shieldHits = 0;
	public int heavySpearTimer;

	public UpperGoblinKnightEntity(EntityType<? extends UpperGoblinKnightEntity> type, World world) {
		super(type, world);

		this.setHasArmor(true);
		this.setHasShield(true);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new HeavySpearAttackGoal(this));
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, false));
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MonsterEntity.func_234295_eP_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 30.0D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.28D)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 8.0D);
	}

	@Override
	protected void registerData() {
		super.registerData();
		dataManager.register(DATA_EQUIP, (byte) 0);
	}

	public boolean hasArmor() {
		return (dataManager.get(DATA_EQUIP) & 1) > 0;
	}

	private void setHasArmor(boolean flag) {
		byte otherFlags = dataManager.get(DATA_EQUIP);
		dataManager.set(DATA_EQUIP, flag ? (byte) (otherFlags | 1) : (byte) (otherFlags & ~1));

		if (!world.isRemote) {
			if (flag) {
				if (!getAttribute(Attributes.ARMOR).hasModifier(ARMOR_MODIFIER)) {
					getAttribute(Attributes.ARMOR).applyNonPersistentModifier(ARMOR_MODIFIER);
				}
			} else {
				getAttribute(Attributes.ARMOR).removeModifier(ARMOR_MODIFIER);
			}
		}
	}

	public boolean hasShield() {
		return (dataManager.get(DATA_EQUIP) & 2) > 0;
	}

	public void setHasShield(boolean flag) {
		byte otherFlags = dataManager.get(DATA_EQUIP);
		dataManager.set(DATA_EQUIP, flag ? (byte) (otherFlags | 2) : (byte) (otherFlags & ~2));
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putBoolean("hasArmor", this.hasArmor());
		compound.putBoolean("hasShield", this.hasShield());
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		this.setHasArmor(compound.getBoolean("hasArmor"));
		this.setHasShield(compound.getBoolean("hasShield"));
	}

	@Override
	public void livingTick() {
		super.livingTick();
		// Must be decremented on client as well for rendering
		if ((world.isRemote || !isAIDisabled()) && heavySpearTimer > 0) {
			--heavySpearTimer;
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return TFSounds.GOBLIN_KNIGHT_AMBIENT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TFSounds.GOBLIN_KNIGHT_DEATH;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return TFSounds.GOBLIN_KNIGHT_HURT;
	}

	@Override
	public void updateAITasks() {
		super.updateAITasks();

		if (this.isAlive()) {
			// synch target with lower goblin
			if (getRidingEntity() instanceof LivingEntity && this.getAttackTarget() == null) {
				this.setAttackTarget(((MobEntity) this.getRidingEntity()).getAttackTarget());
			}

			if(getAttackTarget() instanceof PlayerEntity && ((PlayerEntity)getAttackTarget()).abilities.disableDamage) {
				this.setAttackTarget(null);
			}

			if (!isPassenger() && this.hasShield()) {
				this.breakShield();
			}

			if (heavySpearTimer > 0) {
				if (!getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(DAMAGE_MODIFIER)) {
					getAttribute(Attributes.ATTACK_DAMAGE).applyNonPersistentModifier(DAMAGE_MODIFIER);
				}
			} else {
				getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(DAMAGE_MODIFIER.getID());
			}
		}
	}

	public void landHeavySpearAttack() {
		// find vector in front of us
		Vector3d vector = this.getLookVec();

		double dist = 1.25;
		double px = this.getPosX() + vector.x * dist;
		double py = this.getBoundingBox().minY - 0.75;
		double pz = this.getPosZ() + vector.z * dist;


		for (int i = 0; i < 50; i++) {
			world.addParticle(ParticleTypes.LARGE_SMOKE, px, py, pz, (rand.nextFloat() - rand.nextFloat()) * 0.25F, 0, (rand.nextFloat() - rand.nextFloat()) * 0.25F);
		}

		// damage things in front that aren't us or our "mount"
		double radius = 1.5D;

		AxisAlignedBB spearBB = new AxisAlignedBB(px - radius, py - radius, pz - radius, px + radius, py + radius, pz + radius);

		List<Entity> inBox = world.getEntitiesInAABBexcluding(this, spearBB, e -> e != this.getRidingEntity());

		for (Entity entity : inBox) {
			super.attackEntityAsMob(entity);
		}

		if (!inBox.isEmpty()) {
			playSound(SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, getSoundVolume(), getSoundPitch());
		}
	}

	@Override
	public void updateRidden() {
		super.updateRidden();
		if (getRidingEntity() instanceof LivingEntity) {
			this.renderYawOffset = ((LivingEntity) this.getRidingEntity()).renderYawOffset;
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void handleStatusUpdate(byte id) {
		if (id == 4) {
			this.heavySpearTimer = HEAVY_SPEAR_TIMER_START;
		} else if (id == 5) {
			ItemStack broken = new ItemStack(Items.IRON_CHESTPLATE);
			this.renderBrokenItemStack(broken);
			this.renderBrokenItemStack(broken);
			this.renderBrokenItemStack(broken);
		} else {
			super.handleStatusUpdate(id);
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {

		if (this.heavySpearTimer > 0) {
			return false;
		}

		if (rand.nextInt(2) == 0) {
			this.heavySpearTimer = HEAVY_SPEAR_TIMER_START;
			this.world.setEntityState(this, (byte) 4);
			return false;
		}

		this.swingArm(Hand.MAIN_HAND);
		return super.attackEntityAsMob(entity);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float amount) {
		// don't take suffocation damage while riding
		if (damageSource == DamageSource.IN_WALL && this.getRidingEntity() != null) {
			return false;
		}

		Entity attacker = damageSource.getTrueSource();

		if (attacker != null && !damageSource.isCreativePlayer()) {
			double dx = this.getPosX() - attacker.getPosX();
			double dz = this.getPosZ() - attacker.getPosZ();
			float angle = (float) ((Math.atan2(dz, dx) * 180D) / Math.PI) - 90F;

			float difference = MathHelper.abs((this.renderYawOffset - angle) % 360);

			if (this.hasShield() && difference > 150 && difference < 230) {
				if (takeHitOnShield(damageSource, amount)) {
					return false;
				}
			} else {
				if (this.hasShield() && rand.nextBoolean()) {
					damageShield();
				}
			}

			if (this.hasArmor() && (difference > 300 || difference < 60)) {
				breakArmor();
			}
		}

		return super.attackEntityFrom(damageSource, amount);
	}

	private void breakArmor() {
		world.setEntityState(this, (byte) 5);
		this.setHasArmor(false);
	}

	private void breakShield() {
		world.setEntityState(this, (byte) 5);
		this.setHasShield(false);
	}


	public boolean takeHitOnShield(DamageSource source, float amount) {
		if (amount > SHIELD_DAMAGE_THRESHOLD && !this.world.isRemote) {
			damageShield();
		} else {
			playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
		}

		// knock back slightly
		LivingEntity toKnockback = (getRidingEntity() instanceof LivingEntity) ? (LivingEntity) getRidingEntity() : this;

		if (source.getTrueSource() != null) {
			double d0 = source.getTrueSource().getPosX() - this.getPosX();
			double d1;

			for (d1 = source.getTrueSource().getPosZ() - this.getPosZ(); d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D) {
				d0 = (Math.random() - Math.random()) * 0.01D;
			}

			toKnockback.applyKnockback(0, d0 / 4D, d1 / 4D);

			// also set revenge target
			if (source.getTrueSource() instanceof LivingEntity) {
				this.setRevengeTarget((LivingEntity) source.getTrueSource());
			}
		}

		return true;
	}


	private void damageShield() {
		playSound(SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 0.25F, 0.25F);

		this.shieldHits++;

		if (!world.isRemote && this.shieldHits >= 3) {
			this.breakShield();
		}
	}
}

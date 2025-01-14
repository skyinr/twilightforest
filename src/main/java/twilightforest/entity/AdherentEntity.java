package twilightforest.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import twilightforest.entity.projectile.NatureBoltEntity;

public class AdherentEntity extends MonsterEntity implements IRangedAttackMob, ITFCharger {

 	private static final DataParameter<Boolean> CHARGE_FLAG = EntityDataManager.createKey(AdherentEntity.class, DataSerializers.BOOLEAN);

	public AdherentEntity(EntityType<? extends AdherentEntity> type, World world) {
		super(type, world);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(2, new RestrictSunGoal(this));
		this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new RangedAttackGoal(this, 1.0D, 60, 10.0F));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
	}

	@Override
	protected void registerData() {
		super.registerData();
		dataManager.register(CHARGE_FLAG, false);
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MonsterEntity.func_234295_eP_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 20.0D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	@Override
	public void attackEntityWithRangedAttack(LivingEntity attackTarget, float extraDamage) {
		NatureBoltEntity natureBolt = new NatureBoltEntity(this.world, this);
		playSound(SoundEvents.ENTITY_GHAST_SHOOT, 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));

		// [VanillaCopy] adapted from EntitySnowman, with lower velocity and inaccuracy calculation
		double d0 = attackTarget.getPosY() + attackTarget.getEyeHeight() - 1.100000023841858D;
		double d1 = attackTarget.getPosX() - this.getPosX();
		double d2 = d0 - natureBolt.getPosY();
		double d3 = attackTarget.getPosZ() - this.getPosZ();
		float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
		natureBolt.shoot(d1, d2 + f, d3, 0.6F, 10 - this.world.getDifficulty().getId() * 4);

		this.world.addEntity(natureBolt);

	}

	@Override
	protected boolean canBeRidden(Entity entityIn) {
		return false;
	}

	@Override
	public boolean isCharging() {
		return dataManager.get(CHARGE_FLAG);
	}

	@Override
	public void setCharging(boolean flag) {
		dataManager.set(CHARGE_FLAG, flag);
	}
}

package twilightforest.entity.boss;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.event.ForgeEventFactory;
import twilightforest.TFFeature;
import twilightforest.TFSounds;
import twilightforest.block.TFBlocks;
import twilightforest.util.EntityUtil;
import twilightforest.util.WorldUtil;
import twilightforest.world.TFGenerationSettings;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HydraEntity extends MobEntity implements IMob {

	private static final int TICKS_BEFORE_HEALING = 1000;
	private static final int HEAD_RESPAWN_TICKS = 100;
	private static final int HEAD_MAX_DAMAGE = 120;
	private static final float ARMOR_MULTIPLIER = 8.0F;
	private static final int MAX_HEALTH = 360;
	private static float HEADS_ACTIVITY_FACTOR = 0.3F;

	private static final int SECONDARY_FLAME_CHANCE = 10;
	private static final int SECONDARY_MORTAR_CHANCE = 16;

	private final HydraPartEntity[] partArray;

	public final int numHeads = 7;
	public final HydraHeadContainer[] hc = new HydraHeadContainer[numHeads];

	public final HydraSmallPartEntity body;
	private final HydraSmallPartEntity leftLeg;
	private final HydraSmallPartEntity rightLeg;
	private final HydraSmallPartEntity tail;
	private final ServerBossInfo bossInfo = new ServerBossInfo(getDisplayName(), BossInfo.Color.BLUE, BossInfo.Overlay.PROGRESS);
	private float randomYawVelocity = 0f;

	private int ticksSinceDamaged = 0;

	public HydraEntity(EntityType<? extends HydraEntity> type, World world) {
		super(type, world);

		List<HydraPartEntity> parts = new ArrayList<>();

		body = new HydraSmallPartEntity(this, 6F, 6F);
		leftLeg = new HydraSmallPartEntity(this, 2F, 3F);
		rightLeg = new HydraSmallPartEntity(this, 2F, 3F);
		tail = new HydraSmallPartEntity(this, 6.0f, 2.0f);

		parts.add(body);
		parts.add(leftLeg);
		parts.add(rightLeg);
		parts.add(tail);

		for (int i = 0; i < numHeads; i++) {
			hc[i] = new HydraHeadContainer(this, i, i < 3);
			parts.add(hc[i].headEntity);
			Collections.addAll(parts, hc[i].getNeckArray());
		}

		partArray = parts.toArray(new HydraPartEntity[0]);

		this.ignoreFrustumCheck = true;
		this.isImmuneToFire();
		this.experienceValue = 511;

	}

	@Override
	public void setCustomName(@Nullable ITextComponent name) {
		super.setCustomName(name);
		this.bossInfo.setName(this.getDisplayName());
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MobEntity.func_233666_p_()
				.createMutableAttribute(Attributes.MAX_HEALTH, MAX_HEALTH)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.28D);
	}

	@Override
	public void addTrackingPlayer(ServerPlayerEntity player) {
		super.addTrackingPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	@Override
	public void removeTrackingPlayer(ServerPlayerEntity player) {
		super.removeTrackingPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	@Override
	public void checkDespawn() {
		if (world.getDifficulty() == Difficulty.PEACEFUL) {
			world.setBlockState(getPosition().add(0, 2, 0), TFBlocks.boss_spawner_hydra.get().getDefaultState());
			remove();
			for (HydraHeadContainer container : hc) {
				if (container.headEntity != null) {
					container.headEntity.remove();
				}
			}
		} else {
			super.checkDespawn();
		}
	}

	// [Vanilla Copy] from LivingEntity. Hydra doesn't like the one from EntityLiving for whatever reason
	@Override
	protected float updateDistance(float p_110146_1_, float p_110146_2_)
	{
		float f = MathHelper.wrapDegrees(p_110146_1_ - this.renderYawOffset);
		this.renderYawOffset += f * 0.3F;
		float f1 = MathHelper.wrapDegrees(this.rotationYaw - this.renderYawOffset);
		boolean flag = f1 < -90.0F || f1 >= 90.0F;

		if (f1 < -75.0F)
		{
			f1 = -75.0F;
		}

		if (f1 >= 75.0F)
		{
			f1 = 75.0F;
		}

		this.renderYawOffset = this.rotationYaw - f1;

		if (f1 * f1 > 2500.0F)
		{
			this.renderYawOffset += f1 * 0.2F;
		}

		if (flag)
		{
			p_110146_2_ *= -1.0F;
		}

		return p_110146_2_;
	}

	@Override
	public void livingTick() {
		extinguish();
		body.tick();
		leftLeg.tick();
		rightLeg.tick();

		// update all heads (maybe we should change to only active ones
		for (int i = 0; i < numHeads; i++) {
			hc[i].tick();
		}

		if (this.hurtTime > 0) {
			for (int i = 0; i < numHeads; i++) {
				hc[i].setHurtTime(this.hurtTime);
			}
		}

		this.ticksSinceDamaged++;

		if (!this.world.isRemote && this.ticksSinceDamaged > TICKS_BEFORE_HEALING && this.ticksSinceDamaged % 5 == 0) {
			this.heal(1);
		}

		// update fight variables for difficulty setting
		setDifficultyVariables();

		super.livingTick();

		// set body part positions
		float angle;
		double dx, dy, dz;

		// body goes behind the actual position of the hydra
		angle = (((renderYawOffset + 180) * 3.141593F) / 180F);

		dx = getPosX() - MathHelper.sin(angle) * 3.0;
		dy = getPosY() + 0.1;
		dz = getPosZ() + MathHelper.cos(angle) * 3.0;
		body.setPosition(dx, dy, dz);

		dx = getPosX() - MathHelper.sin(angle) * 10.5;
		dy = getPosY() + 0.1;
		dz = getPosZ() + MathHelper.cos(angle) * 10.5;
		tail.setPosition(dx, dy, dz);

		// destroy blocks
		if (!this.world.isRemote) {
			if (hurtTime == 0) {
				this.collideWithEntities(this.world.getEntitiesWithinAABBExcludingEntity(this, this.body.getBoundingBox()), this.body);
				this.collideWithEntities(this.world.getEntitiesWithinAABBExcludingEntity(this, this.tail.getBoundingBox()), this.tail);
			}

			this.destroyBlocksInAABB(this.body.getBoundingBox());
			this.destroyBlocksInAABB(this.tail.getBoundingBox());

			for (int i = 0; i < numHeads; i++) {
				if (hc[i].headEntity != null && hc[i].isActive()) {
					this.destroyBlocksInAABB(this.hc[i].headEntity.getBoundingBox());
				}
			}

			// smash blocks beneath us too
			if (this.ticksExisted % 20 == 0) {
				if (isUnsteadySurfaceBeneath()) {
					this.destroyBlocksInAABB(this.getBoundingBox().offset(0, -1, 0));

				}
			}

			bossInfo.setPercent(getHealth() / getMaxHealth());
		}
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putByte("NumHeads", (byte) countActiveHeads());
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		activateNumberOfHeads(compound.getByte("NumHeads"));
		if (this.hasCustomName()) {
			this.bossInfo.setName(this.getDisplayName());
		}
	}


	// TODO modernize this more (old AI copypasta still kind of here)
	private int numTicksToChaseTarget;

	@Override
	protected void updateAITasks() {
		moveStrafing = 0.0F;
		moveForward = 0.0F;
		float f = 48F;

		// kill heads that have taken too much damage
		for (int i = 0; i < numHeads; i++) {
			if (hc[i].isActive() && hc[i].getDamageTaken() > HEAD_MAX_DAMAGE) {
				hc[i].setNextState(HydraHeadContainer.State.DYING);
				hc[i].endCurrentAction();

				// set this head and a random dead head to respawn
				hc[i].setRespawnCounter(HEAD_RESPAWN_TICKS);
				int otherHead = getRandomDeadHead();
				if (otherHead != -1) {
					hc[otherHead].setRespawnCounter(HEAD_RESPAWN_TICKS);
				}
			}
		}

		if (rand.nextFloat() < 0.7F) {
			PlayerEntity entityplayer1 = world.getClosestPlayer(this, f);

			if (entityplayer1 != null && !entityplayer1.isCreative()) {
				setAttackTarget(entityplayer1);
				numTicksToChaseTarget = 100 + rand.nextInt(20);
			} else {
				randomYawVelocity = (rand.nextFloat() - 0.5F) * 20F;
			}
		}

		if (getAttackTarget() != null) {
			faceEntity(getAttackTarget(), 10F, getVerticalFaceSpeed());

			// have any heads not currently attacking switch to the primary target
			for (int i = 0; i < numHeads; i++) {
				if (!hc[i].isAttacking() && !hc[i].isSecondaryAttacking) {
					hc[i].setTargetEntity(getAttackTarget());
				}
			}

			// let's pick an attack
			if (this.getAttackTarget().isAlive()) {
				float distance = this.getAttackTarget().getDistance(this);

				if (this.getEntitySenses().canSee(this.getAttackTarget())) {
					this.attackEntity(this.getAttackTarget(), distance);
				}
			}

			if (numTicksToChaseTarget-- <= 0 || !getAttackTarget().isAlive() || getAttackTarget().getDistanceSq(this) > f * f) {
				setAttackTarget(null);
			}
		} else {
			if (rand.nextFloat() < 0.05F) {
				randomYawVelocity = (rand.nextFloat() - 0.5F) * 20F;
			}

			rotationYaw += randomYawVelocity;
			rotationPitch = 0;

			// TODO: while we are idle, consider having the heads breathe fire on passive mobs

			// set idle heads to no target
			for (int i = 0; i < numHeads; i++) {
				if (hc[i].isIdle()) {
					hc[i].setTargetEntity(null);
				}
			}
		}

		// heads that are free at this point may consider attacking secondary targets
		this.secondaryAttacks();
	}

	private void setDifficultyVariables() {
		if (world.getDifficulty() != Difficulty.HARD) {
			HydraEntity.HEADS_ACTIVITY_FACTOR = 0.3F;
		} else {
			HydraEntity.HEADS_ACTIVITY_FACTOR = 0.5F;  // higher is harder
		}
	}

	// TODO: make random
	private int getRandomDeadHead() {
		for (int i = 0; i < numHeads; i++) {
			if (hc[i].canRespawn()) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Used when re-loading from save.  Assumes three heads are active and activates more if necessary.
	 */
	private void activateNumberOfHeads(int howMany) {
		int moreHeads = howMany - this.countActiveHeads();

		for (int i = 0; i < moreHeads; i++) {
			int otherHead = getRandomDeadHead();
			if (otherHead != -1) {
				// move directly into not dead
				hc[otherHead].setNextState(HydraHeadContainer.State.IDLE);
				hc[otherHead].endCurrentAction();
			}
		}
	}

	/**
	 * Count timers, and pick an attack against the entity if our timer says go
	 */
	private void attackEntity(Entity target, float distance) {

		int BITE_CHANCE = 10;
		int FLAME_CHANCE = 100;
		int MORTAR_CHANCE = 160;

		boolean targetAbove = target.getBoundingBox().minY > this.getBoundingBox().maxY;

		// three main heads can do these kinds of attacks
		for (int i = 0; i < 3; i++) {
			if (hc[i].isIdle() && !areTooManyHeadsAttacking(i)) {
				if (distance > 4 && distance < 10 && rand.nextInt(BITE_CHANCE) == 0 && this.countActiveHeads() > 2 && !areOtherHeadsBiting(i)) {
					hc[i].setNextState(HydraHeadContainer.State.BITE_BEGINNING);
				} else if (distance > 0 && distance < 20 && rand.nextInt(FLAME_CHANCE) == 0) {
					hc[i].setNextState(HydraHeadContainer.State.FLAME_BEGINNING);
				} else if (distance > 8 && distance < 32 && !targetAbove && rand.nextInt(MORTAR_CHANCE) == 0) {
					hc[i].setNextState(HydraHeadContainer.State.MORTAR_BEGINNING);
				}
			}
		}

		// heads 4-7 can do everything but bite
		for (int i = 3; i < numHeads; i++) {
			if (hc[i].isIdle() && !areTooManyHeadsAttacking(i)) {
				if (distance > 0 && distance < 20 && rand.nextInt(FLAME_CHANCE) == 0) {
					hc[i].setNextState(HydraHeadContainer.State.FLAME_BEGINNING);
				} else if (distance > 8 && distance < 32 && !targetAbove && rand.nextInt(MORTAR_CHANCE) == 0) {
					hc[i].setNextState(HydraHeadContainer.State.MORTAR_BEGINNING);
				}
			}
		}
	}

	private boolean areTooManyHeadsAttacking(int testHead) {
		int otherAttacks = 0;

		for (int i = 0; i < numHeads; i++) {
			if (i != testHead && hc[i].isAttacking()) {
				otherAttacks++;

				// biting heads count triple
				if (hc[i].isBiting()) {
					otherAttacks += 2;
				}
			}
		}

		return otherAttacks >= 1 + (countActiveHeads() * HEADS_ACTIVITY_FACTOR);
	}

	private int countActiveHeads() {
		int count = 0;

		for (int i = 0; i < numHeads; i++) {
			if (hc[i].isActive()) {
				count++;
			}
		}

		return count;
	}

	private boolean areOtherHeadsBiting(int testHead) {
		for (int i = 0; i < numHeads; i++) {
			if (i != testHead && hc[i].isBiting()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Called sometime after the main attackEntity routine.  Finds a valid secondary target and has an unoccupied head start an attack against it.
	 * <p>
	 * The center head (head 0) does not make secondary attacks
	 */
	private void secondaryAttacks() {
		for (int i = 0; i < numHeads; i++) {
			if (hc[i].headEntity == null) {
				return;
			}
		}

		LivingEntity secondaryTarget = findSecondaryTarget(20);

		if (secondaryTarget != null) {
			float distance = secondaryTarget.getDistance(this);

			for (int i = 1; i < numHeads; i++) {
				if (hc[i].isActive() && hc[i].isIdle() && isTargetOnThisSide(i, secondaryTarget)) {
					if (distance > 0 && distance < 20 && rand.nextInt(SECONDARY_FLAME_CHANCE) == 0) {
						hc[i].setTargetEntity(secondaryTarget);
						hc[i].isSecondaryAttacking = true;
						hc[i].setNextState(HydraHeadContainer.State.FLAME_BEGINNING);
					} else if (distance > 8 && distance < 32 && rand.nextInt(SECONDARY_MORTAR_CHANCE) == 0) {
						hc[i].setTargetEntity(secondaryTarget);
						hc[i].isSecondaryAttacking = true;
						hc[i].setNextState(HydraHeadContainer.State.MORTAR_BEGINNING);
					}
				}
			}
		}
	}

	/**
	 * Used to make sure heads don't attack across the whole body
	 */
	private boolean isTargetOnThisSide(int headNum, Entity target) {
		double headDist = distanceSqXZ(hc[headNum].headEntity, target);
		double middleDist = distanceSqXZ(this, target);
		return headDist < middleDist;
	}

	/**
	 * Square of distance between two entities with y not a factor, just x and z
	 */
	private double distanceSqXZ(Entity headEntity, Entity target) {
		double distX = headEntity.getPosX() - target.getPosX();
		double distZ = headEntity.getPosZ() - target.getPosZ();
		return distX * distX + distZ * distZ;
	}

	@Nullable
	private LivingEntity findSecondaryTarget(double range) {
		return this.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(this.getPosX(), this.getPosY(), this.getPosZ(), this.getPosX() + 1, this.getPosY() + 1, this.getPosZ() + 1).grow(range, range, range))
				.stream()
				.filter(e -> !(e instanceof HydraEntity))
				.filter(e -> e != getAttackTarget() && !isAnyHeadTargeting(e) && getEntitySenses().canSee(e) && EntityPredicates.CAN_HOSTILE_AI_TARGET.test(e))
				.min(Comparator.comparingDouble(this::getDistanceSq)).orElse(null);
	}

	private boolean isAnyHeadTargeting(Entity targetEntity) {
		for (int i = 0; i < numHeads; i++) {
			if (hc[i].targetEntity != null && hc[i].targetEntity.equals(targetEntity)) {
				return true;
			}
		}

		return false;
	}

	// [VanillaCopy] based on EntityDragon.collideWithEntities
	private void collideWithEntities(List<Entity> entities, Entity part) {
		double d0 = (part.getBoundingBox().minX + part.getBoundingBox().maxX) / 2.0D;
		double d1 = (part.getBoundingBox().minZ + part.getBoundingBox().maxZ) / 2.0D;

		for (Entity entity : entities) {
			if (entity instanceof LivingEntity) {
				double d2 = entity.getPosX() - d0;
				double d3 = entity.getPosZ() - d1;
				double d4 = d2 * d2 + d3 * d3;
				entity.addVelocity(d2 / d4 * 4.0D, 0.20000000298023224D, d3 / d4 * 4.0D);
			}
		}
	}

	/**
	 * Check the surface immediately beneath us, if it is less than 80% solid
	 */
	private boolean isUnsteadySurfaceBeneath() {
		int minX = MathHelper.floor(this.getBoundingBox().minX);
		int minZ = MathHelper.floor(this.getBoundingBox().minZ);
		int maxX = MathHelper.floor(this.getBoundingBox().maxX);
		int maxZ = MathHelper.floor(this.getBoundingBox().maxZ);
		int minY = MathHelper.floor(this.getBoundingBox().minY);

		int solid = 0;
		int total = 0;

		int dy = minY - 1;

		for (int dx = minX; dx <= maxX; ++dx) {
			for (int dz = minZ; dz <= maxZ; ++dz) {
				total++;
				if (this.world.getBlockState(new BlockPos(dx, dy, dz)).getMaterial().isSolid()) {
					solid++;
				}
			}
		}

		return ((float) solid / (float) total) < 0.6F;
	}

	private void destroyBlocksInAABB(AxisAlignedBB box) {
		if (ForgeEventFactory.getMobGriefingEvent(world, this)) {
			for (BlockPos pos : WorldUtil.getAllInBB(box)) {
				if (EntityUtil.canDestroyBlock(world, pos, this)) {
					world.destroyBlock(pos, false);
				}
			}
		}
	}

	@Override
	public int getVerticalFaceSpeed() {
		return 500;
	}

	public boolean attackEntityFromPart(HydraPartEntity part, DamageSource source, float damage) {
		// if we're in a wall, kill that wall
		if (!world.isRemote && source == DamageSource.IN_WALL) {
			destroyBlocksInAABB(part.getBoundingBox());
		}

		if (source.getTrueSource() == this || source.getImmediateSource() == this)
			return false;
		if (getParts() != null)
			for (PartEntity<?> partEntity : getParts())
				if (partEntity == source.getTrueSource() || partEntity == source.getImmediateSource())
					return false;

		HydraHeadContainer headCon = null;

		for (int i = 0; i < numHeads; i++) {
			if (hc[i].headEntity == part) {
				headCon = hc[i];
			} else if (part instanceof HydraNeckEntity && hc[i].headEntity == ((HydraNeckEntity) part).head && !hc[i].isActive())
				return false;
		}

		double range = calculateRange(source);

		// Give some leeway for reflected mortars
		if (range > 400 + (source.getImmediateSource() instanceof HydraMortarHead ? 200 : 0)) {
			return false;
		}

		// ignore hits on dying heads, it's weird
		if (headCon != null && !headCon.isActive()) {
			return false;
		}

		boolean tookDamage;
		if (headCon != null && headCon.getCurrentMouthOpen() > 0.5) {
			tookDamage = super.attackEntityFrom(source, damage);
			headCon.addDamage(damage);
		} else {
			int armoredDamage = Math.round(damage / ARMOR_MULTIPLIER);
			tookDamage = super.attackEntityFrom(source, armoredDamage);

			if (headCon != null) {
				headCon.addDamage(armoredDamage);
			}
		}

		if (tookDamage) {
			this.ticksSinceDamaged = 0;
		}

		return tookDamage;
	}

	private double calculateRange(DamageSource damagesource) {
		return damagesource.getTrueSource() != null ? getDistanceSq(damagesource.getTrueSource()) : -1;
	}

	@Override
	public boolean attackEntityFrom(DamageSource src, float damage) {
		return src == DamageSource.OUT_OF_WORLD && super.attackEntityFrom(src, damage);
	}

	@Override
	public boolean isMultipartEntity() {
		return true;
	}

	/**
	 * We need to do this for the bounding boxes on the parts to become active
	 */
	@Nullable
	@Override
	public PartEntity<?>[] getParts() {
		return partArray;
	}

	/**
	 * This is set as off for the hydra, which has an enormous bounding box, but set as on for the parts.
	 */
	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	/**
	 * If this is on, the player pushes us based on our bounding box rather than it going by parts
	 */
	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	protected void collideWithEntity(Entity entity) {}

	@Override
	public void applyKnockback(float strength, double xRatio, double zRatio) {
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return TFSounds.HYDRA_GROWL;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return TFSounds.HYDRA_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TFSounds.HYDRA_DEATH;
	}

	@Override
	protected float getSoundVolume() {
		return 2F;
	}

	@Override
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
		// mark the lair as defeated
		if (!world.isRemote) {
			TFGenerationSettings.markStructureConquered(world, new BlockPos(this.getPosition()), TFFeature.HYDRA_LAIR);
		}
	}

	@Override
	public boolean canDespawn(double distance) {
		return false;
	}

	@Override
	public boolean isBurning() {
		return false;
	}

	@Override
	protected void onDeathUpdate() {
		++this.deathTime;

		// stop any head actions on death
		if (deathTime == 1) {
			for (int i = 0; i < numHeads; i++) {
				hc[i].setRespawnCounter(-1);
				if (hc[i].isActive()) {
					hc[i].setNextState(HydraHeadContainer.State.IDLE);
					hc[i].endCurrentAction();
					hc[i].setHurtTime(200);
				}
			}
		}

		// heads die off one by one
		if (this.deathTime <= 140 && this.deathTime % 20 == 0) {
			int headToDie = (this.deathTime / 20) - 1;

			if (hc[headToDie].isActive()) {
				hc[headToDie].setNextState(HydraHeadContainer.State.DYING);
				hc[headToDie].endCurrentAction();
			}
		}

		if (this.deathTime == 200) {
			if (!this.world.isRemote && (this.isPlayer() || this.recentlyHit > 0 && this.canDropLoot() && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT))) {
				int i = this.getExperiencePoints(this.attackingPlayer);
				i = ForgeEventFactory.getExperienceDrop(this, this.attackingPlayer, i);
				while (i > 0) {
					int j = ExperienceOrbEntity.getXPSplit(i);
					i -= j;
					this.world.addEntity(new ExperienceOrbEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), j));
				}
			}

			this.remove();
		}

		for (int i = 0; i < 20; ++i) {
			double vx = this.rand.nextGaussian() * 0.02D;
			double vy = this.rand.nextGaussian() * 0.02D;
			double vz = this.rand.nextGaussian() * 0.02D;
			this.world.addParticle((rand.nextInt(2) == 0 ? ParticleTypes.EXPLOSION_EMITTER : ParticleTypes.EXPLOSION),
					this.getPosX() + this.rand.nextFloat() * this.body.getWidth() * 2.0F - this.body.getWidth(),
					this.getPosY() + this.rand.nextFloat() * this.body.getHeight(),
					this.getPosZ() + this.rand.nextFloat() * this.body.getWidth() * 2.0F - this.body.getWidth(),
					vx, vy, vz
			);
		}
	}

	@Override
	protected boolean canBeRidden(Entity entityIn) {
		return false;
	}

	@Override
	public boolean canChangeDimension() {
		return false;
	}
}

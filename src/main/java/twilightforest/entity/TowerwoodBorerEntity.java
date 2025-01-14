package twilightforest.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import twilightforest.TFSounds;
import twilightforest.block.TFBlocks;

import java.util.EnumSet;
import java.util.Random;

public class TowerwoodBorerEntity extends MonsterEntity {

	private AISummonSilverfish summonSilverfish;

	public TowerwoodBorerEntity(EntityType<? extends TowerwoodBorerEntity> type, World world) {
		super(type, world);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(2, this.summonSilverfish = new AISummonSilverfish(this));
		this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(4, new AIHideInStone(this));
		this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MonsterEntity.func_234295_eP_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 15.0D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.27D)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 5.0D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 8.0D);
	}

	@Override
	public boolean isSteppingCarefully() {
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return TFSounds.TERMITE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return TFSounds.TERMITE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TFSounds.TERMITE_DEATH;
	}

	// [VanillaCopy] EntitySilverfish.attackEntityFrom
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isInvulnerableTo(source)) {
			return false;
		} else {
			if ((source instanceof EntityDamageSource || source == DamageSource.MAGIC) && this.summonSilverfish != null) {
				this.summonSilverfish.notifyHurt();
			}

			return super.attackEntityFrom(source, amount);
		}
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState block) {
		this.playSound(TFSounds.TERMITE_STEP, 0.15F, 1.0F);
	}

	@Override
	public void tick() {
		this.renderYawOffset = this.rotationYaw;
		super.tick();
	}

	@Override
	public CreatureAttribute getCreatureAttribute() {
		return CreatureAttribute.ARTHROPOD;
	}

	// [VanillaCopy] EntitySilverfish$AIHideInStone. Changes noted
	private static class AIHideInStone extends RandomWalkingGoal {

		private Direction facing;
		private boolean doMerge;

		public AIHideInStone(TowerwoodBorerEntity silverfishIn) {
			super(silverfishIn, 1.0D, 10);
			this.setMutexFlags(EnumSet.of(Flag.MOVE));
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		@Override
		public boolean shouldExecute() {
			if (this.creature.getAttackTarget() != null) {
				return false;
			} else if (!this.creature.getNavigator().noPath()) {
				return false;
			} else {
				Random random = this.creature.getRNG();

				if (random.nextInt(10) == 0 && ForgeEventFactory.getMobGriefingEvent(this.creature.world, this.creature)) {
					this.facing = Direction.getRandomDirection(random);
					BlockPos blockpos = (new BlockPos(this.creature.getPosX(), this.creature.getPosY() + 0.5D, this.creature.getPosZ())).offset(this.facing);
					BlockState iblockstate = this.creature.world.getBlockState(blockpos);

					// TF - Change block check
					if (iblockstate == TFBlocks.tower_wood.get().getDefaultState()) {
						this.doMerge = true;
						return true;
					}
				}

				this.doMerge = false;
				return super.shouldExecute();
			}
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		@Override
		public boolean shouldContinueExecuting() {
			return this.doMerge ? false : super.shouldContinueExecuting();
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		@Override
		public void startExecuting() {
			if (!this.doMerge) {
				super.startExecuting();
			} else {
				World world = this.creature.world;
				BlockPos blockpos = (new BlockPos(this.creature.getPosX(), this.creature.getPosY() + 0.5D, this.creature.getPosZ())).offset(this.facing);
				BlockState iblockstate = world.getBlockState(blockpos);

				// TF - Change block check
				if (iblockstate == TFBlocks.tower_wood.get().getDefaultState()) {
					// TF - Change block type
					world.setBlockState(blockpos, TFBlocks.tower_wood_infested.get().getDefaultState(), 3);
					this.creature.spawnExplosionParticle();
					this.creature.remove();
				}
			}
		}
	}

	// [VanillaCopy] of EntitySilverfish$AISummonSilverfish. Changes noted
	private static class AISummonSilverfish extends Goal {

		private TowerwoodBorerEntity silverfish; // TF - type change
		private int lookForFriends;

		public AISummonSilverfish(TowerwoodBorerEntity silverfishIn) {
			this.silverfish = silverfishIn;
		}

		public void notifyHurt() {
			if (this.lookForFriends == 0) {
				this.lookForFriends = 20;
			}
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		@Override
		public boolean shouldExecute() {
			return this.lookForFriends > 0;
		}

		/**
		 * Updates the task
		 */
		@Override
		public void tick() {
			--this.lookForFriends;

			if (this.lookForFriends <= 0) {

				World world = this.silverfish.world;
				Random random = this.silverfish.getRNG();
				BlockPos blockpos = new BlockPos(this.silverfish.getPosition());

				for (int i = 0; i <= 5 && i >= -5; i = i <= 0 ? 1 - i : -i) {
					for (int j = 0; j <= 10 && j >= -10; j = j <= 0 ? 1 - j : -j) {
						for (int k = 0; k <= 10 && k >= -10; k = k <= 0 ? 1 - k : -k) {

							BlockPos blockpos1 = blockpos.add(j, i, k);
							BlockState iblockstate = world.getBlockState(blockpos1);

							// TF - Change block check
							if (iblockstate == TFBlocks.tower_wood_infested.get().getDefaultState()) {
								if (ForgeEventFactory.getMobGriefingEvent(world, this.silverfish)) {
									world.destroyBlock(blockpos1, true);
								} else {
									// TF - reset to normal tower wood
									world.setBlockState(blockpos1, TFBlocks.tower_wood.get().getDefaultState(), 3);
								}

								if (random.nextBoolean()) {
									return;
								}
							}
						}
					}
				}
			}
		}
	}
}

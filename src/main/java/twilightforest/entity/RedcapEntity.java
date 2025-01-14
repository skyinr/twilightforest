package twilightforest.entity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import twilightforest.TFSounds;
import twilightforest.entity.ai.AvoidAnyEntityGoal;
import twilightforest.entity.ai.RedcapLightTNTGoal;
import twilightforest.entity.ai.RedcapShyGoal;

import javax.annotation.Nullable;

public class RedcapEntity extends MonsterEntity {

	public ItemStack heldPick = new ItemStack(Items.IRON_PICKAXE);
	public ItemStack heldTNT = new ItemStack(Blocks.TNT);
	public ItemStack heldFlint = new ItemStack(Items.FLINT_AND_STEEL);

	public RedcapEntity(EntityType<? extends RedcapEntity> type, World world) {
		super(type, world);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new AvoidAnyEntityGoal<>(this, TNTEntity.class, 2.0F, 1.0F, 2.0F));
		this.goalSelector.addGoal(2, new RedcapShyGoal(this, 1.0F));
		this.goalSelector.addGoal(3, new RedcapLightTNTGoal(this, 1.0F)); // light TNT
		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
	}

	public static AttributeModifierMap.MutableAttribute registerAttributes() {
		return MonsterEntity.func_234295_eP_()
				.createMutableAttribute(Attributes.MAX_HEALTH, 20.0D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.28D);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return TFSounds.REDCAP_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return TFSounds.REDCAP_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TFSounds.REDCAP_DEATH;
	}

	public boolean isShy() {
		return this.recentlyHit <= 0;
	}

	@Nullable
	@Override
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		ILivingEntityData data = super.onInitialSpawn(worldIn, difficulty, reason, spawnDataIn, dataTag);

		this.setEquipmentBasedOnDifficulty(difficulty);
		this.setEnchantmentBasedOnDifficulty(difficulty);

		this.setDropChance(EquipmentSlotType.MAINHAND, 0.2F);
		this.setDropChance(EquipmentSlotType.FEET, 0.2F);

		return data;
	}

	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
		this.setItemStackToSlot(EquipmentSlotType.MAINHAND, heldPick);
		this.setItemStackToSlot(EquipmentSlotType.FEET, new ItemStack(Items.IRON_BOOTS));
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putInt("TNTLeft", heldTNT.getCount());
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		heldTNT.setCount(compound.getInt("TNTLeft"));
	}
}

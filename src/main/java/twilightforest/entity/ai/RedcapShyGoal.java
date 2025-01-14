package twilightforest.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import twilightforest.entity.RedcapEntity;

import java.util.EnumSet;

public class RedcapShyGoal extends RedcapBaseGoal {

	private LivingEntity entityTarget;
	private final float speed;
	private final boolean lefty = Math.random() < 0.5;
	private double targetX;
	private double targetY;
	private double targetZ;

	private static final double minDistance = 3.0;
	private static final double maxDistance = 6.0;

	public RedcapShyGoal(RedcapEntity entityTFRedcap, float moveSpeed) {
		super(entityTFRedcap);
		this.speed = moveSpeed;
		this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
	}

	@Override
	public boolean shouldExecute() {
		LivingEntity attackTarget = this.redcap.getAttackTarget();

		if (attackTarget == null
				|| !this.redcap.isShy()
				|| attackTarget.getDistance(redcap) > maxDistance
				|| attackTarget.getDistance(redcap) < minDistance
				|| !isTargetLookingAtMe(attackTarget)) {
			return false;
		} else {
			this.entityTarget = attackTarget;
			Vector3d avoidPos = findCirclePoint(redcap, entityTarget, 5, lefty ? 1 : -1);

			this.targetX = avoidPos.x;
			this.targetY = avoidPos.y;
			this.targetZ = avoidPos.z;
			return true;
		}

	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		this.redcap.getNavigator().tryMoveToXYZ(this.targetX, this.targetY, this.targetZ, this.speed);
	}

	@Override
	public boolean shouldContinueExecuting() {
		LivingEntity attackTarget = this.redcap.getAttackTarget();

		if (attackTarget == null) {
			return false;
		} else if (!this.entityTarget.isAlive()) {
			return false;
		} else if (this.redcap.getNavigator().noPath()) {
			return false;
		}

		return redcap.isShy() && attackTarget.getDistance(redcap) < maxDistance && attackTarget.getDistance(redcap) > minDistance && isTargetLookingAtMe(attackTarget);
	}

	@Override
	public void tick() {
		this.redcap.getLookController().setLookPositionWithEntity(this.entityTarget, 30.0F, 30.0F);
	}

	@Override
	public void resetTask() {
		this.entityTarget = null;
		this.redcap.getNavigator().clearPath();
	}

	private Vector3d findCirclePoint(Entity circler, Entity toCircle, double radius, double rotation) {
		// compute angle
		double vecx = circler.getPosX() - toCircle.getPosX();
		double vecz = circler.getPosZ() - toCircle.getPosZ();
		float rangle = (float) (Math.atan2(vecz, vecx));

		// add a little, so he circles
		rangle += rotation;

		// figure out where we're headed from the target angle
		double dx = MathHelper.cos(rangle) * radius;
		double dz = MathHelper.sin(rangle) * radius;

		// add that to the target entity's position, and we have our destination
		return new Vector3d(toCircle.getPosX() + dx, circler.getBoundingBox().minY, toCircle.getPosZ() + dz);
	}
}

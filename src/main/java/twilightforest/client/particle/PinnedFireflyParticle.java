package twilightforest.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import twilightforest.client.particle.data.PinnedFireflyData;

import javax.annotation.Nullable;

/**
 * Version of {@link FireflyParticle} where location is pinned to an entity
 */
public class PinnedFireflyParticle extends FireflyParticle {
	private final Entity follow;

	public PinnedFireflyParticle(ClientWorld world, double x, double y, double z, double vx, double vy, double vz, Entity e) {
		super(world, x, y, z, vx, vy, vz);
		this.follow = e;
	}

	@Override
	public void tick() {
		super.tick();
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		setPosition(follow.getPosX(), follow.getPosY(), follow.getPosZ());
	}

	public static class Factory implements IParticleFactory<PinnedFireflyData> {
		private final IAnimatedSprite sprite;
		public Factory(IAnimatedSprite sprite) {
			this.sprite = sprite;
		}

		@Nullable
		@Override
		public Particle makeParticle(PinnedFireflyData data, ClientWorld world, double x, double y, double z, double vx, double vy, double vz) {
			Entity e = world.getEntityByID(data.follow);
			if (e == null) {
				return null;
			} else {
				PinnedFireflyParticle ret = new PinnedFireflyParticle(world, x, y, z, vx, vy, vz, e);
				ret.selectSpriteRandomly(sprite);
				return ret;
			}
		}
	}
}

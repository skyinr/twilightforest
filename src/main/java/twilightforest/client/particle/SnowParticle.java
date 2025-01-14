package twilightforest.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SnowParticle extends SpriteTexturedParticle {

	float initialParticleScale;

	SnowParticle(ClientWorld world, double x, double y, double z, double vx, double vy, double vz) {
		this(world, x, y, z, vx, vy, vz, 1.0F);
	}

	SnowParticle(ClientWorld world, double x, double y, double z, double vx, double vy, double vz, float scale) {
		super(world, x, y, z, 0.0D, 0.0D, 0.0D);
		this.motionX *= 0.10000000149011612D;
		this.motionY *= 0.10000000149011612D;
		this.motionZ *= 0.10000000149011612D;
		this.motionX += vx * 0.4D;
		this.motionY += vy * 0.4D;
		this.motionZ += vz * 0.4D;
		this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
		this.particleScale *= 0.75F;
		this.particleScale *= scale;
		this.initialParticleScale = this.particleScale;
		this.maxAge = (int) (6.0D / (Math.random() * 0.8D + 0.6D));
		this.maxAge = (int) (this.maxAge * scale);
		this.canCollide = true;
	}

	@Override
	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public void tick() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.age++ >= this.maxAge) {
			this.setExpired();
		}

		this.move(this.motionX, this.motionY, this.motionZ);

		this.motionX *= 0.699999988079071D;
		this.motionY *= 0.699999988079071D;
		this.motionZ *= 0.699999988079071D;
		this.motionY -= 0.019999999552965164D;

		if (this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
		}
	}

	@Override
	public int getBrightnessForRender(float partialTicks) {
		return 240 | 240 << 16;
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite spriteSet;

		public Factory(IAnimatedSprite sprite) {
			this.spriteSet = sprite;
		}

		@Override
		public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			SnowParticle particle = new SnowParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
			particle.selectSpriteRandomly(this.spriteSet);
			return particle;
		}
	}
}

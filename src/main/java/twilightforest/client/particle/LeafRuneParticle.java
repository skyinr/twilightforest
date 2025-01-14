package twilightforest.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LeafRuneParticle extends SpriteTexturedParticle {

	LeafRuneParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ) {
		super(world, x, y, z, velX, velY, velZ);
		// super applies jittering, reset it
		this.motionX = velX;
		this.motionY = velY;
		this.motionZ = velZ;

		this.particleScale = this.rand.nextFloat() * 0.25F;
		this.maxAge = (int)(Math.random() * 10.0D) + 40;
		this.particleGravity = 0.3F + rand.nextFloat() * 0.6F;
	}

	@Override
	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite spriteSet;

		public Factory(IAnimatedSprite sprite) {
			this.spriteSet = sprite;
		}

		@Override
		public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			LeafRuneParticle particle = new LeafRuneParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
			particle.selectSpriteRandomly(this.spriteSet);
			return particle;
		}
	}
}

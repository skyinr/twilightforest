package twilightforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TFConfig;
import twilightforest.TFSounds;
import twilightforest.client.particle.TFParticleType;

import java.util.Random;

public class JarBlock extends Block {

	private static final VoxelShape JAR = Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 14.0D, 13.0D);
	private static final VoxelShape LID = Block.makeCuboidShape(4.0D, 14.0D, 4.0D, 12.0D, 16.0D, 12.0D);
	private static final VoxelShape AABB = VoxelShapes.or(JAR, LID);

	protected JarBlock(Block.Properties props) {
		super(props);
	}

	@Override
	@Deprecated
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return AABB;
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemEntity jarStuff = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ());
		if(player.isSneaking()) {
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
			jarStuff.entityDropItem(this == TFBlocks.firefly_jar.get() ? TFBlocks.firefly.get() : TFBlocks.cicada.get());
			jarStuff.entityDropItem(Items.GLASS_BOTTLE);
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}

	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		super.randomTick(state, worldIn, pos, random);
		//need to counter a higher random tick speed resulting in so many sounds, so here we go
		if(!TFConfig.CLIENT_CONFIG.silentCicadas.get() && random.nextInt(worldIn.getGameRules().get(GameRules.RANDOM_TICK_SPEED).get()) <= 3) {
			worldIn.playSound(null, pos, TFSounds.CICADA, SoundCategory.BLOCKS, 1.0F, 1.0F);
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
		if(this == TFBlocks.firefly_jar.get()) {
			for (int i = 0; i < 2; i++) {
				double dx = pos.getX() + ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 0.5F);
				double dy = pos.getY() + 0.4F + ((rand.nextFloat() - rand.nextFloat()) * 0.3F);
				double dz = pos.getZ() + ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 0.5F);

				world.addParticle(TFParticleType.FIREFLY.get(), dx, dy, dz, 0, 0, 0);
			}
		} else {
			double dx = pos.getX() + ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 0.5F);
			double dy = pos.getY() + 0.4F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F);
			double dz = pos.getZ() + ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 0.5F);

			world.addParticle(ParticleTypes.NOTE, dx, dy, dz, 0, 0, 0);
		}
	}
}

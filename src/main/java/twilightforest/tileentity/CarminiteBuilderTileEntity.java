package twilightforest.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import twilightforest.block.BuilderBlock;
import twilightforest.block.TranslucentBuiltBlock;
import twilightforest.block.TFBlocks;
import twilightforest.enums.TowerDeviceVariant;

public class CarminiteBuilderTileEntity extends TileEntity implements ITickableTileEntity {
	private static final int RANGE = 16;

	private int ticksRunning = 0;
	private int blockedCounter = 0;
	private int ticksStopped = 0;

	public boolean makingBlocks = false;

	private int blocksMade = 0;

	private BlockPos lastBlockCoords;

    private PlayerEntity trackedPlayer;

	private BlockState blockBuiltState = TFBlocks.built_block.get().getDefaultState().with(TranslucentBuiltBlock.ACTIVE, false);

	public CarminiteBuilderTileEntity() {
		super(TFTileEntities.TOWER_BUILDER.get());
	}

	/**
	 * Start building stuffs
	 */
	public void startBuilding() {
		this.makingBlocks = true;
		this.blocksMade = 0;
		this.lastBlockCoords = getPos();
	}

	@Override
	public void tick() {
		if (!world.isRemote && this.makingBlocks) {
			// if we are not tracking the nearest player, start tracking them
			if (trackedPlayer == null) {
				this.trackedPlayer = findClosestValidPlayer();
			}

			// find player facing
            Direction nextFacing = findNextFacing();

			++this.ticksRunning;

			// if we are at the half second marker, make a block and advance the block cursor
			if (this.ticksRunning % 10 == 0 && lastBlockCoords != null && nextFacing != null) {
				BlockPos nextPos = lastBlockCoords.offset(nextFacing);

				// make a block
				if (blocksMade <= RANGE && world.isAirBlock(nextPos)) {
					world.setBlockState(nextPos, blockBuiltState, 3);

					world.playEvent(1001, nextPos, 0);

					this.lastBlockCoords = nextPos;

					blockedCounter = 0;
					blocksMade++;
				} else {
					blockedCounter++;
				}
			}

			// if we're blocked for more than a second, shut down block making
			if (blockedCounter > 0) {
				this.makingBlocks = false;
				this.trackedPlayer = null;
				ticksStopped = 0;
			}
		} else if (!world.isRemote && !this.makingBlocks) {
			this.trackedPlayer = null;
			if (++ticksStopped == 60) {
				// force the builder back into an inactive state
				world.setBlockState(getPos(), getBlockState().with(BuilderBlock.STATE, TowerDeviceVariant.BUILDER_TIMEOUT));
				world.getPendingBlockTicks().scheduleTick(getPos(), getBlockState().getBlock(), 4);
			}
		}
	}

	private Direction findNextFacing() {
		if (this.trackedPlayer != null) {
			// check up and down
			int pitch = MathHelper.floor(trackedPlayer.rotationPitch * 4.0F / 360.0F + 1.5D) & 3;

			if (pitch == 0) {
				return Direction.UP; // todo 1.9 recheck this and down
			} else if (pitch == 2) {
				return Direction.DOWN;
			} else {
				return trackedPlayer.getHorizontalFacing();
			}
		}

		return null;
	}

	/**
	 * Who is the closest player?  Used to find which player we should track when building
	 */
	private PlayerEntity findClosestValidPlayer() {
		return world.getClosestPlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16, false);
	}
}

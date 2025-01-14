package twilightforest.tileentity.spawner;

import net.minecraft.entity.player.PlayerEntity;
import twilightforest.entity.TFEntities;
import twilightforest.entity.boss.MinoshroomEntity;
import twilightforest.tileentity.TFTileEntities;

public class MinoshroomSpawnerTileEntity extends BossSpawnerTileEntity<MinoshroomEntity> {

	public MinoshroomSpawnerTileEntity() {
		super(TFTileEntities.MINOSHROOM_SPAWNER.get(), TFEntities.minoshroom);
	}

	@Override
	public boolean anyPlayerInRange() {
		PlayerEntity closestPlayer = world.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, getRange(), false);
		return closestPlayer != null && closestPlayer.getPosY() > pos.getY() - 4;
	}
}

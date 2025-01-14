package twilightforest.tileentity;

import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import twilightforest.client.renderer.tileentity.*;
import twilightforest.tileentity.spawner.*;

public class TFTileEntities {

	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, TwilightForestMod.ID);

	public static final RegistryObject<TileEntityType<AntibuilderTileEntity>> ANTIBUILDER               = TILE_ENTITIES.register("antibuilder", () ->
			TileEntityType.Builder.create(AntibuilderTileEntity::new, TFBlocks.antibuilder.get()).build(null));
	public static final RegistryObject<TileEntityType<CinderFurnaceTileEntity>> CINDER_FURNACE          = TILE_ENTITIES.register("cinder_furnace", () ->
			TileEntityType.Builder.create(CinderFurnaceTileEntity::new, TFBlocks.cinder_furnace.get()).build(null));
	public static final RegistryObject<TileEntityType<ActiveCarminiteReactorTileEntity>> CARMINITE_REACTOR      = TILE_ENTITIES.register("carminite_reactor", () ->
			TileEntityType.Builder.create(ActiveCarminiteReactorTileEntity::new, TFBlocks.carminite_reactor.get()).build(null));
	public static final RegistryObject<TileEntityType<FireJetTileEntity>> FLAME_JET                    = TILE_ENTITIES.register("flame_jet", () ->
			TileEntityType.Builder.create(FireJetTileEntity::new, TFBlocks.fire_jet.get(), TFBlocks.encased_fire_jet.get()).build(null));
	public static final RegistryObject<TileEntityType<ActiveGhastTrapTileEntity>> GHAST_TRAP = TILE_ENTITIES.register("ghast_trap", () ->
			TileEntityType.Builder.create(ActiveGhastTrapTileEntity::new, TFBlocks.ghast_trap.get()).build(null));
	public static final RegistryObject<TileEntityType<TFSmokerTileEntity>> SMOKER                           = TILE_ENTITIES.register("smoker", () ->
			TileEntityType.Builder.create(TFSmokerTileEntity::new, TFBlocks.smoker.get(), TFBlocks.encased_smoker.get()).build(null));
	public static final RegistryObject<TileEntityType<CarminiteBuilderTileEntity>> TOWER_BUILDER            = TILE_ENTITIES.register("tower_builder", () ->
			TileEntityType.Builder.create(CarminiteBuilderTileEntity::new, TFBlocks.carminite_builder.get()).build(null));
	public static final RegistryObject<TileEntityType<TrophyTileEntity>> TROPHY                         = TILE_ENTITIES.register("trophy", () ->
			TileEntityType.Builder.create(TrophyTileEntity::new, TFBlocks.naga_trophy.get(), TFBlocks.lich_trophy.get(), TFBlocks.minoshroom_trophy.get(),
					TFBlocks.hydra_trophy.get(), TFBlocks.knight_phantom_trophy.get(), TFBlocks.ur_ghast_trophy.get(), TFBlocks.yeti_trophy.get(),
					TFBlocks.snow_queen_trophy.get(), TFBlocks.quest_ram_trophy.get(), TFBlocks.naga_wall_trophy.get(), TFBlocks.lich_wall_trophy.get(),
					TFBlocks.minoshroom_wall_trophy.get(), TFBlocks.hydra_wall_trophy.get(), TFBlocks.knight_phantom_wall_trophy.get(), TFBlocks.ur_ghast_wall_trophy.get(),
					TFBlocks.yeti_wall_trophy.get(), TFBlocks.snow_queen_wall_trophy.get(), TFBlocks.quest_ram_wall_trophy.get()).build(null));
	public static final RegistryObject<TileEntityType<AlphaYetiSpawnerTileEntity>> ALPHA_YETI_SPAWNER     = TILE_ENTITIES.register("alpha_yeti_spawner", () ->
			TileEntityType.Builder.create(AlphaYetiSpawnerTileEntity::new, TFBlocks.boss_spawner_alpha_yeti.get()).build(null));
	public static final RegistryObject<TileEntityType<FinalBossSpawnerTileEntity>> FINAL_BOSS_SPAWNER     = TILE_ENTITIES.register("final_boss_spawner", () ->
			TileEntityType.Builder.create(FinalBossSpawnerTileEntity::new, TFBlocks.boss_spawner_final_boss.get()).build(null));
	public static final RegistryObject<TileEntityType<HydraSpawnerTileEntity>> HYDRA_SPAWNER          = TILE_ENTITIES.register("hydra_boss_spawner", () ->
			TileEntityType.Builder.create(HydraSpawnerTileEntity::new, TFBlocks.boss_spawner_hydra.get()).build(null));
	public static final RegistryObject<TileEntityType<KnightPhantomSpawnerTileEntity>> KNIGHT_PHANTOM_SPAWNER = TILE_ENTITIES.register("knight_phantom_spawner", () ->
			TileEntityType.Builder.create(KnightPhantomSpawnerTileEntity::new, TFBlocks.boss_spawner_knight_phantom.get()).build(null));
	public static final RegistryObject<TileEntityType<LichSpawnerTileEntity>> LICH_SPAWNER           = TILE_ENTITIES.register("lich_spawner", () ->
			TileEntityType.Builder.create(LichSpawnerTileEntity::new, TFBlocks.boss_spawner_lich.get()).build(null));
	public static final RegistryObject<TileEntityType<MinoshroomSpawnerTileEntity>> MINOSHROOM_SPAWNER     = TILE_ENTITIES.register("minoshroom_spawner", () ->
			TileEntityType.Builder.create(MinoshroomSpawnerTileEntity::new, TFBlocks.boss_spawner_minoshroom.get()).build(null));
	public static final RegistryObject<TileEntityType<NagaSpawnerTileEntity>> NAGA_SPAWNER           = TILE_ENTITIES.register("naga_spawner", () ->
			TileEntityType.Builder.create(NagaSpawnerTileEntity::new, TFBlocks.boss_spawner_naga.get()).build(null));
	public static final RegistryObject<TileEntityType<SnowQueenSpawnerTileEntity>> SNOW_QUEEN_SPAWNER     = TILE_ENTITIES.register("snow_queen_spawner", () ->
			TileEntityType.Builder.create(SnowQueenSpawnerTileEntity::new, TFBlocks.boss_spawner_snow_queen.get()).build(null));
	public static final RegistryObject<TileEntityType<TowerBossSpawnerTileEntity>> TOWER_BOSS_SPAWNER     = TILE_ENTITIES.register("tower_boss_spawner", () ->
			TileEntityType.Builder.create(TowerBossSpawnerTileEntity::new, TFBlocks.boss_spawner_ur_ghast.get()).build(null));

	public static final RegistryObject<TileEntityType<CicadaTileEntity>> CICADA     = TILE_ENTITIES.register("cicada", () ->
			TileEntityType.Builder.create(CicadaTileEntity::new, TFBlocks.cicada.get()).build(null));
	public static final RegistryObject<TileEntityType<FireflyTileEntity>> FIREFLY   = TILE_ENTITIES.register("firefly", () ->
			TileEntityType.Builder.create(FireflyTileEntity::new, TFBlocks.firefly.get()).build(null));
	public static final RegistryObject<TileEntityType<MoonwormTileEntity>> MOONWORM = TILE_ENTITIES.register("moonworm", () ->
			TileEntityType.Builder.create(MoonwormTileEntity::new, TFBlocks.moonworm.get()).build(null));

	public static final RegistryObject<TileEntityType<KeepsakeCasketTileEntity>> KEEPSAKE_CASKET          = TILE_ENTITIES.register("keepsake_casket", () ->
			TileEntityType.Builder.create(KeepsakeCasketTileEntity::new, TFBlocks.keepsake_casket.get()).build(null));

	public static final RegistryObject<TileEntityType<TFSignTileEntity>> TF_SIGN = TILE_ENTITIES.register("tf_sign", () ->
			TileEntityType.Builder.create(TFSignTileEntity::new,
					TFBlocks.twilight_oak_sign.get(), TFBlocks.twilight_wall_sign.get(),
					TFBlocks.canopy_sign.get(), TFBlocks.canopy_wall_sign.get(),
					TFBlocks.mangrove_sign.get(), TFBlocks.mangrove_wall_sign.get(),
					TFBlocks.darkwood_sign.get(), TFBlocks.darkwood_wall_sign.get(),
					TFBlocks.time_sign.get(), TFBlocks.time_wall_sign.get(),
					TFBlocks.trans_sign.get(), TFBlocks.trans_wall_sign.get(),
					TFBlocks.mine_sign.get(), TFBlocks.mine_wall_sign.get(),
					TFBlocks.sort_sign.get(), TFBlocks.sort_wall_sign.get()).build(null));

	@OnlyIn(Dist.CLIENT)
	public static void registerTileEntityRenders() {
		// tile entities
		ClientRegistry.bindTileEntityRenderer(FIREFLY.get(), FireflyTileEntityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(CICADA.get(), CicadaTileEntityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(MOONWORM.get(), MoonwormTileEntityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(TROPHY.get(), TrophyTileEntityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(TF_SIGN.get(), SignTileEntityRenderer::new);
		ClientRegistry.bindTileEntityRenderer(KEEPSAKE_CASKET.get(), CasketTileEntityRenderer::new);
	}
}

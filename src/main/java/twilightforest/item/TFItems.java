package twilightforest.item;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.TwilightForestMod;
import twilightforest.block.TFBlocks;
import twilightforest.enums.TwilightArmorMaterial;
import twilightforest.enums.TwilightItemTier;

import javax.annotation.Nullable;
import java.util.UUID;

public class TFItems {
	public static final Food EXPERIMENT_115 = new Food.Builder().hunger(4).saturation(0.3F).build();
	public static final Food HYDRA_CHOP = new Food.Builder().hunger(18).saturation(2.0F).effect(() -> new EffectInstance(Effects.REGENERATION, 100, 0), 1.0F).build();
	public static final Food MAZE_WAFER = new Food.Builder().hunger(4).saturation(0.6F).build();
	public static final Food MEEF_COOKED = new Food.Builder().hunger(6).saturation(0.6F).meat().build();
	public static final Food MEEF_RAW = new Food.Builder().hunger(2).saturation(0.3F).meat().build();
	public static final Food MEEF_STROGANOFF = new Food.Builder().hunger(8).saturation(0.6F).build();
	public static final Food VENISON_COOKED = new Food.Builder().hunger(8).saturation(0.8F).meat().build();
	public static final Food VENISON_RAW = new Food.Builder().hunger(3).saturation(0.3F).meat().build();

	public static final UUID GIANT_REACH_MODIFIER = UUID.fromString("7f10172d-de69-49d7-81bd-9594286a6827");

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TwilightForestMod.ID);

	public static final RegistryObject<Item> naga_scale = ITEMS.register("naga_scale", () -> new Item(defaultBuilder().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> naga_chestplate = ITEMS.register("naga_chestplate", () -> new NagaArmorItem(TwilightArmorMaterial.ARMOR_NAGA, EquipmentSlotType.CHEST, defaultBuilder().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> naga_leggings = ITEMS.register("naga_leggings", () -> new NagaArmorItem(TwilightArmorMaterial.ARMOR_NAGA, EquipmentSlotType.LEGS, defaultBuilder().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> twilight_scepter = ITEMS.register("twilight_scepter", () -> new TwilightWandItem(defaultBuilder().maxDamage(99).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> lifedrain_scepter = ITEMS.register("lifedrain_scepter", () -> new LifedrainScepterItem(defaultBuilder().maxDamage(99).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> zombie_scepter = ITEMS.register("zombie_scepter", () -> new ZombieWandItem(defaultBuilder().maxDamage(9).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> shield_scepter = ITEMS.register("shield_scepter", () -> new FortificationWandItem(defaultBuilder().maxDamage(9).rarity(Rarity.UNCOMMON)));
	//items.register("Wand of Pacification [NYI]", new Item().setIconIndex(6).setTranslationKey("wandPacification").setMaxStackSize(1));
	public static final RegistryObject<Item> ore_meter = ITEMS.register("ore_meter", () -> new OreMeterItem(defaultBuilder()));
	public static final RegistryObject<Item> magic_map = ITEMS.register("magic_map", () -> new MagicMapItem(unstackable()));
	public static final RegistryObject<Item> maze_map = ITEMS.register("maze_map", () -> new MazeMapItem(false, unstackable()));
	public static final RegistryObject<Item> ore_map = ITEMS.register("ore_map", () -> new MazeMapItem(true, unstackable().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> raven_feather = ITEMS.register("raven_feather", () -> new Item(defaultBuilder()));
	public static final RegistryObject<Item> magic_map_focus = ITEMS.register("magic_map_focus", () -> new Item(defaultBuilder()));
	public static final RegistryObject<Item> maze_map_focus = ITEMS.register("maze_map_focus", () -> new Item(defaultBuilder()));
	public static final RegistryObject<Item> liveroot = ITEMS.register("liveroot", () -> new Item(defaultBuilder()));
	public static final RegistryObject<Item> ironwood_raw = ITEMS.register("ironwood_raw", () -> new Item(defaultBuilder()));
	public static final RegistryObject<Item> ironwood_ingot = ITEMS.register("ironwood_ingot", () -> new Item(defaultBuilder()));
	public static final RegistryObject<Item> ironwood_helmet = ITEMS.register("ironwood_helmet", () -> new IronwoodArmorItem(TwilightArmorMaterial.ARMOR_IRONWOOD, EquipmentSlotType.HEAD, defaultBuilder()));
	public static final RegistryObject<Item> ironwood_chestplate = ITEMS.register("ironwood_chestplate", () -> new IronwoodArmorItem(TwilightArmorMaterial.ARMOR_IRONWOOD, EquipmentSlotType.CHEST, defaultBuilder()));
	public static final RegistryObject<Item> ironwood_leggings = ITEMS.register("ironwood_leggings", () -> new IronwoodArmorItem(TwilightArmorMaterial.ARMOR_IRONWOOD, EquipmentSlotType.LEGS, defaultBuilder()));
	public static final RegistryObject<Item> ironwood_boots = ITEMS.register("ironwood_boots", () -> new IronwoodArmorItem(TwilightArmorMaterial.ARMOR_IRONWOOD, EquipmentSlotType.FEET, defaultBuilder()));
	public static final RegistryObject<Item> ironwood_sword = ITEMS.register("ironwood_sword", () -> new IronwoodSwordItem(TwilightItemTier.TOOL_IRONWOOD, defaultBuilder()));
	public static final RegistryObject<Item> ironwood_shovel = ITEMS.register("ironwood_shovel", () -> new IronwoodShovelItem(TwilightItemTier.TOOL_IRONWOOD, defaultBuilder()));
	public static final RegistryObject<Item> ironwood_pickaxe = ITEMS.register("ironwood_pickaxe", () -> new IronwoodPickItem(TwilightItemTier.TOOL_IRONWOOD, defaultBuilder()));
	public static final RegistryObject<Item> ironwood_axe = ITEMS.register("ironwood_axe", () -> new IronwoodAxeItem(TwilightItemTier.TOOL_IRONWOOD, defaultBuilder()));
	public static final RegistryObject<Item> ironwood_hoe = ITEMS.register("ironwood_hoe", () -> new IronwoodHoeItem(TwilightItemTier.TOOL_IRONWOOD, defaultBuilder()));
	public static final RegistryObject<Item> torchberries = ITEMS.register("torchberries", () -> new Item(defaultBuilder()));
	public static final RegistryObject<Item> raw_venison = ITEMS.register("raw_venison", () -> new Item(defaultBuilder().food(TFItems.VENISON_RAW)));
	public static final RegistryObject<Item> cooked_venison = ITEMS.register("cooked_venison", () -> new Item(defaultBuilder().food(TFItems.VENISON_COOKED)));
	public static final RegistryObject<Item> hydra_chop = ITEMS.register("hydra_chop", () -> new HydraChopItem(defaultBuilder().isImmuneToFire().food(TFItems.HYDRA_CHOP).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> fiery_blood = ITEMS.register("fiery_blood", () -> new Item(defaultBuilder().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> fiery_tears = ITEMS.register("fiery_tears", () -> new Item(defaultBuilder().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> fiery_ingot = ITEMS.register("fiery_ingot", () -> new Item(defaultBuilder().isImmuneToFire().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> fiery_helmet = ITEMS.register("fiery_helmet", () -> new FieryArmorItem(TwilightArmorMaterial.ARMOR_FIERY, EquipmentSlotType.HEAD, defaultBuilder().isImmuneToFire().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> fiery_chestplate = ITEMS.register("fiery_chestplate", () -> new FieryArmorItem(TwilightArmorMaterial.ARMOR_FIERY, EquipmentSlotType.CHEST, defaultBuilder().isImmuneToFire().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> fiery_leggings = ITEMS.register("fiery_leggings", () -> new FieryArmorItem(TwilightArmorMaterial.ARMOR_FIERY, EquipmentSlotType.LEGS, defaultBuilder().isImmuneToFire().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> fiery_boots = ITEMS.register("fiery_boots", () -> new FieryArmorItem(TwilightArmorMaterial.ARMOR_FIERY, EquipmentSlotType.FEET, defaultBuilder().isImmuneToFire().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> fiery_sword = ITEMS.register("fiery_sword", () -> new FierySwordItem(TwilightItemTier.TOOL_FIERY, defaultBuilder().isImmuneToFire().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> fiery_pickaxe = ITEMS.register("fiery_pickaxe", () -> new FieryPickItem(TwilightItemTier.TOOL_FIERY, defaultBuilder().isImmuneToFire().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> steeleaf_ingot = ITEMS.register("steeleaf_ingot", () -> new Item(defaultBuilder()));
	public static final RegistryObject<Item> steeleaf_helmet = ITEMS.register("steeleaf_helmet", () -> new SteeleafArmorItem(TwilightArmorMaterial.ARMOR_STEELEAF, EquipmentSlotType.HEAD, defaultBuilder()));
	public static final RegistryObject<Item> steeleaf_chestplate = ITEMS.register("steeleaf_chestplate", () -> new SteeleafArmorItem(TwilightArmorMaterial.ARMOR_STEELEAF, EquipmentSlotType.CHEST, defaultBuilder()));
	public static final RegistryObject<Item> steeleaf_leggings = ITEMS.register("steeleaf_leggings", () -> new SteeleafArmorItem(TwilightArmorMaterial.ARMOR_STEELEAF, EquipmentSlotType.LEGS, defaultBuilder()));
	public static final RegistryObject<Item> steeleaf_boots = ITEMS.register("steeleaf_boots", () -> new SteeleafArmorItem(TwilightArmorMaterial.ARMOR_STEELEAF, EquipmentSlotType.FEET, defaultBuilder()));
	public static final RegistryObject<Item> steeleaf_sword = ITEMS.register("steeleaf_sword", () -> new SteeleafSwordItem(TwilightItemTier.TOOL_STEELEAF, defaultBuilder()));
	public static final RegistryObject<Item> steeleaf_shovel = ITEMS.register("steeleaf_shovel", () -> new SteeleafShovelItem(TwilightItemTier.TOOL_STEELEAF, defaultBuilder()));
	public static final RegistryObject<Item> steeleaf_pickaxe = ITEMS.register("steeleaf_pickaxe", () -> new SteeleafPickItem(TwilightItemTier.TOOL_STEELEAF, defaultBuilder()));
	public static final RegistryObject<Item> steeleaf_axe = ITEMS.register("steeleaf_axe", () -> new SteeleafAxeItem(TwilightItemTier.TOOL_STEELEAF, defaultBuilder()));
	public static final RegistryObject<Item> steeleaf_hoe = ITEMS.register("steeleaf_hoe", () -> new SteeleafHoeItem(TwilightItemTier.TOOL_STEELEAF, defaultBuilder()));
	public static final RegistryObject<Item> minotaur_axe_gold = ITEMS.register("minotaur_axe_gold", () -> new MinotaurAxeItem(ItemTier.GOLD, defaultBuilder().rarity(Rarity.COMMON)));
	public static final RegistryObject<Item> minotaur_axe = ITEMS.register("minotaur_axe", () -> new MinotaurAxeItem(ItemTier.DIAMOND, defaultBuilder().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> mazebreaker_pickaxe = ITEMS.register("mazebreaker_pickaxe", () -> new MazebreakerPickItem(ItemTier.DIAMOND, defaultBuilder().setNoRepair().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> transformation_powder = ITEMS.register("transformation_powder", () -> new TransformPowderItem(defaultBuilder()));
	public static final RegistryObject<Item> raw_meef = ITEMS.register("raw_meef", () -> new Item(defaultBuilder().food(TFItems.MEEF_RAW)));
	public static final RegistryObject<Item> cooked_meef = ITEMS.register("cooked_meef", () -> new Item(defaultBuilder().food(TFItems.MEEF_COOKED)));
	public static final RegistryObject<Item> meef_stroganoff = ITEMS.register("meef_stroganoff", () -> new SoupItem(defaultBuilder().food(TFItems.MEEF_STROGANOFF).maxStackSize(1)));
	public static final RegistryObject<Item> maze_wafer = ITEMS.register("maze_wafer", () -> new Item(defaultBuilder().food(TFItems.MAZE_WAFER)));
	public static final RegistryObject<Item> magic_map_empty = ITEMS.register("magic_map_empty", () -> new EmptyMagicMapItem(defaultBuilder()));
	public static final RegistryObject<Item> maze_map_empty = ITEMS.register("maze_map_empty", () -> new EmptyMazeMapItem(false, defaultBuilder()));
	public static final RegistryObject<Item> ore_map_empty = ITEMS.register("ore_map_empty", () -> new EmptyMazeMapItem(true, defaultBuilder()));
	public static final RegistryObject<Item> ore_magnet = ITEMS.register("ore_magnet", () -> new OreMagnetItem(defaultBuilder().maxDamage(12)));
	public static final RegistryObject<Item> crumble_horn = ITEMS.register("crumble_horn", () -> new CrumbleHornItem(defaultBuilder().maxDamage(1024).rarity(Rarity.RARE)));
	public static final RegistryObject<Item> peacock_fan = ITEMS.register("peacock_fan", () -> new PeacockFanItem(defaultBuilder().maxDamage(1024).rarity(Rarity.RARE)));
	public static final RegistryObject<Item> moonworm_queen = ITEMS.register("moonworm_queen", () -> new MoonwormQueenItem(defaultBuilder().setNoRepair().maxDamage(256).rarity(Rarity.RARE)));
	public static final RegistryObject<Item> charm_of_life_1 = ITEMS.register("charm_of_life_1", () -> new CuriosCharmItem(defaultBuilder().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> charm_of_life_2 = ITEMS.register("charm_of_life_2", () -> new CuriosCharmItem(defaultBuilder().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> charm_of_keeping_1 = ITEMS.register("charm_of_keeping_1", () -> new CuriosCharmItem(defaultBuilder().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> charm_of_keeping_2 = ITEMS.register("charm_of_keeping_2", () -> new CuriosCharmItem(defaultBuilder().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> charm_of_keeping_3 = ITEMS.register("charm_of_keeping_3", () -> new CuriosCharmItem(defaultBuilder().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> tower_key = ITEMS.register("tower_key", () -> new Item(defaultBuilder().isImmuneToFire().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> borer_essence = ITEMS.register("borer_essence", () -> new Item(defaultBuilder()));
	public static final RegistryObject<Item> carminite = ITEMS.register("carminite", () -> new Item(defaultBuilder()));
	public static final RegistryObject<Item> experiment_115 = ITEMS.register("experiment_115", () -> new Experiment115Item(TFBlocks.experiment_115.get(), defaultBuilder().food(TFItems.EXPERIMENT_115)));
	public static final RegistryObject<Item> armor_shard = ITEMS.register("armor_shard", () -> new Item(defaultBuilder()));
	public static final RegistryObject<Item> knightmetal_ingot = ITEMS.register("knightmetal_ingot", () -> new Item(defaultBuilder()));
	public static final RegistryObject<Item> armor_shard_cluster = ITEMS.register("armor_shard_cluster", () -> new Item(defaultBuilder()));
	public static final RegistryObject<Item> knightmetal_helmet = ITEMS.register("knightmetal_helmet", () -> new KnightmetalArmorItem(TwilightArmorMaterial.ARMOR_KNIGHTLY, EquipmentSlotType.HEAD, defaultBuilder()));
	public static final RegistryObject<Item> knightmetal_chestplate = ITEMS.register("knightmetal_chestplate", () -> new KnightmetalArmorItem(TwilightArmorMaterial.ARMOR_KNIGHTLY, EquipmentSlotType.CHEST, defaultBuilder()));
	public static final RegistryObject<Item> knightmetal_leggings = ITEMS.register("knightmetal_leggings", () -> new KnightmetalArmorItem(TwilightArmorMaterial.ARMOR_KNIGHTLY, EquipmentSlotType.LEGS, defaultBuilder()));
	public static final RegistryObject<Item> knightmetal_boots = ITEMS.register("knightmetal_boots", () -> new KnightmetalArmorItem(TwilightArmorMaterial.ARMOR_KNIGHTLY, EquipmentSlotType.FEET, defaultBuilder()));
	public static final RegistryObject<Item> knightmetal_sword = ITEMS.register("knightmetal_sword", () -> new KnightmetalSwordItem(TwilightItemTier.TOOL_KNIGHTLY, defaultBuilder()));
	public static final RegistryObject<Item> knightmetal_pickaxe = ITEMS.register("knightmetal_pickaxe", () -> new KnightmetalPickItem(TwilightItemTier.TOOL_KNIGHTLY, defaultBuilder()));
	public static final RegistryObject<Item> knightmetal_axe = ITEMS.register("knightmetal_axe", () -> new KnightmetalAxeItem(TwilightItemTier.TOOL_KNIGHTLY, defaultBuilder()));
	public static final RegistryObject<Item> knightmetal_shield = ITEMS.register("knightmetal_shield", () -> new KnightmetalShieldItem(defaultBuilder().maxDamage(1024)));
	public static final RegistryObject<Item> phantom_helmet = ITEMS.register("phantom_helmet", () -> new PhantomArmorItem(TwilightArmorMaterial.ARMOR_PHANTOM, EquipmentSlotType.HEAD, defaultBuilder().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> phantom_chestplate = ITEMS.register("phantom_chestplate", () -> new PhantomArmorItem(TwilightArmorMaterial.ARMOR_PHANTOM, EquipmentSlotType.CHEST, defaultBuilder().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> lamp_of_cinders = ITEMS.register("lamp_of_cinders", () -> new LampOfCindersItem(defaultBuilder().isImmuneToFire().maxDamage(1024).rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> alpha_fur = ITEMS.register("alpha_fur", () -> new Item(defaultBuilder().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> yeti_helmet = ITEMS.register("yeti_helmet", () -> new YetiArmorItem(TwilightArmorMaterial.ARMOR_YETI, EquipmentSlotType.HEAD, defaultBuilder().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> yeti_chestplate = ITEMS.register("yeti_chestplate", () -> new YetiArmorItem(TwilightArmorMaterial.ARMOR_YETI, EquipmentSlotType.CHEST, defaultBuilder().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> yeti_leggings = ITEMS.register("yeti_leggings", () -> new YetiArmorItem(TwilightArmorMaterial.ARMOR_YETI, EquipmentSlotType.LEGS, defaultBuilder().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> yeti_boots = ITEMS.register("yeti_boots", () -> new YetiArmorItem(TwilightArmorMaterial.ARMOR_YETI, EquipmentSlotType.FEET, defaultBuilder().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> ice_bomb = ITEMS.register("ice_bomb", () -> new IceBombItem(defaultBuilder().maxStackSize(16)));
	public static final RegistryObject<Item> arctic_fur = ITEMS.register("arctic_fur", () -> new Item(defaultBuilder()));
	public static final RegistryObject<Item> arctic_helmet = ITEMS.register("arctic_helmet", () -> new ArcticArmorItem(TwilightArmorMaterial.ARMOR_ARCTIC, EquipmentSlotType.HEAD, defaultBuilder()));
	public static final RegistryObject<Item> arctic_chestplate = ITEMS.register("arctic_chestplate", () -> new ArcticArmorItem(TwilightArmorMaterial.ARMOR_ARCTIC, EquipmentSlotType.CHEST, defaultBuilder()));
	public static final RegistryObject<Item> arctic_leggings = ITEMS.register("arctic_leggings", () -> new ArcticArmorItem(TwilightArmorMaterial.ARMOR_ARCTIC, EquipmentSlotType.LEGS, defaultBuilder()));
	public static final RegistryObject<Item> arctic_boots = ITEMS.register("arctic_boots", () -> new ArcticArmorItem(TwilightArmorMaterial.ARMOR_ARCTIC, EquipmentSlotType.FEET, defaultBuilder()));
	public static final RegistryObject<Item> magic_beans = ITEMS.register("magic_beans", () -> new MagicBeansItem(defaultBuilder()));
	public static final RegistryObject<Item> giant_pickaxe = ITEMS.register("giant_pickaxe", () -> new GiantPickItem(TwilightItemTier.TOOL_GIANT, defaultBuilder()));
	public static final RegistryObject<Item> giant_sword = ITEMS.register("giant_sword", () -> new GiantSwordItem(TwilightItemTier.TOOL_GIANT, defaultBuilder()));
	public static final RegistryObject<Item> triple_bow = ITEMS.register("triple_bow", () -> new TripleBowItem(defaultBuilder().rarity(Rarity.UNCOMMON).maxDamage(384)));
	public static final RegistryObject<Item> seeker_bow = ITEMS.register("seeker_bow", () -> new SeekerBowItem(defaultBuilder().rarity(Rarity.UNCOMMON).maxDamage(384)));
	public static final RegistryObject<Item> ice_bow = ITEMS.register("ice_bow", () -> new IceBowItem(defaultBuilder().rarity(Rarity.UNCOMMON).maxDamage(384)));
	public static final RegistryObject<Item> ender_bow = ITEMS.register("ender_bow", () -> new EnderBowItem(defaultBuilder().rarity(Rarity.UNCOMMON).maxDamage(384)));
	public static final RegistryObject<Item> ice_sword = ITEMS.register("ice_sword", () -> new IceSwordItem(TwilightItemTier.TOOL_ICE, defaultBuilder()));
	public static final RegistryObject<Item> glass_sword = ITEMS.register("glass_sword", () -> new GlassSwordItem(TwilightItemTier.TOOL_GLASS, defaultBuilder().setNoRepair().rarity(Rarity.RARE)));
	public static final RegistryObject<Item> knightmetal_ring = ITEMS.register("knightmetal_ring", () -> new Item(defaultBuilder()));
	public static final RegistryObject<Item> block_and_chain = ITEMS.register("block_and_chain", () -> new ChainBlockItem(defaultBuilder().maxDamage(99)));
	public static final RegistryObject<Item> cube_talisman = ITEMS.register("cube_talisman", () -> new Item(defaultBuilder().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> cube_of_annihilation = ITEMS.register("cube_of_annihilation", () -> new CubeOfAnnihilationItem(unstackable().rarity(Rarity.UNCOMMON)));
	public static final RegistryObject<Item> moon_dial = ITEMS.register("moon_dial", () -> new Item(defaultBuilder()));

	public static ItemGroup creativeTab = new ItemGroup(TwilightForestMod.ID) {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(TFBlocks.twilight_portal_miniature_structure.get());
		}
	};

	public static Item.Properties defaultBuilder() {
		return new Item.Properties().group(creativeTab);
	}

	public static Item.Properties unstackable() {
		return defaultBuilder().maxStackSize(1);
	}

	@OnlyIn(Dist.CLIENT)
	public static void addItemModelProperties() {
		ItemModelsProperties.registerProperty(cube_of_annihilation.get(), TwilightForestMod.prefix("thrown"), (stack, world, entity) ->
				CubeOfAnnihilationItem.getThrownUuid(stack) != null ? 1 : 0);

		ItemModelsProperties.registerProperty(TFItems.knightmetal_shield.get(), new ResourceLocation("blocking"), (stack, world, entity) ->
				entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F);

		ItemModelsProperties.registerProperty(moon_dial.get(), new ResourceLocation("phase"), new IItemPropertyGetter() {
			@Override
			public float call(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entityBase) {
				boolean flag = entityBase != null;
				Entity entity = flag ? entityBase : stack.getItemFrame();

				if (world == null && entity != null) world = (ClientWorld) entity.world;

				return world == null ? 0.0F : (float) (world.getDimensionType().isNatural() ? MathHelper.frac(world.getMoonPhase() / 8.0f) : this.wobble(world, Math.random()));
			}

			@OnlyIn(Dist.CLIENT)
			double rotation;
			@OnlyIn(Dist.CLIENT)
			double rota;
			@OnlyIn(Dist.CLIENT)
			long lastUpdateTick;

			@OnlyIn(Dist.CLIENT)
			private double wobble(World world, double rotation) {
				if (world.getGameTime() != this.lastUpdateTick) {
					this.lastUpdateTick = world.getGameTime();
					double delta = rotation - this.rotation;
					delta = MathHelper.positiveModulo(delta + 0.5D, 1.0D) - 0.5D;
					this.rota += delta * 0.1D;
					this.rota *= 0.9D;
					this.rotation = MathHelper.positiveModulo(this.rotation + this.rota, 1.0D);
				}
				return this.rotation;
			}
		});

		ItemModelsProperties.registerProperty(moonworm_queen.get(), TwilightForestMod.prefix("alt"), (stack, world, entity) -> {
			if (entity != null && entity.getActiveItemStack() == stack) {
				int useTime = stack.getUseDuration() - entity.getItemInUseCount();
				if (useTime >= MoonwormQueenItem.FIRING_TIME && (useTime >>> 1) % 2 == 0) {
					return 1;
				}
			}
			return 0;
		});

		ItemModelsProperties.registerProperty(TFItems.ender_bow.get(), new ResourceLocation("pull"), (stack, world, entity) -> {
			if(entity == null) return 0.0F;
			else return entity.getActiveItemStack() != stack ? 0.0F : (stack.getUseDuration() - entity.getItemInUseCount()) / 20.0F; });

		ItemModelsProperties.registerProperty(TFItems.ender_bow.get(), new ResourceLocation("pulling"), (stack, world, entity) ->
				entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F);

		ItemModelsProperties.registerProperty(TFItems.ice_bow.get(), new ResourceLocation("pull"), (stack, world, entity) -> {
			if(entity == null) return 0.0F;
			else return entity.getActiveItemStack() != stack ? 0.0F : (stack.getUseDuration() - entity.getItemInUseCount()) / 20.0F; });

		ItemModelsProperties.registerProperty(TFItems.ice_bow.get(), new ResourceLocation("pulling"), (stack, world, entity) ->
				entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F);

		ItemModelsProperties.registerProperty(TFItems.seeker_bow.get(), new ResourceLocation("pull"), (stack, world, entity) -> {
			if (entity == null) return 0.0F;
			else return entity.getActiveItemStack() != stack ? 0.0F : (stack.getUseDuration() - entity.getItemInUseCount()) / 20.0F; });

		ItemModelsProperties.registerProperty(TFItems.seeker_bow.get(), new ResourceLocation("pulling"), (stack, world, entity) ->
				entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F);

		ItemModelsProperties.registerProperty(TFItems.triple_bow.get(), new ResourceLocation("pull"), (stack, world, entity) -> {
			if (entity == null) return 0.0F;
			else return entity.getActiveItemStack() != stack ? 0.0F : (stack.getUseDuration() - entity.getItemInUseCount()) / 20.0F; });

		ItemModelsProperties.registerProperty(TFItems.triple_bow.get(), new ResourceLocation("pulling"), (stack, world, entity) ->
				entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F);

		ItemModelsProperties.registerProperty(ore_magnet.get(), new ResourceLocation("pull"), (stack, world, entity) -> {
			if (entity == null) return 0.0F;
			else {
				ItemStack itemstack = entity.getActiveItemStack();
				return !itemstack.isEmpty() ? (stack.getUseDuration() - entity.getItemInUseCount()) / 20.0F : 0.0F;
			}});

		ItemModelsProperties.registerProperty(ore_magnet.get(), new ResourceLocation("pulling"), (stack, world, entity) ->
				entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack ? 1.0F : 0.0F);

		ItemModelsProperties.registerProperty(block_and_chain.get(), TwilightForestMod.prefix("thrown"), (stack, world, entity) ->
				ChainBlockItem.getThrownUuid(stack) != null ? 1 : 0);

		ItemModelsProperties.registerProperty(experiment_115.get(), Experiment115Item.THINK, (stack, world, entity) ->
				stack.hasTag() && stack.getTag().contains("think") ? 1 : 0);

		ItemModelsProperties.registerProperty(experiment_115.get(), Experiment115Item.FULL, (stack, world, entity) ->
				stack.hasTag() && stack.getTag().contains("full") ? 1 : 0);
	}
}

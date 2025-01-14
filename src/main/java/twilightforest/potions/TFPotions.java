package twilightforest.potions;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import twilightforest.TwilightForestMod;

public class TFPotions {

	public static final DeferredRegister<Effect> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, TwilightForestMod.ID);

	public static final RegistryObject<Effect> frosty = POTIONS.register("frosted",() -> new FrostedPotion(EffectType.HARMFUL, 0x56CBFD));
}

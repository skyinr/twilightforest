package twilightforest.item;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.armor.KnightlyArmorModel;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;

public class KnightmetalArmorItem extends ArmorItem {

	private static final Map<EquipmentSlotType, BipedModel<?>> knightlyArmorModel = new EnumMap<>(EquipmentSlotType.class);

	public KnightmetalArmorItem(IArmorMaterial material, EquipmentSlotType slot, Properties props) {
		super(material, slot, props);
	}

	@Override
	public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlotType slot, String layer) {
		if (slot == EquipmentSlotType.LEGS) {
			return TwilightForestMod.ARMOR_DIR + "knightly_2.png";
		} else {
			return TwilightForestMod.ARMOR_DIR + "knightly_1.png";
		}
	}

	@Nullable
	@Override
	@OnlyIn(Dist.CLIENT)
	@SuppressWarnings("unchecked")
	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A original) {
		return (A) knightlyArmorModel.get(armorSlot);
	}

	@OnlyIn(Dist.CLIENT)
	public static void initArmorModel() {
		knightlyArmorModel.put(EquipmentSlotType.HEAD, new KnightlyArmorModel(0.75F));
		knightlyArmorModel.put(EquipmentSlotType.CHEST, new KnightlyArmorModel(1.0F));
		knightlyArmorModel.put(EquipmentSlotType.LEGS, new KnightlyArmorModel(0.5F));
		knightlyArmorModel.put(EquipmentSlotType.FEET, new KnightlyArmorModel(1.0F));
	}
}

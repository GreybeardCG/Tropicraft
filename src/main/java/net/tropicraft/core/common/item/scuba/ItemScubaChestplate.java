package net.tropicraft.core.common.item.scuba;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
import net.tropicraft.core.common.item.scuba.api.ScubaMaterial;

public class ItemScubaChestplate extends ItemScubaGear {

	public ItemScubaChestplate(ArmorMaterial material, ScubaMaterial scubaMaterial, int renderIndex, EntityEquipmentSlot slot) {
		super(material, scubaMaterial, renderIndex, slot);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return 0;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		stack.damageItem(damage, entity);
	}

	@Override
	public void onScubaTick(World world, EntityPlayer player, ItemStack itemStack) {
		
	}

    @Override
    protected void onRemovedFromArmorInventory(World world, EntityPlayer player, ItemStack itemstack) {
        
    }

}

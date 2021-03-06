package net.tropicraft.core.common.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Basic interface for all Tropicraft blocks to implement to make doing states easier
 * @author Cojo
 *
 */
public interface ITropicraftBlock {
	/** Get the name of this block based on its state */
	public String getStateName(IBlockState state);
	
	@SideOnly(Side.CLIENT)
	default IBlockColor getBlockColor() {
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	default IItemColor getItemColor() {
		return null;
	}
}
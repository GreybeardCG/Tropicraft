package net.tropicraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.tropicraft.info.TCInfo;
import net.tropicraft.info.TCNames;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTropicsWater extends BlockFluidClassic {

	@SideOnly(Side.CLIENT)
	public static IIcon stillIcon, flowingIcon;
	
	public BlockTropicsWater(Fluid fluid, Material material) {
		super(fluid, material);
		this.lightOpacity = 0;
		this.setCreativeTab(null);
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		stillIcon = iconRegister.registerIcon(TCInfo.ICON_LOCATION + TCNames.stillWater);
		flowingIcon = iconRegister.registerIcon(TCInfo.ICON_LOCATION + TCNames.flowingWater);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return side != 0 && side != 1 ? flowingIcon : stillIcon;
	}
	
}

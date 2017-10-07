package net.tropicraft.core.client.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.tropicraft.core.client.TropicraftRenderUtils;
import net.tropicraft.core.client.entity.model.ModelFish;
import net.tropicraft.core.common.entity.underdasea.atlantoku.EntityTropicalFish;
import net.tropicraft.core.common.entity.underdasea.atlantoku.EntityTropicraftWaterBase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

public class RenderTropicalFish extends RenderLiving<EntityTropicraftWaterBase> {

	public ModelFish fish;
	private TropicraftSpecialRenderHelper renderHelper;
	private static final Logger LOGGER = LogManager.getLogger();
	
	private int fishSlot = -1;

	public RenderTropicalFish(ModelBase modelbase, float f) {
		super(Minecraft.getMinecraft().getRenderManager(), modelbase, f);
		fish = (ModelFish) modelbase;
		renderHelper = new TropicraftSpecialRenderHelper();
	}
	
	public RenderTropicalFish(int fishSlot) {
		this(new ModelFish(), 1f);
		this.fishSlot = fishSlot;
	}
	
	/**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityTropicraftWaterBase entity, double x, double y, double z, float entityYaw, float partialTicks) {
    		this.shadowSize = 0.08f;
		this.shadowOpaque = 0.3f;
    		GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        this.mainModel.swingProgress = this.getSwingProgress(entity, partialTicks);
        boolean shouldSit = entity.isRiding() && (entity.getRidingEntity() != null && entity.getRidingEntity().shouldRiderSit());
        this.mainModel.isRiding = shouldSit;
        this.mainModel.isChild = entity.isChild();

        float offset = 0f;
        try
        {
            float f = this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
            float f1 = this.interpolateRotation(entity.prevSwimYaw+offset, entity.swimYaw+offset, partialTicks);
            float f2 = f1 - f;
            
            f1 = -f1;
            f = f1;

            float f7 = entity.prevSwimPitch + (entity.swimPitch - entity.prevSwimPitch) * partialTicks;
            this.renderLivingAt(entity, x, y, z);
            float f8 = this.handleRotationFloat(entity, partialTicks);
            this.applyRotations(entity, f8, f, partialTicks);
            float f4 = this.prepareScale(entity, partialTicks);
            float f5 = 0.0F;
            float f6 = 0.0F;

            if (!entity.isRiding()) {
                f5 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialTicks;
                f6 = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);

                if (entity.isChild()) {
                    f6 *= 3.0F;
                }

                if (f5 > 1.0F) {
                    f5 = 1.0F;
                }
            }
            
            if(entity.hurtTime > 0){

				GL11.glColor4f(2f, 0f, 0f, 1f);
			}
        

            this.renderFishy(entity);
			GL11.glColor4f(1f, 1f, 1f, 1f);

            GlStateManager.enableAlpha();
            this.mainModel.setLivingAnimations(entity, f6, f5, partialTicks);
            this.mainModel.setRotationAngles(f6, f5, f8, f2, f7, f4, entity);
            
       

            if (this.renderOutlines) {
                boolean flag1 = this.setScoreTeamColor(entity);
                GlStateManager.enableColorMaterial();
                GlStateManager.enableOutlineMode(this.getTeamColor(entity));

                if (!this.renderMarker) {
                    this.renderModel(entity, f6, f5, f8, f2, f7, f4);
                }

                this.renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, f4);

                GlStateManager.disableOutlineMode();
                GlStateManager.disableColorMaterial();

                if (flag1)
                {
                    this.unsetScoreTeamColor();
                }
            }
            else
            {
                boolean flag = this.setDoRenderBrightness(entity, partialTicks);
                this.renderModel(entity, f6, f5, f8, f2, f7, f4);

                if (flag)
                {
                    this.unsetBrightness();
                }

                GlStateManager.depthMask(true);
                this.renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, f4);
            }

            GlStateManager.disableRescaleNormal();
        }
        catch (Exception exception)
        {
            LOGGER.error((String)"Couldn\'t render entity", (Throwable)exception);
        }

        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

	protected void renderFishy(EntityTropicraftWaterBase entityliving) {
		GL11.glPushMatrix();
		fish.Body.postRender(.045F);
		TropicraftRenderUtils.bindTextureEntity("tropicalFish");
		GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(.85F, 0.0F, 0.0F);
		
		int fishTex = 0;
		if(entityliving instanceof EntityTropicalFish) {
			fishTex = ((EntityTropicalFish) entityliving).getColor() * 2;
		}
		if(this.fishSlot != -1) {
			fishTex = fishSlot;
			fishTex *= 2;
		}
	
		renderHelper.renderFish(fishTex);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		fish.Tail.postRender(.045F);
		GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-.90F, 0.725F, 0.0F);
			renderHelper.renderFish(fishTex+1);
		GL11.glPopMatrix();
	}

	protected void preRenderScale(EntityTropicraftWaterBase entityTropicalFish, float f) {
		GL11.glScalef(.75F, .20F, .20F);
	}

	@Override
	protected void preRenderCallback(EntityTropicraftWaterBase entityliving, float f) {
		preRenderScale(entityliving, f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTropicraftWaterBase entity) {
		return TropicraftRenderUtils.bindTextureEntity("tropicalFish");
	}
}
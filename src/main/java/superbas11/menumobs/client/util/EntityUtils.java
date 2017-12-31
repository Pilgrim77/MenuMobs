package superbas11.menumobs.client.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import superbas11.menumobs.MenuMobs;
import superbas11.menumobs.util.LogHelper;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class EntityUtils {
    // @formatter:off

    /**
     * <hipsterpig> i have some idea how to <hipsterpig> but it's too
     * complicated for me to bother with <hipsterpig> i'll have to sketch it on
     * a piece of paper for me to get it just right <hipsterpig> okay so first
     * off you need to take note of the how large/squishy the renderer is
     * scaling the model, that's done in preRenderCallback of the renderer,
     * protected iirc so you have to reflect in <hipsterpig> then you need to go
     * through the list of ModelRenderer in the mainModel class and set the
     * compiled field in all of them to false <hipsterpig> force the model to
     * render so that pieces of the model that will actually render will set the
     * compiled field back to true <hipsterpig> gather all these ModelRenderer
     * vars in said list, then you hav to go through their ModelBox list, find
     * out their size and relative position on the ModelRenderer to find the
     * outermost ModelBoxes in the XYZ axises <hipsterpig> then from there you
     * have your width, height, and length.. as well as how much the renderer
     * scales the model <hipsterpig> now the problem here is there are some
     * unique models, in vanilla alone. one being the enderdragon, another being
     * the villager zombie, just off the top of my mind <hipsterpig> ender
     * dragon only has a half of the body as a model, the other half is
     * reflected while rendering, and the model itself is rotated 180
     * <hipsterpig> for the villager zombie, when it's being rendered it doesn't
     * set the mainModel as the villager zombie's model, so if you pull the
     * mainModel out of the RenderLivingEntity and try to render that to get
     * the compiled ModelRenderers, it'll turn up with nothing <hipsterpig> if
     * you ask why i do that compile check, horse models. <hipsterpig> horse
     * models have horse, donkey, mule, armor, chest, all in one model, and in
     * one big cluttered mess <superbas11> would renderPassModel work? <hipsterpig>
     * i've never looked at renderPassModel. I assumed renderPassModel would be
     * null for a lot mod entities, especially custom mobs with no armor equip
     * <hipsterpig> but.. that poi also never looked how minecraft swaps
     * villager zombies properly <hipsterpig> superbas11: heh, <hipsterpig> but it's
     * too complicated for me to bother with <superbas11> yep <superbas11> seems like it
     * shouldn't be too hard, but then it is <hipsterpig> oh right <hipsterpig>
     * i forgot to tell you <hipsterpig> ModelBox doesn't actually store itself
     * as dimensions <hipsterpig> it stores x1 and x2 coords <hipsterpig> so you
     * need to get the Math.abs the difference to get the size of each axis,
     * then round it off to int <hipsterpig> and the texture vertex of the cubes
     * are defined by the texture offsets of the parent ModelRenderer, which
     * could change in between ModelBox creation in the same ModelRenderer, so
     * you have to calculate the texture offsets manually if you want to do
     * something texture related with ModelBox (this isn't related to what
     * you're asking)
     */
    // @formatter:on
    @SideOnly(Side.CLIENT)
    public static float getModelSize(EntityLivingBase ent) {
        Render render = Minecraft.getMinecraft().getRenderManager().getEntityRenderObject(ent);
        if (render instanceof RenderLivingBase) {
            RenderLivingBase entRender = (RenderLivingBase) render;
            ModelBase mainModel;
            ModelBase renderPassModel;
            try {
                Field mainModelField;
                Field renderPassModelField;

            } catch (Throwable e) {

            }
        }
        return 1.8F;
    }

    //    public static void drawEntityOnScreenAtRotation(int posX, int posY,
    //            float scale, float xAngle, float yAngle, EntityLivingBase ent)
    //    {
    //        GL11.glDisable(GL11.GL_BLEND);
    //        GL11.glDepthMask(true);
    //        GL11.glEnable(GL11.GL_DEPTH_TEST);
    //        GL11.glEnable(GL11.GL_ALPHA_TEST);
    //        GL11.glPushMatrix();
    //        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
    //        GlStateManager.translate(posX, posY, 50.0F);
    //        GL11.glScalef((-scale), scale, scale);
    //        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
    //        float f2 = ent.renderYawOffset;
    //        float f3 = ent.rotationYaw;
    //        float f4 = ent.rotationPitch;
    //        float f5 = ent.rotationYawHead;
    //        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
    //        RenderHelper.enableStandardItemLighting();
    //        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
    //        GlStateManager.rotate(xAngle, 1.0F, 0.0F, 0.0F);
    //        GlStateManager.rotate(yAngle, 0.0F, 1.0F, 0.0F);
    //        ent.renderYawOffset = (float) Math.atan(2.0F / 40.0F) * 20.0F;
    //        ent.rotationYaw = (float) Math.atan(2.0F / 40.0F) * 40.0F;
    //        ent.rotationPitch = -((float) Math.atan(2.0F / 40.0F)) * 20.0F;
    //        ent.rotationYawHead = ent.renderYawOffset;
    //        GlStateManager.translate(0.0F, ent.yOffset, 0.0F);
    //        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    //        float viewY = RenderManager.instance.playerViewY;
    //        try
    //        {
    //            RenderManager.instance.playerViewY = 180.0F;
    //            RenderManager.instance.renderEntityWithPosYaw(ent, 0.0D, 0.0D,
    //                    0.0D, 0.0F, 1.0F);
    //        }
    //        finally
    //        {
    //            GlStateManager.translate(0.0F, -0.22F, 0.0F);
    //            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,
    //                    255.0F * 0.8F, 255.0F * 0.8F);
    //            RenderManager.instance.playerViewY = viewY;
    //            ent.renderYawOffset = f2;
    //            ent.rotationYaw = f3;
    //            ent.rotationPitch = f4;
    //            ent.rotationYawHead = f5;
    //            GL11.glPopMatrix();
    //            RenderHelper.disableStandardItemLighting();
    //            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
    //            OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
    //            GL11.glDisable(GL11.GL_TEXTURE_2D);
    //            OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    //        }
    //    }

    @SideOnly(Side.CLIENT)
    public static void drawEntityOnScreen(int posX, int posY, float scale, float mouseX, float mouseY, EntityLivingBase ent) {
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.translate(posX, posY, 50.0F);
        GlStateManager.scale(-scale, scale, scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f2 = ent.renderYawOffset;
        float f3 = ent.rotationYaw;
        float f4 = ent.rotationPitch;
        float f5 = ent.prevRotationYawHead;
        float f6 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float) Math.atan(mouseY / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float) Math.atan(mouseX / 40.0F) * 20.0F;
        ent.rotationYaw = (float) Math.atan(mouseX / 40.0F) * 40.0F;
        ent.rotationPitch = -((float) Math.atan(mouseY / 40.0F)) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        if (ent instanceof EntityPlayer)
            ((EntityPlayer) ent).updateCape();
        try {
            RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
            rendermanager.setPlayerViewY(180.0F);
            rendermanager.setRenderShadow(false);
            rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, true);
            rendermanager.setRenderShadow(true);
        } finally {
            ent.renderYawOffset = f2;
            ent.rotationYaw = f3;
            ent.rotationPitch = f4;
            ent.prevRotationYawHead = f5;
            ent.rotationYawHead = f6;
            GlStateManager.popMatrix();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.disableTexture2D();
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GlStateManager.translate(0.0F, 0.0F, 20.0F);
            GlStateManager.resetColor();
        }
    }

    public static float getEntityScale(EntityLivingBase ent, float baseScale, float targetHeight) {
        return (targetHeight / Math.max(ent.width, ent.height)) * baseScale;
    }

    @SideOnly(Side.CLIENT)
    public static EntityLivingBase getRandomLivingEntity(World world) {
        return getRandomLivingEntity(world, null, 5, null);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @SideOnly(Side.CLIENT)
    public static EntityLivingBase getRandomLivingEntity(World world,
                                                         Set blacklist, int numberOfAttempts,
                                                         List<SimpleEntry<UUID, String>> fallbackPlayerNames) {
        Random random = new Random();
        final NetworkPlayerInfo networkPlayerInfo;
        GameProfile gameProfile;
        Minecraft mcClient = Minecraft.getMinecraft();
        // Get a COPY dumbass!
        Set<String> entities = ForgeRegistries.ENTITIES.getKeys().stream().map(ResourceLocation::toString).collect(Collectors.toSet());

        if (blacklist != null) {
            if (!entities.removeAll(blacklist))
                LogHelper.severe("Nothing got removed!");
        }

        String[] entStrings = entities.toArray(new String[]{});
        int id;
        Class clazz;

        int tries = 0;
        do {
            id = random.nextInt(entStrings.length);
            clazz = (Class) EntityList.getClass(new ResourceLocation(entStrings[id]));
        }
        while (!EntityLivingBase.class.isAssignableFrom(clazz)
               && (++tries <= numberOfAttempts));

        if (!EntityLivingBase.class.isAssignableFrom(clazz)) {
            if (fallbackPlayerNames != null) {
                SimpleEntry<UUID, String> entry = fallbackPlayerNames.get(random.nextInt(fallbackPlayerNames.size()));
                gameProfile = mcClient.getSessionService().fillProfileProperties(new GameProfile(entry.getKey(), entry.getValue()), true);
                networkPlayerInfo = new NetworkPlayerInfo(gameProfile);
                return new EntityOtherPlayerMP(world, gameProfile) {
                    @Nullable
                    @Override
                    protected NetworkPlayerInfo getPlayerInfo() {
                        return networkPlayerInfo;
                    }
                };
            } else
                return (EntityLivingBase) EntityList.createEntityByIDFromName(
                        new ResourceLocation("Chicken"), world);
        }

        if (MenuMobs.instance.allowDebugOutput)
            LogHelper.info(entStrings[id]);

        return (EntityLivingBase) EntityList.createEntityByIDFromName(
                new ResourceLocation(entStrings[id]), world);
    }

    @SideOnly(Side.CLIENT)
    public static void updateLightmap(Minecraft mc, World world) {
        float f = world.getSunBrightness(1.0F);
        float f1 = f * 0.95F + 0.05F;

        for (int i = 0; i < 256; ++i) {
            float f2 = world.provider.getLightBrightnessTable()[i / 16] * f1;
            float f3 = world.provider.getLightBrightnessTable()[i % 16] * (mc.entityRenderer.torchFlickerX * 0.1F + 1.5F);

            float f4 = f2 * (f * 0.65F + 0.35F);
            float f5 = f2 * (f * 0.65F + 0.35F);
            float f6 = f3 * ((f3 * 0.6F + 0.4F) * 0.6F + 0.4F);
            float f7 = f3 * (f3 * f3 * 0.6F + 0.4F);
            float f8 = f4 + f3;
            float f9 = f5 + f6;
            float f10 = f2 + f7;
            f8 = f8 * 0.96F + 0.03F;
            f9 = f9 * 0.96F + 0.03F;
            f10 = f10 * 0.96F + 0.03F;

            if (f8 > 1.0F) {
                f8 = 1.0F;
            }

            if (f9 > 1.0F) {
                f9 = 1.0F;
            }

            if (f10 > 1.0F) {
                f10 = 1.0F;
            }

            float f16 = mc.gameSettings.gammaSetting;
            float f17 = 1.0F - f8;
            float f13 = 1.0F - f9;
            float f14 = 1.0F - f10;
            f17 = 1.0F - f17 * f17 * f17 * f17;
            f13 = 1.0F - f13 * f13 * f13 * f13;
            f14 = 1.0F - f14 * f14 * f14 * f14;
            f8 = f8 * (1.0F - f16) + f17 * f16;
            f9 = f9 * (1.0F - f16) + f13 * f16;
            f10 = f10 * (1.0F - f16) + f14 * f16;
            f8 = f8 * 0.96F + 0.03F;
            f9 = f9 * 0.96F + 0.03F;
            f10 = f10 * 0.96F + 0.03F;

            if (f8 > 1.0F) {
                f8 = 1.0F;
            }

            if (f9 > 1.0F) {
                f9 = 1.0F;
            }

            if (f10 > 1.0F) {
                f10 = 1.0F;
            }

            if (f8 < 0.0F) {
                f8 = 0.0F;
            }

            if (f9 < 0.0F) {
                f9 = 0.0F;
            }

            if (f10 < 0.0F) {
                f10 = 0.0F;
            }

            int j = 255;
            int k = (int) (f8 * 255.0F);
            int l = (int) (f9 * 255.0F);
            int i1 = (int) (f10 * 255.0F);
            mc.entityRenderer.lightmapColors[i] = -16777216 | k << 16 | l << 8 | i1;
        }

        mc.entityRenderer.lightmapTexture.updateDynamicTexture();
    }

    public static Class<? extends Entity> getEntityClassByName(String name) {
        for (EntityEntry e : ForgeRegistries.ENTITIES) {
            if (e.getName().equals(name))
                return e.getEntityClass();
        }
        return null;
    }
}

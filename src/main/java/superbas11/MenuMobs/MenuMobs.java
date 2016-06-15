package superbas11.MenuMobs;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.Metadata;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = "@MOD_VERSION@", useMetadata = true, clientSideOnly = true, guiFactory = Reference.GUI_FACTORY)
public class MenuMobs {
    @Metadata(value = Reference.MODID)
    public static ModMetadata metadata;
    @Instance(value = Reference.MODID)
    public static MenuMobs instance;
    public boolean allowDebugOutput = false;
    public boolean showMainMenuMobs = true;
    private BSMainMenuRenderTicker mainMenuTicker;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        metadata = event.getModMetadata();

        File file = event.getSuggestedConfigurationFile();

        Reference.config = new Configuration(file);

        syncConfig();
    }

    public void syncConfig() {
        String ctgyGen = Configuration.CATEGORY_GENERAL;
        Reference.config.load();

        Reference.config.setCategoryComment(ctgyGen, "ATTENTION: Editing this file manually is no longer necessary. \n" +
                "On the Mods list screen select the entry for bspkrsCore, then click the Config button to modify these settings.");

        List<String> orderedKeys = new ArrayList<String>(ConfigElement.values().length);

        allowDebugOutput = Reference.config.getBoolean(ConfigElement.ALLOW_DEBUG_OUTPUT.key(), ctgyGen, allowDebugOutput,
                ConfigElement.ALLOW_DEBUG_OUTPUT.desc(), ConfigElement.ALLOW_DEBUG_OUTPUT.languageKey());
        orderedKeys.add(ConfigElement.ALLOW_DEBUG_OUTPUT.key());

        showMainMenuMobs = Reference.config.getBoolean(ConfigElement.SHOW_MAIN_MENU_MOBS.key(), ctgyGen, showMainMenuMobs,
                ConfigElement.SHOW_MAIN_MENU_MOBS.desc(), ConfigElement.SHOW_MAIN_MENU_MOBS.languageKey());
        orderedKeys.add(ConfigElement.SHOW_MAIN_MENU_MOBS.key());

        Reference.config.setCategoryPropertyOrder(ctgyGen, orderedKeys);

        Reference.config.save();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(instance);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        mainMenuTicker = new BSMainMenuRenderTicker();
    }

    @SubscribeEvent
    public void onConfigChanged(OnConfigChangedEvent event) {
        if (event.getModID().equals(Reference.MODID)) {
            Reference.config.save();
            syncConfig();
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (showMainMenuMobs)
            if ((event.getGui() instanceof GuiMainMenu) && !mainMenuTicker.isRegistered())
                mainMenuTicker.register();
            else if (mainMenuTicker.isRegistered())
                mainMenuTicker.unRegister();
    }
}

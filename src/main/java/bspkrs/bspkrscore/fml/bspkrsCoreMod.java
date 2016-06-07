package bspkrs.bspkrscore.fml;

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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = "@MOD_VERSION@", useMetadata = true, guiFactory = Reference.GUI_FACTORY)
public class bspkrsCoreMod
{
    // config stuff
    private final boolean       allowDebugOutputDefault          = false;
    public boolean              allowDebugOutput                 = allowDebugOutputDefault;
    private final boolean       showMainMenuMobsDefault          = true;
    public boolean              showMainMenuMobs                 = showMainMenuMobsDefault;

    @Metadata(value = Reference.MODID)
    public static ModMetadata   metadata;

    @Instance(value = Reference.MODID)
    public static bspkrsCoreMod instance;

    @SidedProxy(clientSide = Reference.PROXY_CLIENT, serverSide = Reference.PROXY_COMMON)
    public static CommonProxy   proxy;

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        FMLCommonHandler.instance().bus().register(instance);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.registerMainMenuTickHandler();
    }

    @SubscribeEvent
    public void onConfigChanged(OnConfigChangedEvent event)
    {
        if (event.getModID().equals(Reference.MODID))
        {
            Reference.config.save();
        }
    }
}

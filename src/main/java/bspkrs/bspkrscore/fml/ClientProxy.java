package bspkrs.bspkrscore.fml;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    private BSMainMenuRenderTicker mainMenuTicker;

    @Override
    protected void registerMainMenuTickHandler()
    {
        mainMenuTicker = new BSMainMenuRenderTicker();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event)
    {
        if (bspkrsCoreMod.instance.showMainMenuMobs)
            if ((event.getGui() instanceof GuiMainMenu) && !mainMenuTicker.isRegistered())
                mainMenuTicker.register();
            else if (mainMenuTicker.isRegistered())
                mainMenuTicker.unRegister();
    }
}

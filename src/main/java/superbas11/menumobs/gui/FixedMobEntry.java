package superbas11.menumobs.gui;

import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.IConfigElement;

public class FixedMobEntry extends GuiConfigEntries.ArrayEntry{
    public FixedMobEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
        super(owningScreen, owningEntryList, configElement);
    }

    @Override
    public void valueButtonPressed(int slotIndex)
    {
        mc.displayGuiScreen(new GuiFixedMobEntry(this.owningScreen, configElement, slotIndex, currentValues, enabled()));
    }
}

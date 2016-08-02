package superbas11.menumobs.gui;

import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.Arrays;

public class FixedMobEntry extends GuiConfigEntries.ArrayEntry {
    public FixedMobEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
        super(owningScreen, owningEntryList, configElement);
    }

    @Override
    public void valueButtonPressed(int slotIndex) {
        mc.displayGuiScreen(new GuiFixedMobEntry(this.owningScreen, configElement, slotIndex, currentValues, enabled()));
    }

    @Override
    public void updateValueButtonText() {
        super.updateValueButtonText();
        if (this.btnValue.displayString.equals("") || this.btnValue.displayString.equals("[]"))
            this.btnValue.displayString = "Off";
    }

    @Override
    public boolean isDefault() {
        return Arrays.asList(currentValues).isEmpty();
    }

    @Override
    public void setToDefault() {
        if (enabled()) {
            this.currentValues = new Object[0];
            updateValueButtonText();
        }
    }
}

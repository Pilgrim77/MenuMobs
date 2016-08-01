package superbas11.menumobs.gui;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiSelectString;
import net.minecraftforge.fml.client.config.GuiSelectStringEntries;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.Map;

public class GuiSelectFixedMob extends GuiSelectString {
    public GuiSelectFixedMob(GuiScreen parentScreen, IConfigElement configElement, int slotIndex, Map<Object, String> selectableValues, Object currentValue, boolean enabled) {
        super(parentScreen, configElement, slotIndex, selectableValues, currentValue, enabled);
    }

    @Override
    public void initGui() {
        super.initGui();
        this.entryList = new GuiSelectFixedMobEntries(this, this.mc, this.configElement, this.selectableValues);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 2000) {
            try {
                this.entryList.saveChanges();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            this.mc.displayGuiScreen(this.parentScreen);
        } else if (button.id == 2001) {
            this.currentValue = configElement.getDefault();
            this.entryList = new GuiSelectFixedMobEntries(this, this.mc, this.configElement, this.selectableValues);
        } else if (button.id == 2002) {
            this.currentValue = beforeValue;
            this.entryList = new GuiSelectFixedMobEntries(this, this.mc, this.configElement, this.selectableValues);
        }
    }

    public class GuiSelectFixedMobEntries extends GuiSelectStringEntries {
        public GuiSelectFixedMobEntries(GuiSelectString owningScreen, Minecraft mc, IConfigElement configElement, Map<Object, String> selectableValues) {
            super(owningScreen, mc, configElement, selectableValues);
        }

        @Override
        public void saveChanges() {
            if (slotIndex != -1 && parentScreen != null && parentScreen instanceof GuiFixedMobEntry)
                ((GuiFixedMobEntry) parentScreen).setValueFromChildScreen(slotIndex, owningScreen.currentValue);
        }
    }
}

package superbas11.menumobs.gui;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiEditArray;
import net.minecraftforge.fml.client.config.GuiEditArrayEntries;
import net.minecraftforge.fml.client.config.IConfigElement;

public class BlacklistArrayEntry extends GuiEditArrayEntries.BaseEntry {
    protected GuiButtonExt btnValue;

    public BlacklistArrayEntry(GuiEditArray owningScreen, GuiEditArrayEntries owningEntryList, IConfigElement configElement, Object value) {
        super(owningScreen, owningEntryList, configElement);
        btnValue = new GuiButtonExt(0, owningEntryList.controlWidth, 0, owningEntryList.controlWidth, 18,
                value.toString());
    }

    @Override
    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
        super.drawEntry(slotIndex, x, y, listWidth, slotHeight, mouseX, mouseY, isSelected);

        btnValue.width = owningEntryList.controlWidth;
        btnValue.xPosition = listWidth / 4;
        btnValue.yPosition = y;
        btnValue.drawButton(owningEntryList.mc, mouseX, mouseY);
    }

    @Override
    public Object getValue() {
        return btnValue.displayString.trim();
    }
}

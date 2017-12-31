package superbas11.menumobs.gui;

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
    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, float partialTicks) {
        super.drawEntry(slotIndex, x, y, listWidth, slotHeight, mouseX, mouseY, isSelected, partialTicks);

        btnValue.width = owningEntryList.controlWidth;
        btnValue.x = listWidth / 4;
        btnValue.y = y;
        btnValue.drawButton(owningEntryList.getMC(), mouseX, mouseY, partialTicks);
    }

    @Override
    public Object getValue() {
        return btnValue.displayString.trim();
    }
}

package superbas11.menumobs.gui;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.client.config.*;
import net.minecraftforge.fml.common.FMLLog;
import org.lwjgl.input.Keyboard;
import superbas11.menumobs.MainMenuRenderTicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.minecraftforge.fml.client.config.GuiUtils.RESET_CHAR;
import static net.minecraftforge.fml.client.config.GuiUtils.UNDO_CHAR;

public class GuiFixedMobEntry extends GuiEditArray {

    public GuiFixedMobEntry(GuiScreen parentScreen, IConfigElement configElement, int slotIndex, Object[] currentValues, boolean enabled) {
        super(parentScreen, configElement, slotIndex, currentValues, enabled);
    }

    @Override
    public void initGui() {
        if (entryList == null)
            entryList = new GuiEditFixedMobEntries(this, this.mc, this.configElement, this.beforeValues, this.currentValues);

        ((GuiEditFixedMobEntries) entryList).controlWidth = (this.width / 2) - (configElement.isListLengthFixed() ? 0 : 48);
        entryList.width = width;
        entryList.height = height;
        entryList.top = titleLine2 != null ? (titleLine3 != null ? 43 : 33) : 23;
        entryList.bottom = height - 32;
        entryList.right = width;
        entryList.recalculateState();

        int undoGlyphWidth = mc.fontRenderer.getStringWidth(UNDO_CHAR) * 2;
        int resetGlyphWidth = mc.fontRenderer.getStringWidth(RESET_CHAR) * 2;
        int doneWidth = Math.max(mc.fontRenderer.getStringWidth(I18n.format("gui.done")) + 20, 100);
        int undoWidth = mc.fontRenderer.getStringWidth(" " + I18n.format("fml.configgui.tooltip.undoChanges")) + undoGlyphWidth + 20;
        int resetWidth = mc.fontRenderer.getStringWidth(" " + I18n.format("fml.configgui.tooltip.resetToDefault")) + resetGlyphWidth + 20;
        int buttonWidthHalf = (doneWidth + 5 + undoWidth + 5 + resetWidth) / 2;
        this.buttonList.add(btnDone = new GuiButtonExt(2000, this.width / 2 - buttonWidthHalf, this.height - 29, doneWidth, 20, I18n.format("gui.done")));
        this.buttonList.add(btnDefault = new GuiUnicodeGlyphButton(2001, this.width / 2 - buttonWidthHalf + doneWidth + 5 + undoWidth + 5,
                this.height - 29, resetWidth, 20, " " + I18n.format("fml.configgui.tooltip.resetToDefault"), RESET_CHAR, 2.0F));
        this.buttonList.add(btnUndoChanges = new GuiUnicodeGlyphButton(2002, this.width / 2 - buttonWidthHalf + doneWidth + 5,
                this.height - 29, undoWidth, 20, " " + I18n.format("fml.configgui.tooltip.undoChanges"), UNDO_CHAR, 2.0F));
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    @Override
    protected void actionPerformed(GuiButton button) {
        super.actionPerformed(button);
        if (button.id == 2001)
            this.currentValues = new Object[0];
        if (button.id == 2001 || button.id == 2002)
            this.entryList = new GuiEditFixedMobEntries(this, this.mc, this.configElement, this.beforeValues, this.currentValues);
        this.entryList.recalculateState();
    }

    void setValueFromChildScreen(int index, Object value) {
        this.entryList.listEntries.set(index, new FixedEntityArrayEntry.FixedMobArrayEntry(this, entryList, configElement, value));
    }

    public static class GuiEditFixedMobEntries extends GuiEditArrayEntries {

        int controlWidth;

        public GuiEditFixedMobEntries(GuiEditArray parent, Minecraft mc, IConfigElement configElement, Object[] beforeValues, Object[] currentValues) {
            super(parent, mc, configElement, beforeValues, currentValues);
            this.controlWidth = super.controlWidth;
            listEntries = new ArrayList<IArrayEntry>();

            if (configElement.isList()) {
                Class<? extends IArrayEntry> clazz;
                for (Object value : currentValues) {
                    try {
                        if (value == "")
                            continue;
                        if (Arrays.asList(MainMenuRenderTicker.getEntStrings()).contains(value.toString()))
                            clazz = FixedEntityArrayEntry.FixedMobArrayEntry.class;
                        else
                            clazz = FixedEntityArrayEntry.FixedPlayerArrayEntry.class;

                        listEntries.add(clazz.getConstructor(GuiEditArray.class, GuiEditArrayEntries.class, IConfigElement.class, Object.class)
                                .newInstance(this.owningGui, this, configElement, value.toString()));
                    } catch (Throwable e) {
                        FMLLog.severe("There was a critical error instantiating the custom IGuiEditListEntry for property %s.", configElement.getName());
                        e.printStackTrace();
                    }
                }
            }

            if (!configElement.isListLengthFixed())
                listEntries.add(new BaseEntry(this.owningGui, this, configElement));
        }

        @Override
        public void addNewEntry(int index) {

            FixedEntityArrayEntry entry;

            entry = new FixedEntityArrayEntry.FixedEntityOptionEntry(this.owningGui, this, this.configElement);

            listEntries.add(index, entry);
            keyTyped((char) Keyboard.CHAR_NONE, Keyboard.KEY_END);
        }

        @Override
        public void recalculateState() {
            isDefault = true;
            isChanged = false;

            int listLength = configElement.isListLengthFixed() ? listEntries.size() : listEntries.size() - 1;

            if (listLength > 0) {
                isDefault = false;
            }

            if (listLength != beforeValues.length) {
                isChanged = true;
            }

            if (!isChanged)
                for (int i = 0; i < listLength; i++)
                    if (!beforeValues[i].equals(listEntries.get(i).getValue()))
                        isChanged = true;
        }

        @Override
        protected void saveListChanges() {
            List<IArrayEntry> removeList = new ArrayList<IArrayEntry>();
            for (IArrayEntry entry : listEntries) {
                if (entry.getValue() == "" && entry instanceof FixedEntityArrayEntry)
                    removeList.add(entry);
            }
            listEntries.removeAll(removeList);
            super.saveListChanges();
        }
    }
}

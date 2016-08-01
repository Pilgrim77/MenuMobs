package superbas11.menumobs.gui;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiEditArray;
import net.minecraftforge.fml.client.config.GuiEditArrayEntries;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.common.FMLLog;
import org.lwjgl.input.Keyboard;
import superbas11.menumobs.MainMenuRenderTicker;

import java.util.*;

public class GuiFixedMobEntry extends GuiEditArray {
    private HashMap<Integer, Object> newValues = new HashMap();
    private List<GuiEditArrayEntries.IArrayEntry> listEntries;

    public GuiFixedMobEntry(GuiScreen parentScreen, IConfigElement configElement, int slotIndex, Object[] currentValues, boolean enabled) {
        super(parentScreen, configElement, slotIndex, currentValues, enabled);
    }

    @Override
    public void initGui() {
        super.initGui();
        entryList = new GuiEditFixedMobEntries(this, this.mc, this.configElement, this.beforeValues, this.currentValues);

        if (listEntries != null)
            this.entryList.listEntries = listEntries;

        for (Map.Entry<Integer, Object> entry : newValues.entrySet()) {
            try {
                if (this.entryList.listEntries.get(entry.getKey()) instanceof FixedEntityArrayEntry.FixedMobArrayEntry)
                    ((FixedEntityArrayEntry.FixedMobArrayEntry) this.entryList.listEntries.get(entry.getKey())).setValueFromChildScreen(entry.getValue());
                else
                    this.entryList.listEntries.set(entry.getKey(), new FixedEntityArrayEntry.FixedMobArrayEntry(this, entryList, configElement, entry.getValue()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        newValues = new HashMap<Integer, Object>();
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    @Override
    protected void actionPerformed(GuiButton button) {
        super.actionPerformed(button);
        if (button.id == 2001 || button.id == 2002)
            this.entryList = new GuiEditFixedMobEntries(this, this.mc, this.configElement, this.beforeValues, this.currentValues);

    }

    public void setValueFromChildScreen(int index, Object value) {
        this.newValues.put(index, value);
        listEntries = this.entryList.listEntries;
    }

    public static class GuiEditFixedMobEntries extends GuiEditArrayEntries {

        public GuiEditFixedMobEntries(GuiEditArray parent, Minecraft mc, IConfigElement configElement, Object[] beforeValues, Object[] currentValues) {
            super(parent, mc, configElement, beforeValues, currentValues);
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
        protected void saveListChanges() {
            List removeList = new ArrayList<IArrayEntry>();
            for (IArrayEntry entry : listEntries) {
                if (entry.getValue() == "" && entry instanceof FixedEntityArrayEntry)
                    removeList.add(entry);
            }
            listEntries.removeAll(removeList);
            super.saveListChanges();
        }
    }
}

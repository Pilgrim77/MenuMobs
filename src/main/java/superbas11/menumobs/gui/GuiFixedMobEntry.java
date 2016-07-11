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

import java.util.ArrayList;
import java.util.Arrays;

public class GuiFixedMobEntry extends GuiEditArray
{
    public GuiFixedMobEntry(GuiScreen parentScreen, IConfigElement configElement, int slotIndex, Object[] currentValues, boolean enabled) {
        super(parentScreen, configElement, slotIndex, currentValues, enabled);
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.entryList = new GuiEditFixedMobEntries(this, this.mc, this.configElement, this.beforeValues, this.currentValues);
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    @Override
    protected void actionPerformed(GuiButton button)
    {
        super.actionPerformed(button);
        if (button.id == 2001)
        {
            this.entryList = new GuiEditFixedMobEntries(this, this.mc, this.configElement, this.beforeValues, this.currentValues);
        }
        else if (button.id == 2002)
        {
            this.entryList = new GuiEditFixedMobEntries(this, this.mc, this.configElement, this.beforeValues, this.currentValues);
        }
    }

    public GuiEditArrayEntries getEntryList(){
        return this.entryList;
    }

    public void setCurrentValue(int index, Object value){
        this.currentValues[index] = value;
        this.updateScreen();
    }

    public static class GuiEditFixedMobEntries extends GuiEditArrayEntries {
        public GuiEditFixedMobEntries(GuiEditArray parent, Minecraft mc, IConfigElement configElement, Object[] beforeValues, Object[] currentValues) {
            super(parent, mc, configElement, beforeValues, currentValues);
            listEntries = new ArrayList<IArrayEntry>();
            if (configElement.isList() && configElement.getArrayEntryClass() != null) {
                Class<? extends IArrayEntry> clazz = configElement.getArrayEntryClass();
                for (Object value : currentValues) {
                    try {
                        if (Arrays.asList(MainMenuRenderTicker.getEntStrings()).contains(value.toString()))
                            clazz = FixedEntityArrayEntry.FixedMobArrayEntry.class;

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

            //TODO fix logic
            if (true == true)
                entry = new FixedEntityArrayEntry.FixedPlayerArrayEntry(this.owningGui, this, this.configElement, "");
            else
                entry = new FixedEntityArrayEntry.FixedMobArrayEntry(this.owningGui, this, this.configElement, "");

            listEntries.add(index, entry);
            keyTyped((char) Keyboard.CHAR_NONE, Keyboard.KEY_END);
        }
    }
}

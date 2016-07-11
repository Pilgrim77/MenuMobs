package superbas11.menumobs.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiEditArray;
import net.minecraftforge.fml.client.config.GuiEditArrayEntries;
import net.minecraftforge.fml.client.config.IConfigElement;
import org.lwjgl.input.Keyboard;
import superbas11.menumobs.MainMenuRenderTicker;

import java.util.Map;
import java.util.TreeMap;

public abstract class FixedEntityArrayEntry extends GuiEditArrayEntries.BaseEntry{

    public FixedEntityArrayEntry(GuiEditArray owningScreen, GuiEditArrayEntries owningEntryList, IConfigElement configElement, Object value) {
        super(owningScreen, owningEntryList, configElement);
    }

    @Override
    public abstract void keyTyped(char eventChar, int eventKey);

    @Override
    public abstract void updateCursorCounter();

    @Override
    public abstract Object getValue();


    public static class FixedMobArrayEntry extends FixedEntityArrayEntry {
        protected GuiButtonExt btnValue;
        protected Minecraft mc;
        protected final String        beforeValue;
        protected Object              currentValue;
        protected Map<Object, String> selectableValues;

        public FixedMobArrayEntry(GuiEditArray owningScreen, GuiEditArrayEntries owningEntryList, IConfigElement configElement, Object value) {
            super(owningScreen, owningEntryList, configElement, value);
            this.mc = Minecraft.getMinecraft();
            this.btnValue = new GuiButtonExt(0, owningEntryList.controlWidth, 0, owningEntryList.controlWidth, 18,
                    value.toString());
            beforeValue = value.toString();
            currentValue = value.toString();
            this.selectableValues = getSelectableValues();
            updateValueButtonText();
        }

        private static Map<Object, String> getSelectableValues()
        {
            Map<Object, String> selectableValues = new TreeMap<Object, String>();
            Class clazz;
            for (String mob: MainMenuRenderTicker.getEntStrings()) {
                clazz = (Class) EntityList.NAME_TO_CLASS.get(mob);
                if (EntityLivingBase.class.isAssignableFrom(clazz))
                    selectableValues.put(mob,mob);

            }

            return selectableValues;
        }

        public void updateValueButtonText()
        {
            this.btnValue.displayString = currentValue.toString();
        }

        @Override
        public boolean mousePressed(int index, int x, int y, int mouseEvent, int relativeX, int relativeY) {
            if (this.btnValue.mousePressed(this.mc, x, y))
            {
                btnValue.playPressSound(mc.getSoundHandler());
                valueButtonPressed(index);
                updateValueButtonText();
                return true;
            }
            else
                return super.mousePressed(index, x, y, mouseEvent, relativeX, relativeY);
        }

        public void valueButtonPressed(int slotIndex)
        {
            mc.displayGuiScreen(new GuiSelectFixedMob(this.owningScreen, configElement,slotIndex, selectableValues, currentValue, enabled()));
        }


        public void setValueFromChildScreen(Object newValue)
        {
            if (enabled() && currentValue != null ? !currentValue.equals(newValue) : newValue != null)
            {
                currentValue = newValue;
                updateValueButtonText();
            }
        }

        public boolean isDefault()
        {
            if (configElement.getDefault() != null)
                return configElement.getDefault().equals(currentValue);
            else
                return currentValue == null;
        }

        public void setToDefault()
        {
            if (enabled())
            {
                this.currentValue = configElement.getDefault().toString();
                updateValueButtonText();
            }
        }

        public boolean isChanged()
        {
            if (beforeValue != null)
                return !beforeValue.equals(currentValue);
            else
                return currentValue == null;
        }

        public void undoChanges()
        {
            if (enabled())
            {
                currentValue = beforeValue;
                updateValueButtonText();
            }
        }

        public boolean saveConfigElement()
        {
            if (enabled() && isChanged())
            {
                this.configElement.set(currentValue);
                return configElement.requiresMcRestart();
            }
            return false;
        }

        public String getCurrentValue()
        {
            return this.currentValue.toString();
        }


        public String[] getCurrentValues()
        {
            return new String[] { getCurrentValue() };
        }


        @Override
        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
            super.drawEntry(slotIndex, x, y, listWidth, slotHeight, mouseX, mouseY, isSelected);

            this.btnValue.width = this.owningEntryList.controlWidth;
            this.btnValue.xPosition = listWidth/4;
            this.btnValue.yPosition = y;
            this.btnValue.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        }

        @Override
        public void keyTyped(char eventChar, int eventKey) {

        }

        @Override
        public void updateCursorCounter() {

        }

        @Override
        public Object getValue() {
            return currentValue.toString().trim();
        }

        //TODO remove this
        public boolean enabled(){return true;}
    }

    public static class FixedPlayerArrayEntry extends FixedEntityArrayEntry
    {
        protected GuiTextField textFieldValue;
        protected boolean enabled = true;

        public FixedPlayerArrayEntry(GuiEditArray owningScreen, GuiEditArrayEntries owningEntryList, IConfigElement configElement, Object value) {
            super(owningScreen, owningEntryList, configElement, value);
                this.textFieldValue = new GuiTextField(0, owningEntryList.mc.fontRendererObj, owningEntryList.width / 4 + 1, 0, owningEntryList.controlWidth - 3, 16);
                this.textFieldValue.setMaxStringLength(10000);
                this.textFieldValue.setText(value.toString());
        }

        @Override
        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
            super.drawEntry(slotIndex, x, y, listWidth, slotHeight, mouseX, mouseY, isSelected);

                if (configElement.isListLengthFixed() || slotIndex != owningEntryList.listEntries.size() - 1)
                {
                    this.textFieldValue.setVisible(true);
                    this.textFieldValue.yPosition = y + 1;
                    this.textFieldValue.drawTextBox();
                }
                else
                    this.textFieldValue.setVisible(false);
        }


        @Override
        public void keyTyped(char eventChar, int eventKey)
        {
            if (this.enabled ||eventKey == Keyboard.KEY_LEFT || eventKey == Keyboard.KEY_RIGHT
                    || eventKey == Keyboard.KEY_HOME || eventKey == Keyboard.KEY_END)
            {
                this.textFieldValue.textboxKeyTyped((this.enabled ? eventChar : Keyboard.CHAR_NONE), eventKey);

                if (configElement.getValidationPattern() != null)
                {
                    if (configElement.getValidationPattern().matcher(this.textFieldValue.getText().trim()).matches())
                        isValidValue = true;
                    else
                        isValidValue = false;
                }
            }
        }

        @Override
        public void updateCursorCounter()
        {
            this.textFieldValue.updateCursorCounter();
        }

        @Override
        public void mouseClicked(int x, int y, int mouseEvent)
        {
            this.textFieldValue.mouseClicked(x, y, mouseEvent);
        }

        @Override
        public Object getValue()
        {
            return this.textFieldValue.getText().trim();
        }
    }
}

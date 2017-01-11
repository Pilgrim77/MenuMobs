package superbas11.menumobs.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiEditArray;
import net.minecraftforge.fml.client.config.GuiEditArrayEntries;
import net.minecraftforge.fml.client.config.IConfigElement;
import org.lwjgl.input.Keyboard;
import superbas11.menumobs.MainMenuRenderTicker;
import superbas11.menumobs.util.LogHelper;

import java.util.Map;
import java.util.TreeMap;

public abstract class FixedEntityArrayEntry extends GuiEditArrayEntries.BaseEntry {
    private static Map<Object, String> selectableValues = FixedMobArrayEntry.getSelectableValues();
    protected Minecraft mc;

    public FixedEntityArrayEntry(GuiEditArray owningScreen, GuiEditArrayEntries owningEntryList, IConfigElement configElement) {
        super(owningScreen, owningEntryList, configElement);
        this.mc = Minecraft.getMinecraft();
    }

    @Override
    public abstract Object getValue();

    public static class FixedEntityOptionEntry extends FixedEntityArrayEntry {
        protected GuiButtonExt btnPlayer;
        protected GuiButtonExt btnEntity;

        public FixedEntityOptionEntry(GuiEditArray owningScreen, GuiEditArrayEntries owningEntryList, IConfigElement configElement) {
            super(owningScreen, owningEntryList, configElement);
            this.btnPlayer = new GuiButtonExt(0, ((GuiFixedMobEntry.GuiEditFixedMobEntries) owningEntryList).controlWidth, 0, ((GuiFixedMobEntry.GuiEditFixedMobEntries) owningEntryList).controlWidth / 2 - 2, 18, "Player");
            this.btnEntity = new GuiButtonExt(0, ((GuiFixedMobEntry.GuiEditFixedMobEntries) owningEntryList).controlWidth, 0, ((GuiFixedMobEntry.GuiEditFixedMobEntries) owningEntryList).controlWidth / 2 - 2, 18, "Entity");
        }

        @Override
        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
            super.drawEntry(slotIndex, x, y, listWidth, slotHeight, mouseX, mouseY, isSelected);

            btnPlayer.width = ((GuiFixedMobEntry.GuiEditFixedMobEntries) owningEntryList).controlWidth / 2 - 2;
            btnPlayer.xPosition = listWidth / 4;
            btnPlayer.yPosition = y;
            btnPlayer.drawButton(mc, mouseX, mouseY);

            btnEntity.width = ((GuiFixedMobEntry.GuiEditFixedMobEntries) owningEntryList).controlWidth / 2 - 2;
            btnEntity.xPosition = listWidth / 4 + ((GuiFixedMobEntry.GuiEditFixedMobEntries) owningEntryList).controlWidth / 2 + 2;
            btnEntity.yPosition = y;
            btnEntity.drawButton(mc, mouseX, mouseY);
        }

        @Override
        public boolean mousePressed(int index, int x, int y, int mouseEvent, int relativeX, int relativeY) {
            if (this.btnPlayer.mousePressed(mc, x, y)) {
                btnPlayer.playPressSound(mc.getSoundHandler());
                this.owningEntryList.listEntries.set(index, new FixedPlayerArrayEntry(this.owningScreen, this.owningEntryList, this.configElement, ""));
                return true;
            } else if (btnEntity.mousePressed(mc, x, y)) {
                btnPlayer.playPressSound(mc.getSoundHandler());
                mc.displayGuiScreen(new GuiSelectFixedMob(this.owningScreen, configElement, index, selectableValues, "", true));
                return true;
            } else
                return super.mousePressed(index, x, y, mouseEvent, relativeX, relativeY);
        }

        @Override
        public Object getValue() {
            return "";
        }
    }

    public static class FixedMobArrayEntry extends FixedEntityArrayEntry {
        protected GuiButtonExt btnValue;
        protected Object currentValue;

        public FixedMobArrayEntry(GuiEditArray owningScreen, GuiEditArrayEntries owningEntryList, IConfigElement configElement, Object value) {
            super(owningScreen, owningEntryList, configElement);
            btnValue = new GuiButtonExt(0, ((GuiFixedMobEntry.GuiEditFixedMobEntries) owningEntryList).controlWidth, 0, ((GuiFixedMobEntry.GuiEditFixedMobEntries) owningEntryList).controlWidth, 18,
                    value.toString());
            currentValue = value.toString();
            updateValueButtonText();
        }

        protected static Map<Object, String> getSelectableValues() {
            Map<Object, String> selectableValues = new TreeMap<Object, String>();
            Class clazz;
            for (String mobID : MainMenuRenderTicker.getEntStrings()) {
                clazz = (Class) EntityList.getClass(new ResourceLocation(mobID));
                if (clazz == null) {
                    LogHelper.severe("Cannot find class of entity %s", mobID);
                    continue;
                }
                if (EntityLivingBase.class.isAssignableFrom(clazz))
                    selectableValues.put(mobID, EntityList.getTranslationName(new ResourceLocation(mobID)));
            }
            return selectableValues;
        }

        public void updateValueButtonText() {
            this.btnValue.displayString = currentValue.equals("") ? "None" : currentValue.toString();
        }

        @Override
        public boolean mousePressed(int index, int x, int y, int mouseEvent, int relativeX, int relativeY) {
            if (btnValue.mousePressed(mc, x, y)) {
                btnValue.playPressSound(mc.getSoundHandler());
                valueButtonPressed(index);
                updateValueButtonText();
                return true;
            } else
                return super.mousePressed(index, x, y, mouseEvent, relativeX, relativeY);
        }

        public void valueButtonPressed(int slotIndex) {
            mc.displayGuiScreen(new GuiSelectFixedMob(this.owningScreen, configElement, slotIndex, selectableValues, currentValue, enabled()));
        }


        public void setValueFromChildScreen(Object newValue) {
            if (enabled() && currentValue != null ? !currentValue.equals(newValue) : newValue != null) {
                currentValue = newValue;
                updateValueButtonText();
            }
        }

        @Override
        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
            super.drawEntry(slotIndex, x, y, listWidth, slotHeight, mouseX, mouseY, isSelected);

            btnValue.width = ((GuiFixedMobEntry.GuiEditFixedMobEntries) owningEntryList).controlWidth;
            btnValue.xPosition = listWidth / 4;
            btnValue.yPosition = y;
            btnValue.drawButton(mc, mouseX, mouseY);
        }

        @Override
        public Object getValue() {
            return currentValue.toString().trim();
        }

        public boolean enabled() {
            return this.owningEntryList.getEnabled();
        }

    }

    public static class FixedPlayerArrayEntry extends FixedEntityArrayEntry {
        protected GuiTextField textFieldValue;
        protected boolean enabled = true;

        public FixedPlayerArrayEntry(GuiEditArray owningScreen, GuiEditArrayEntries owningEntryList, IConfigElement configElement, Object value) {
            super(owningScreen, owningEntryList, configElement);
            this.textFieldValue = new GuiTextField(0, mc.fontRendererObj, owningEntryList.width / 4 + 1, 0, ((GuiFixedMobEntry.GuiEditFixedMobEntries) owningEntryList).controlWidth - 3, 16);
            this.textFieldValue.setMaxStringLength(10000);
            this.textFieldValue.setText(value.toString());
        }

        @Override
        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
            super.drawEntry(slotIndex, x, y, listWidth, slotHeight, mouseX, mouseY, isSelected);

            if (configElement.isListLengthFixed() || slotIndex != owningEntryList.listEntries.size() - 1) {
                this.textFieldValue.setVisible(true);
                this.textFieldValue.yPosition = y + 1;
                this.textFieldValue.drawTextBox();
            } else
                this.textFieldValue.setVisible(false);
        }


        @Override
        public void keyTyped(char eventChar, int eventKey) {
            if (this.enabled || eventKey == Keyboard.KEY_LEFT || eventKey == Keyboard.KEY_RIGHT
                    || eventKey == Keyboard.KEY_HOME || eventKey == Keyboard.KEY_END) {
                this.textFieldValue.textboxKeyTyped((this.enabled ? eventChar : Keyboard.CHAR_NONE), eventKey);

                if (configElement.getValidationPattern() != null) {
                    if (configElement.getValidationPattern().matcher(this.textFieldValue.getText().trim()).matches())
                        isValidValue = true;
                    else
                        isValidValue = false;
                }
            }
        }

        @Override
        public void updateCursorCounter() {
            this.textFieldValue.updateCursorCounter();
        }

        @Override
        public void mouseClicked(int x, int y, int mouseEvent) {
            this.textFieldValue.mouseClicked(x, y, mouseEvent);
        }

        @Override
        public Object getValue() {
            return this.textFieldValue.getText().trim();
        }
    }

}

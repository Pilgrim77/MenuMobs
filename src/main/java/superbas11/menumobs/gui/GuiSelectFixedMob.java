package superbas11.menumobs.gui;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.config.GuiSelectString;
import net.minecraftforge.fml.client.config.GuiSelectStringEntries;
import net.minecraftforge.fml.client.config.IConfigElement;
import org.lwjgl.input.Mouse;
import superbas11.menumobs.client.util.MobComparator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        HashMap<String, Integer[]> categories = new HashMap<String, Integer[]>();
        ArrayList<String> KeyList = new ArrayList<String>();


        public GuiSelectFixedMobEntries(GuiSelectString owningScreen, Minecraft mc, IConfigElement configElement, Map<Object, String> selectableValues) {
            super(owningScreen, mc, configElement, selectableValues);
            listEntries = new ArrayList<IGuiSelectStringListEntry>();

            int index = 0;
            String lastCategory = "minecraft";
            List<Map.Entry<Object, String>> sortedList = new ArrayList<Map.Entry<Object, String>>(selectableValues.entrySet());
            sortedList.sort(new MobComparator());

            for (Map.Entry<Object, String> entry : sortedList) {
                listEntries.add(new ListEntry(this, entry));
                KeyList.add(entry.getKey().toString());

                String slotValue = entry.getKey().toString();
                if (!slotValue.contains("minecraft:") && !slotValue.contains(lastCategory)) {
                    lastCategory = slotValue.split(":")[0];
                    categories.put(lastCategory,
                                   new Integer[]{
                                           index * this.slotHeight + this.headerPadding + categories.size() * 18 - 4,
                                           categories.size() + 1
                                   });
                }

                if (mc.fontRenderer.getStringWidth(entry.getValue()) > maxEntryWidth)
                    maxEntryWidth = mc.fontRenderer.getStringWidth(entry.getValue());

                if (owningScreen.currentValue.equals(entry.getKey())) {
                    this.selectedIndex = index;
                }

                index++;
            }
        }

        @Override
        public void saveChanges() {
            if (slotIndex != -1 && parentScreen != null && parentScreen instanceof GuiFixedMobEntry)
                ((GuiFixedMobEntry) parentScreen).setValueFromChildScreen(slotIndex, owningScreen.currentValue);
        }

        @Override
        protected void drawSelectionBox(int insideLeft, int insideTop, int mouseXIn, int mouseYIn, float partialTicks) {
            int i = this.getSize();
            int categoryCount = 0;
            String lastCategory = "minecraft";
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder vertexBuffer = tessellator.getBuffer();

            for (int j = 0; j < i; ++j) {
                String slotValue = KeyList.get(j);
                if (!slotValue.contains("minecraft:") && !slotValue.contains(lastCategory)) {
                    lastCategory = slotValue.split(":")[0];
                    drawCenteredString(fontRenderer, lastCategory, width / 2, insideTop + j * this.slotHeight + this.headerPadding + categoryCount * 18 + 4, 16777215);
                    categoryCount++;
                }

                int k = insideTop + j * this.slotHeight + this.headerPadding + categoryCount * 18;
                int l = this.slotHeight - 4;

                if (k > this.bottom || k + l < this.top) {
                    this.updateItemPos(j, insideLeft, k, partialTicks);
                }

                if (this.showSelectionBox && this.isSelected(j)) {
                    int i1 = this.left + (this.width / 2 - this.getListWidth() / 2);
                    int j1 = this.left + this.width / 2 + this.getListWidth() / 2;
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    GlStateManager.disableTexture2D();
                    vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                    vertexBuffer.pos((double) i1, (double) (k + l + 2), 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
                    vertexBuffer.pos((double) j1, (double) (k + l + 2), 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
                    vertexBuffer.pos((double) j1, (double) (k - 2), 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
                    vertexBuffer.pos((double) i1, (double) (k - 2), 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
                    vertexBuffer.pos((double) (i1 + 1), (double) (k + l + 1), 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
                    vertexBuffer.pos((double) (j1 - 1), (double) (k + l + 1), 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
                    vertexBuffer.pos((double) (j1 - 1), (double) (k - 1), 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
                    vertexBuffer.pos((double) (i1 + 1), (double) (k - 1), 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
                    tessellator.draw();
                    GlStateManager.enableTexture2D();
                }

                this.drawSlot(j, insideLeft, k, l, mouseXIn, mouseYIn, partialTicks);
            }
        }

        @Override
        public void drawScreen(int mouseXIn, int mouseYIn, float partialTicks) {
            super.drawScreen(mouseXIn, mouseYIn, partialTicks);
        }

        @Override
        public int getMaxScroll() {
            return Math.max(0, this.getContentHeight() - (this.bottom - this.top - 4) + categories.size() * 18);
        }

        @Override
        public void handleMouseInput() {
            if (this.isMouseYWithinSlotBounds(this.mouseY)) {

                int k = this.mouseY - this.top - this.headerPadding + (int) this.amountScrolled - 4;
                int categoryOffset = 0;
                int categoryNumber = 0;

                for (Map.Entry<String, Integer[]> category : categories.entrySet()) {
                    if (category.getValue()[0] < k && (category.getValue()[0] > categoryOffset || categoryOffset == 0)) {
                        categoryOffset = category.getValue()[0];
                        categoryNumber = category.getValue()[1];
                    }
                }

                int l;
                if (k < (categoryOffset + 18) && categoryOffset != 0)
                    l = -1;
                else
                    l = (k - categoryNumber * 18) / this.slotHeight;

                if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState() && this.mouseY >= this.top && this.mouseY <= this.bottom) {
                    int i = (this.width - this.getListWidth()) / 2;
                    int j = (this.width + this.getListWidth()) / 2;

                    if (l < this.getSize() && this.mouseX >= i && this.mouseX <= j && l >= 0 && k >= 0) {
                        this.elementClicked(l, false, this.mouseX, this.mouseY);
                        this.selectedElement = l;
                    } else if (this.mouseX >= i && this.mouseX <= j && k < 0) {
                        this.clickedHeader(this.mouseX - i, this.mouseY - this.top + (int) this.amountScrolled - 4);
                    }
                }

                if (Mouse.isButtonDown(0) && this.getEnabled()) {
                    if (this.initialClickY == -1) {
                        boolean flag1 = true;

                        if (this.mouseY >= this.top && this.mouseY <= this.bottom) {
                            int j2 = (this.width - this.getListWidth()) / 2;
                            int k2 = (this.width + this.getListWidth()) / 2;

                            if (l < this.getSize() && this.mouseX >= j2 && this.mouseX <= k2 && l >= 0 && k >= 0) {
                                boolean flag = l == this.selectedElement && Minecraft.getSystemTime() - this.lastClicked < 250L;
                                //this.elementClicked(i1, flag, this.mouseX, this.mouseY);
                                //this.selectedElement = i1;
                                this.lastClicked = Minecraft.getSystemTime();
                            } else if (this.mouseX >= j2 && this.mouseX <= k2 && k < 0) {
                                this.clickedHeader(this.mouseX - j2, this.mouseY - this.top + (int) this.amountScrolled - 4);
                                flag1 = false;
                            }

                            int i3 = this.getScrollBarX();
                            int j1 = i3 + 6;

                            if (this.mouseX >= i3 && this.mouseX <= j1) {
                                this.scrollMultiplier = -1.0F;
                                int k1 = this.getMaxScroll();

                                if (k1 < 1) {
                                    k1 = 1;
                                }

                                int l1 = (int) ((float) ((this.bottom - this.top) * (this.bottom - this.top)) / (float) this.getContentHeight());
                                l1 = MathHelper.clamp(l1, 32, this.bottom - this.top - 8);
                                this.scrollMultiplier /= (float) (this.bottom - this.top - l1) / (float) k1;
                            } else {
                                this.scrollMultiplier = 1.0F;
                            }

                            if (flag1) {
                                this.initialClickY = this.mouseY;
                            } else {
                                this.initialClickY = -2;
                            }
                        } else {
                            this.initialClickY = -2;
                        }
                    } else if (this.initialClickY >= 0) {
                        this.amountScrolled -= (float) (this.mouseY - this.initialClickY) * this.scrollMultiplier;
                        this.initialClickY = this.mouseY;
                    }
                } else {
                    this.initialClickY = -1;
                }

                int i2 = Mouse.getEventDWheel();

                if (i2 != 0) {
                    if (i2 > 0) {
                        i2 = -1;
                    } else if (i2 < 0) {
                        i2 = 1;
                    }

                    this.amountScrolled += (float) (i2 * this.slotHeight / 2);
                }
            }

        }
    }
}

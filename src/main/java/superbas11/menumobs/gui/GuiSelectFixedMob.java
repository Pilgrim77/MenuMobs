package superbas11.menumobs.gui;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.client.config.GuiSelectString;
import net.minecraftforge.fml.client.config.GuiSelectStringEntries;
import net.minecraftforge.fml.client.config.IConfigElement;
import superbas11.menumobs.client.util.MobComparator;

import java.util.*;

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
        HashMap<String,Integer> categories = new HashMap<String, Integer>();


        public GuiSelectFixedMobEntries(GuiSelectString owningScreen, Minecraft mc, IConfigElement configElement, Map<Object, String> selectableValues) {
            super(owningScreen, mc, configElement, selectableValues);
            listEntries = new ArrayList<IGuiSelectStringListEntry>();

            int index = 0;
            String lastCategory = "";
            List<Map.Entry<Object, String>> sortedList = new ArrayList<Map.Entry<Object, String>>(selectableValues.entrySet());
            Collections.sort(sortedList, new MobComparator());

            for (Map.Entry<Object, String> entry : sortedList) {
                listEntries.add(new ListEntry(this, entry));

                String slotValue = entry.getKey().toString();
                if (slotValue.contains(".") && !slotValue.contains(lastCategory)) {
                    lastCategory = slotValue.split("\\.")[0];
                    categories.put(lastCategory,index);
                }

                if (mc.fontRendererObj.getStringWidth(entry.getValue()) > maxEntryWidth)
                    maxEntryWidth = mc.fontRendererObj.getStringWidth(entry.getValue());

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
        protected void drawSelectionBox(int insideLeft, int insideTop, int mouseXIn, int mouseYIn) {
            int i = this.getSize();
            int categoryCount = 0;
            String lastCategory = "";
            Tessellator tessellator = Tessellator.getInstance();
            VertexBuffer vertexbuffer = tessellator.getBuffer();

            for (int j = 0; j < i; ++j) {
                String slotValue = listEntries.get(j).getValue().toString();
                if (slotValue.contains(".") && !slotValue.contains(lastCategory)) {
                    lastCategory = slotValue.split("\\.")[0];
                    drawCenteredString(fontRendererObj, lastCategory, width / 2, insideTop + j * this.slotHeight + this.headerPadding + categoryCount*18 + 4, 16777215);
                    categoryCount++;
                }

                int k = insideTop + j * this.slotHeight + this.headerPadding + categoryCount*18;
                int l = this.slotHeight - 4;

                if (k > this.bottom || k + l < this.top) {
                    this.updateItemPos(j, insideLeft, k);
                }

                if (this.showSelectionBox && this.isSelected(j)) {
                    int i1 = this.left + (this.width / 2 - this.getListWidth() / 2);
                    int j1 = this.left + this.width / 2 + this.getListWidth() / 2;
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    GlStateManager.disableTexture2D();
                    vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                    vertexbuffer.pos((double) i1, (double) (k + l + 2), 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
                    vertexbuffer.pos((double) j1, (double) (k + l + 2), 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
                    vertexbuffer.pos((double) j1, (double) (k - 2), 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
                    vertexbuffer.pos((double) i1, (double) (k - 2), 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
                    vertexbuffer.pos((double) (i1 + 1), (double) (k + l + 1), 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
                    vertexbuffer.pos((double) (j1 - 1), (double) (k + l + 1), 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
                    vertexbuffer.pos((double) (j1 - 1), (double) (k - 1), 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
                    vertexbuffer.pos((double) (i1 + 1), (double) (k - 1), 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
                    tessellator.draw();
                    GlStateManager.enableTexture2D();
                }

                this.drawSlot(j, insideLeft, k, l, mouseXIn, mouseYIn);
            }
        }

        @Override
        public void drawScreen(int mouseXIn, int mouseYIn, float partialTicks) {
            super.drawScreen(mouseXIn, mouseYIn, partialTicks);
        }

        @Override
        public int getMaxScroll() {
            return Math.max(0, this.getContentHeight() - (this.bottom - this.top - 4) + categories.size()*18);
        }

        @Override
        public void handleMouseInput() {
            //todo fix me
            super.handleMouseInput();
        }
    }
}

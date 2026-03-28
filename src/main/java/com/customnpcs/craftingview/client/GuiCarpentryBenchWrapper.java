package com.customnpcs.craftingview.client;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.customnpcs.craftingview.network.PacketHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import noppes.npcs.containers.ContainerCarpentryBench;
import noppes.npcs.controllers.RecipeCarpentry;

@SideOnly(Side.CLIENT)
public class GuiCarpentryBenchWrapper extends noppes.npcs.client.gui.player.GuiNpcCarpentryBench {

    private final RecipePanel panel;
    private boolean lastLeftDown = false;

    public GuiCarpentryBenchWrapper(ContainerCarpentryBench container) {
        super(container);
        // 1.6.4 ContainerCarpentryBench has no getMetadata(); always use global recipes
        panel = new RecipePanel(false);
    }

    @Override
    public void func_73863_a(int mouseX, int mouseY, float partialTick) {
        super.func_73863_a(mouseX, mouseY, partialTick);
        RecipePanelRenderer.render(this, panel, mouseX, mouseY);
        handleMouseInput(mouseX, mouseY);
        handleKeyInput();
    }

    public int getGuiLeft() {
        return field_74198_m;
    }

    public int getGuiTop() {
        return field_74197_n;
    }

    private void handleMouseInput(int mx, int my) {
        int guiLeft = field_74198_m;
        int guiTop = field_74197_n;

        boolean leftDown = Mouse.isButtonDown(0);
        boolean clicked = leftDown && !lastLeftDown;
        lastLeftDown = leftDown;

        int dwheel = Mouse.getDWheel();
        if (dwheel != 0) {
            if (RecipePanelRenderer.isPanelHit(panel, guiLeft, guiTop, mx, my)) {
                panel.scroll(dwheel < 0 ? 1 : -1);
            }
            return;
        }

        if (!clicked) return;

        if (!RecipePanelRenderer.isPanelHit(panel, guiLeft, guiTop, mx, my)) {
            panel.searchField.setFocused(false);
            return;
        }

        if (RecipePanelRenderer.isCollapseButtonHit(panel, guiLeft, guiTop, mx, my)) {
            panel.toggleCollapsed();
            return;
        }

        if (panel.isCollapsed()) return;

        panel.searchField.mouseClicked(mx, my, 0);

        int catIdx = RecipePanelRenderer.getCategoryTabHit(panel, guiLeft, guiTop, mx, my);
        if (catIdx >= 0) {
            panel.setCategory(catIdx);
            return;
        }

        int rowIdx = RecipePanelRenderer.getRecipeRowHit(panel, guiLeft, guiTop, mx, my);
        if (rowIdx >= 0) {
            java.util.List visible = panel.getVisible();
            if (rowIdx < visible.size()) {
                RecipeCarpentry recipe = (RecipeCarpentry) visible.get(rowIdx);
                if (RecipePanelRenderer.isPlusButtonHit(panel, guiLeft, guiTop, mx, my, rowIdx)) {
                    Minecraft.getMinecraft().getNetHandler().addToSendQueue(
                        PacketHandler.buildFillGridPacket(recipe.id));
                } else if (recipe == panel.getSelectedRecipe()) {
                    panel.selectRecipe(null);
                } else {
                    panel.selectRecipe(recipe);
                }
            }
        }
    }

    private void handleKeyInput() {
        if (panel.isCollapsed()) return;
        if (!panel.searchField.isFocused()) return;
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                panel.searchField.textboxKeyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
                panel.rebuildFiltered();
            }
        }
    }
}

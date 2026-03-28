package com.customnpcs.craftingview.client;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.customnpcs.craftingview.network.PacketFillCraftingGrid;
import com.customnpcs.craftingview.network.PacketHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import noppes.npcs.client.gui.player.GuiNpcCarpentryBench;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.containers.ContainerCarpentryBench;
import noppes.npcs.controllers.data.RecipeCarpentry;

@SideOnly(Side.CLIENT)
public class GuiEventHandler {

    private RecipePanel activePanel = null;
    private boolean lastLeftDown = false;

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (event.gui instanceof GuiNpcCarpentryBench) {
            GuiContainer guiContainer = (GuiContainer) event.gui;
            if (guiContainer.inventorySlots instanceof ContainerCarpentryBench) {
                ContainerCarpentryBench container = (ContainerCarpentryBench) guiContainer.inventorySlots;
                boolean isAnvil = container.getMetadata() >= 4;
                activePanel = new RecipePanel(isAnvil);
                lastLeftDown = false;
            } else {
                activePanel = null;
            }
        } else {
            activePanel = null;
        }
    }

    @SubscribeEvent
    public void onGuiDrawPost(GuiScreenEvent.DrawScreenEvent.Post event) {
        if (activePanel == null) return;
        if (!(event.gui instanceof GuiContainerNPCInterface)) return;

        Minecraft mc = Minecraft.getMinecraft();
        GuiContainerNPCInterface gui = (GuiContainerNPCInterface) mc.currentScreen;

        int mx = event.mouseX;
        int my = event.mouseY;

        RecipePanelRenderer.render(event.gui, activePanel, mx, my);

        handleMouseInput(gui, mx, my);
        handleKeyInput();
    }

    private void handleMouseInput(GuiContainerNPCInterface gui, int mx, int my) {
        int guiLeft = gui.guiLeft;
        int guiTop = gui.guiTop;

        boolean leftDown = Mouse.isButtonDown(0);
        boolean clicked = leftDown && !lastLeftDown;
        lastLeftDown = leftDown;

        // Scroll wheel
        int dwheel = Mouse.getDWheel();
        if (dwheel != 0) {
            if (RecipePanelRenderer.isPanelHit(activePanel, guiLeft, guiTop, mx, my)) {
                activePanel.scroll(dwheel < 0 ? 1 : -1);
            }
            return;
        }

        if (!clicked) return;

        if (!RecipePanelRenderer.isPanelHit(activePanel, guiLeft, guiTop, mx, my)) {
            activePanel.searchField.setFocused(false);
            return;
        }

        // Collapse toggle
        if (RecipePanelRenderer.isCollapseButtonHit(activePanel, guiLeft, guiTop, mx, my)) {
            activePanel.toggleCollapsed();
            return;
        }

        if (activePanel.isCollapsed()) return;

        // Search field click
        activePanel.searchField.mouseClicked(mx, my, 0);

        // Category tab
        int catIdx = RecipePanelRenderer.getCategoryTabHit(activePanel, guiLeft, guiTop, mx, my);
        if (catIdx >= 0) {
            activePanel.setCategory(catIdx);
            return;
        }

        // Recipe row / "+" button
        int rowIdx = RecipePanelRenderer.getRecipeRowHit(activePanel, guiLeft, guiTop, mx, my);
        if (rowIdx >= 0) {
            List<RecipeCarpentry> visible = activePanel.getVisible();
            if (rowIdx < visible.size()) {
                RecipeCarpentry recipe = visible.get(rowIdx);
                if (RecipePanelRenderer.isPlusButtonHit(activePanel, guiLeft, guiTop, mx, my, rowIdx)) {
                    PacketHandler.CHANNEL.sendToServer(new PacketFillCraftingGrid(recipe.id));
                } else if (recipe == activePanel.getSelectedRecipe()) {
                    activePanel.selectRecipe(null);
                } else {
                    activePanel.selectRecipe(recipe);
                }
            }
        }
    }

    private void handleKeyInput() {
        if (activePanel == null || activePanel.isCollapsed()) return;
        if (!activePanel.searchField.isFocused()) return;
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                activePanel.searchField.textboxKeyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
                activePanel.rebuildFiltered();
            }
        }
    }
}

package com.customnpcs.craftingview.client;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;

import com.customnpcs.craftingview.Config;
import com.customnpcs.craftingview.Config.CategoryDefinition;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import noppes.npcs.controllers.RecipeCarpentry;
import noppes.npcs.controllers.RecipeController;

@SideOnly(Side.CLIENT)
public class RecipePanel {

    public static final int PANEL_WIDTH = 124;
    public static final int RECIPES_PER_PAGE = 7;

    public static final CategoryDefinition BROWSE_ALL = new CategoryDefinition(
        "Browse All", new ArrayList(), new ArrayList());

    private final boolean isAnvil;
    private final List allRecipes = new ArrayList();
    private final List filtered = new ArrayList();
    private final List categories = new ArrayList();

    private boolean collapsed = false;
    private int scrollOffset = 0;
    private RecipeCarpentry selectedRecipe = null;
    private int activeCategoryIndex = 0;

    // Search field state — rebuilt each frame at the correct position
    private String searchText = "";
    private boolean searchFocused = false;
    // Current frame's field (set by renderer before input handling)
    public GuiTextField searchField;

    public RecipePanel(boolean isAnvil) {
        this.isAnvil = isAnvil;

        if (RecipeController.instance != null) {
            allRecipes.addAll(RecipeController.instance.globalRecipes.values());
            allRecipes.addAll(RecipeController.instance.anvilRecipes.values());
        }

        categories.add(BROWSE_ALL);
        categories.addAll(Config.categories);

        rebuildFiltered();
    }

    public GuiTextField buildSearchField(int x, int y) {
        GuiTextField tf = new GuiTextField(Minecraft.getMinecraft().fontRenderer,
            x, y, PANEL_WIDTH - 8, 12);
        tf.setMaxStringLength(32);
        tf.setText(searchText);
        tf.setFocused(searchFocused);
        searchField = tf;
        return tf;
    }

    public void syncSearchField() {
        if (searchField != null) {
            searchText = searchField.getText();
            searchFocused = searchField.isFocused();
        }
    }

    public void setSearchFocused(boolean focused) {
        searchFocused = focused;
        if (searchField != null) searchField.setFocused(focused);
    }

    public boolean isSearchFocused() {
        return searchFocused;
    }

    public void rebuildFiltered() {
        String query = searchText.toLowerCase().trim();
        CategoryDefinition cat = (CategoryDefinition) categories.get(activeCategoryIndex);

        filtered.clear();
        for (int i = 0; i < allRecipes.size(); i++) {
            RecipeCarpentry recipe = (RecipeCarpentry) allRecipes.get(i);
            if (!matchesCategory(recipe, cat)) continue;
            if (!query.isEmpty() && !matchesSearch(recipe, query)) continue;
            filtered.add(recipe);
        }

        int maxScroll = Math.max(0, filtered.size() - RECIPES_PER_PAGE);
        if (scrollOffset > maxScroll) scrollOffset = maxScroll;
    }

    private boolean matchesCategory(RecipeCarpentry recipe, CategoryDefinition cat) {
        if (cat == BROWSE_ALL || (cat.recipeIds.isEmpty() && cat.recipeNames.isEmpty())) return true;
        if (cat.recipeIds.contains(Integer.valueOf(recipe.id))) return true;
        if (recipe.name != null) {
            String rname = recipe.name.toLowerCase();
            for (int i = 0; i < cat.recipeNames.size(); i++) {
                if (rname.contains((String) cat.recipeNames.get(i))) return true;
            }
        }
        return false;
    }

    private boolean matchesSearch(RecipeCarpentry recipe, String query) {
        if (recipe.name != null && recipe.name.toLowerCase().contains(query)) return true;
        if (recipe.recipeOutput != null) {
            String itemName = recipe.recipeOutput.getDisplayName().toLowerCase();
            if (itemName.contains(query)) return true;
        }
        return false;
    }

    public List getVisible() {
        int end = Math.min(scrollOffset + RECIPES_PER_PAGE, filtered.size());
        return filtered.subList(scrollOffset, end);
    }

    public int getScrollOffset() { return scrollOffset; }
    public int getVisiblePerPage() { return RECIPES_PER_PAGE; }
    public int getFilteredSize() { return filtered.size(); }

    public void scroll(int delta) {
        int maxScroll = Math.max(0, filtered.size() - RECIPES_PER_PAGE);
        scrollOffset = Math.max(0, Math.min(scrollOffset + delta, maxScroll));
    }

    public void setCategory(int index) {
        if (index >= 0 && index < categories.size()) {
            activeCategoryIndex = index;
            scrollOffset = 0;
            rebuildFiltered();
        }
    }

    public void selectRecipe(RecipeCarpentry recipe) { selectedRecipe = recipe; }
    public void toggleCollapsed() { collapsed = !collapsed; }
    public boolean isCollapsed() { return collapsed; }
    public RecipeCarpentry getSelectedRecipe() { return selectedRecipe; }
    public int getActiveCategoryIndex() { return activeCategoryIndex; }
    public List getCategories() { return categories; }
    public boolean isAnvil() { return isAnvil; }
    public int getPanelX(int guiLeft) { return guiLeft - PANEL_WIDTH - 4; }
}

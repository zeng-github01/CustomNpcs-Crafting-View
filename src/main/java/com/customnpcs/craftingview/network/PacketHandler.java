package com.customnpcs.craftingview.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Field;
import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.customnpcs.craftingview.CraftingViewMod;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {

    public static final String CHANNEL = "cnpcs_craftview";
    public static final byte PACKET_FILL_GRID = 0;
    public static final byte PACKET_REQUEST_GLOBAL_RECIPES = 1;
    public static final byte PACKET_GLOBAL_RECIPES = 2;
    public static final byte PACKET_FILL_TWILIGHT_GRID = 3;

    private static final String TWILIGHT_CONTAINER_CLASS = "twilightforest.ContainerTFUncrafting";

    public static void init() {
        NetworkRegistry.instance().registerChannel(new PacketHandler(), CHANNEL);
    }

    public static Packet250CustomPayload buildFillGridPacket(int recipeId) {
        return buildRecipeIdPacket(PACKET_FILL_GRID, recipeId);
    }

    public static Packet250CustomPayload buildFillTwilightGridPacket(int recipeId) {
        return buildRecipeIdPacket(PACKET_FILL_TWILIGHT_GRID, recipeId);
    }

    public static Packet250CustomPayload buildRequestGlobalRecipesPacket() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeByte(PACKET_REQUEST_GLOBAL_RECIPES);
            dos.flush();
            return buildPayload(baos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Packet250CustomPayload buildRecipeIdPacket(byte type, int recipeId) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeByte(type);
            dos.writeInt(recipeId);
            dos.flush();
            return buildPayload(baos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Packet250CustomPayload buildPayload(byte[] data) {
        Packet250CustomPayload pkt = new Packet250CustomPayload();
        pkt.channel = CHANNEL;
        pkt.data = data;
        pkt.length = pkt.data.length;
        return pkt;
    }

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        try {
            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(packet.data));
            byte type = dis.readByte();
            if (type == PACKET_FILL_GRID) {
                if (player instanceof EntityPlayerMP) {
                    handleFillGrid(dis, (EntityPlayerMP) player);
                }
            } else if (type == PACKET_REQUEST_GLOBAL_RECIPES) {
                if (player instanceof EntityPlayerMP) {
                    handleRequestGlobalRecipes((EntityPlayerMP) player);
                }
            } else if (type == PACKET_GLOBAL_RECIPES) {
                handleGlobalRecipesClient(dis);
            } else if (type == PACKET_FILL_TWILIGHT_GRID) {
                if (player instanceof EntityPlayerMP) {
                    handleFillTwilightGrid(dis, (EntityPlayerMP) player);
                }
            }
        } catch (Exception e) {
            CraftingViewMod.LOG.log(Level.SEVERE, "Error handling packet", e);
        }
    }

    private void handleFillGrid(DataInputStream dis, EntityPlayerMP player) throws Exception {
        int recipeId = dis.readInt();

        if (!(player.openContainer instanceof noppes.npcs.containers.ContainerCarpentryBench)) return;
        noppes.npcs.containers.ContainerCarpentryBench container =
                (noppes.npcs.containers.ContainerCarpentryBench) player.openContainer;

        noppes.npcs.controllers.RecipeCarpentry recipe = getCarpentryRecipe(recipeId);
        if (recipe == null) return;

        int rw = recipe.recipeWidth;
        int rh = recipe.recipeHeight;
        for (int row = 0; row < rh; row++) {
            for (int col = 0; col < rw; col++) {
                ItemStack required = recipe.getCraftingItem(row * rw + col);
                if (required == null) continue;

                int gridSlot = row * 4 + col;
                ItemStack existing = container.craftMatrix.getStackInSlot(gridSlot);
                if (existing != null && matches(existing, required, recipe.ignoreDamage)) continue;

                if (existing != null) {
                    returnToInventory(player, existing);
                    container.craftMatrix.setInventorySlotContents(gridSlot, null);
                }

                ItemStack found = findAndTake(player, required, recipe.ignoreDamage);
                if (found != null) {
                    container.craftMatrix.setInventorySlotContents(gridSlot, found);
                }
            }
        }

        container.onCraftMatrixChanged(container.craftMatrix);
        container.detectAndSendChanges();
    }

    private void handleRequestGlobalRecipes(EntityPlayerMP player) throws Exception {
        noppes.npcs.controllers.RecipeController rc = noppes.npcs.controllers.RecipeController.instance;
        if (rc == null) return;

        NBTTagCompound compound = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        for (Object obj : rc.globalRecipes.values()) {
            noppes.npcs.controllers.RecipeCarpentry recipe = (noppes.npcs.controllers.RecipeCarpentry) obj;
            if (recipe.recipeWidth <= 3 && recipe.recipeHeight <= 3) {
                list.appendTag(recipe.writeNBT());
            }
        }
        compound.setTag("recipes", list);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeByte(PACKET_GLOBAL_RECIPES);
        CompressedStreamTools.write(compound, dos);
        dos.flush();
        PacketDispatcher.sendPacketToPlayer(buildPayload(baos.toByteArray()), (Player) player);
    }

    private void handleGlobalRecipesClient(DataInputStream dis) throws Exception {
        CraftingViewMod.proxy.handleTwilightGlobalRecipes(CompressedStreamTools.read(dis));
    }

    private void handleFillTwilightGrid(DataInputStream dis, EntityPlayerMP player) throws Exception {
        int recipeId = dis.readInt();
        Container container = player.openContainer;
        if (container == null || !TWILIGHT_CONTAINER_CLASS.equals(container.getClass().getName())) return;

        noppes.npcs.controllers.RecipeCarpentry recipe = getGlobalRecipe(recipeId);
        if (recipe == null || recipe.recipeWidth > 3 || recipe.recipeHeight > 3) return;

        Field field = container.getClass().getField("assemblyMatrix");
        InventoryCrafting assemblyMatrix = (InventoryCrafting) field.get(container);
        if (assemblyMatrix == null) return;

        // Twilight support intentionally clears the 3x3 assembly grid first so stale
        // partial recipes never remain mixed with the selected CustomNPCs global recipe.
        for (int i = 0; i < 9; i++) {
            ItemStack existing = assemblyMatrix.getStackInSlot(i);
            if (existing != null) {
                returnToInventory(player, existing);
                assemblyMatrix.setInventorySlotContents(i, null);
            }
        }

        for (int row = 0; row < recipe.recipeHeight; row++) {
            for (int col = 0; col < recipe.recipeWidth; col++) {
                ItemStack required = recipe.getCraftingItem(row * recipe.recipeWidth + col);
                if (required == null) continue;
                ItemStack found = findAndTake(player, required, recipe.ignoreDamage);
                if (found != null) {
                    assemblyMatrix.setInventorySlotContents(row * 3 + col, found);
                }
            }
        }

        container.onCraftMatrixChanged(assemblyMatrix);
        container.detectAndSendChanges();
    }

    private noppes.npcs.controllers.RecipeCarpentry getGlobalRecipe(int recipeId) {
        noppes.npcs.controllers.RecipeController rc = noppes.npcs.controllers.RecipeController.instance;
        return rc == null ? null :
            (noppes.npcs.controllers.RecipeCarpentry) rc.globalRecipes.get(Integer.valueOf(recipeId));
    }

    private noppes.npcs.controllers.RecipeCarpentry getCarpentryRecipe(int recipeId) {
        noppes.npcs.controllers.RecipeController rc = noppes.npcs.controllers.RecipeController.instance;
        return rc == null ? null :
            (noppes.npcs.controllers.RecipeCarpentry) rc.anvilRecipes.get(Integer.valueOf(recipeId));
    }

    private void returnToInventory(EntityPlayerMP player, ItemStack stack) {
        ItemStack remaining = stack.copy();
        if (!player.inventory.addItemStackToInventory(remaining) && remaining.stackSize > 0) {
            player.dropPlayerItemWithRandomChoice(remaining, false);
        }
    }

    private ItemStack findAndTake(EntityPlayerMP player, ItemStack required, boolean ignoreDamage) {
        for (int i = 0; i < player.inventory.mainInventory.length; i++) {
            ItemStack stack = player.inventory.mainInventory[i];
            if (stack == null) continue;
            if (!matches(stack, required, ignoreDamage)) continue;

            ItemStack taken = stack.copy();
            taken.stackSize = 1;
            if (stack.stackSize <= 1) {
                player.inventory.mainInventory[i] = null;
            } else {
                stack.stackSize--;
            }
            return taken;
        }
        return null;
    }

    private boolean matches(ItemStack stack, ItemStack required, boolean ignoreDamage) {
        if (stack.itemID != required.itemID) return false;
        if (!ignoreDamage && stack.getItemDamage() != required.getItemDamage()) return false;
        return ItemStack.areItemStackTagsEqual(stack, required);
    }
}

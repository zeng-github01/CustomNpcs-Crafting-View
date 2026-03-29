package com.customnpcs.craftingview.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.customnpcs.craftingview.CraftingViewMod;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {

    public static final String CHANNEL = CraftingViewMod.MODID;
    public static final byte PACKET_FILL_GRID = 0;

    public static void init() {
        NetworkRegistry.instance().registerChannel(new PacketHandler(), CHANNEL);
    }

    public static Packet250CustomPayload buildFillGridPacket(int recipeId) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeByte(PACKET_FILL_GRID);
            dos.writeInt(recipeId);
            dos.flush();
            Packet250CustomPayload pkt = new Packet250CustomPayload();
            pkt.channel = CHANNEL;
            pkt.data = baos.toByteArray();
            pkt.length = pkt.data.length;
            return pkt;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        try {
            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(packet.data));
            byte type = dis.readByte();
            if (type == PACKET_FILL_GRID) {
                handleFillGrid(dis, (EntityPlayerMP) player);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleFillGrid(DataInputStream dis, EntityPlayerMP player) throws Exception {
        int recipeId = dis.readInt();

        if (!(player.openContainer instanceof noppes.npcs.containers.ContainerCarpentryBench)) return;
        noppes.npcs.containers.ContainerCarpentryBench container =
            (noppes.npcs.containers.ContainerCarpentryBench) player.openContainer;

        noppes.npcs.controllers.RecipeController rc = noppes.npcs.controllers.RecipeController.instance;
        if (rc == null) return;
        noppes.npcs.controllers.RecipeCarpentry recipe = rc.getRecipe(recipeId);
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

    private void returnToInventory(EntityPlayerMP player, ItemStack stack) {
        if (!player.inventory.addItemStackToInventory(stack.copy())) {
            player.dropPlayerItemWithRandomChoice(stack, false);
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
        return true;
    }
}

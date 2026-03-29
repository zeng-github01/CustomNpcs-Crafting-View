package noppes.npcs.client;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import java.util.EnumSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.world.World;
import noppes.npcs.CustomNpcs;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.client.controllers.MusicController;
import noppes.npcs.client.gui.player.GuiFaction;
import noppes.npcs.client.gui.player.GuiQuestLog;
import noppes.npcs.client.gui.util.GuiMenuTopButton;
import noppes.npcs.client.gui.util.IButtonListener;
import noppes.npcs.constants.EnumPlayerPacket;

public class ClientTickHandler implements ITickHandler {
   private World prevWorld;
   private boolean otherContainer = false;

   public void tickStart(EnumSet type, Object... tickData) {
   }

   public void tickEnd(EnumSet type, Object... tickData) {
      if (type.contains(TickType.CLIENT)) {
         Minecraft mc = Minecraft.func_71410_x();
         if (mc.field_71439_g != null && mc.field_71439_g.field_71070_bA instanceof ContainerPlayer) {
            if (this.otherContainer) {
               NoppesUtilPlayer.sendData(EnumPlayerPacket.CheckQuestCompletion);
               this.otherContainer = false;
            }
         } else {
            this.otherContainer = true;
         }

         GuiScreen guiscreen = mc.field_71462_r;
         if (CustomNpcs.InventoryGuiEnabled && guiscreen instanceof GuiInventory && !this.guiHasButtons(guiscreen)) {
            GuiInventory guiinv = (GuiInventory)guiscreen;
            IButtonListener listener = new IButtonListener() {
               public void actionPerformed(GuiButton button) {
                  Minecraft mc = Minecraft.func_71410_x();
                  if (button.field_73741_f == 2) {
                     mc.func_71373_a(new GuiQuestLog(mc.field_71439_g));
                  }

                  if (button.field_73741_f == 3) {
                     mc.func_71373_a(new GuiFaction());
                  }

               }
            };
            GuiMenuTopButton inv = new GuiMenuTopButton(1, guiinv.field_74198_m + 3, guiinv.field_74197_n - 17, "menu.inventory");
            GuiMenuTopButton quests = new GuiMenuTopButton(2, inv, "quest.quests", listener);
            GuiMenuTopButton factions = new GuiMenuTopButton(3, quests, "menu.factions", listener);
            inv.active = true;
            guiinv.field_73887_h.add(inv);
            guiinv.field_73887_h.add(quests);
            guiinv.field_73887_h.add(factions);
         }

         ++CustomNpcs.ticks;
         if (this.prevWorld != mc.field_71441_e) {
            MusicController.Instance.playing = "";
            this.prevWorld = mc.field_71441_e;
         }
      }

   }

   public EnumSet ticks() {
      return EnumSet.of(TickType.CLIENT, TickType.WORLD);
   }

   public boolean guiHasButtons(GuiScreen guiscreen) {
      for(Object ob : guiscreen.field_73887_h) {
         if (ob instanceof GuiMenuTopButton) {
            return true;
         }
      }

      return false;
   }

   public String getLabel() {
      return "CNPCs ClientTickHandler";
   }
}

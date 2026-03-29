package noppes.npcs.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import noppes.npcs.CustomNpcs;
import org.apache.commons.lang3.RandomStringUtils;

public class PlayerDataController {
   private static final String id = RandomStringUtils.random(12, true, true);
   public static PlayerDataController instance;

   public PlayerDataController() {
      instance = this;
   }

   public File getSaveDir() {
      try {
         File file = new File(CustomNpcs.getWorldSaveDirectory(), "playerdata");
         if (!file.exists()) {
            file.mkdir();
         }

         return file;
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   public NBTTagCompound loadPlayerData(String player) {
      File saveDir = this.getSaveDir();
      String filename = player;
      if (player.isEmpty()) {
         filename = "noplayername";
      }

      filename = filename + ".dat";

      try {
         File file = new File(saveDir, filename);
         if (file.exists()) {
            return CompressedStreamTools.func_74796_a(new FileInputStream(file));
         }
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }

      try {
         File file = new File(saveDir, filename + "_old");
         if (file.exists()) {
            return CompressedStreamTools.func_74796_a(new FileInputStream(file));
         }
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }

      return new NBTTagCompound();
   }

   public void savePlayerData(String username, NBTTagCompound compound) {
      String filename = username;
      if (username.isEmpty()) {
         filename = "noplayername";
      } else {
         EntityPlayer player = MinecraftServer.func_71276_C().func_71203_ab().func_72361_f(username);
         if (player != null) {
            player.getEntityData().func_74778_a("CustomNpcsId", id);
            player.getEntityData().func_74782_a("CustomNpcsData", compound);
         }
      }

      filename = filename + ".dat";

      try {
         File saveDir = this.getSaveDir();
         File file = new File(saveDir, filename + "_new");
         File file1 = new File(saveDir, filename + "_old");
         File file2 = new File(saveDir, filename);
         CompressedStreamTools.func_74799_a(compound, new FileOutputStream(file));
         if (file1.exists()) {
            file1.delete();
         }

         file2.renameTo(file1);
         if (file2.exists()) {
            file2.delete();
         }

         file.renameTo(file2);
         if (file.exists()) {
            file.delete();
         }
      } catch (Exception e) {
         System.err.println(e.getMessage());
      }

   }

   public PlayerBankData getBankData(EntityPlayer player, int bankId) {
      Bank bank = BankController.getInstance().getBank(bankId);
      PlayerBankData data = this.getPlayerData(player).bankData;
      if (!data.hasBank(bank.id)) {
         data.loadNew(bank.id);
      }

      return data;
   }

   public PlayerData getPlayerData(EntityPlayer player) {
      PlayerData data = (PlayerData)player.getExtendedProperties("CustomNpcsData");
      if (data == null) {
         player.registerExtendedProperties("CustomNpcsData", data = new PlayerData(player.func_70005_c_()));
      }

      return data;
   }

   public String hasPlayer(String username) {
      for(String file : this.getSaveDir().list()) {
         if (file.equalsIgnoreCase(username + ".dat")) {
            return file.substring(0, file.length() - 4);
         }
      }

      return "";
   }

   public void addPlayerMessage(String username, PlayerMail mail) {
      mail.time = System.currentTimeMillis();
      EntityPlayer player = MinecraftServer.func_71276_C().func_71203_ab().func_72361_f(username);
      PlayerData data;
      if (player == null) {
         data = new PlayerData(username);
      } else {
         data = this.getPlayerData(player);
      }

      data.mailData.playermail.add(mail);
      this.savePlayerData(username, data.getNBT());
   }

   public boolean hasMail(EntityPlayer player) {
      return this.getPlayerData(player).mailData.hasMail();
   }
}

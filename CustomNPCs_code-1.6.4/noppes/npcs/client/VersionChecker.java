package noppes.npcs.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;

public class VersionChecker extends Thread {
   private int revision = 4;

   public void run() {
      String name = "§2CustomNpcs§f";
      String link = "§9§nhttp://tiny.cc/customnpcs";
      String text = name + " installed. More info at " + link;
      if (this.hasUpdate()) {
         text = name + '§' + "4 update available " + '§' + "fat " + link;
      }

      try {
         EntityPlayer player = Minecraft.func_71410_x().field_71439_g;
      } catch (NoSuchMethodError var7) {
         return;
      }

      EntityClientPlayerMP var8;
      while((var8 = Minecraft.func_71410_x().field_71439_g) == null) {
         try {
            Thread.sleep(2000L);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }

      ((EntityPlayer)var8).func_71035_c(text);
   }

   private boolean hasUpdate() {
      try {
         URL url = new URL("https://dl.dropboxusercontent.com/u/3096920/update/minecraft/1.6/CustomNPCs.txt");
         URLConnection yc = url.openConnection();
         BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
         String inputLine = in.readLine();
         if (inputLine == null) {
            return false;
         } else {
            int newVersion = Integer.parseInt(inputLine);
            return this.revision < newVersion;
         }
      } catch (Exception var6) {
         return false;
      }
   }
}

package noppes.npcs.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class AnalyticsTracking extends Thread {
   public void run() {
      while(true) {
         EntityPlayer player = Minecraft.func_71410_x().field_71439_g;

         while(player != null) {
            try {
               String analyticsPostData = "v=" + URLEncoder.encode("1", "UTF-8") + "&tid=" + URLEncoder.encode("UA-29079943-3", "UTF-8") + "&cid=" + URLEncoder.encode(player.func_110124_au().toString(), "UTF-8") + "&t=" + URLEncoder.encode("event", "UTF-8") + "&ec=" + URLEncoder.encode("mods", "UTF-8") + "&ea=" + URLEncoder.encode("customnpcs", "UTF-8") + "&el=" + URLEncoder.encode("1.6", "UTF-8") + "&ev=" + URLEncoder.encode("300", "UTF-8");
               String postDataType = "application/x-www-form-urlencoded";
               URL url = new URL("http://www.google-analytics.com/collect");
               HttpURLConnection connection = (HttpURLConnection)url.openConnection();
               connection.setDoOutput(true);
               connection.setRequestMethod("POST");
               connection.setRequestProperty("Content-Type", postDataType);
               connection.setRequestProperty("Content-Length", String.valueOf(analyticsPostData.length()));
               OutputStream dataOutput = connection.getOutputStream();
               dataOutput.write(analyticsPostData.getBytes());
               dataOutput.close();
               if (connection.getResponseCode() != 200) {
                  break;
               }
            } catch (IOException var9) {
            }

            try {
               Thread.sleep(30000L);
            } catch (InterruptedException var8) {
               break;
            }
         }

         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var7) {
         }
      }
   }
}

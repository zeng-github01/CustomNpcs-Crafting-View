package noppes.npcs.client.controllers;

import java.util.TreeSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.audio.SoundPool;
import net.minecraft.client.audio.SoundPoolEntry;
import noppes.npcs.client.AssetsBrowser;

public class MusicController {
   public TreeSet sounds = new TreeSet();
   public TreeSet music = new TreeSet();
   public static MusicController Instance;
   public String playing = "";
   private SoundPool musicPool;
   private int soundID = 0;

   public MusicController() {
      Instance = this;
      this.musicPool = new SoundPool(Minecraft.func_71410_x().func_110442_L(), "music", false);
      this.loadSounds();
   }

   private void loadSounds() {
      System.out.println("loading sounds");
      this.checkFolder(true, Minecraft.func_71410_x().field_71416_A.field_77379_b, "/customnpcs/sound/", "customnpcs:");
      this.checkFolder(false, this.musicPool, "/customnpcs/music/", "customnpcs:");
   }

   private void checkFolder(boolean isSounds, SoundPool pool, String folder, String name) {
      AssetsBrowser browser = new AssetsBrowser(folder, new String[]{"wav", "ogg"});

      for(String file : browser.files) {
         String dir = name + file;
         pool.func_77459_a(dir);
         this.addSound(isSounds, dir);
      }

      for(String map : browser.folders) {
         this.checkFolder(isSounds, pool, folder + map + "/", name + map + "/");
      }

   }

   private void addSound(boolean isSounds, String sound) {
      sound = sound.substring(0, sound.indexOf("."));
      sound = sound.replaceAll("/", ".");
      if (!isSounds) {
         this.music.add(sound);
      } else {
         while(Character.isDigit(sound.charAt(sound.length() - 1))) {
            sound = sound.substring(0, sound.length() - 1);
         }

         this.sounds.add(sound);
      }

   }

   public void stopMusic() {
      SoundManager manager = Minecraft.func_71410_x().field_71416_A;
      if (manager != null) {
         if (manager.field_77381_a.playing("streaming")) {
            manager.field_77381_a.stop("streaming");
         }

         if (manager.field_77381_a.playing("BgMusic")) {
            manager.field_77381_a.stop("BgMusic");
         }

         this.playing = "";
      }
   }

   public void playStreaming(String music, float x, float y, float z) {
      Minecraft mc = Minecraft.func_71410_x();
      if (mc.field_71474_y.field_74340_b != 0.0F && music != null) {
         SoundPoolEntry soundpoolentry = this.musicPool.func_77458_a(music);
         if (soundpoolentry != null) {
            this.playing = music;
            SoundManager manager = mc.field_71416_A;
            manager.func_82464_d();
            manager.field_77381_a.newStreamingSource(true, "streaming", soundpoolentry.func_110457_b(), soundpoolentry.func_110458_a(), false, x, y, z, 2, 64.0F);
            manager.field_77381_a.setVolume("streaming", 0.5F * mc.field_71474_y.field_74340_b);
            manager.field_77381_a.play("streaming");
         }
      }
   }

   public void playMusic(String music) {
      Minecraft mc = Minecraft.func_71410_x();
      if (mc.field_71474_y.field_74340_b != 0.0F && music != null) {
         SoundPoolEntry soundpoolentry = this.musicPool.func_77458_a(music);
         if (soundpoolentry != null) {
            this.playing = music;
            SoundManager manager = mc.field_71416_A;
            manager.func_82464_d();
            manager.field_77381_a.backgroundMusic("streaming", soundpoolentry.func_110457_b(), soundpoolentry.func_110458_a(), false);
            manager.field_77381_a.setVolume("streaming", 0.5F * mc.field_71474_y.field_74340_b);
            manager.field_77381_a.play("streaming");
         }
      }
   }

   public boolean isStreaming() {
      SoundManager manager = Minecraft.func_71410_x().field_71416_A;
      return manager.field_77381_a != null ? manager.field_77381_a.playing("streaming") : false;
   }

   public boolean isPlaying(String sound) {
      return this.isStreaming() && sound.equals(this.playing);
   }

   public void playSound(String music, float x, float y, float z) {
      Minecraft mc = Minecraft.func_71410_x();
      if (mc.field_71474_y.field_74340_b != 0.0F && music != null) {
         SoundPoolEntry soundpoolentry = this.musicPool.func_77458_a(music);
         if (soundpoolentry != null) {
            String sound = "csound_" + this.soundID++;
            SoundManager manager = mc.field_71416_A;
            manager.field_77381_a.newSource(true, sound, soundpoolentry.func_110457_b(), soundpoolentry.func_110458_a(), false, x, y, z, 2, 64.0F);
            manager.field_77381_a.setVolume(sound, 0.5F * mc.field_71474_y.field_74340_b);
            manager.field_77381_a.play(sound);
         }
      }
   }
}

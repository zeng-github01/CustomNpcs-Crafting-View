package noppes.npcs.roles;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.CustomItems;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.controllers.MusicController;
import noppes.npcs.constants.EnumBardInstrument;

public class JobBard extends JobInterface {
   public int minRange = 2;
   public int maxRange = 64;
   public boolean isStreamer = true;
   public String song = "";
   public String song2 = "";
   public String song3 = "";
   public String song4 = "";
   private EnumBardInstrument instrument;
   private long ticks;

   public JobBard(EntityNPCInterface npc) {
      super(npc);
      this.instrument = EnumBardInstrument.Banjo;
      this.ticks = 0L;
      if (CustomItems.banjo != null) {
         this.mainhand = new ItemStack(CustomItems.banjo);
         this.overrideMainHand = this.overrideOffHand = true;
      }

   }

   public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.func_74778_a("BardSong", this.song);
      nbttagcompound.func_74768_a("BardMinRange", this.minRange);
      nbttagcompound.func_74768_a("BardMaxRange", this.maxRange);
      nbttagcompound.func_74768_a("BardInstrument", this.instrument.ordinal());
      nbttagcompound.func_74757_a("BardStreamer", this.isStreamer);
   }

   public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
      this.song = nbttagcompound.func_74779_i("BardSong");
      this.minRange = nbttagcompound.func_74762_e("BardMinRange");
      this.maxRange = nbttagcompound.func_74762_e("BardMaxRange");
      this.setInstrument(nbttagcompound.func_74762_e("BardInstrument"));
      this.isStreamer = nbttagcompound.func_74767_n("BardStreamer");
   }

   public void setInstrument(int i) {
      if (CustomItems.banjo != null) {
         this.instrument = EnumBardInstrument.values()[i];
         this.overrideMainHand = this.overrideOffHand = this.instrument != EnumBardInstrument.None;
         switch (this.instrument) {
            case None:
               this.mainhand = null;
               this.offhand = null;
               break;
            case Banjo:
               this.mainhand = new ItemStack(CustomItems.banjo);
               this.offhand = null;
               break;
            case Violin:
               this.mainhand = new ItemStack(CustomItems.violin);
               this.offhand = new ItemStack(CustomItems.violinbow);
               break;
            case Guitar:
               this.mainhand = new ItemStack(CustomItems.guitar);
               this.offhand = null;
               break;
            case Harp:
               this.mainhand = new ItemStack(CustomItems.harp);
               this.offhand = null;
         }

      }
   }

   public EnumBardInstrument getInstrument() {
      return this.instrument;
   }

   public void onLivingUpdate() {
      if (this.npc.field_70170_p.field_72995_K) {
         ++this.ticks;
         if (this.ticks % 10L == 0L) {
            if (!this.song.isEmpty()) {
               boolean isStreaming = MusicController.Instance.isStreaming();
               if (isStreaming && this.song.equals(MusicController.Instance.playing)) {
                  if (this.isStreamer) {
                     List<EntityPlayer> list = this.npc.field_70170_p.func_72872_a(EntityPlayer.class, this.npc.field_70121_D.func_72314_b((double)this.maxRange, (double)(this.maxRange / 2), (double)this.maxRange));
                     if (!list.contains(Minecraft.func_71410_x().field_71439_g)) {
                        MusicController.Instance.stopMusic();
                     }
                  }
               } else {
                  List<EntityPlayer> list = this.npc.field_70170_p.func_72872_a(EntityPlayer.class, this.npc.field_70121_D.func_72314_b((double)this.minRange, (double)(this.minRange / 2), (double)this.minRange));
                  if (!list.contains(Minecraft.func_71410_x().field_71439_g)) {
                     return;
                  }

                  if (this.isStreamer) {
                     MusicController.Instance.playStreaming(this.song, (float)this.npc.field_70165_t, (float)this.npc.field_70163_u, (float)this.npc.field_70161_v);
                  } else {
                     MusicController.Instance.playMusic(this.song);
                  }
               }

            }
         }
      }
   }

   public void killed() {
      this.delete();
   }

   public void delete() {
      if (this.npc.field_70170_p.field_72995_K && MusicController.Instance.isPlaying(this.song)) {
         MusicController.Instance.stopMusic();
      }

   }
}

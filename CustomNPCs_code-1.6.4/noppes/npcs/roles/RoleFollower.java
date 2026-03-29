package noppes.npcs.roles;

import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.NBTTags;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.NpcMiscInventory;
import noppes.npcs.constants.EnumGuiType;

public class RoleFollower extends RoleInterface {
   private String owner;
   public boolean isFollowing = true;
   public HashMap rates = new HashMap();
   public NpcMiscInventory inventory = new NpcMiscInventory(3);
   public String dialogHire = StatCollector.func_74838_a("follower.hireText") + " {days} " + StatCollector.func_74838_a("follower.days");
   public String dialogFarewell = StatCollector.func_74838_a("follower.farewellText") + " {player}";
   public int daysHired;
   public long hiredTime;
   public int updateTick = 0;

   public RoleFollower(EntityNPCInterface npc) {
      super(npc);
   }

   public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.func_74768_a("MercenaryDaysHired", this.daysHired);
      nbttagcompound.func_74772_a("MercenaryHiredTime", this.hiredTime);
      nbttagcompound.func_74778_a("MercenaryDialogHired", this.dialogHire);
      nbttagcompound.func_74778_a("MercenaryDialogFarewell", this.dialogFarewell);
      if (this.owner != null && !this.owner.isEmpty()) {
         nbttagcompound.func_74778_a("MercenaryOwner", this.owner);
      }

      nbttagcompound.func_74782_a("MercenaryDayRates", NBTTags.nbtIntegerIntegerMap(this.rates));
      nbttagcompound.func_74766_a("MercenaryInv", this.inventory.getToNBT());
      nbttagcompound.func_74757_a("MercenaryIsFollowing", this.isFollowing);
   }

   public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
      this.owner = nbttagcompound.func_74779_i("MercenaryOwner");
      this.daysHired = nbttagcompound.func_74762_e("MercenaryDaysHired");
      this.hiredTime = nbttagcompound.func_74763_f("MercenaryHiredTime");
      this.dialogHire = nbttagcompound.func_74779_i("MercenaryDialogHired");
      this.dialogFarewell = nbttagcompound.func_74779_i("MercenaryDialogFarewell");
      this.rates = NBTTags.getIntegerIntegerMap(nbttagcompound.func_74761_m("MercenaryDayRates"));
      this.inventory.setFromNBT(nbttagcompound.func_74775_l("MercenaryInv"));
      this.isFollowing = nbttagcompound.func_74767_n("MercenaryIsFollowing");
   }

   public boolean aiShouldExecute() {
      if (this.hasOwner() && this.isFollowing) {
         if (this.getDaysLeft() <= 0) {
            EntityPlayer player = this.getOwner();
            if (player != null) {
               player.func_71035_c(this.dialogFarewell.replaceAll("\\{player\\}", player.field_71092_bJ));
            }

            this.killed();
            return false;
         } else {
            return !(this.npc.func_70032_d(this.getOwner()) < 10.0F);
         }
      } else {
         return false;
      }
   }

   public void aiUpdateTask() {
      ++this.updateTick;
      if (this.updateTick >= 10) {
         EntityPlayer theOwner = this.getOwner();
         this.npc.func_70671_ap().func_75651_a(theOwner, 10.0F, (float)this.npc.func_70646_bf());
         if (!this.npc.func_70661_as().func_75497_a(theOwner, (double)1.0F) && this.npc.func_70068_e(theOwner) >= (double)144.0F) {
            int var1 = MathHelper.func_76128_c(theOwner.field_70165_t) - 2;
            int var2 = MathHelper.func_76128_c(theOwner.field_70161_v) - 2;
            int var3 = MathHelper.func_76128_c(theOwner.field_70121_D.field_72338_b);

            for(int var4 = 0; var4 <= 4; ++var4) {
               for(int var5 = 0; var5 <= 4; ++var5) {
                  if ((var4 < 1 || var5 < 1 || var4 > 3 || var5 > 3) && this.npc.field_70170_p.func_72797_t(var1 + var4, var3 - 1, var2 + var5) && !this.npc.field_70170_p.func_72809_s(var1 + var4, var3, var2 + var5) && !this.npc.field_70170_p.func_72809_s(var1 + var4, var3 + 1, var2 + var5)) {
                     this.npc.func_70012_b((double)((float)(var1 + var4) + 0.5F), (double)var3, (double)((float)(var2 + var5) + 0.5F), this.npc.field_70177_z, this.npc.field_70125_A);
                     this.npc.func_70661_as().func_75499_g();
                     return;
                  }
               }
            }
         }

         this.updateTick = 0;
      }
   }

   public void aiStartExecuting() {
      this.updateTick = 10;
   }

   public boolean aiContinueExecute() {
      EntityPlayer owner = this.getOwner();
      return owner != null && !this.npc.func_70661_as().func_75500_f() && this.npc.func_70032_d(owner) > 2.0F && this.isFollowing;
   }

   public boolean isFollowing() {
      if (!this.isFollowing) {
         return false;
      } else {
         return this.getOwner() != null;
      }
   }

   public EntityPlayer getOwner() {
      return this.owner != null && !this.owner.isEmpty() ? this.npc.field_70170_p.func_72924_a(this.owner) : null;
   }

   public boolean hasOwner() {
      if (this.daysHired <= 0) {
         return false;
      } else {
         return this.getOwner() != null;
      }
   }

   public void killed() {
      this.owner = null;
      this.daysHired = 0;
      this.hiredTime = 0L;
   }

   public int getDaysLeft() {
      if (this.daysHired <= 0) {
         return 0;
      } else {
         int days = (int)((this.npc.field_70170_p.func_72820_D() - this.hiredTime) / 24000L);
         return this.daysHired - days;
      }
   }

   public void addDays(int days) {
      this.daysHired += days + this.getDaysLeft();
      this.hiredTime = this.npc.field_70170_p.func_72820_D();
   }

   public boolean interact(EntityPlayer player) {
      if (this.owner != null && !this.owner.isEmpty()) {
         if (player == this.getOwner()) {
            NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerFollower, this.npc);
         }
      } else {
         this.npc.say(player, this.npc.advanced.getInteractLine());
         NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerFollowerHire, this.npc);
      }

      return false;
   }

   public void delete() {
   }

   public void setOwner(String username) {
      if (this.owner == null || !this.owner.equals(username)) {
         this.killed();
      }

      this.owner = username;
   }
}

package noppes.npcs.roles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerTransportData;
import noppes.npcs.controllers.TransportController;
import noppes.npcs.controllers.TransportLocation;

public class RoleTransporter extends RoleInterface {
   public int transportId = -1;
   public String name;
   private int ticks = 10;

   public RoleTransporter(EntityNPCInterface npc) {
      super(npc);
   }

   public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.func_74768_a("TransporterId", this.transportId);
   }

   public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
      this.transportId = nbttagcompound.func_74762_e("TransporterId");
      TransportLocation loc = this.getLocation();
      if (loc != null) {
         this.name = loc.name;
      }

      if (loc == null) {
         this.transportId = -1;
         this.name = "";
      }

   }

   public boolean aiShouldExecute() {
      --this.ticks;
      if (this.ticks > 0) {
         return false;
      } else {
         this.ticks = 10;
         if (!this.hasTransport()) {
            return false;
         } else {
            TransportLocation loc = this.getLocation();
            if (loc.type != 0) {
               return false;
            } else {
               for(EntityPlayer player : this.npc.field_70170_p.func_72872_a(EntityPlayer.class, this.npc.field_70121_D.func_72314_b((double)6.0F, (double)6.0F, (double)6.0F))) {
                  if (this.npc.func_70685_l(player)) {
                     this.unlock(player, loc);
                  }
               }

               return false;
            }
         }
      }
   }

   private void unlock(EntityPlayer player, TransportLocation loc) {
      PlayerTransportData data = PlayerDataController.instance.getPlayerData(player).transportData;
      if (!data.transports.contains(this.transportId)) {
         data.transports.add(this.transportId);
         NoppesUtilServer.sendData(player, EnumPacketType.Chat, "transporter.unlock1", " " + loc.name + " ", "transporter.unlock2");
      }
   }

   public void aiStartExecuting() {
   }

   public boolean interact(EntityPlayer player) {
      if (this.hasTransport()) {
         TransportLocation loc = this.getLocation();
         if (loc.type == 2) {
            this.unlock(player, loc);
         }

         NoppesUtilServer.sendOpenGui(player, EnumGuiType.PlayerTransporter, this.npc);
      }

      return false;
   }

   public TransportLocation getLocation() {
      return this.npc.field_70170_p.field_72995_K ? null : TransportController.getInstance().getTransport(this.transportId);
   }

   public boolean hasTransport() {
      TransportLocation loc = this.getLocation();
      return loc != null && loc.isNpc(this.npc);
   }

   public void setTransport(TransportLocation location) {
      this.transportId = location.id;
      this.name = location.name;
   }
}

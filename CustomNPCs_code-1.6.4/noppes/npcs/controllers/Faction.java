package noppes.npcs.controllers;

import java.util.HashSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.NBTTags;

public class Faction {
   public String name = "";
   public int color = Integer.parseInt("FF00", 16);
   public HashSet attackFactions;
   public int id = -1;
   public int neutralPoints = 500;
   public int friendlyPoints = 1500;
   public int minPoints = 0;
   public int maxPoints = 2000;
   public int defaultPoints = 1000;
   public boolean hideFaction = false;
   public boolean getsAttacked = false;

   public Faction() {
      this.attackFactions = new HashSet();
   }

   public Faction(int id, String name, int color, int defaultPoints) {
      this.name = name;
      this.color = color;
      this.defaultPoints = defaultPoints;
      this.id = id;
      this.attackFactions = new HashSet();
   }

   public static String formatName(String name) {
      name = name.toLowerCase().trim();
      return name.substring(0, 1).toUpperCase() + name.substring(1);
   }

   public void readNBT(NBTTagCompound compound) {
      this.name = compound.func_74779_i("Name");
      this.color = compound.func_74762_e("Color");
      this.id = compound.func_74762_e("Slot");
      this.neutralPoints = compound.func_74762_e("NeutralPoints");
      this.friendlyPoints = compound.func_74762_e("FriendlyPoints");
      this.defaultPoints = compound.func_74762_e("DefaultPoints");
      this.hideFaction = compound.func_74767_n("HideFaction");
      this.getsAttacked = compound.func_74767_n("GetsAttacked");
      this.attackFactions = NBTTags.getIntegerSet(compound.func_74761_m("AttackFactions"));
   }

   public void writeNBT(NBTTagCompound compound) {
      compound.func_74768_a("Slot", this.id);
      compound.func_74778_a("Name", this.name);
      compound.func_74768_a("Color", this.color);
      compound.func_74768_a("NeutralPoints", this.neutralPoints);
      compound.func_74768_a("FriendlyPoints", this.friendlyPoints);
      compound.func_74768_a("DefaultPoints", this.defaultPoints);
      compound.func_74757_a("HideFaction", this.hideFaction);
      compound.func_74757_a("GetsAttacked", this.getsAttacked);
      compound.func_74782_a("AttackFactions", NBTTags.nbtIntegerSet(this.attackFactions));
   }

   public boolean isFriendlyToPlayer(EntityPlayer player) {
      PlayerFactionData data = PlayerDataController.instance.getPlayerData(player).factionData;
      return data.getFactionPoints(this.id) >= this.friendlyPoints;
   }

   public boolean isAggressiveToPlayer(EntityPlayer player) {
      PlayerFactionData data = PlayerDataController.instance.getPlayerData(player).factionData;
      return data.getFactionPoints(this.id) < this.neutralPoints;
   }

   public boolean isAggressiveToNpc(EntityNPCInterface entity) {
      return this.attackFactions.contains(entity.getFaction().id);
   }
}

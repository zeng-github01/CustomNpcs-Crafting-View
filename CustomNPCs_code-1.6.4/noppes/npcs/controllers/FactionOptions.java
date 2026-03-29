package noppes.npcs.controllers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatMessageComponent;

public class FactionOptions {
   public boolean decreaseFactionPoints = false;
   public boolean decreaseFaction2Points = false;
   public int factionId = -1;
   public int faction2Id = -1;
   public int factionPoints = 100;
   public int faction2Points = 100;

   public void readFromNBT(NBTTagCompound compound) {
      this.factionId = compound.func_74762_e("OptionFactions1");
      this.faction2Id = compound.func_74762_e("OptionFactions2");
      this.decreaseFactionPoints = compound.func_74767_n("DecreaseFaction1Points");
      this.decreaseFaction2Points = compound.func_74767_n("DecreaseFaction2Points");
      this.factionPoints = compound.func_74762_e("OptionFaction1Points");
      this.faction2Points = compound.func_74762_e("OptionFaction2Points");
   }

   public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound) {
      par1NBTTagCompound.func_74768_a("OptionFactions1", this.factionId);
      par1NBTTagCompound.func_74768_a("OptionFactions2", this.faction2Id);
      par1NBTTagCompound.func_74768_a("OptionFaction1Points", this.factionPoints);
      par1NBTTagCompound.func_74768_a("OptionFaction2Points", this.faction2Points);
      par1NBTTagCompound.func_74757_a("DecreaseFaction1Points", this.decreaseFactionPoints);
      par1NBTTagCompound.func_74757_a("DecreaseFaction2Points", this.decreaseFaction2Points);
      return par1NBTTagCompound;
   }

   public boolean hasFaction(int id) {
      return this.factionId == id || this.faction2Id == id;
   }

   public void addPoints(EntityPlayer player) {
      if (this.factionId >= 0 || this.faction2Id >= 0) {
         PlayerFactionData data = PlayerDataController.instance.getPlayerData(player).factionData;
         if (this.factionId >= 0 && this.factionPoints > 0) {
            this.addPoints(player, data, this.factionId, this.decreaseFactionPoints, this.factionPoints);
         }

         if (this.faction2Id >= 0 && this.faction2Points > 0) {
            this.addPoints(player, data, this.faction2Id, this.decreaseFaction2Points, this.faction2Points);
         }

      }
   }

   private void addPoints(EntityPlayer player, PlayerFactionData data, int factionId, boolean decrease, int points) {
      Faction faction = FactionController.getInstance().getFaction(factionId);
      if (faction != null) {
         if (!faction.hideFaction) {
            String message = decrease ? "faction.decreasepoints" : "faction.increasepoints";
            player.func_70006_a(ChatMessageComponent.func_111082_b(message, new Object[]{faction.name, points}));
         }

         data.increasePoints(factionId, decrease ? -points : points);
      }
   }
}

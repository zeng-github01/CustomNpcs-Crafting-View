package noppes.npcs.controllers;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PlayerData implements IExtendedEntityProperties {
   public PlayerDialogData dialogData = new PlayerDialogData();
   public PlayerBankData bankData = new PlayerBankData();
   public PlayerQuestData questData = new PlayerQuestData();
   public PlayerTransportData transportData = new PlayerTransportData();
   public PlayerFactionData factionData = new PlayerFactionData();
   public PlayerItemGiverData itemgiverData = new PlayerItemGiverData();
   public PlayerMailData mailData = new PlayerMailData();
   private String playerName;

   public PlayerData(String player) {
      this.playerName = player;
      this.readNBT(PlayerDataController.instance.loadPlayerData(this.playerName));
   }

   public void readNBT(NBTTagCompound compound) {
      this.dialogData.readNBT(compound);
      this.bankData.readNBT(compound);
      this.questData.readNBT(compound);
      this.transportData.readNBT(compound);
      this.factionData.readNBT(compound);
      this.itemgiverData.readNBT(compound);
      this.mailData.readNBT(compound);
   }

   public NBTTagCompound getNBT() {
      NBTTagCompound compound = new NBTTagCompound();
      this.dialogData.writeNBT(compound);
      this.bankData.writeNBT(compound);
      this.questData.writeNBT(compound);
      this.transportData.writeNBT(compound);
      this.factionData.writeNBT(compound);
      this.itemgiverData.writeNBT(compound);
      this.mailData.writeNBT(compound);
      return compound;
   }

   public void saveNBTData(NBTTagCompound compound) {
      PlayerDataController.instance.savePlayerData(this.playerName, this.getNBT());
   }

   public void loadNBTData(NBTTagCompound compound) {
   }

   public void init(Entity entity, World world) {
   }
}

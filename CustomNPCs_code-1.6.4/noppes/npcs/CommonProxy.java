package noppes.npcs;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.containers.ContainerCarpentryBench;
import noppes.npcs.containers.ContainerManageBanks;
import noppes.npcs.containers.ContainerManageRecipes;
import noppes.npcs.containers.ContainerMerchantAdd;
import noppes.npcs.containers.ContainerNPCBankLarge;
import noppes.npcs.containers.ContainerNPCBankSmall;
import noppes.npcs.containers.ContainerNPCBankUnlock;
import noppes.npcs.containers.ContainerNPCBankUpgrade;
import noppes.npcs.containers.ContainerNPCFollower;
import noppes.npcs.containers.ContainerNPCFollowerHire;
import noppes.npcs.containers.ContainerNPCFollowerSetup;
import noppes.npcs.containers.ContainerNPCInv;
import noppes.npcs.containers.ContainerNPCTrader;
import noppes.npcs.containers.ContainerNPCTraderSetup;
import noppes.npcs.containers.ContainerNpcItemGiver;
import noppes.npcs.containers.ContainerNpcQuestReward;
import noppes.npcs.containers.ContainerNpcQuestTypeItem;
import noppes.npcs.events.ItemInteractEvent;
import noppes.npcs.events.PickupHandler;

public class CommonProxy implements IGuiHandler {
   public boolean newVersionAvailable = false;
   public int revision = 4;

   public void load() {
      GameRegistry.registerPickupHandler(new PickupHandler());
   }

   public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
      if (ID > EnumGuiType.values().length) {
         return null;
      } else {
         EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
         EnumGuiType gui = EnumGuiType.values()[ID];
         return this.getContainer(gui, player, x, y, z, npc);
      }
   }

   public Container getContainer(EnumGuiType gui, EntityPlayer player, int x, int y, int z, EntityNPCInterface npc) {
      if (gui == EnumGuiType.MainMenuInv) {
         return new ContainerNPCInv(npc, player);
      } else if (gui == EnumGuiType.PlayerBankSmall) {
         return new ContainerNPCBankSmall(player, x, y);
      } else if (gui == EnumGuiType.PlayerBankUnlock) {
         return new ContainerNPCBankUnlock(player, x, y);
      } else if (gui == EnumGuiType.PlayerBankUprade) {
         return new ContainerNPCBankUpgrade(player, x, y);
      } else if (gui == EnumGuiType.PlayerBankLarge) {
         return new ContainerNPCBankLarge(player, x, y);
      } else if (gui == EnumGuiType.PlayerFollowerHire) {
         return new ContainerNPCFollowerHire(npc, player);
      } else if (gui == EnumGuiType.PlayerFollower) {
         return new ContainerNPCFollower(npc, player);
      } else if (gui == EnumGuiType.PlayerTrader) {
         return new ContainerNPCTrader(npc, player);
      } else if (gui == EnumGuiType.PlayerAnvil) {
         return new ContainerCarpentryBench(player.field_71071_by, player.field_70170_p, x, y, z);
      } else if (gui == EnumGuiType.SetupItemGiver) {
         return new ContainerNpcItemGiver(npc, player);
      } else if (gui == EnumGuiType.SetupTrader) {
         return new ContainerNPCTraderSetup(npc, player);
      } else if (gui == EnumGuiType.SetupFollower) {
         return new ContainerNPCFollowerSetup(npc, player);
      } else if (gui == EnumGuiType.QuestReward) {
         return new ContainerNpcQuestReward(player);
      } else if (gui == EnumGuiType.QuestItem) {
         return new ContainerNpcQuestTypeItem(player);
      } else if (gui == EnumGuiType.ManageRecipes) {
         return new ContainerManageRecipes(player, x);
      } else if (gui == EnumGuiType.ManageBanks) {
         return new ContainerManageBanks(player);
      } else {
         return gui == EnumGuiType.MerchantAdd ? new ContainerMerchantAdd(player.field_71071_by, ItemInteractEvent.Merchant, player.field_70170_p) : null;
      }
   }

   public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
      return null;
   }

   public void openGui(EntityNPCInterface npc, EnumGuiType gui) {
   }

   public void openGui(int i, int j, int k, EnumGuiType gui, EntityPlayer player) {
   }

   public void openGui(EntityPlayer player, Object guiscreen) {
   }

   public void spawnParticle(EntityLivingBase player, String string, Object... ob) {
   }

   public boolean hasClient() {
      return false;
   }

   public EntityPlayer getPlayer() {
      return null;
   }

   public void registerItem(int itemID) {
   }

   public Object loadResource(String texture) {
      return null;
   }
}

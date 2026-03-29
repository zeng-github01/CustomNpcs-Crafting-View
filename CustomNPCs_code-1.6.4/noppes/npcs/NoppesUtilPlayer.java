package noppes.npcs;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.constants.EnumOptionType;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.constants.EnumPlayerPacket;
import noppes.npcs.constants.EnumRoleType;
import noppes.npcs.containers.ContainerNPCBankInterface;
import noppes.npcs.containers.ContainerNPCFollower;
import noppes.npcs.containers.ContainerNPCFollowerHire;
import noppes.npcs.controllers.Bank;
import noppes.npcs.controllers.BankController;
import noppes.npcs.controllers.BankData;
import noppes.npcs.controllers.Dialog;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.DialogOption;
import noppes.npcs.controllers.Line;
import noppes.npcs.controllers.PlayerBankData;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerQuestController;
import noppes.npcs.controllers.PlayerQuestData;
import noppes.npcs.controllers.PlayerTransportData;
import noppes.npcs.controllers.QuestData;
import noppes.npcs.controllers.TransportController;
import noppes.npcs.controllers.TransportLocation;
import noppes.npcs.roles.RoleFollower;

public class NoppesUtilPlayer {
   public static void changeFollowerState(EntityPlayerMP player, EntityNPCInterface npc) {
      if (npc.advanced.role == EnumRoleType.Follower) {
         RoleFollower role = (RoleFollower)npc.roleInterface;
         EntityPlayer owner = role.getOwner();
         if (owner != null && owner.field_71092_bJ.equals(player.field_71092_bJ)) {
            role.isFollowing = !role.isFollowing;
         }
      }
   }

   public static void hireFollower(EntityPlayerMP player, EntityNPCInterface npc) {
      if (npc.advanced.role == EnumRoleType.Follower) {
         Container con = player.field_71070_bA;
         if (con != null && con instanceof ContainerNPCFollowerHire) {
            ContainerNPCFollowerHire container = (ContainerNPCFollowerHire)con;
            RoleFollower role = (RoleFollower)npc.roleInterface;
            followerBuy(role, container.currencyMatrix, player, npc);
         }
      }
   }

   public static void extendFollower(EntityPlayerMP player, EntityNPCInterface npc) {
      if (npc.advanced.role == EnumRoleType.Follower) {
         Container con = player.field_71070_bA;
         if (con != null && con instanceof ContainerNPCFollower) {
            ContainerNPCFollower container = (ContainerNPCFollower)con;
            RoleFollower role = (RoleFollower)npc.roleInterface;
            followerBuy(role, container.currencyMatrix, player, npc);
         }
      }
   }

   public static void transport(EntityPlayerMP player, EntityNPCInterface npc, String location) {
      TransportLocation loc = TransportController.getInstance().getTransport(location);
      PlayerTransportData playerdata = PlayerDataController.instance.getPlayerData(player).transportData;
      if (loc != null && (loc.isDefault() || playerdata.transports.contains(loc.id))) {
         if (player.field_71093_bK != loc.dimension) {
            System.out.println(loc.dimension + " transfering");
            int dim = player.field_71093_bK;
            MinecraftServer server = MinecraftServer.func_71276_C();
            WorldServer wor = server.func_71218_a(player.field_71093_bK);
            player.field_71135_a.func_72569_a(loc.posX, loc.posY, loc.posZ, player.field_70177_z, player.field_70125_A);
            server.func_71203_ab().transferPlayerToDimension(player, loc.dimension, new CustomTeleporter(wor));
            if (dim == 1 && player.func_70089_S()) {
               player.func_70012_b(loc.posX, loc.posY, loc.posZ, player.field_70177_z, player.field_70125_A);
               wor.func_72838_d(player);
            }

            player.field_70170_p.func_72866_a(player, false);
         } else {
            player.field_71135_a.func_72569_a(loc.posX, loc.posY, loc.posZ, player.field_70177_z, player.field_70125_A);
            player.field_70170_p.func_72866_a(player, false);
         }

      }
   }

   private static void followerBuy(RoleFollower role, IInventory currencyInv, EntityPlayerMP player, EntityNPCInterface npc) {
      ItemStack currency = currencyInv.func_70301_a(0);
      if (currency != null) {
         HashMap<ItemStack, Integer> cd = new HashMap();

         for(int i : role.inventory.items.keySet()) {
            ItemStack is = (ItemStack)role.inventory.items.get(i);
            if (is != null && is.field_77993_c == currency.field_77993_c && (!is.func_77981_g() || is.func_77960_j() == currency.func_77960_j())) {
               int days = 1;
               if (role.rates.containsKey(i)) {
                  days = (Integer)role.rates.get(i);
               }

               cd.put(is, days);
            }
         }

         if (cd.size() != 0) {
            int stackSize = currency.field_77994_a;
            int days = 0;
            int possibleDays = 0;
            int possibleSize = stackSize;

            while(true) {
               for(ItemStack item : cd.keySet()) {
                  int rDays = (Integer)cd.get(item);
                  int rValue = item.field_77994_a;
                  if (rValue <= stackSize) {
                     int newStackSize = stackSize % rValue;
                     int size = stackSize - newStackSize;
                     int posDays = size / rValue * rDays;
                     if (possibleDays <= posDays) {
                        possibleDays = posDays;
                        possibleSize = newStackSize;
                     }
                  }
               }

               if (stackSize == possibleSize) {
                  if (days == 0) {
                     return;
                  }

                  if (stackSize <= 0) {
                     currencyInv.func_70299_a(0, (ItemStack)null);
                  } else {
                     currency.func_77979_a(stackSize);
                  }

                  npc.say(player, new Line(role.dialogHire.replaceAll("\\{days\\}", days + "")));
                  role.setOwner(player.field_71092_bJ);
                  role.addDays(days);
                  return;
               }

               stackSize = possibleSize;
               days += possibleDays;
               possibleDays = 0;
            }
         }
      }
   }

   public static void bankUpgrade(EntityPlayerMP player, EntityNPCInterface npc) {
      if (npc.advanced.role == EnumRoleType.Bank) {
         Container con = player.field_71070_bA;
         if (con != null && con instanceof ContainerNPCBankInterface) {
            ContainerNPCBankInterface container = (ContainerNPCBankInterface)con;
            Bank bank = BankController.getInstance().getBank(container.bankid);
            ItemStack item = bank.upgradeInventory.func_70301_a(container.slot);
            if (item != null) {
               int price = item.field_77994_a;
               ItemStack currency = container.currencyMatrix.func_70301_a(0);
               if (currency != null && price <= currency.field_77994_a) {
                  if (currency.field_77994_a - price == 0) {
                     container.currencyMatrix.func_70299_a(0, (ItemStack)null);
                  } else {
                     currency.func_77979_a(price);
                  }

                  PlayerBankData data = PlayerDataController.instance.getBankData(player, bank.id);
                  BankData bankData = data.getBank(bank.id);
                  bankData.upgradedSlots.put(container.slot, true);
                  bankData.openBankGui(player, npc, bank.id, container.slot);
               }
            }
         }
      }
   }

   public static void bankUnlock(EntityPlayerMP player, EntityNPCInterface npc) {
      if (npc.advanced.role == EnumRoleType.Bank) {
         Container con = player.field_71070_bA;
         if (con != null && con instanceof ContainerNPCBankInterface) {
            ContainerNPCBankInterface container = (ContainerNPCBankInterface)con;
            Bank bank = BankController.getInstance().getBank(container.bankid);
            ItemStack item = bank.currencyInventory.func_70301_a(container.slot);
            if (item != null) {
               int price = item.field_77994_a;
               ItemStack currency = container.currencyMatrix.func_70301_a(0);
               if (currency != null && price <= currency.field_77994_a) {
                  if (currency.field_77994_a - price == 0) {
                     container.currencyMatrix.func_70299_a(0, (ItemStack)null);
                  } else {
                     currency.func_77979_a(price);
                  }

                  PlayerBankData data = PlayerDataController.instance.getBankData(player, bank.id);
                  BankData bankData = data.getBank(bank.id);
                  if (bankData.unlockedSlots + 1 <= bank.maxSlots) {
                     ++bankData.unlockedSlots;
                  }

                  bankData.openBankGui(player, npc, bank.id, container.slot);
               }
            }
         }
      }
   }

   public static void sendData(EnumPlayerPacket enu, Object... obs) {
      try {
         ByteArrayOutputStream bytes = new ByteArrayOutputStream();
         DataOutputStream out = NoppesUtil.getDataOutputStream(bytes);
         out.writeInt(enu.ordinal());

         for(Object ob : obs) {
            if (ob instanceof Integer) {
               out.writeInt((Integer)ob);
            } else if (ob instanceof String) {
               out.writeUTF((String)ob);
            } else if (ob instanceof Long) {
               out.writeLong((Long)ob);
            } else if (ob instanceof NBTTagCompound) {
               CompressedStreamTools.func_74800_a((NBTTagCompound)ob, out);
            }
         }

         out.close();
         PacketDispatcher.sendPacketToServer(new Packet250CustomPayload("CNPCs Player", bytes.toByteArray()));
      } catch (IOException e) {
         e.printStackTrace();
      }

   }

   public static void dialogSelected(int dialogId, int optionId, EntityPlayer player, EntityNPCInterface npc) {
      Dialog dialog = (Dialog)DialogController.instance.dialogs.get(dialogId);
      if (dialog != null && (dialog.hasDialogs(player) || dialog.hasOtherOptions())) {
         DialogOption option = (DialogOption)dialog.options.get(optionId);
         if (option != null && (option.optionType != EnumOptionType.DialogOption || option.isAvailable(player) && option.hasDialog()) && option.optionType != EnumOptionType.Disabled) {
            if (option.optionType == EnumOptionType.RoleOption) {
               if (npc.roleInterface != null) {
                  npc.roleInterface.interact(player);
               }
            } else if (option.optionType == EnumOptionType.DialogOption) {
               NoppesUtilServer.openDialog(player, npc, option.getDialog());
            } else if (option.optionType == EnumOptionType.CommandBlock) {
               String command = option.command.replaceAll("@dp", player.field_71092_bJ);
               TileEntityCommandBlock tile = new TileEntityCommandBlock();
               tile.field_70331_k = npc.field_70170_p;
               tile.func_82352_b(command);
               tile.func_96104_c("@" + npc.func_70005_c_());
               tile.field_70329_l = MathHelper.func_76128_c(npc.field_70165_t);
               tile.field_70330_m = MathHelper.func_76128_c(npc.field_70163_u);
               tile.field_70327_n = MathHelper.func_76128_c(npc.field_70161_v);
               tile.func_82351_a(npc.field_70170_p);
            }

         }
      }
   }

   public static void sendQuestLogData(EntityPlayerMP player) {
      if (PlayerQuestController.hasActiveQuests(player)) {
         QuestLogData data = new QuestLogData();
         data.setData(player);
         NoppesUtilServer.sendData(player, EnumPacketType.GuiData, data.writeNBT());
      }
   }

   public static void questCompletion(EntityPlayerMP player, int questId) {
      PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData(player).questData;
      QuestData data = (QuestData)playerdata.activeQuests.get(questId);
      if (data != null) {
         if (data.quest.questInterface.isCompleted(player)) {
            data.quest.questInterface.handleComplete(player);
            if (data.quest.rewardExp > 0) {
               player.field_70170_p.func_72956_a(player, "random.orb", 0.1F, 0.5F * ((player.field_70170_p.field_73012_v.nextFloat() - player.field_70170_p.field_73012_v.nextFloat()) * 0.7F + 1.8F));
               player.func_71023_q(data.quest.rewardExp);
            }

            data.quest.factionOptions.addPoints(player);
            if (data.quest.mail.isValid()) {
               PlayerDataController.instance.addPlayerMessage(player.field_71092_bJ, data.quest.mail);
            }

            for(ItemStack item : data.quest.rewardItems.items.values()) {
               CustomNpcs.GivePlayerItem(player, player, item);
            }

            PlayerQuestController.setQuestFinished(data.quest, player);
            if (data.quest.hasNewQuest()) {
               PlayerQuestController.addActiveQuest(data.quest.getNextQuest(), player);
            }

         }
      }
   }

   public static boolean compareItems(ItemStack var9, ItemStack var10, boolean ignoreDamage) {
      if ((var10 != null || var9 == null) && (var10 == null || var9 != null)) {
         if (var9.field_77993_c != var10.field_77993_c) {
            return false;
         } else if (var9.func_77960_j() != -1 && var9.func_77960_j() != var10.func_77960_j() && !ignoreDamage) {
            return false;
         } else {
            return var9.field_77990_d == null || var10.field_77990_d != null && var9.field_77990_d.equals(var10.field_77990_d);
         }
      } else {
         return false;
      }
   }
}

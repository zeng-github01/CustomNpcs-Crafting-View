package noppes.npcs;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.constants.EnumPlayerPacket;
import noppes.npcs.constants.EnumQuestType;
import noppes.npcs.controllers.BankData;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerFactionData;
import noppes.npcs.controllers.PlayerMail;
import noppes.npcs.controllers.PlayerMailData;
import noppes.npcs.controllers.PlayerQuestController;
import noppes.npcs.controllers.PlayerQuestData;

public class PacketHandlerPlayer implements IPacketHandler {
   public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
      if (packet.field_73630_a.equals("CNPCs Player")) {
         try {
            DataInputStream dis = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(packet.field_73629_c))));
            this.player(dis, (EntityPlayerMP)player, EnumPlayerPacket.values()[dis.readInt()]);
            dis.close();
         } catch (IOException e) {
            e.printStackTrace();
         }

      }
   }

   private void player(DataInputStream dis, EntityPlayerMP player, EnumPlayerPacket type) throws IOException {
      if (type == EnumPlayerPacket.FollowerHire) {
         EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
         if (npc == null) {
            return;
         }

         NoppesUtilPlayer.hireFollower(player, npc);
      } else if (type == EnumPlayerPacket.FollowerExtend) {
         EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
         if (npc == null) {
            return;
         }

         NoppesUtilPlayer.extendFollower(player, npc);
      } else if (type == EnumPlayerPacket.FollowerState) {
         EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
         if (npc == null) {
            return;
         }

         NoppesUtilPlayer.changeFollowerState(player, npc);
      } else if (type == EnumPlayerPacket.Transport) {
         EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
         if (npc == null) {
            return;
         }

         NoppesUtilPlayer.transport(player, npc, dis.readUTF());
      } else if (type == EnumPlayerPacket.BankUpgrade) {
         EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
         if (npc == null) {
            return;
         }

         NoppesUtilPlayer.bankUpgrade(player, npc);
      } else if (type == EnumPlayerPacket.BankUnlock) {
         EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
         if (npc == null) {
            return;
         }

         NoppesUtilPlayer.bankUnlock(player, npc);
      } else if (type == EnumPlayerPacket.BankSlotOpen) {
         EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
         if (npc == null) {
            return;
         }

         int slot = dis.readInt();
         int bankId = dis.readInt();
         BankData data = PlayerDataController.instance.getBankData(player, bankId).getBankOrDefault(bankId);
         data.openBankGui(player, npc, bankId, slot);
      } else if (type == EnumPlayerPacket.Dialog) {
         EntityNPCInterface npc = NoppesUtilServer.getEditingNpc(player);
         if (npc == null) {
            return;
         }

         NoppesUtilPlayer.dialogSelected(dis.readInt(), dis.readInt(), player, npc);
      } else if (type == EnumPlayerPacket.CheckQuestCompletion) {
         PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData(player).questData;
         playerdata.checkQuestCompletion(player, (EnumQuestType)null);
      } else if (type == EnumPlayerPacket.QuestLog) {
         NoppesUtilPlayer.sendQuestLogData(player);
      } else if (type == EnumPlayerPacket.QuestCompletion) {
         NoppesUtilPlayer.questCompletion(player, dis.readInt());
      } else if (type == EnumPlayerPacket.FactionsGet) {
         PlayerFactionData data = PlayerDataController.instance.getPlayerData(player).factionData;
         NoppesUtilServer.sendData(player, EnumPacketType.GuiData, data.getPlayerGuiData());
      } else if (type == EnumPlayerPacket.MailGet) {
         PlayerMailData data = PlayerDataController.instance.getPlayerData(player).mailData;
         NoppesUtilServer.sendData(player, EnumPacketType.GuiData, data.writeNBT(new NBTTagCompound()));
      } else if (type == EnumPlayerPacket.MailDelete) {
         long time = dis.readLong();
         String username = dis.readUTF();
         PlayerMailData data = PlayerDataController.instance.getPlayerData(player).mailData;
         Iterator<PlayerMail> it = data.playermail.iterator();

         while(it.hasNext()) {
            PlayerMail mail = (PlayerMail)it.next();
            if (mail.time == time && mail.sender.equals(username)) {
               it.remove();
            }
         }

         NoppesUtilServer.sendData(player, EnumPacketType.GuiData, data.writeNBT(new NBTTagCompound()));
      } else if (type == EnumPlayerPacket.MailSend) {
         String username = PlayerDataController.instance.hasPlayer(dis.readUTF());
         if (username.isEmpty()) {
            NoppesUtilServer.sendGuiError(player, 0);
            return;
         }

         PlayerMail mail = new PlayerMail();
         mail.readNBT(CompressedStreamTools.func_74794_a(dis));
         if (mail.subject.isEmpty()) {
            NoppesUtilServer.sendGuiError(player, 1);
            return;
         }

         PlayerDataController.instance.addPlayerMessage(username, mail);
         NBTTagCompound comp = new NBTTagCompound();
         comp.func_74778_a("username", username);
         NoppesUtilServer.sendGuiClose(player, 1, comp);
      } else if (type == EnumPlayerPacket.MailRead) {
         long time = dis.readLong();
         String username = dis.readUTF();
         PlayerMailData data = PlayerDataController.instance.getPlayerData(player).mailData;

         for(PlayerMail mail : data.playermail) {
            if (mail.time == time && mail.sender.equals(username)) {
               mail.beenRead = true;
               if (mail.hasQuest()) {
                  PlayerQuestController.addActiveQuest(mail.getQuest(), player);
               }
            }
         }
      }

   }
}

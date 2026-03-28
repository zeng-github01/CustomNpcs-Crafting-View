package noppes.npcs;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.WeightedRandomMinecart;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.WorldServer;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.constants.EnumPlayerData;
import noppes.npcs.constants.EnumRoleType;
import noppes.npcs.containers.ContainerManageBanks;
import noppes.npcs.containers.ContainerManageRecipes;
import noppes.npcs.controllers.Bank;
import noppes.npcs.controllers.BankController;
import noppes.npcs.controllers.Dialog;
import noppes.npcs.controllers.DialogCategory;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.DialogOption;
import noppes.npcs.controllers.Faction;
import noppes.npcs.controllers.FactionController;
import noppes.npcs.controllers.PlayerBankData;
import noppes.npcs.controllers.PlayerData;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerDialogData;
import noppes.npcs.controllers.PlayerFactionData;
import noppes.npcs.controllers.PlayerQuestController;
import noppes.npcs.controllers.PlayerQuestData;
import noppes.npcs.controllers.PlayerTransportData;
import noppes.npcs.controllers.Quest;
import noppes.npcs.controllers.QuestCategory;
import noppes.npcs.controllers.QuestController;
import noppes.npcs.controllers.RecipeCarpentry;
import noppes.npcs.controllers.RecipeController;
import noppes.npcs.controllers.TransportCategory;
import noppes.npcs.controllers.TransportController;
import noppes.npcs.controllers.TransportLocation;
import noppes.npcs.roles.RoleTransporter;

public class NoppesUtilServer {
   private static HashMap selectedNpcs = new HashMap();
   private static HashMap editingQuests = new HashMap();

   public static byte[] CompoundToBytes(NBTTagCompound compound, EnumPacketType type) {
      try {
         ByteArrayOutputStream bytes = new ByteArrayOutputStream();
         DataOutputStream out = getDataOutputStream(bytes);
         out.writeInt(type.ordinal());
         if (compound != null) {
            CompressedStreamTools.func_74800_a(compound, out);
         }

         out.close();
         return bytes.toByteArray();
      } catch (IOException e) {
         e.printStackTrace();
         return null;
      }
   }

   public static void setEditingNpc(EntityPlayer player, EntityNPCInterface npc) {
      selectedNpcs.put(player.field_71092_bJ, npc);
      if (npc != null) {
         sendData(player, EnumPacketType.EditingNpc, npc.field_70157_k);
      }

   }

   public static EntityNPCInterface getEditingNpc(EntityPlayer player) {
      return (EntityNPCInterface)selectedNpcs.get(player.field_71092_bJ);
   }

   public static void setEditingQuest(EntityPlayer player, Quest quest) {
      editingQuests.put(player.field_71092_bJ, quest);
   }

   public static Quest getEditingQuest(EntityPlayer player) {
      return (Quest)editingQuests.get(player.field_71092_bJ);
   }

   private static void sendRoleData(EntityPlayer player, EntityNPCInterface npc) {
      if (npc.advanced.role != EnumRoleType.None) {
         NBTTagCompound comp = new NBTTagCompound();
         npc.roleInterface.writeEntityToNBT(comp);
         comp.func_74768_a("EntityId", npc.field_70157_k);
         comp.func_74768_a("Role", npc.advanced.role.ordinal());
         byte[] data = CompoundToBytes(comp, EnumPacketType.SaveRole);
         PacketDispatcher.sendPacketToPlayer(new Packet250CustomPayload("CNPCs Client", data), (Player)player);
      }
   }

   public static void sendFactionDataAll(EntityPlayerMP player) {
      Map<String, Integer> map = new HashMap();

      for(Faction faction : FactionController.getInstance().factions.values()) {
         map.put(faction.name, faction.id);
      }

      sendData(player, EnumPacketType.ScrollData, map);
   }

   public static void sendBankDataAll(EntityPlayerMP player) {
      Map<String, Integer> map = new HashMap();

      for(Bank bank : BankController.getInstance().banks.values()) {
         map.put(bank.name, bank.id);
      }

      sendData(player, EnumPacketType.ScrollData, map);
   }

   public static void openDialog(EntityPlayer player, EntityNPCInterface npc, Dialog dia) {
      Dialog dialog = dia.copy(player);
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();

      try {
         DataOutputStream out = getDataOutputStream(bytes);
         out.writeInt(EnumPacketType.Dialog.ordinal());
         out.writeInt(npc.field_70157_k);
         CompressedStreamTools.func_74800_a(dialog.writeToNBT(new NBTTagCompound()), out);
         out.close();
         PacketDispatcher.sendPacketToPlayer(new Packet250CustomPayload("CNPCs Client", bytes.toByteArray()), (Player)player);
      } catch (IOException e) {
         e.printStackTrace();
      }

      dia.factionOptions.addPoints(player);
      if (dialog.hasQuest()) {
         PlayerQuestController.addActiveQuest(dialog.getQuest(), player);
      }

      if (!dialog.command.isEmpty()) {
         String command = dialog.command.replaceAll("@dp", player.field_71092_bJ);
         TileEntityCommandBlock tile = new TileEntityCommandBlock();
         tile.field_70331_k = npc.field_70170_p;
         tile.func_82352_b(command);
         tile.func_96104_c("@" + npc.func_70005_c_());
         tile.field_70329_l = MathHelper.func_76128_c(npc.field_70165_t);
         tile.field_70330_m = MathHelper.func_76128_c(npc.field_70163_u);
         tile.field_70327_n = MathHelper.func_76128_c(npc.field_70161_v);
         tile.func_82351_a(npc.field_70170_p);
      }

      if (dialog.mail.isValid()) {
         PlayerDataController.instance.addPlayerMessage(player.field_71092_bJ, dialog.mail);
      }

      PlayerDialogData data = PlayerDataController.instance.getPlayerData(player).dialogData;
      if (!data.dialogsRead.contains(dialog.id)) {
         data.dialogsRead.add(dialog.id);
      }

      setEditingNpc(player, npc);
   }

   public static DataOutputStream getDataOutputStream(ByteArrayOutputStream stream) throws IOException {
      return new DataOutputStream(new GZIPOutputStream(stream));
   }

   public static void sendOpenGui(EntityPlayer player, EnumGuiType gui, EntityNPCInterface npc) {
      sendOpenGui(player, gui, npc, 0, 0, 0);
   }

   public static void sendOpenGui(EntityPlayer player, EnumGuiType gui, EntityNPCInterface npc, int i, int j, int k) {
      if (player instanceof EntityPlayerMP) {
         setEditingNpc(player, npc);
         sendExtraData(player, npc, gui, i, j, k);
         if (CustomNpcs.proxy.getServerGuiElement(gui.ordinal(), player, player.field_70170_p, i, j, k) != null) {
            player.openGui(CustomNpcs.instance, gui.ordinal(), player.field_70170_p, i, j, k);
         } else {
            try {
               ByteArrayOutputStream bytes = new ByteArrayOutputStream();
               DataOutputStream out = getDataOutputStream(bytes);
               out.writeInt(EnumPacketType.Gui.ordinal());
               out.writeInt(gui.ordinal());
               out.close();
               PacketDispatcher.sendPacketToPlayer(new Packet250CustomPayload("CNPCs Client", bytes.toByteArray()), (Player)player);
            } catch (IOException e) {
               e.printStackTrace();
            }

            ArrayList<String> list = getScrollData(player, gui, npc);
            if (list != null && !list.isEmpty()) {
               try {
                  ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                  DataOutputStream out = getDataOutputStream(bytes);
                  out.writeInt(EnumPacketType.ScrollList.ordinal());

                  for(String line : list) {
                     out.writeUTF(line);
                  }

                  out.close();
                  PacketDispatcher.sendPacketToPlayer(new Packet250CustomPayload("CNPCs Client", bytes.toByteArray()), (Player)player);
               } catch (IOException e) {
                  e.printStackTrace();
               }

            }
         }
      }
   }

   private static void sendExtraData(EntityPlayer player, EntityNPCInterface npc, EnumGuiType gui, int i, int j, int k) {
      if (gui == EnumGuiType.PlayerFollower || gui == EnumGuiType.PlayerFollowerHire || gui == EnumGuiType.PlayerTrader || gui == EnumGuiType.PlayerTransporter) {
         sendRoleData(player, npc);
      }

   }

   private static ArrayList getScrollData(EntityPlayer player, EnumGuiType gui, EntityNPCInterface npc) {
      if (gui == EnumGuiType.PlayerTransporter) {
         RoleTransporter role = (RoleTransporter)npc.roleInterface;
         ArrayList<String> list = new ArrayList();
         TransportLocation location = role.getLocation();
         String name = role.getLocation().name;

         for(TransportLocation loc : location.category.getDefaultLocations()) {
            if (!list.contains(loc.name)) {
               list.add(loc.name);
            }
         }

         PlayerTransportData playerdata = PlayerDataController.instance.getPlayerData(player).transportData;

         for(int i : playerdata.transports) {
            TransportLocation loc = TransportController.getInstance().getTransport(i);
            if (loc != null && location.category.locations.containsKey(loc.id) && !list.contains(loc.name)) {
               list.add(loc.name);
            }
         }

         list.remove(name);
         return list;
      } else {
         return null;
      }
   }

   public static void spawnParticle(Entity entity, String particle, int dimension) {
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();

      try {
         DataOutputStream stream = getDataOutputStream(bytes);
         stream.writeInt(EnumPacketType.Particle.ordinal());
         stream.writeDouble(entity.field_70165_t);
         stream.writeDouble(entity.field_70163_u);
         stream.writeDouble(entity.field_70161_v);
         stream.writeFloat(entity.field_70131_O);
         stream.writeFloat(entity.field_70130_N);
         stream.writeFloat(entity.field_70129_M);
         stream.writeUTF(particle);
         stream.close();
      } catch (IOException e) {
         e.printStackTrace();
      }

      Packet250CustomPayload packet = new Packet250CustomPayload("CNPCs Client", bytes.toByteArray());
      PacketDispatcher.sendPacketToAllAround(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, (double)60.0F, dimension, packet);
   }

   public static void deleteNpc(EntityNPCInterface npc, EntityPlayer player) {
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();

      try {
         DataOutputStream stream = getDataOutputStream(bytes);
         stream.writeInt(EnumPacketType.Delete.ordinal());
         stream.writeInt(npc.field_70157_k);
         stream.close();
      } catch (IOException e) {
         e.printStackTrace();
      }

      Packet250CustomPayload packet = new Packet250CustomPayload("CNPCs Client", bytes.toByteArray());
      PacketDispatcher.sendPacketToAllAround(npc.field_70165_t, npc.field_70163_u, npc.field_70161_v, (double)256.0F, player.field_71093_bK, packet);
   }

   public static void createMobSpawner(DataInputStream dis, EntityPlayer player) {
      try {
         int x = dis.readInt();
         int y = dis.readInt();
         int z = dis.readInt();
         NBTTagCompound comp = CompressedStreamTools.func_74794_a(dis);
         if (comp.func_74779_i("id").equalsIgnoreCase("entityhorse")) {
            player.func_70006_a(ChatMessageComponent.func_111066_d("Currently you cant create horse spawner, its a minecraft bug"));
            return;
         }

         player.field_70170_p.func_94575_c(x, y, z, Block.field_72065_as.field_71990_ca);
         TileEntityMobSpawner tile = (TileEntityMobSpawner)player.field_70170_p.func_72796_p(x, y, z);
         MobSpawnerBaseLogic logic = tile.func_98049_a();
         logic.func_98277_a(new WeightedRandomMinecart(logic, comp, comp.func_74779_i("id")));
      } catch (IOException e) {
         e.printStackTrace();
      }

   }

   public static void sendDialogCategoryData(EntityPlayerMP player) {
      Map<String, Integer> map = new HashMap();

      for(DialogCategory category : DialogController.instance.categories.values()) {
         map.put(category.title, category.id);
      }

      sendData(player, EnumPacketType.ScrollData, map);
   }

   public static void sendQuestCategoryData(EntityPlayerMP player) {
      Map<String, Integer> map = new HashMap();

      for(QuestCategory category : QuestController.instance.categories.values()) {
         map.put(category.title, category.id);
      }

      sendData(player, EnumPacketType.ScrollData, map);
   }

   public static void sendPlayerData(EnumPlayerData type, EntityPlayerMP player, String name) throws IOException {
      Map<String, Integer> map = new HashMap();
      if (type == EnumPlayerData.Players) {
         File loc = PlayerDataController.instance.getSaveDir();

         for(File file : loc.listFiles()) {
            if (!file.isDirectory() && file.getName().endsWith(".dat")) {
               map.put(file.getName().substring(0, file.getName().length() - 4), 0);
            }
         }
      } else {
         PlayerData playerdata = new PlayerData(name);
         if (type == EnumPlayerData.Dialog) {
            PlayerDialogData data = playerdata.dialogData;

            for(int questId : data.dialogsRead) {
               Dialog dialog = (Dialog)DialogController.instance.dialogs.get(questId);
               if (dialog != null) {
                  map.put(dialog.category.title + ": " + dialog.title, questId);
               }
            }
         } else if (type == EnumPlayerData.Quest) {
            PlayerQuestData data = playerdata.questData;

            for(int questId : data.activeQuests.keySet()) {
               Quest quest = (Quest)QuestController.instance.quests.get(questId);
               if (quest != null) {
                  map.put(quest.category.title + ": " + quest.title + "(Active quest)", questId);
               }
            }

            for(int questId : data.finishedQuests.keySet()) {
               Quest quest = (Quest)QuestController.instance.quests.get(questId);
               if (quest != null) {
                  map.put(quest.category.title + ": " + quest.title + "(Finished quest)", questId);
               }
            }
         } else if (type == EnumPlayerData.Transport) {
            PlayerTransportData data = playerdata.transportData;

            for(int questId : data.transports) {
               TransportLocation location = TransportController.getInstance().getTransport(questId);
               if (location != null) {
                  map.put(location.category.title + ": " + location.name, questId);
               }
            }
         } else if (type == EnumPlayerData.Bank) {
            PlayerBankData data = playerdata.bankData;

            for(int bankId : data.banks.keySet()) {
               Bank bank = (Bank)BankController.getInstance().banks.get(bankId);
               if (bank != null) {
                  map.put(bank.name, bankId);
               }
            }
         } else if (type == EnumPlayerData.Factions) {
            PlayerFactionData data = playerdata.factionData;

            for(int factionId : data.factionData.keySet()) {
               Faction faction = (Faction)FactionController.getInstance().factions.get(factionId);
               if (faction != null) {
                  map.put(faction.name + "(" + data.getFactionPoints(factionId) + ")", factionId);
               }
            }
         }
      }

      HashMap<String, Integer> toSend = new HashMap();

      for(String s : map.keySet()) {
         if (toSend.size() == 1000) {
            sendData(player, EnumPacketType.ScrollData, toSend);
            toSend = new HashMap();
         }

         toSend.put(s, map.get(s));
      }

      sendData(player, EnumPacketType.ScrollData, toSend);
   }

   public static void removePlayerData(DataInputStream dis, EntityPlayerMP player) throws IOException {
      int id = dis.readInt();
      if (EnumPlayerData.values().length > id) {
         String name = dis.readUTF();
         EnumPlayerData type = EnumPlayerData.values()[id];
         EntityPlayer pl = MinecraftServer.func_71276_C().func_71203_ab().func_72361_f(name);
         PlayerData playerdata = null;
         if (pl == null) {
            playerdata = new PlayerData(name);
         } else {
            playerdata = PlayerDataController.instance.getPlayerData(pl);
         }

         if (type == EnumPlayerData.Players) {
            File file = new File(PlayerDataController.instance.getSaveDir(), name + ".dat");
            if (file.exists()) {
               file.delete();
            }

            file = new File(PlayerDataController.instance.getSaveDir(), name + ".dat_old");
            if (file.exists()) {
               file.delete();
            }

            if (pl != null) {
               playerdata.readNBT(new NBTTagCompound());
               sendPlayerData(type, player, name);
               playerdata.saveNBTData((NBTTagCompound)null);
               return;
            }
         }

         if (type == EnumPlayerData.Quest) {
            PlayerQuestData data = playerdata.questData;
            int questId = dis.readInt();
            data.activeQuests.remove(questId);
            data.finishedQuests.remove(questId);
            playerdata.saveNBTData((NBTTagCompound)null);
         }

         if (type == EnumPlayerData.Dialog) {
            PlayerDialogData data = playerdata.dialogData;
            data.dialogsRead.remove(dis.readInt());
            playerdata.saveNBTData((NBTTagCompound)null);
         }

         if (type == EnumPlayerData.Transport) {
            PlayerTransportData data = playerdata.transportData;
            data.transports.remove(dis.readInt());
            playerdata.saveNBTData((NBTTagCompound)null);
         }

         if (type == EnumPlayerData.Bank) {
            PlayerBankData data = playerdata.bankData;
            data.banks.remove(dis.readInt());
            playerdata.saveNBTData((NBTTagCompound)null);
         }

         if (type == EnumPlayerData.Factions) {
            PlayerFactionData data = playerdata.factionData;
            data.factionData.remove(dis.readInt());
            playerdata.saveNBTData((NBTTagCompound)null);
         }

         sendPlayerData(type, player, name);
      }
   }

   public static void sendRecipeData(EntityPlayerMP player, int size) {
      HashMap<String, Integer> map = new HashMap();
      if (size == 3) {
         for(RecipeCarpentry recipe : RecipeController.instance.globalRecipes.values()) {
            map.put(recipe.name, recipe.id);
         }
      } else {
         for(RecipeCarpentry recipe : RecipeController.instance.anvilRecipes.values()) {
            map.put(recipe.name, recipe.id);
         }
      }

      sendData(player, EnumPacketType.ScrollData, map);
   }

   public static void sendDialogData(EntityPlayerMP player, DialogCategory category) {
      if (category != null) {
         HashMap<String, Integer> map = new HashMap();

         for(Dialog dialog : category.dialogs.values()) {
            map.put(dialog.title, dialog.id);
         }

         sendData(player, EnumPacketType.ScrollData, map);
      }
   }

   public static void sendQuestData(EntityPlayerMP player, QuestCategory category) {
      if (category != null) {
         HashMap<String, Integer> map = new HashMap();

         for(Quest quest : category.quests.values()) {
            map.put(quest.title, quest.id);
         }

         sendData(player, EnumPacketType.ScrollData, map);
      }
   }

   public static void sendTransportCategoryData(EntityPlayerMP player) {
      HashMap<String, Integer> map = new HashMap();

      for(TransportCategory category : TransportController.getInstance().categories.values()) {
         map.put(category.title, category.id);
      }

      sendData(player, EnumPacketType.ScrollData, map);
   }

   public static void sendTransportData(EntityPlayerMP player, int categoryid) {
      TransportCategory category = (TransportCategory)TransportController.getInstance().categories.get(categoryid);
      if (category != null) {
         HashMap<String, Integer> map = new HashMap();

         for(TransportLocation transport : category.locations.values()) {
            map.put(transport.name, transport.id);
         }

         sendData(player, EnumPacketType.ScrollData, map);
      }
   }

   public static void sendData(EntityPlayer player, EnumPacketType enu, Object... obs) {
      PacketDispatcher.sendPacketToPlayer(getPacket(enu, obs), (Player)player);
   }

   public static void sendDataToAll(Entity entity, EnumPacketType enu, Object... obs) {
      Packet packet = getPacket(enu, obs);
      ((WorldServer)entity.field_70170_p).func_73039_n().func_72789_b(entity, packet);
   }

   public static Packet250CustomPayload getPacket(EnumPacketType enu, Object... obs) {
      try {
         ByteArrayOutputStream bytes = new ByteArrayOutputStream();
         DataOutputStream out = getDataOutputStream(bytes);
         out.writeInt(enu.ordinal());

         for(Object ob : obs) {
            if (ob != null) {
               if (ob instanceof Map) {
                  Map<String, Integer> map = (Map)ob;

                  for(String key : map.keySet()) {
                     int value = (Integer)map.get(key);
                     out.writeInt(value);
                     out.writeUTF(key);
                  }
               } else if (ob instanceof Enum) {
                  out.writeInt(((Enum)ob).ordinal());
               } else if (ob instanceof Double) {
                  out.writeDouble((Double)ob);
               } else if (ob instanceof Float) {
                  out.writeFloat((Float)ob);
               } else if (ob instanceof Integer) {
                  out.writeInt((Integer)ob);
               } else if (ob instanceof String) {
                  out.writeUTF((String)ob);
               } else if (ob instanceof NBTTagCompound) {
                  CompressedStreamTools.func_74800_a((NBTTagCompound)ob, out);
               } else if (ob instanceof MerchantRecipeList) {
                  ((MerchantRecipeList)ob).func_77200_a(out);
               }
            }
         }

         out.close();
         bytes.close();
         return new Packet250CustomPayload("CNPCs Client", bytes.toByteArray());
      } catch (IOException e) {
         e.printStackTrace();
         return null;
      }
   }

   public static void sendNpcDialogs(EntityPlayer player) {
      EntityNPCInterface npc = getEditingNpc(player);
      if (npc != null) {
         for(int pos : npc.dialogs.keySet()) {
            DialogOption option = (DialogOption)npc.dialogs.get(pos);
            if (option != null && option.hasDialog()) {
               NBTTagCompound compound = option.writeNBT();
               compound.func_74768_a("Position", pos);
               sendData(player, EnumPacketType.GuiData, compound);
            }
         }

      }
   }

   public static DialogOption setNpcDialog(int slot, int dialogId, EntityPlayer player) throws IOException {
      EntityNPCInterface npc = getEditingNpc(player);
      if (npc == null) {
         return null;
      } else {
         if (!npc.dialogs.containsKey(slot)) {
            npc.dialogs.put(slot, new DialogOption());
         }

         DialogOption option = (DialogOption)npc.dialogs.get(slot);
         option.dialogId = dialogId;
         if (option.hasDialog()) {
            option.title = option.getDialog().title;
         }

         return option;
      }
   }

   public static void saveTileEntity(EntityPlayerMP player, DataInputStream dis) throws IOException {
      NBTTagCompound compound = CompressedStreamTools.func_74794_a(dis);
      int x = compound.func_74762_e("x");
      int y = compound.func_74762_e("y");
      int z = compound.func_74762_e("z");
      TileEntity tile = player.field_70170_p.func_72796_p(x, y, z);
      if (tile != null) {
         tile.func_70307_a(compound);
      }

   }

   public static void setRecipeGui(EntityPlayerMP player, RecipeCarpentry recipe) {
      if (recipe != null) {
         if (player.field_71070_bA instanceof ContainerManageRecipes) {
            ContainerManageRecipes container = (ContainerManageRecipes)player.field_71070_bA;
            container.setRecipe(recipe);
            sendData(player, EnumPacketType.GuiData, recipe.writeNBT());
         }
      }
   }

   public static void sendBank(EntityPlayerMP player, Bank bank) {
      NBTTagCompound compound = new NBTTagCompound();
      bank.writeEntityToNBT(compound);
      sendData(player, EnumPacketType.GuiData, compound);
      if (player.field_71070_bA instanceof ContainerManageBanks) {
         ((ContainerManageBanks)player.field_71070_bA).setBank(bank);
      }

      player.func_71110_a(player.field_71070_bA, player.field_71070_bA.func_75138_a());
   }

   public static void sendNearbyNpcs(EntityPlayerMP player) {
      List<EntityNPCInterface> npcs = player.field_70170_p.func_72872_a(EntityNPCInterface.class, player.field_70121_D.func_72314_b((double)64.0F, (double)64.0F, (double)64.0F));
      HashMap<String, Integer> map = new HashMap();

      for(EntityNPCInterface npc : npcs) {
         if (!npc.field_70128_L) {
            float distance = player.func_70032_d(npc);
            DecimalFormat df = new DecimalFormat("#.#");
            String s = df.format((double)distance);
            if (distance < 10.0F) {
               s = "0" + s;
            }

            map.put(s + " : " + npc.display.name, npc.field_70157_k);
         }
      }

      sendData(player, EnumPacketType.ScrollData, map);
   }

   public static void sendGuiError(EntityPlayer player, int i) {
      sendData(player, EnumPacketType.GuiError, i, new NBTTagCompound());
   }

   public static void sendGuiClose(EntityPlayerMP player, int i, NBTTagCompound comp) {
      sendData(player, EnumPacketType.GuiClose, i, comp);
   }
}

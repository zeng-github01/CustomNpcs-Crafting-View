package noppes.npcs.client;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.zip.GZIPOutputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;
import noppes.npcs.CustomNpcs;
import noppes.npcs.EntityEnderFX;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.client.gui.player.GuiDialogInteract;
import noppes.npcs.client.gui.player.GuiQuestCompletion;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface2;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.constants.EnumPlayerPacket;
import noppes.npcs.controllers.BankController;
import noppes.npcs.controllers.Dialog;
import noppes.npcs.controllers.DialogController;
import noppes.npcs.controllers.Quest;
import noppes.npcs.entity.EntityNpcEnderchibi;

public class NoppesUtil {
   private static EntityNPCInterface lastNpc;

   public static void requestOpenGUI(EnumGuiType gui) {
      requestOpenGUI(gui, 0, 0, 0);
   }

   public static void requestOpenGUI(EnumGuiType gui, int i, int j, int k) {
      try {
         ByteArrayOutputStream bytes = new ByteArrayOutputStream();
         DataOutputStream out = getDataOutputStream(bytes);
         out.writeInt(EnumPacketType.Gui.ordinal());
         out.writeInt(gui.ordinal());
         out.writeInt(i);
         out.writeInt(j);
         out.writeInt(k);
         out.close();
         PacketDispatcher.sendPacketToServer(new Packet250CustomPayload("CNPCs Server", bytes.toByteArray()));
      } catch (IOException e) {
         e.printStackTrace();
      }

   }

   public static DataOutputStream getDataOutputStream(ByteArrayOutputStream stream) throws IOException {
      return new DataOutputStream(new GZIPOutputStream(stream));
   }

   public static void spawnParticle(DataInputStream dis) {
      try {
         double posX = dis.readDouble();
         double posY = dis.readDouble();
         double posZ = dis.readDouble();
         float height = dis.readFloat();
         float width = dis.readFloat();
         float yOffset = dis.readFloat();
         String particle = dis.readUTF();
         World worldObj = Minecraft.func_71410_x().field_71441_e;
         Random rand = worldObj.field_73012_v;
         if (particle.equals("heal")) {
            for(int k = 0; k < 6; ++k) {
               worldObj.func_72869_a("instantSpell", posX + (rand.nextDouble() - (double)0.5F) * (double)width, posY + rand.nextDouble() * (double)height - (double)yOffset, posZ + (rand.nextDouble() - (double)0.5F) * (double)width, (double)0.0F, (double)0.0F, (double)0.0F);
               worldObj.func_72869_a("spell", posX + (rand.nextDouble() - (double)0.5F) * (double)width, posY + rand.nextDouble() * (double)height - (double)yOffset, posZ + (rand.nextDouble() - (double)0.5F) * (double)width, (double)0.0F, (double)0.0F, (double)0.0F);
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }

   }

   public static EntityNPCInterface getLastNpc() {
      return lastNpc;
   }

   public static void setLastNpc(EntityNPCInterface npc) {
      lastNpc = npc;
   }

   public static void openGUI(EntityPlayer player, Object guiscreen) {
      CustomNpcs.proxy.openGui(player, guiscreen);
   }

   public static void setScrollList(DataInputStream dis) {
      GuiScreen gui = Minecraft.func_71410_x().field_71462_r;
      if (gui != null && gui instanceof IScrollData) {
         Vector<String> data = new Vector();

         String line;
         try {
            while((line = dis.readUTF()) != null) {
               data.add(line);
            }
         } catch (Exception var5) {
         }

         ((IScrollData)gui).setData(data, (HashMap)null);
      }
   }

   public static void setScrollData(DataInputStream dis) {
      GuiScreen gui = Minecraft.func_71410_x().field_71462_r;
      if (gui != null) {
         Vector<String> list = new Vector();
         HashMap<String, Integer> data = new HashMap();

         try {
            while(true) {
               int id = dis.readInt();
               String name = dis.readUTF();
               data.put(name, id);
               list.add(name);
            }
         } catch (Exception var6) {
            if (gui instanceof GuiNPCInterface2 && ((GuiNPCInterface2)gui).hasSubGui()) {
               gui = ((GuiNPCInterface2)gui).getSubGui();
            }

            if (gui instanceof GuiContainerNPCInterface2 && ((GuiContainerNPCInterface2)gui).hasSubGui()) {
               gui = ((GuiContainerNPCInterface2)gui).getSubGui();
            }

            if (gui instanceof IScrollData) {
               ((IScrollData)gui).setData(list, data);
            }

         }
      }
   }

   public static void guiQuestCompletion(EntityPlayer player, NBTTagCompound read) {
      Quest quest = new Quest();
      quest.readNBT(read);
      if (!quest.completeText.equals("")) {
         openGUI(player, new GuiQuestCompletion(quest));
      } else {
         NoppesUtilPlayer.sendData(EnumPlayerPacket.QuestCompletion, quest.id);
      }

   }

   public static void openDialog(DataInputStream dis, EntityNPCInterface npc, EntityPlayer player) throws IOException {
      if (DialogController.instance == null) {
         DialogController.instance = new DialogController();
      }

      NBTTagCompound compound = CompressedStreamTools.func_74794_a(dis);
      Dialog dialog = new Dialog();
      dialog.readNBT(compound);
      CustomNpcs.proxy.openGui((EntityPlayer)player, (Object)(new GuiDialogInteract(npc, dialog)));
   }

   public static void sendData(EnumPacketType enu, Object... obs) {
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
         PacketDispatcher.sendPacketToServer(new Packet250CustomPayload("CNPCs Server", bytes.toByteArray()));
      } catch (IOException e) {
         e.printStackTrace();
      }

   }

   public static void bankData(DataInputStream dis) throws IOException {
      BankController controller = BankController.getInstance();
      controller.loadBanks(dis);
      GuiScreen gui = Minecraft.func_71410_x().field_71462_r;
      if (gui != null) {
         gui.func_73866_w_();
      }

   }

   public static void spawnEnderchibi(EntityNpcEnderchibi chibi) {
      Random rand = chibi.field_70170_p.field_73012_v;
      EntityEnderFX fx = new EntityEnderFX(chibi, chibi.field_70165_t + (rand.nextDouble() - (double)0.5F) * (double)chibi.field_70130_N, chibi.field_70163_u + rand.nextDouble() * (double)chibi.field_70131_O, chibi.field_70161_v + (rand.nextDouble() - (double)0.5F) * (double)chibi.field_70130_N, (rand.nextDouble() - (double)0.5F) * (double)2.0F, -rand.nextDouble(), (rand.nextDouble() - (double)0.5F) * (double)2.0F);
      Minecraft.func_71410_x().field_71452_i.func_78873_a(fx);
   }

   public static void saveRedstoneBlock(EntityPlayer player, DataInputStream dis) throws IOException {
      NBTTagCompound compound = CompressedStreamTools.func_74794_a(dis);
      int x = compound.func_74762_e("x");
      int y = compound.func_74762_e("y");
      int z = compound.func_74762_e("z");
      TileEntity tile = player.field_70170_p.func_72796_p(x, y, z);
      tile.func_70307_a(compound);
      CustomNpcs.proxy.openGui(x, y, z, EnumGuiType.RedstoneBlock, player);
   }

   public static void saveWayPointBlock(EntityPlayer player, DataInputStream dis) throws IOException {
      NBTTagCompound compound = CompressedStreamTools.func_74794_a(dis);
      int x = compound.func_74762_e("x");
      int y = compound.func_74762_e("y");
      int z = compound.func_74762_e("z");
      TileEntity tile = player.field_70170_p.func_72796_p(x, y, z);
      tile.func_70307_a(compound);
      CustomNpcs.proxy.openGui(x, y, z, EnumGuiType.Waypoint, player);
   }
}

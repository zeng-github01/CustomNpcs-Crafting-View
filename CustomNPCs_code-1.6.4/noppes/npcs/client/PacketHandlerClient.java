package noppes.npcs.client;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.stats.Achievement;
import net.minecraft.util.StatCollector;
import net.minecraft.village.MerchantRecipeList;
import noppes.npcs.CustomNpcs;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.controllers.MusicController;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.IGuiClose;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.IGuiError;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.controllers.RecipeCarpentry;
import noppes.npcs.controllers.RecipeController;
import noppes.npcs.events.ItemInteractEvent;

public class PacketHandlerClient implements IPacketHandler {
   public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
      if (packet.field_73630_a.equals("CNPCs Client")) {
         try {
            DataInputStream dis = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(packet.field_73629_c))));
            this.client(dis, (EntityPlayer)player, EnumPacketType.values()[dis.readInt()]);
            dis.close();
         } catch (IOException e) {
            e.printStackTrace();
         }

      }
   }

   private void client(DataInputStream dis, EntityPlayer player, EnumPacketType type) throws IOException {
      if (type == EnumPacketType.Bank) {
         NoppesUtil.bankData(dis);
      } else if (type == EnumPacketType.Chat) {
         String message = "";

         try {
            while(true) {
               String str = dis.readUTF();
               message = message + StatCollector.func_74838_a(str);
            }
         } catch (EOFException var8) {
            player.func_71035_c(message);
         }
      } else if (type == EnumPacketType.Message) {
         String description = StatCollector.func_74838_a(dis.readUTF());
         String message = dis.readUTF();
         Achievement ach = new QuestAchievement(message, description);
         Minecraft.func_71410_x().field_71458_u.func_73846_a(ach);
         Minecraft.func_71410_x().field_71458_u.field_73852_d = ach.func_75989_e();
      } else if (type == EnumPacketType.SyncRecipes) {
         NBTTagList list = CompressedStreamTools.func_74794_a(dis).func_74761_m("recipes");
         HashMap<Integer, RecipeCarpentry> recipes = new HashMap();
         if (list == null) {
            return;
         }

         for(int i = 0; i < list.func_74745_c(); ++i) {
            RecipeCarpentry recipe = new RecipeCarpentry();
            recipe.readNBT((NBTTagCompound)list.func_74743_b(i));
            recipes.put(recipe.id, recipe);
         }

         RecipeController.reloadGlobalRecipes(recipes);
      } else if (type == EnumPacketType.Dialog) {
         Entity entity = Minecraft.func_71410_x().field_71441_e.func_73045_a(dis.readInt());
         if (entity == null || !(entity instanceof EntityNPCInterface)) {
            return;
         }

         NoppesUtil.openDialog(dis, (EntityNPCInterface)entity, player);
      } else if (type == EnumPacketType.QuestCompletion) {
         NoppesUtil.guiQuestCompletion(player, CompressedStreamTools.func_74794_a(dis));
      } else if (type == EnumPacketType.EditingNpc) {
         Entity entity = Minecraft.func_71410_x().field_71441_e.func_73045_a(dis.readInt());
         if (entity == null || !(entity instanceof EntityNPCInterface)) {
            return;
         }

         NoppesUtil.setLastNpc((EntityNPCInterface)entity);
      } else if (type == EnumPacketType.PlayMusic) {
         MusicController.Instance.playMusic("customnpcs:Failboat103 - Excalibuuur");
      } else if (type == EnumPacketType.PlaySound) {
         MusicController.Instance.playSound(dis.readUTF(), dis.readFloat(), dis.readFloat(), dis.readFloat());
      } else if (type == EnumPacketType.UpdateNpc) {
         NBTTagCompound compound = CompressedStreamTools.func_74794_a(dis);
         Entity entity = Minecraft.func_71410_x().field_71441_e.func_73045_a(compound.func_74762_e("EntityId"));
         if (entity == null || !(entity instanceof EntityNPCInterface)) {
            return;
         }

         entity.func_70020_e(compound);
      } else if (type == EnumPacketType.SaveRole) {
         NBTTagCompound compound = CompressedStreamTools.func_74794_a(dis);
         Entity entity = Minecraft.func_71410_x().field_71441_e.func_73045_a(compound.func_74762_e("EntityId"));
         if (entity == null || !(entity instanceof EntityNPCInterface)) {
            return;
         }

         ((EntityNPCInterface)entity).advanced.setRole(compound.func_74762_e("Role"));
         ((EntityNPCInterface)entity).roleInterface.readEntityFromNBT(compound);
         NoppesUtil.setLastNpc((EntityNPCInterface)entity);
      } else if (type == EnumPacketType.Gui) {
         EnumGuiType gui = EnumGuiType.values()[dis.readInt()];
         CustomNpcs.proxy.openGui(NoppesUtil.getLastNpc(), gui);
      } else if (type == EnumPacketType.Particle) {
         NoppesUtil.spawnParticle(dis);
      } else if (type == EnumPacketType.Delete) {
         Entity entity = Minecraft.func_71410_x().field_71441_e.func_73045_a(dis.readInt());
         if (entity == null || !(entity instanceof EntityNPCInterface)) {
            return;
         }

         ((EntityNPCInterface)entity).delete();
      } else if (type == EnumPacketType.ScrollList) {
         NoppesUtil.setScrollList(dis);
      } else if (type == EnumPacketType.ScrollData) {
         NoppesUtil.setScrollData(dis);
      } else if (type == EnumPacketType.ScrollSelected) {
         GuiScreen gui = Minecraft.func_71410_x().field_71462_r;
         if (gui == null || !(gui instanceof IScrollData)) {
            return;
         }

         String selected = dis.readUTF();
         ((IScrollData)gui).setSelected(selected);
      } else if (type == EnumPacketType.RedstoneBlockSave) {
         NoppesUtil.saveRedstoneBlock(player, dis);
      } else if (type == EnumPacketType.WaypointSave) {
         NoppesUtil.saveWayPointBlock(player, dis);
      } else if (type == EnumPacketType.GuiData) {
         GuiScreen gui = Minecraft.func_71410_x().field_71462_r;
         if (gui == null) {
            return;
         }

         if (gui instanceof GuiNPCInterface2 && ((GuiNPCInterface2)gui).hasSubGui()) {
            gui = ((GuiNPCInterface2)gui).getSubGui();
         }

         if (gui instanceof IGuiData) {
            ((IGuiData)gui).setGuiData(CompressedStreamTools.func_74794_a(dis));
         }
      } else if (type == EnumPacketType.GuiError) {
         GuiScreen gui = Minecraft.func_71410_x().field_71462_r;
         if (gui == null || !(gui instanceof IGuiError)) {
            return;
         }

         int i = dis.readInt();
         NBTTagCompound compound = CompressedStreamTools.func_74794_a(dis);
         ((IGuiError)gui).setError(i, compound);
      } else if (type == EnumPacketType.GuiClose) {
         GuiScreen gui = Minecraft.func_71410_x().field_71462_r;
         if (gui == null) {
            return;
         }

         int i = dis.readInt();
         NBTTagCompound compound = CompressedStreamTools.func_74794_a(dis);
         if (gui instanceof IGuiClose) {
            ((IGuiClose)gui).setClose(i, compound);
         }

         Minecraft mc = Minecraft.func_71410_x();
         mc.func_71373_a((GuiScreen)null);
         mc.func_71381_h();
      } else if (type == EnumPacketType.MerchantAdd) {
         MerchantRecipeList merchantrecipelist = MerchantRecipeList.func_77204_a(dis);
         ItemInteractEvent.Merchant.func_70930_a(merchantrecipelist);
      }

   }
}

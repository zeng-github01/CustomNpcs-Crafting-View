package noppes.npcs.client.gui.player;

import java.util.ArrayList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.client.gui.util.GuiNPCInterface;
import noppes.npcs.client.gui.util.GuiPlayerTopMenu;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.constants.EnumPlayerPacket;
import noppes.npcs.controllers.Faction;
import noppes.npcs.controllers.PlayerFactionData;
import org.lwjgl.opengl.GL11;

public class GuiFaction extends GuiNPCInterface implements IGuiData {
   private int xSize = 200;
   private int ySize = 216;
   private int guiLeft;
   private int guiTop;
   private ArrayList playerFactions = new ArrayList();
   private GuiPlayerTopMenu topMenu;
   private int page = 0;
   private int pages = 1;
   private noppes.npcs.client.gui.util.GuiButtonNextPage buttonNextPage;
   private noppes.npcs.client.gui.util.GuiButtonNextPage buttonPreviousPage;
   private ResourceLocation indicator;

   public GuiFaction() {
      this.drawDefaultBackground = false;
      this.title = "";
      NoppesUtilPlayer.sendData(EnumPlayerPacket.FactionsGet);
      this.indicator = this.getResource("menubg.png");
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.guiLeft = (this.field_73880_f - this.xSize) / 2;
      this.guiTop = (this.field_73881_g - this.ySize) / 2;
      this.topMenu = new GuiPlayerTopMenu(this.guiLeft + 4, this.guiTop - 9, 3, this.player);
      this.field_73887_h.add(this.buttonNextPage = new noppes.npcs.client.gui.util.GuiButtonNextPage(1, this.guiLeft + this.xSize - 43, this.guiTop + 198, true));
      this.field_73887_h.add(this.buttonPreviousPage = new noppes.npcs.client.gui.util.GuiButtonNextPage(2, this.guiLeft + 20, this.guiTop + 198, false));
   }

   public void func_73863_a(int i, int j, float f) {
      this.func_73873_v_();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73882_e.field_71446_o.func_110577_a(this.indicator);
      this.func_73729_b(this.guiLeft, this.guiTop + 8, 0, 0, this.xSize, this.ySize);
      this.func_73729_b(this.guiLeft + 4, this.guiTop + 8, 56, 0, 200, this.ySize);
      if (this.playerFactions.isEmpty()) {
         String noFaction = StatCollector.func_74838_a("faction.nostanding");
         this.field_73886_k.func_78276_b(noFaction, this.guiLeft + (this.xSize - this.field_73886_k.func_78256_a(noFaction)) / 2, this.guiTop + 80, 4210752);
      } else {
         this.renderScreen();
      }

      super.func_73863_a(i, j, f);
      this.topMenu.func_73863_a(i, j, f);
   }

   private void renderScreen() {
      int size = 6;
      if (this.pages == 1) {
         size = this.playerFactions.size();
      }

      if (this.page == this.pages) {
         size = this.playerFactions.size() % 6;
      }

      for(int id = 0; id < size; ++id) {
         this.func_73730_a(this.guiLeft + 2, this.guiLeft + this.xSize, this.guiTop + 14 + id * 30, -12566464);
         Faction faction = (Faction)this.playerFactions.get((this.page - 1) * 6 + id);
         String name = faction.name;
         String points = " : " + faction.defaultPoints;
         String standing = StatCollector.func_74838_a("faction.friendly");
         int color = 65280;
         if (faction.defaultPoints < faction.neutralPoints) {
            standing = StatCollector.func_74838_a("faction.unfriendly");
            color = 16711680;
            points = points + "/" + faction.neutralPoints;
         } else if (faction.defaultPoints < faction.friendlyPoints) {
            standing = StatCollector.func_74838_a("faction.neutral");
            color = 15924992;
            points = points + "/" + faction.friendlyPoints;
         } else {
            points = points + "/-";
         }

         this.field_73886_k.func_78276_b(name, this.guiLeft + (this.xSize - this.field_73886_k.func_78256_a(name)) / 2, this.guiTop + 19 + id * 30, faction.color);
         this.field_73886_k.func_78276_b(standing, this.field_73880_f / 2 - this.field_73886_k.func_78256_a(standing) - 1, this.guiTop + 31 + id * 30, color);
         this.field_73886_k.func_78276_b(points, this.field_73880_f / 2, this.guiTop + 35 + id * 30, 4210752);
      }

      this.func_73730_a(this.guiLeft + 2, this.guiLeft + this.xSize, this.guiTop + 14 + size * 30, -12566464);
      if (this.pages > 1) {
         String s = this.page + "/" + this.pages;
         this.field_73886_k.func_78276_b(s, this.guiLeft + (this.xSize - this.field_73886_k.func_78256_a(s)) / 2, this.guiTop + 203, 4210752);
      }

   }

   protected void func_73875_a(GuiButton guibutton) {
      if (guibutton.field_73741_f == 1) {
         ++this.page;
      }

      if (guibutton.field_73741_f == 2) {
         --this.page;
      }

      this.updateButtons();
   }

   private void updateButtons() {
      this.buttonNextPage.field_73748_h = this.page < this.pages;
      this.buttonPreviousPage.field_73748_h = this.page > 1;
   }

   protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
   }

   public void func_73864_a(int i, int j, int k) {
      super.func_73864_a(i, j, k);
      this.topMenu.func_73864_a(i, j, k);
   }

   public void func_73869_a(char c, int i) {
      if (i == 1 || i == this.field_73882_e.field_71474_y.field_74315_B.field_74512_d) {
         this.close();
      }

   }

   public void save() {
   }

   public void setGuiData(NBTTagCompound compound) {
      this.playerFactions = new ArrayList();
      NBTTagList list = compound.func_74761_m("FactionList");

      for(int i = 0; i < list.func_74745_c(); ++i) {
         Faction faction = new Faction();
         faction.readNBT((NBTTagCompound)list.func_74743_b(i));
         this.playerFactions.add(faction);
      }

      PlayerFactionData data = new PlayerFactionData();
      data.readNBT(compound);

      for(int id : data.factionData.keySet()) {
         int points = (Integer)data.factionData.get(id);

         for(Faction faction : this.playerFactions) {
            if (faction.id == id) {
               faction.defaultPoints = points;
            }
         }
      }

      this.pages = (this.playerFactions.size() - 1) / 6;
      ++this.pages;
      this.page = 1;
      this.updateButtons();
   }
}

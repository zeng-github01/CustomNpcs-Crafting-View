package noppes.npcs.client.gui.global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiCustomScrollActionListener;
import noppes.npcs.client.gui.util.GuiNPCInterface2;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcLabel;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.constants.EnumPlayerData;

public class GuiNpcManagePlayerData extends GuiNPCInterface2 implements IScrollData, GuiCustomScrollActionListener {
   private GuiCustomScroll scroll;
   private String selectedPlayer = null;
   private String selected = null;
   private HashMap data = new HashMap();
   private EnumPlayerData selection;
   private String search;

   public GuiNpcManagePlayerData(EntityNPCInterface npc, GuiNPCInterface2 parent) {
      super(npc);
      this.selection = EnumPlayerData.Players;
      this.search = "";
      NoppesUtil.sendData(EnumPacketType.PlayerDataGet, this.selection);
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.scroll = new GuiCustomScroll(this, 0);
      this.scroll.func_73872_a(this.field_73882_e, 350, 250);
      this.scroll.setSize(190, 175);
      this.scroll.guiLeft = this.guiLeft + 4;
      this.scroll.guiTop = this.guiTop + 16;
      this.addLabel(new GuiNpcLabel(0, "All Players", this.guiLeft + 10, this.guiTop + 6, 4210752));
      this.addButton(new GuiNpcButton(0, this.guiLeft + 200, this.guiTop + 10, 98, 20, "selectWorld.deleteButton"));
      this.addButton(new GuiNpcButton(1, this.guiLeft + 200, this.guiTop + 32, 98, 20, "Players"));
      this.addButton(new GuiNpcButton(2, this.guiLeft + 200, this.guiTop + 54, 98, 20, "Quest Data"));
      this.addButton(new GuiNpcButton(3, this.guiLeft + 200, this.guiTop + 76, 98, 20, "Dialog Data"));
      this.addButton(new GuiNpcButton(4, this.guiLeft + 200, this.guiTop + 98, 98, 20, "Transport Data"));
      this.addButton(new GuiNpcButton(5, this.guiLeft + 200, this.guiTop + 120, 98, 20, "Bank Data"));
      this.addButton(new GuiNpcButton(6, this.guiLeft + 200, this.guiTop + 142, 98, 20, "Faction Data"));
      this.addTextField(new GuiNpcTextField(0, this, this.field_73886_k, this.guiLeft + 4, this.guiTop + 193, 190, 20, this.search));
      this.getTextField(0).enabled = this.selection == EnumPlayerData.Players;
      this.initButtons();
   }

   public void initButtons() {
      this.getButton(1).field_73742_g = this.selection != EnumPlayerData.Players;
      this.getButton(2).field_73742_g = this.selection != EnumPlayerData.Quest;
      this.getButton(3).field_73742_g = this.selection != EnumPlayerData.Dialog;
      this.getButton(4).field_73742_g = this.selection != EnumPlayerData.Transport;
      this.getButton(5).field_73742_g = this.selection != EnumPlayerData.Bank;
      this.getButton(6).field_73742_g = this.selection != EnumPlayerData.Factions;
      if (this.selection == EnumPlayerData.Players) {
         this.getLabel(0).label = "All Players";
      } else {
         this.getLabel(0).label = "Selected player: " + this.selectedPlayer;
      }

   }

   public void func_73863_a(int i, int j, float f) {
      super.func_73863_a(i, j, f);
      this.scroll.func_73863_a(i, j, f);
   }

   public void func_73864_a(int i, int j, int k) {
      super.func_73864_a(i, j, k);
      if (k == 0 && this.scroll != null) {
         this.scroll.func_73864_a(i, j, k);
      }

   }

   public void func_73869_a(char c, int i) {
      super.func_73869_a(c, i);
      if (this.selection == EnumPlayerData.Players) {
         if (!this.search.equals(this.getTextField(0).func_73781_b())) {
            this.search = this.getTextField(0).func_73781_b().toLowerCase();
            this.scroll.setList(this.getSearchList());
         }
      }
   }

   private List getSearchList() {
      if (!this.search.isEmpty() && this.selection == EnumPlayerData.Players) {
         List<String> list = new ArrayList();

         for(String name : this.data.keySet()) {
            if (name.toLowerCase().contains(this.search)) {
               list.add(name);
            }
         }

         return list;
      } else {
         return new ArrayList(this.data.keySet());
      }
   }

   protected void func_73875_a(GuiButton guibutton) {
      if (guibutton.field_73741_f == 0) {
         if (this.selected != null) {
            if (this.selection == EnumPlayerData.Players) {
               NoppesUtil.sendData(EnumPacketType.PlayerDataRemove, this.selection, this.selectedPlayer, this.selected);
            } else {
               NoppesUtil.sendData(EnumPacketType.PlayerDataRemove, this.selection, this.selectedPlayer, this.data.get(this.selected));
            }

            this.data.clear();
         }

         this.selected = null;
      }

      if (guibutton.field_73741_f >= 1 && guibutton.field_73741_f <= 6) {
         if (this.selectedPlayer == null && guibutton.field_73741_f != 1) {
            return;
         }

         this.selection = EnumPlayerData.values()[guibutton.field_73741_f - 1];
         this.initButtons();
         this.scroll.clear();
         this.data.clear();
         NoppesUtil.sendData(EnumPacketType.PlayerDataGet, this.selection, this.selectedPlayer);
         this.selected = null;
      }

   }

   public void save() {
   }

   public void setData(Vector list, HashMap data) {
      this.data.putAll(data);
      this.scroll.setList(this.getSearchList());
      if (this.selection == EnumPlayerData.Players && this.selectedPlayer != null) {
         this.scroll.setSelected(this.selectedPlayer);
         this.selected = this.selectedPlayer;
      }

   }

   public void setSelected(String selected) {
   }

   public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
      this.selected = guiCustomScroll.getSelected();
      if (this.selection == EnumPlayerData.Players) {
         this.selectedPlayer = this.selected;
      }

   }
}

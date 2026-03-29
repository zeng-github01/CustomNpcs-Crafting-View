package noppes.npcs.client.gui.global;

import java.util.HashMap;
import java.util.Vector;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.NoppesUtil;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface2;
import noppes.npcs.client.gui.util.GuiCustomScroll;
import noppes.npcs.client.gui.util.GuiCustomScrollActionListener;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.GuiNpcTextField;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.client.gui.util.IScrollData;
import noppes.npcs.client.gui.util.ITextfieldListener;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.containers.ContainerManageBanks;
import noppes.npcs.controllers.Bank;

public class GuiNPCManageBanks extends GuiContainerNPCInterface2 implements IScrollData, GuiCustomScrollActionListener, ITextfieldListener, IGuiData {
   private GuiCustomScroll scroll;
   private HashMap data = new HashMap();
   private ContainerManageBanks container;
   private Bank bank = new Bank();
   private String selected = null;

   public GuiNPCManageBanks(EntityNPCInterface npc, ContainerManageBanks container) {
      super(npc, container);
      this.container = container;
      this.drawDefaultBackground = false;
      NoppesUtil.sendData(EnumPacketType.BanksGet);
      this.setBackground("npcbanksetup.png");
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.addButton(new GuiNpcButton(6, this.field_74198_m + 340, this.field_74197_n + 10, 45, 20, "gui.add"));
      this.addButton(new GuiNpcButton(7, this.field_74198_m + 340, this.field_74197_n + 32, 45, 20, "gui.remove"));
      this.scroll = new GuiCustomScroll(this, 0);
      this.scroll.func_73872_a(this.field_73882_e, 350, 250);
      this.scroll.setSize(160, 180);
      this.scroll.guiLeft = this.field_74198_m + 174;
      this.scroll.guiTop = this.field_74197_n + 8;

      for(int i = 0; i < 6; ++i) {
         int x = this.field_74198_m + 6;
         int y = this.field_74197_n + 36 + i * 22;
         this.addButton(new GuiNpcButton(i, x + 50, y, 80, 20, new String[]{"Can Upgrade", "Can't Upgrade", "Upgraded"}, 0));
         this.getButton(i).field_73742_g = false;
      }

      this.addTextField(new GuiNpcTextField(0, this, this.field_73886_k, this.field_74198_m + 8, this.field_74197_n + 8, 160, 16, ""));
      this.getTextField(0).func_73804_f(20);
      this.addTextField(new GuiNpcTextField(1, this, this.field_73886_k, this.field_74198_m + 10, this.field_74197_n + 80, 16, 16, ""));
      this.getTextField(1).numbersOnly = true;
      this.getTextField(1).func_73804_f(1);
      this.addTextField(new GuiNpcTextField(2, this, this.field_73886_k, this.field_74198_m + 10, this.field_74197_n + 110, 16, 16, ""));
      this.getTextField(2).numbersOnly = true;
      this.getTextField(2).func_73804_f(1);
   }

   public void func_73863_a(int x, int y, float f) {
      super.func_73863_a(x, y, f);
   }

   protected void func_73875_a(GuiButton guibutton) {
      GuiNpcButton button = (GuiNpcButton)guibutton;
      if (guibutton.field_73741_f == 6) {
         this.save();
         this.scroll.clear();

         String name;
         for(name = "New"; this.data.containsKey(name); name = name + "_") {
         }

         Bank bank = new Bank();
         bank.name = name;
         NBTTagCompound compound = new NBTTagCompound();
         bank.writeEntityToNBT(compound);
         NoppesUtil.sendData(EnumPacketType.BankSave, compound);
      } else if (guibutton.field_73741_f == 7) {
         if (this.data.containsKey(this.scroll.getSelected())) {
            NoppesUtil.sendData(EnumPacketType.BankRemove, this.data.get(this.selected));
         }
      } else if (guibutton.field_73741_f >= 0 && guibutton.field_73741_f < 6) {
         this.bank.slotTypes.put(guibutton.field_73741_f, button.getValue());
      }

   }

   protected void func_74189_g(int par1, int par2) {
      this.field_73886_k.func_78276_b("Tab Cost", 23, 10, 4210752);
      this.field_73886_k.func_78276_b("Upg. Cost", 123, 10, 4210752);
      this.field_73886_k.func_78276_b("Start", 6, 52, 4210752);
      this.field_73886_k.func_78276_b("Max", 9, 82, 4210752);
   }

   protected void func_74185_a(float f, int i, int j) {
      super.func_74185_a(f, i, j);
      this.scroll.func_73863_a(i, j, f);
   }

   public void setGuiData(NBTTagCompound compound) {
      Bank bank = new Bank();
      bank.readEntityFromNBT(compound);
      this.bank = bank;
      if (bank.id == -1) {
         this.getTextField(0).func_73782_a("");
         this.getTextField(1).func_73782_a("");
         this.getTextField(2).func_73782_a("");

         for(int i = 0; i < 6; ++i) {
            this.getButton(i).setDisplay(0);
            this.getButton(i).field_73742_g = false;
         }
      } else {
         this.getTextField(0).func_73782_a(bank.name);
         this.getTextField(1).func_73782_a(Integer.toString(bank.startSlots));
         this.getTextField(2).func_73782_a(Integer.toString(bank.maxSlots));

         for(int i = 0; i < 6; ++i) {
            int type = 0;
            if (bank.slotTypes.containsKey(i)) {
               type = (Integer)bank.slotTypes.get(i);
            }

            this.getButton(i).setDisplay(type);
            this.getButton(i).field_73742_g = true;
         }
      }

      this.setSelected(bank.name);
   }

   public void setData(Vector list, HashMap data) {
      String name = this.scroll.getSelected();
      this.data = data;
      this.scroll.setList(list);
      if (name != null) {
         this.scroll.setSelected(name);
      }

   }

   public void func_73864_a(int i, int j, int k) {
      super.func_73864_a(i, j, k);
      if (k == 0 && this.scroll != null) {
         this.scroll.func_73864_a(i, j, k);
      }

   }

   public void setSelected(String selected) {
      this.selected = selected;
      this.scroll.setSelected(selected);
   }

   public void customScrollClicked(int i, int j, int k, GuiCustomScroll guiCustomScroll) {
      if (guiCustomScroll.id == 0) {
         this.save();
         this.selected = this.scroll.getSelected();
         NoppesUtil.sendData(EnumPacketType.BankGet, this.data.get(this.selected));
      }

   }

   public void save() {
      if (this.selected != null && this.data.containsKey(this.selected) && this.bank != null) {
         NBTTagCompound compound = new NBTTagCompound();
         this.bank.currencyInventory = this.container.bank.currencyInventory;
         this.bank.upgradeInventory = this.container.bank.upgradeInventory;
         this.bank.writeEntityToNBT(compound);
         NoppesUtil.sendData(EnumPacketType.BankSave, compound);
      }

   }

   public void unFocused(GuiNpcTextField guiNpcTextField) {
      if (this.bank.id != -1) {
         if (guiNpcTextField.id == 0) {
            String name = guiNpcTextField.func_73781_b();
            if (!name.isEmpty() && !this.data.containsKey(name)) {
               String old = this.bank.name;
               this.data.remove(this.bank.name);
               this.bank.name = name;
               this.data.put(this.bank.name, this.bank.id);
               this.selected = name;
               this.scroll.replace(old, this.bank.name);
            }
         } else if (guiNpcTextField.id == 1 || guiNpcTextField.id == 2) {
            int num = 1;
            if (!guiNpcTextField.isEmpty()) {
               num = guiNpcTextField.getInteger();
            }

            if (num > 6) {
               num = 6;
            }

            if (num < 0) {
               num = 0;
            }

            if (guiNpcTextField.id == 1) {
               this.bank.startSlots = num;
            } else if (guiNpcTextField.id == 2) {
               this.bank.maxSlots = num;
            }

            if (this.bank.startSlots > this.bank.maxSlots) {
               this.bank.maxSlots = this.bank.startSlots;
            }

            guiNpcTextField.func_73782_a(Integer.toString(num));
         }
      }

   }
}

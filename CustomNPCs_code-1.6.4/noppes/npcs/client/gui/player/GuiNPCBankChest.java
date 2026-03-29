package noppes.npcs.client.gui.player;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.client.gui.util.GuiContainerNPCInterface;
import noppes.npcs.client.gui.util.GuiNpcButton;
import noppes.npcs.client.gui.util.IGuiData;
import noppes.npcs.constants.EnumPlayerPacket;
import noppes.npcs.containers.ContainerNPCBankInterface;
import org.lwjgl.opengl.GL11;

public class GuiNPCBankChest extends GuiContainerNPCInterface implements IGuiData {
   private final ResourceLocation resource = new ResourceLocation("customnpcs", "textures/gui/bankchest.png");
   private ContainerNPCBankInterface container;
   private int availableSlots = 0;
   private int maxSlots = 1;
   private int unlockedSlots = 1;
   private ItemStack currency;

   public GuiNPCBankChest(EntityNPCInterface npc, ContainerNPCBankInterface container) {
      super(npc, container);
      this.container = container;
      this.title = "";
      this.field_73885_j = false;
      this.field_74195_c = 235;
      this.closeOnEsc = true;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      this.availableSlots = 0;
      if (this.maxSlots > 1) {
         for(int i = 0; i < this.maxSlots; ++i) {
            GuiNpcButton button = new GuiNpcButton(i, this.field_74198_m - 50, this.field_74197_n + 10 + i * 24, 50, 20, "Tab " + (i + 1));
            if (i > this.unlockedSlots) {
               button.field_73742_g = false;
            }

            this.addButton(button);
            ++this.availableSlots;
         }

         if (this.availableSlots == 1) {
            this.field_73887_h.clear();
         }
      }

      if (!this.container.isAvailable()) {
         this.addButton(new GuiNpcButton(8, this.field_74198_m + 48, this.field_74197_n + 48, 80, 20, StatCollector.func_74838_a("bank.unlock")));
      } else if (this.container.canBeUpgraded()) {
         this.addButton(new GuiNpcButton(9, this.field_74198_m + 48, this.field_74197_n + 48, 80, 20, StatCollector.func_74838_a("bank.upgrade")));
      }

      if (this.maxSlots > 1) {
         this.getButton(this.container.slot).shown = false;
         this.getButton(this.container.slot).field_73742_g = false;
      }

   }

   public void func_73875_a(GuiButton guibutton) {
      super.func_73875_a(guibutton);
      if (guibutton.field_73741_f < 6) {
         this.close();
         NoppesUtilPlayer.sendData(EnumPlayerPacket.BankSlotOpen, guibutton.field_73741_f, this.container.bankid);
      }

      if (guibutton.field_73741_f == 8) {
         NoppesUtilPlayer.sendData(EnumPlayerPacket.BankUnlock);
      }

      if (guibutton.field_73741_f == 9) {
         NoppesUtilPlayer.sendData(EnumPlayerPacket.BankUpgrade);
      }

   }

   protected void func_74185_a(float f, int i, int j) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73882_e.field_71446_o.func_110577_a(this.resource);
      int l = (this.field_73880_f - this.field_74194_b) / 2;
      int i1 = (this.field_73881_g - this.field_74195_c) / 2;
      this.func_73729_b(l, i1, 0, 0, this.field_74194_b, 6);
      if (!this.container.isAvailable()) {
         this.func_73729_b(l, i1 + 6, 0, 6, this.field_74194_b, 64);
         this.func_73729_b(l, i1 + 70, 0, 124, this.field_74194_b, 98);
         int x = this.field_74198_m + 30;
         int y = this.field_74197_n + 8;
         this.field_73886_k.func_78276_b(StatCollector.func_74838_a("bank.unlockCosts") + ":", x, y + 4, 4210752);
         this.drawItem(x + 90, y, this.currency, i, j);
      } else if (this.container.isUpgraded()) {
         this.func_73729_b(l, i1 + 60, 0, 60, this.field_74194_b, 162);
         this.func_73729_b(l, i1 + 6, 0, 60, this.field_74194_b, 64);
      } else if (this.container.canBeUpgraded()) {
         this.func_73729_b(l, i1 + 6, 0, 6, this.field_74194_b, 216);
         int x = this.field_74198_m + 30;
         int y = this.field_74197_n + 8;
         this.field_73886_k.func_78276_b(StatCollector.func_74838_a("bank.upgradeCosts") + ":", x, y + 4, 4210752);
         this.drawItem(x + 90, y, this.currency, i, j);
      } else {
         this.func_73729_b(l, i1 + 6, 0, 60, this.field_74194_b, 162);
      }

      if (this.maxSlots > 1) {
         for(int ii = 0; ii < this.maxSlots && this.availableSlots != ii; ++ii) {
            this.field_73886_k.func_78276_b("Tab " + (ii + 1), this.field_74198_m - 40, this.field_74197_n + 16 + ii * 24, 16777215);
         }
      }

      super.func_74185_a(f, i, j);
   }

   private void drawItem(int x, int y, ItemStack item, int mouseX, int mouseY) {
      if (item != null) {
         GL11.glEnable(32826);
         RenderHelper.func_74520_c();
         field_74196_a.func_77015_a(this.field_73886_k, this.field_73882_e.field_71446_o, item, x, y);
         field_74196_a.func_77021_b(this.field_73886_k, this.field_73882_e.field_71446_o, item, x, y);
         RenderHelper.func_74518_a();
         GL11.glDisable(32826);
         if (this.func_74188_c(x - this.field_74198_m, y - this.field_74197_n, 16, 16, mouseX, mouseY)) {
            this.func_74184_a(item, mouseX, mouseY);
         }

      }
   }

   public void save() {
   }

   public void setGuiData(NBTTagCompound compound) {
      this.maxSlots = compound.func_74762_e("MaxSlots");
      this.unlockedSlots = compound.func_74762_e("UnlockedSlots");
      if (compound.func_74764_b("Currency")) {
         this.currency = ItemStack.func_77949_a(compound.func_74775_l("Currency"));
      } else {
         this.currency = null;
      }

      if (this.container.currency != null) {
         this.container.currency.item = this.currency;
      }

      this.func_73866_w_();
   }
}

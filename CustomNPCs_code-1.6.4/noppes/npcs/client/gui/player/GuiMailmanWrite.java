package noppes.npcs.client.gui.player;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiMailmanWrite extends GuiScreen {
   private static final ResourceLocation bookGuiTextures = new ResourceLocation("textures/gui/book.png");
   private final NBTTagCompound itemstackBook;
   private int updateCount;
   private int bookImageWidth = 192;
   private int bookImageHeight = 192;
   private int bookTotalPages = 1;
   private int currPage;
   private NBTTagList bookPages;
   private noppes.npcs.client.gui.util.GuiButtonNextPage buttonNextPage;
   private noppes.npcs.client.gui.util.GuiButtonNextPage buttonPreviousPage;
   private boolean canEdit;
   private GuiScreen parent;

   public GuiMailmanWrite(GuiScreen parent, NBTTagCompound compound, boolean canEdit) {
      this.parent = parent;
      this.itemstackBook = compound;
      this.canEdit = canEdit;
      if (this.itemstackBook.func_74764_b("pages")) {
         this.bookPages = this.itemstackBook.func_74761_m("pages");
      }

      if (this.bookPages != null) {
         this.bookPages = (NBTTagList)this.bookPages.func_74737_b();
         this.bookTotalPages = this.bookPages.func_74745_c();
         if (this.bookTotalPages < 1) {
            this.bookTotalPages = 1;
         }
      } else {
         this.bookPages = new NBTTagList("pages");
         this.bookPages.func_74742_a(new NBTTagString("1", ""));
         this.bookTotalPages = 1;
      }

   }

   public void func_73876_c() {
      super.func_73876_c();
      ++this.updateCount;
   }

   public void func_73866_w_() {
      this.field_73887_h.clear();
      Keyboard.enableRepeatEvents(true);
      this.field_73887_h.add(new GuiButton(0, this.field_73880_f / 2 - 100, 4 + this.bookImageHeight, 98, 20, I18n.func_135053_a("gui.done")));
      int i = (this.field_73880_f - this.bookImageWidth) / 2;
      byte b0 = 2;
      this.field_73887_h.add(this.buttonNextPage = new noppes.npcs.client.gui.util.GuiButtonNextPage(1, i + 120, b0 + 154, true));
      this.field_73887_h.add(this.buttonPreviousPage = new noppes.npcs.client.gui.util.GuiButtonNextPage(2, i + 38, b0 + 154, false));
      this.updateButtons();
   }

   public void func_73874_b() {
      Keyboard.enableRepeatEvents(false);
   }

   private void updateButtons() {
      this.buttonNextPage.field_73748_h = this.currPage < this.bookTotalPages - 1 || this.canEdit;
      this.buttonPreviousPage.field_73748_h = this.currPage > 0;
   }

   protected void func_73875_a(GuiButton par1GuiButton) {
      if (par1GuiButton.field_73742_g) {
         if (par1GuiButton.field_73741_f == 0) {
            this.close();
         } else if (par1GuiButton.field_73741_f == 1) {
            if (this.currPage < this.bookTotalPages - 1) {
               ++this.currPage;
            } else if (this.canEdit) {
               this.addNewPage();
               if (this.currPage < this.bookTotalPages - 1) {
                  ++this.currPage;
               }
            }
         } else if (par1GuiButton.field_73741_f == 2 && this.currPage > 0) {
            --this.currPage;
         }

         this.updateButtons();
      }

   }

   private void addNewPage() {
      if (this.bookPages != null && this.bookPages.func_74745_c() < 50) {
         this.bookPages.func_74742_a(new NBTTagString("" + (this.bookTotalPages + 1), ""));
         ++this.bookTotalPages;
      }

   }

   protected void func_73869_a(char par1, int par2) {
      if (par2 == 1) {
         this.close();
      }

      if (this.canEdit) {
         this.keyTypedInBook(par1, par2);
      }

   }

   private void keyTypedInBook(char par1, int par2) {
      switch (par1) {
         case '\u0016':
            this.func_74160_b(GuiScreen.func_73870_l());
            return;
         default:
            switch (par2) {
               case 14:
                  String s = this.func_74158_i();
                  if (s.length() > 0) {
                     this.func_74159_a(s.substring(0, s.length() - 1));
                  }

                  return;
               case 28:
               case 156:
                  this.func_74160_b("\n");
                  return;
               default:
                  if (ChatAllowedCharacters.func_71566_a(par1)) {
                     this.func_74160_b(Character.toString(par1));
                  }

            }
      }
   }

   private String func_74158_i() {
      if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.func_74745_c()) {
         NBTTagString nbttagstring = (NBTTagString)this.bookPages.func_74743_b(this.currPage);
         return nbttagstring.toString();
      } else {
         return "";
      }
   }

   private void func_74159_a(String par1Str) {
      if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.func_74745_c()) {
         NBTTagString nbttagstring = (NBTTagString)this.bookPages.func_74743_b(this.currPage);
         nbttagstring.field_74751_a = par1Str;
      }

   }

   private void func_74160_b(String par1Str) {
      String s1 = this.func_74158_i();
      String s2 = s1 + par1Str;
      int i = this.field_73886_k.func_78267_b(s2 + "" + EnumChatFormatting.BLACK + "_", 118);
      if (i <= 118 && s2.length() < 256) {
         this.func_74159_a(s2);
      }

   }

   public void func_73863_a(int par1, int par2, float par3) {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_73882_e.func_110434_K().func_110577_a(bookGuiTextures);
      int k = (this.field_73880_f - this.bookImageWidth) / 2;
      byte b0 = 2;
      this.func_73729_b(k, b0, 0, 0, this.bookImageWidth, this.bookImageHeight);
      String s = String.format(I18n.func_135053_a("book.pageIndicator"), this.currPage + 1, this.bookTotalPages);
      String s1 = "";
      if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.func_74745_c()) {
         NBTTagString nbttagstring = (NBTTagString)this.bookPages.func_74743_b(this.currPage);
         s1 = nbttagstring.toString();
      }

      if (this.canEdit) {
         if (this.field_73886_k.func_78260_a()) {
            s1 = s1 + "_";
         } else if (this.updateCount / 6 % 2 == 0) {
            s1 = s1 + "" + EnumChatFormatting.BLACK + "_";
         } else {
            s1 = s1 + "" + EnumChatFormatting.GRAY + "_";
         }
      }

      int l = this.field_73886_k.func_78256_a(s);
      this.field_73886_k.func_78276_b(s, k - l + this.bookImageWidth - 44, b0 + 16, 0);
      this.field_73886_k.func_78279_b(s1, k + 36, b0 + 16 + 16, 116, 0);
      super.func_73863_a(par1, par2, par3);
   }

   public void close() {
      this.itemstackBook.func_74782_a("pages", this.bookPages);
      this.field_73882_e.func_71373_a(this.parent);
   }

   static ResourceLocation func_110404_g() {
      return bookGuiTextures;
   }
}

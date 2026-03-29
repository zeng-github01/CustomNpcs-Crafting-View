package noppes.npcs;

import java.util.Random;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.constants.EnumModelType;

public class DataDisplay {
   EntityNPCInterface npc;
   public String name;
   public boolean usingSkinUrl = false;
   public String skinUsername = "";
   public String texture = "customnpcs:textures/entity/humanmale/Steve.png";
   public String cloakTexture = "";
   public String glowTexture = "";
   public int visible = 0;
   public EnumModelType modelType;
   public int modelSize;
   public int showName;
   public int skinColor;
   public boolean NoLivingAnimation;

   public DataDisplay(EntityNPCInterface npc) {
      this.modelType = EnumModelType.HumanMale;
      this.modelSize = 5;
      this.showName = 0;
      this.skinColor = 16777215;
      this.NoLivingAnimation = false;
      this.npc = npc;
      String[] names = new String[]{"Noppes", "Noppes", "Noppes", "Noppes", "Atesson", "Rothcersul", "Achdranys", "Pegato", "Chald", "Gareld", "Nalworche", "Ineald", "Tia'kim", "Torerod", "Turturdar", "Ranler", "Dyntan", "Oldrake", "Gharis", "Elmn", "Tanal", "Waran-ess", "Ach-aldhat", "Athi", "Itageray", "Tasr", "Ightech", "Gakih", "Adkal", "Qua'an", "Sieq", "Urnp", "Rods", "Vorbani", "Smaik", "Fian", "Hir", "Ristai", "Kineth", "Naif", "Issraya", "Arisotura", "Honf", "Rilfom", "Estz", "Ghatroth", "Yosil", "Darage", "Aldny", "Tyltran", "Armos", "Loxiku", "Burhat", "Tinlt", "Ightyd", "Mia", "Ken", "Karla", "Lily", "Carina", "Daniel", "Slater", "Zidane", "Valentine", "Eirina", "Carnow", "Grave", "Shadow", "Drakken", "Kaoz", "Silk", "Drake", "Oldam", "Lynxx", "Lenyx", "Winter", "Seth", "Apolitho", "Amethyst", "Ankin", "Seinkan", "Ayumu", "Sakamoto", "Divina", "Div", "Magia", "Magnus", "Tiakono", "Ruin", "Hailinx", "Ethan", "Wate", "Carter", "William", "Brion", "Sparrow", "Basrrelen", "Gyaku", "Claire", "Crowfeather", "Blackwell", "Raven", "Farcri", "Lucas", "Bangheart", "Kamoku", "Kyoukan", "Blaze", "Benjamin", "Larianne", "Kakaragon", "Melancholy", "Epodyno", "Thanato", "Mika", "Dacks", "Ylander", "Neve", "Meadow", "Cuero", "Embrera", "Eldamore", "Faolan", "Chim", "Nasu", "Kathrine", "Ariel", "Arei", "Demytrix", "Kora", "Ava", "Larson", "Leonardo", "Wyrl", "Sakiama", "Lambton", "Kederath", "Malus", "Riplette", "Andern", "Ezall", "Lucien", "Droco", "Cray", "Tymen", "Zenix", "Entranger", "Saenorath", "Chris", "Christine", "Marble", "Mable", "Ross", "Rose", "Xalgan ", "Kennet"};
      this.name = names[(new Random()).nextInt(names.length)];
   }

   public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.func_74778_a("Name", this.name);
      nbttagcompound.func_74778_a("SkinUsername", this.skinUsername);
      nbttagcompound.func_74778_a("Texture", this.texture);
      nbttagcompound.func_74778_a("CloakTexture", this.cloakTexture);
      nbttagcompound.func_74778_a("GlowTexture", this.glowTexture);
      nbttagcompound.func_74757_a("UsingSkinUrl", this.usingSkinUrl);
      nbttagcompound.func_74768_a("ModelType", this.modelType.ordinal());
      nbttagcompound.func_74768_a("Size", this.modelSize);
      nbttagcompound.func_74768_a("ShowName", this.showName);
      nbttagcompound.func_74768_a("SkinColor", this.skinColor);
      nbttagcompound.func_74768_a("NpcVisible", this.visible);
      nbttagcompound.func_74757_a("NoLivingAnimation", this.NoLivingAnimation);
      return nbttagcompound;
   }

   public void readToNBT(NBTTagCompound nbttagcompound) {
      this.name = nbttagcompound.func_74779_i("Name");
      this.skinUsername = nbttagcompound.func_74779_i("SkinUsername");
      this.texture = nbttagcompound.func_74779_i("Texture");
      this.cloakTexture = nbttagcompound.func_74779_i("CloakTexture");
      this.glowTexture = nbttagcompound.func_74779_i("GlowTexture");
      this.usingSkinUrl = nbttagcompound.func_74767_n("UsingSkinUrl");
      this.modelType = EnumModelType.values()[nbttagcompound.func_74762_e("ModelType") % EnumModelType.values().length];
      this.modelSize = nbttagcompound.func_74762_e("Size");
      this.showName = nbttagcompound.func_74762_e("ShowName");
      this.skinColor = nbttagcompound.func_74762_e("SkinColor");
      this.visible = nbttagcompound.func_74762_e("NpcVisible");
      this.NoLivingAnimation = nbttagcompound.func_74767_n("NoLivingAnimation");
   }

   public boolean showName() {
      if (this.npc.isKilled()) {
         return false;
      } else {
         return this.showName == 0 || this.showName == 2 && this.npc.isAttacking();
      }
   }
}

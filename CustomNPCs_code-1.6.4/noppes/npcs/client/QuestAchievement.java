package noppes.npcs.client;

import net.minecraft.item.Item;
import net.minecraft.stats.Achievement;
import noppes.npcs.CustomItems;

public class QuestAchievement extends Achievement {
   private String description;

   public QuestAchievement(String par2Str, String description) {
      super(-1, par2Str, 0, 0, CustomItems.letter == null ? Item.field_77759_aK : CustomItems.letter, (Achievement)null);
      this.description = description;
   }

   public String func_75970_i() {
      return this.field_75978_a.substring(12);
   }

   public String func_75989_e() {
      return this.description;
   }
}

package noppes.npcs.constants;

import java.util.ArrayList;

public enum EnumMovingType {
   Standing("ai.standing"),
   Wandering("ai.wandering"),
   MovingPath("ai.movingpath");

   String name;

   private EnumMovingType(String name) {
      this.name = name;
   }

   public static String[] names() {
      ArrayList<String> list = new ArrayList();

      for(EnumMovingType e : values()) {
         list.add(e.name);
      }

      return (String[])list.toArray(new String[list.size()]);
   }
}

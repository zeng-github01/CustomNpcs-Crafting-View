package noppes.npcs.constants;

import java.util.ArrayList;

public enum EnumNavType {
   Default("aitactics.rush"),
   Dodge("aitactics.stagger"),
   Surround("aitactics.orbit"),
   Ambush("aitactics.ambush"),
   Stalk("aitactics.stalk");

   String name;

   private EnumNavType(String name) {
      this.name = name;
   }

   public static String[] names() {
      ArrayList<String> list = new ArrayList();

      for(EnumNavType e : values()) {
         list.add(e.name);
      }

      return (String[])list.toArray(new String[list.size()]);
   }
}

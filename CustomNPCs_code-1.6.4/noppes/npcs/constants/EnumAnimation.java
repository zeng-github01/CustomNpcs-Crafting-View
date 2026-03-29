package noppes.npcs.constants;

public enum EnumAnimation {
   NONE,
   SITTING,
   LYING,
   SNEAKING,
   DANCING,
   Aiming,
   CRAWLING,
   HUG,
   CRY,
   WAVING,
   BOW;

   public int getWalkingAnimation() {
      if (this == SNEAKING) {
         return 1;
      } else if (this == Aiming) {
         return 2;
      } else {
         return this == DANCING ? 3 : 0;
      }
   }
}

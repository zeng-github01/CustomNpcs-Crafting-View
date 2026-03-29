package noppes.npcs.constants;

public enum EnumAnimationType {
   Normal,
   Sitting,
   Lying,
   Sneaking,
   Dancing,
   Aiming;

   public int getWalkingAnimation() {
      if (this == Sneaking) {
         return 1;
      } else if (this == Aiming) {
         return 2;
      } else {
         return this == Dancing ? 3 : 0;
      }
   }
}

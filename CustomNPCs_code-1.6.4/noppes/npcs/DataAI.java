package noppes.npcs;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.constants.EnumAnimation;
import noppes.npcs.constants.EnumMovingType;
import noppes.npcs.constants.EnumNavType;
import noppes.npcs.constants.EnumStandingType;

public class DataAI {
   private EntityNPCInterface npc;
   public int onAttack = 0;
   public int doorInteract = 2;
   public int findShelter = 2;
   public int distanceToMelee = 4;
   public boolean canSwim = true;
   public boolean reactsToFire = false;
   public boolean avoidsWater = false;
   public boolean avoidsSun = false;
   public boolean returnToStart = true;
   public boolean directLOS = true;
   public boolean canSleep = false;
   public boolean canLeap = false;
   public boolean canFireIndirect = false;
   public boolean canSprint = false;
   public EnumNavType tacticalVariant;
   public int useRangeMelee;
   public int tacticalRadius;
   public EnumAnimation animationType;
   public EnumStandingType standingType;
   public EnumMovingType movingType;
   public int orientation;
   public float bodyOffsetX;
   public float bodyOffsetY;
   public float bodyOffsetZ;
   public int walkingRange;
   private List movingPath;
   protected int movingPos;
   public int movingPattern;
   public boolean movingPause;

   public DataAI(EntityNPCInterface npc) {
      this.tacticalVariant = EnumNavType.Default;
      this.useRangeMelee = 0;
      this.tacticalRadius = 8;
      this.animationType = EnumAnimation.NONE;
      this.standingType = EnumStandingType.RotateBody;
      this.movingType = EnumMovingType.Standing;
      this.orientation = 0;
      this.bodyOffsetX = 5.0F;
      this.bodyOffsetY = 5.0F;
      this.bodyOffsetZ = 5.0F;
      this.walkingRange = 5;
      this.movingPath = new ArrayList();
      this.movingPos = 0;
      this.movingPattern = 0;
      this.movingPause = true;
      this.npc = npc;
   }

   public void readToNBT(NBTTagCompound compound) {
      this.canSwim = compound.func_74767_n("CanSwim");
      this.reactsToFire = compound.func_74767_n("ReactsToFire");
      this.avoidsWater = compound.func_74767_n("AvoidsWater");
      this.avoidsSun = compound.func_74767_n("AvoidsSun");
      this.returnToStart = compound.func_74767_n("ReturnToStart");
      this.onAttack = compound.func_74762_e("OnAttack");
      this.doorInteract = compound.func_74762_e("DoorInteract");
      this.findShelter = compound.func_74762_e("FindShelter");
      this.directLOS = compound.func_74767_n("DirectLOS");
      this.canSleep = compound.func_74767_n("CanSleep");
      this.canLeap = compound.func_74767_n("CanLeap");
      this.canSprint = compound.func_74767_n("CanSprint");
      this.canFireIndirect = compound.func_74767_n("CanFireIndirect");
      this.useRangeMelee = compound.func_74762_e("RangeAndMelee");
      this.distanceToMelee = compound.func_74762_e("DistanceToMelee");
      this.tacticalRadius = compound.func_74762_e("TacticalRadius");
      this.movingPause = compound.func_74767_n("MovingPause");
      this.animationType = EnumAnimation.values()[compound.func_74762_e("MoveState") % EnumAnimation.values().length];
      this.standingType = EnumStandingType.values()[compound.func_74762_e("StandingState") % EnumStandingType.values().length];
      this.movingType = EnumMovingType.values()[compound.func_74762_e("MovingState") % EnumMovingType.values().length];
      this.tacticalVariant = EnumNavType.values()[compound.func_74762_e("TacticalVariant") % EnumNavType.values().length];
      this.orientation = compound.func_74762_e("Orientation");
      this.bodyOffsetY = compound.func_74760_g("PositionOffsetY");
      this.bodyOffsetZ = compound.func_74760_g("PositionOffsetZ");
      this.bodyOffsetX = compound.func_74760_g("PositionOffsetX");
      this.walkingRange = compound.func_74762_e("WalkingRange");
      this.setMovingPath(NBTTags.getIntegerArraySet(compound.func_74761_m("MovingPath")));
      this.movingPos = compound.func_74762_e("MovingPos");
      this.movingPattern = compound.func_74762_e("MovingPatern");
      this.npc.updateTasks();
   }

   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      compound.func_74757_a("CanSwim", this.canSwim);
      compound.func_74757_a("ReactsToFire", this.reactsToFire);
      compound.func_74757_a("AvoidsWater", this.avoidsWater);
      compound.func_74757_a("AvoidsSun", this.avoidsSun);
      compound.func_74757_a("ReturnToStart", this.returnToStart);
      compound.func_74768_a("OnAttack", this.onAttack);
      compound.func_74768_a("DoorInteract", this.doorInteract);
      compound.func_74768_a("FindShelter", this.findShelter);
      compound.func_74757_a("DirectLOS", this.directLOS);
      compound.func_74757_a("CanSleep", this.canSleep);
      compound.func_74757_a("CanLeap", this.canLeap);
      compound.func_74757_a("CanSprint", this.canSprint);
      compound.func_74757_a("CanFireIndirect", this.canFireIndirect);
      compound.func_74768_a("RangeAndMelee", this.useRangeMelee);
      compound.func_74768_a("DistanceToMelee", this.distanceToMelee);
      compound.func_74768_a("TacticalRadius", this.tacticalRadius);
      compound.func_74757_a("MovingPause", this.movingPause);
      compound.func_74768_a("MoveState", this.animationType.ordinal());
      compound.func_74768_a("StandingState", this.standingType.ordinal());
      compound.func_74768_a("MovingState", this.movingType.ordinal());
      compound.func_74768_a("TacticalVariant", this.tacticalVariant.ordinal());
      compound.func_74768_a("Orientation", this.orientation);
      compound.func_74776_a("PositionOffsetX", this.bodyOffsetX);
      compound.func_74776_a("PositionOffsetY", this.bodyOffsetY);
      compound.func_74776_a("PositionOffsetZ", this.bodyOffsetZ);
      compound.func_74768_a("WalkingRange", this.walkingRange);
      compound.func_74782_a("MovingPath", NBTTags.nbtIntegerArraySet(this.movingPath));
      compound.func_74768_a("MovingPos", this.movingPos);
      compound.func_74768_a("MovingPatern", this.movingPattern);
      this.npc.setAvoidWater(this.avoidsWater);
      return compound;
   }

   public List getMovingPath() {
      if (this.movingPath.isEmpty()) {
         this.movingPath.add(this.npc.startPos);
      }

      return this.movingPath;
   }

   public void setMovingPath(List list) {
      this.movingPath = list;
      if (!this.movingPath.isEmpty()) {
         this.npc.startPos = (int[])this.movingPath.get(0);
      }

   }

   public int[] getCurrentMovingPath() {
      List<int[]> list = this.getMovingPath();
      if (list.size() == 1) {
         this.movingPos = 0;
      } else if (this.movingPos >= list.size()) {
         if (this.movingPattern == 0) {
            this.movingPos = 0;
         } else {
            int size = list.size() * 2 - 2;
            if (this.movingPos >= size) {
               this.movingPos = 0;
            } else if (this.movingPos >= list.size()) {
               return (int[])list.get(list.size() - this.movingPos % list.size() - 2);
            }
         }
      }

      return (int[])list.get(this.movingPos);
   }

   public void incrementMovingPath() {
      List<int[]> list = this.getMovingPath();
      if (list.size() == 1) {
         this.movingPos = 0;
      } else if (this.movingPattern == 0) {
         ++this.movingPos;
         this.movingPos %= list.size();
      } else if (this.movingPattern == 1) {
         ++this.movingPos;
         int size = list.size() * 2 - 2;
         this.movingPos %= size;
      }

   }

   public void decreaseMovingPath() {
      List<int[]> list = this.getMovingPath();
      if (list.size() == 1) {
         this.movingPos = 0;
      } else if (this.movingPattern == 0) {
         --this.movingPos;
         if (this.movingPos < 0) {
            this.movingPos += list.size();
         }
      } else if (this.movingPattern == 1) {
         --this.movingPos;
         if (this.movingPos < 0) {
            int size = list.size() * 2 - 2;
            this.movingPos += size;
         }
      }

   }
}

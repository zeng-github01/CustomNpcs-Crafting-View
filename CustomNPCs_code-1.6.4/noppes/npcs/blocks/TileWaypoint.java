package noppes.npcs.blocks;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import noppes.npcs.constants.EnumQuestType;
import noppes.npcs.controllers.PlayerDataController;
import noppes.npcs.controllers.PlayerQuestData;
import noppes.npcs.controllers.QuestData;
import noppes.npcs.quests.QuestLocation;

public class TileWaypoint extends TileEntity {
   public String name = "";
   private int ticks = 10;
   private List recentlyChecked = new ArrayList();
   private List toCheck;
   public int range = 10;

   public void func_70316_g() {
      if (!this.field_70331_k.field_72995_K) {
         --this.ticks;
         if (this.ticks <= 0) {
            this.ticks = 10;
            this.toCheck = this.getPlayerList(this.range, this.range, this.range);
            this.toCheck.removeAll(this.recentlyChecked);
            List<EntityPlayer> listMax = this.getPlayerList(this.range + 10, this.range + 10, this.range + 10);
            this.recentlyChecked.retainAll(listMax);
            this.recentlyChecked.addAll(this.toCheck);
            if (!this.toCheck.isEmpty()) {
               for(EntityPlayer player : this.toCheck) {
                  PlayerQuestData playerdata = PlayerDataController.instance.getPlayerData(player).questData;

                  for(QuestData data : playerdata.activeQuests.values()) {
                     if (data.quest.type == EnumQuestType.Location) {
                        QuestLocation quest = (QuestLocation)data.quest.questInterface;
                        quest.setFound(data, this.name);
                        if (!playerdata.checkQuestCompletion(player, EnumQuestType.Location)) {
                           player.func_71035_c(this.name + " " + StatCollector.func_74838_a("quest.found"));
                        }
                     }
                  }
               }

            }
         }
      }
   }

   private List getPlayerList(int x, int y, int z) {
      return this.field_70331_k.func_72872_a(EntityPlayer.class, AxisAlignedBB.func_72330_a((double)this.field_70329_l, (double)this.field_70330_m, (double)this.field_70327_n, (double)(this.field_70329_l + 1), (double)(this.field_70330_m + 1), (double)(this.field_70327_n + 1)).func_72314_b((double)x, (double)y, (double)z));
   }

   public void func_70307_a(NBTTagCompound compound) {
      super.func_70307_a(compound);
      this.name = compound.func_74779_i("LocationName");
      this.range = compound.func_74762_e("LocationRange");
      if (this.range < 2) {
         this.range = 2;
      }

   }

   public void func_70310_b(NBTTagCompound compound) {
      super.func_70310_b(compound);
      if (!this.name.isEmpty()) {
         compound.func_74778_a("LocationName", this.name);
      }

      compound.func_74768_a("LocationRange", this.range);
   }
}

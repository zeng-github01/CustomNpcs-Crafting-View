package noppes.npcs.events;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.constants.EnumPacketType;
import noppes.npcs.controllers.RecipeCarpentry;
import noppes.npcs.controllers.RecipeController;

public class CraftbenchEvent {
   @ForgeSubscribe
   public void invoke(PlayerInteractEvent event) {
      EntityPlayer player = event.entityPlayer;
      int blockId = player.field_70170_p.func_72798_a(event.x, event.y, event.z);
      if (blockId == Block.field_72060_ay.field_71990_ca && !player.field_70170_p.field_72995_K) {
         RecipeController controller = RecipeController.instance;
         NBTTagList list = new NBTTagList();

         for(RecipeCarpentry recipe : controller.globalRecipes.values()) {
            list.func_74742_a(recipe.writeNBT());
         }

         NBTTagCompound compound = new NBTTagCompound();
         compound.func_74782_a("recipes", list);
         NoppesUtilServer.sendData(player, EnumPacketType.SyncRecipes, compound);
      }
   }
}

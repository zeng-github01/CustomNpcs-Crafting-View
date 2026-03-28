package noppes.npcs.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import noppes.npcs.items.ItemShield;

public class PlayerGetHurtEvent {
   @ForgeSubscribe
   public void invoke(LivingHurtEvent event) {
      if (event.entityLiving instanceof EntityPlayer) {
         this.playerhurt((EntityPlayer)event.entityLiving, event);
      }

   }

   private void playerhurt(EntityPlayer player, LivingHurtEvent hurt) {
      if (!hurt.source.func_76363_c() && !hurt.source.func_76347_k()) {
         if (player.func_70632_aY()) {
            ItemStack item = player.func_71045_bC();
            if (item != null && item.func_77973_b() instanceof ItemShield) {
               float damage = (float)item.func_77960_j() + hurt.ammount;
               item.func_77972_a((int)hurt.ammount, player);
               if (damage > (float)item.func_77958_k()) {
                  hurt.ammount = damage - (float)item.func_77958_k();
               } else {
                  hurt.ammount = 0.0F;
                  hurt.setCanceled(true);
               }

            }
         }
      }
   }
}

package noppes.npcs.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatMessageComponent;
import noppes.npcs.EntityNPCInterface;

public class CommandSlay extends CommandBase {
   private Map map = new HashMap();

   public CommandSlay() {
      HashMap<String, Class<?>> list = new HashMap(EntityList.field_75625_b);

      for(String name : list.keySet()) {
         Class<?> cls = (Class)list.get(name);
         if (!EntityNPCInterface.class.isAssignableFrom(cls) && EntityLivingBase.class.isAssignableFrom(cls)) {
            this.map.put(name.toLowerCase(), list.get(name));
         }
      }

      this.map.remove("monster");
      this.map.remove("mob");
      this.map.put("all", EntityLivingBase.class);
      this.map.put("mobs", EntityMob.class);
      this.map.put("animals", EntityAnimal.class);
      this.map.put("items", EntityItem.class);
      this.map.put("xporbs", EntityXPOrb.class);
   }

   public String func_71517_b() {
      return "slay";
   }

   public String func_71518_a(ICommandSender icommandsender) {
      return "/slay all, /slay mobs, /slay animals, /slay <entityname>";
   }

   public void func_71515_b(ICommandSender icommandsender, String[] vars) {
      if (!(icommandsender instanceof EntityPlayer)) {
         icommandsender.func_70006_a(ChatMessageComponent.func_111066_d("Can only be used by players"));
      } else if (vars.length == 0) {
         icommandsender.func_70006_a(ChatMessageComponent.func_111066_d("No entities given"));
      } else {
         EntityPlayer player = (EntityPlayer)icommandsender;
         ArrayList<Class<?>> toDelete = new ArrayList();

         for(String delete : vars) {
            Class<?> cls = (Class)this.map.get(delete.toLowerCase());
            if (cls != null) {
               toDelete.add(cls);
            }

            if (delete.equals("mobs")) {
               toDelete.add(EntityGhast.class);
               toDelete.add(EntityDragon.class);
            }
         }

         int count = 0;
         AxisAlignedBB range = player.field_70121_D.func_72314_b((double)120.0F, (double)120.0F, (double)120.0F);

         for(Entity entity : player.field_70170_p.func_72872_a(EntityLivingBase.class, range)) {
            if (!(entity instanceof EntityPlayer) && (!(entity instanceof EntityTameable) || !((EntityTameable)entity).func_70909_n()) && !(entity instanceof EntityNPCInterface) && this.delete(entity, toDelete)) {
               ++count;
            }
         }

         if (toDelete.contains(EntityXPOrb.class)) {
            for(Entity entity : player.field_70170_p.func_72872_a(EntityXPOrb.class, range)) {
               entity.field_70128_L = true;
               ++count;
            }
         }

         if (toDelete.contains(EntityItem.class)) {
            for(Entity entity : player.field_70170_p.func_72872_a(EntityItem.class, range)) {
               entity.field_70128_L = true;
               ++count;
            }
         }

         icommandsender.func_70006_a(ChatMessageComponent.func_111066_d(count + " entities deleted"));
      }
   }

   private boolean delete(Entity entity, ArrayList toDelete) {
      for(Class delete : toDelete) {
         if ((delete != EntityAnimal.class || !(entity instanceof EntityHorse)) && delete.isAssignableFrom(entity.getClass())) {
            entity.field_70128_L = true;
            return true;
         }
      }

      return false;
   }

   public List func_71516_a(ICommandSender par1ICommandSender, String[] vars) {
      List<String> list = new ArrayList(this.map.keySet());
      Collections.sort(list);
      return func_71531_a(vars, list);
   }
}

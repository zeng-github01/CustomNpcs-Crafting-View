package noppes.npcs;

import java.util.List;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import noppes.npcs.controllers.Line;
import noppes.npcs.controllers.Lines;

public class VersionCompatibility {
   public static int ModRev = 12;

   public static void CheckNpcCompatibility(EntityNPCInterface npc, NBTTagCompound compound) {
      if (npc.npcVersion != ModRev) {
         CompatabilityFix(compound, npc.advanced.writeToNBT(new NBTTagCompound()));
         CompatabilityFix(compound, npc.ai.writeToNBT(new NBTTagCompound()));
         CompatabilityFix(compound, npc.stats.writeToNBT(new NBTTagCompound()));
         CompatabilityFix(compound, npc.display.writeToNBT(new NBTTagCompound()));
         CompatabilityFix(compound, npc.inventory.writeEntityToNBT(new NBTTagCompound()));
         if (npc.npcVersion < 5) {
            String texture = compound.func_74779_i("Texture");
            texture = texture.replace("/mob/customnpcs/", "customnpcs:textures/entity/");
            texture = texture.replace("/mob/", "customnpcs:textures/entity/");
            compound.func_74778_a("Texture", texture);
         }

         if (npc.npcVersion < 6 && compound.func_74781_a("NpcInteractLines") instanceof NBTTagList) {
            List<String> interactLines = NBTTags.getStringList(compound.func_74761_m("NpcInteractLines"));
            Lines lines = new Lines();

            for(int i = 0; i < interactLines.size(); ++i) {
               Line line = new Line();
               line.text = (String)interactLines.toArray()[i];
               lines.lines.put(i, line);
            }

            compound.func_74766_a("NpcInteractLines", lines.writeToNBT());
            List<String> worldLines = NBTTags.getStringList(compound.func_74761_m("NpcLines"));
            lines = new Lines();

            for(int i = 0; i < worldLines.size(); ++i) {
               Line line = new Line();
               line.text = (String)worldLines.toArray()[i];
               lines.lines.put(i, line);
            }

            compound.func_74766_a("NpcLines", lines.writeToNBT());
            List<String> attackLines = NBTTags.getStringList(compound.func_74761_m("NpcAttackLines"));
            lines = new Lines();

            for(int i = 0; i < attackLines.size(); ++i) {
               Line line = new Line();
               line.text = (String)attackLines.toArray()[i];
               lines.lines.put(i, line);
            }

            compound.func_74766_a("NpcAttackLines", lines.writeToNBT());
            List<String> killedLines = NBTTags.getStringList(compound.func_74761_m("NpcKilledLines"));
            lines = new Lines();

            for(int i = 0; i < killedLines.size(); ++i) {
               Line line = new Line();
               line.text = (String)killedLines.toArray()[i];
               lines.lines.put(i, line);
            }

            compound.func_74766_a("NpcKilledLines", lines.writeToNBT());
         }

         npc.npcVersion = ModRev;
      }
   }

   public static void CheckAvailabilityCompatibility(ICompatibilty compatibilty, NBTTagCompound compound) {
      if (compatibilty.getVersion() != ModRev) {
         CompatabilityFix(compound, compatibilty.writeToNBT(new NBTTagCompound()));
         compatibilty.setVersion(ModRev);
      }
   }

   public static void CompatabilityFix(NBTTagCompound compound, NBTTagCompound check) {
      for(NBTBase nbt : check.func_74758_c()) {
         if (!compound.func_74764_b(nbt.func_74740_e())) {
            compound.func_74782_a(nbt.func_74740_e(), nbt);
         } else if (nbt instanceof NBTTagCompound && compound.func_74781_a(nbt.func_74740_e()) instanceof NBTTagCompound) {
            CompatabilityFix(compound.func_74775_l(nbt.func_74740_e()), (NBTTagCompound)nbt);
         }
      }

   }
}

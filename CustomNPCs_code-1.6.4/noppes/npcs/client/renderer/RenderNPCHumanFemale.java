package noppes.npcs.client.renderer;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.EntityLiving;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.client.model.ModelNPCFemale;

public class RenderNPCHumanFemale extends RenderNPCHumanMale {
   public RenderNPCHumanFemale(ModelNPCFemale mainmodel, ModelNPCFemale armorChest, ModelNPCFemale armor) {
      super(mainmodel, armorChest, armor);
   }

   protected int setArmorModel(EntityNPCInterface entityplayer, int i, float f) {
      ((ModelNPCFemale)this.field_77045_g).Breasts.field_78806_j = entityplayer.inventory.armorItemInSlot(1) == null;

      for(Object child : ((ModelNPCFemale)this.field_77045_g).Breasts.field_78805_m) {
         ((ModelRenderer)child).field_78806_j = entityplayer.inventory.armorItemInSlot(1) == null;
      }

      return super.func_130006_a(entityplayer, i, f);
   }

   protected int shouldRenderPass(EntityLiving entityliving, int i, float f) {
      return this.setArmorModel((EntityNPCInterface)entityliving, i, f);
   }
}

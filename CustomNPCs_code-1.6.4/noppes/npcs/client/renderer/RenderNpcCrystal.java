package noppes.npcs.client.renderer;

import net.minecraft.entity.Entity;
import noppes.npcs.client.model.ModelNpcCrystal;
import noppes.npcs.entity.EntityNpcCrystal;

public class RenderNpcCrystal extends RenderNPCInterface {
   ModelNpcCrystal mainmodel;

   public RenderNpcCrystal(ModelNpcCrystal model) {
      super(model, 0.0F);
      this.mainmodel = model;
   }

   public void func_41035_a(EntityNpcCrystal par1EntityEnderCrystal, double par2, double par4, double par6, float par8, float par9) {
      super.func_77031_a(par1EntityEnderCrystal, par2, par4, par6, par8, par9);
   }

   public void func_76986_a(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
      this.func_41035_a((EntityNpcCrystal)par1Entity, par2, par4, par6, par8, par9);
   }
}

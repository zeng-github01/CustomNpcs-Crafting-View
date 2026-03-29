package noppes.npcs.client.renderer;

import net.minecraft.client.model.ModelVillager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import noppes.npcs.entity.EntityNPCVillager;

public class RenderNpcCreeper extends RenderNPCInterface {
   public RenderNpcCreeper() {
      super(new ModelVillager(0.0F), 0.5F);
   }

   public void renderVillager(EntityNPCVillager entityvillager, double d, double d1, double d2, float f, float f1) {
      super.func_77031_a(entityvillager, d, d1, d2, f, f1);
   }

   protected void func_40290_a(EntityNPCVillager entityvillager, double d, double d1, double d2) {
   }

   protected void func_40291_a(EntityNPCVillager entityvillager, float f) {
      super.func_77029_c(entityvillager, f);
   }

   protected void renderEquippedItems(EntityLiving entityliving, float f) {
      this.func_40291_a((EntityNPCVillager)entityliving, f);
   }

   public void func_77031_a(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
      this.renderVillager((EntityNPCVillager)entityliving, d, d1, d2, f, f1);
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      this.renderVillager((EntityNPCVillager)entity, d, d1, d2, f, f1);
   }
}

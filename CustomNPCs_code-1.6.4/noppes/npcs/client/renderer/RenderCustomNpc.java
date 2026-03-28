package noppes.npcs.client.renderer;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import noppes.npcs.EntityCustomNpc;
import noppes.npcs.client.model.ModelMPM;

public class RenderCustomNpc extends RenderNPCHumanMale {
   public RenderCustomNpc() {
      super(new ModelMPM(0.0F), new ModelMPM(0.5F), new ModelMPM(1.0F));
   }

   public void func_77031_a(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
      this.renderNPC((EntityCustomNpc)entityliving, d, d1, d2, f, f1);
   }

   public void func_76986_a(Entity entity, double d, double d1, double d2, float f, float f1) {
      this.renderNPC((EntityCustomNpc)entity, d, d1, d2, f, f1);
   }

   private void renderNPC(EntityCustomNpc npc, double d, double d1, double d2, float f, float f1) {
      if (npc.renderEntity == null) {
         super.func_77031_a(npc, d, d1, d2, f, f1);
      } else {
         Render render = RenderManager.field_78727_a.func_78713_a(npc.renderEntity);
      }

   }
}

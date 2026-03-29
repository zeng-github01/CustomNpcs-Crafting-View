package noppes.npcs.client.model.part;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.EntityCustomNpc;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.model.ModelMPM;
import noppes.npcs.client.model.util.Model2DRenderer;
import noppes.npcs.client.model.util.ModelPartInterface;

public class ModelWings extends ModelPartInterface {
   private Model2DRenderer lWing;
   private Model2DRenderer rWing;

   public ModelWings(ModelMPM base) {
      super(base);
      this.lWing = new Model2DRenderer(base, 56.0F, 16.0F, 8, 16, 64.0F, 32.0F);
      this.lWing.field_78809_i = true;
      this.lWing.func_78793_a(2.0F, 4.0F, 2.0F);
      this.lWing.setRotationOffset(-16.0F, -12.0F);
      this.setRotation(this.lWing, 0.7141593F, (-(float)Math.PI / 6F), -0.5090659F);
      this.func_78792_a(this.lWing);
      this.rWing = new Model2DRenderer(base, 56.0F, 16.0F, 8, 16, 64.0F, 32.0F);
      this.rWing.func_78793_a(-2.0F, 4.0F, 2.0F);
      this.rWing.setRotationOffset(-16.0F, -12.0F);
      this.setRotation(this.rWing, 0.7141593F, ((float)Math.PI / 6F), 0.5090659F);
      this.func_78792_a(this.rWing);
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
      this.rWing.field_78795_f = 0.7141593F;
      this.rWing.field_78808_h = 0.5090659F;
      this.lWing.field_78795_f = 0.7141593F;
      this.lWing.field_78808_h = -0.5090659F;
      float motion = Math.abs(MathHelper.func_76126_a(par1 * 0.033F + (float)Math.PI) * 0.4F) * par2;
      if (entity.field_70122_E && !((double)motion > 0.01)) {
         Model2DRenderer var14 = this.lWing;
         var14.field_78808_h += MathHelper.func_76134_b(par3 * 0.09F) * 0.05F + 0.05F;
         var14 = this.rWing;
         var14.field_78808_h -= MathHelper.func_76134_b(par3 * 0.09F) * 0.05F + 0.05F;
         var14 = this.lWing;
         var14.field_78795_f += MathHelper.func_76126_a(par3 * 0.067F) * 0.05F;
         var14 = this.rWing;
         var14.field_78795_f += MathHelper.func_76126_a(par3 * 0.067F) * 0.05F;
      } else {
         float speed = 0.55F + 0.5F * motion;
         float y = MathHelper.func_76126_a(par3 * 0.67F);
         Model2DRenderer var10000 = this.rWing;
         var10000.field_78808_h += y * 0.5F * speed;
         var10000 = this.rWing;
         var10000.field_78795_f += y * 0.5F * speed;
         var10000 = this.lWing;
         var10000.field_78808_h -= y * 0.5F * speed;
         var10000 = this.lWing;
         var10000.field_78795_f += y * 0.5F * speed;
      }

   }

   public void initData(EntityCustomNpc data) {
      ModelPartData config = data.getPartData("wings");
      if (config == null) {
         this.field_78807_k = true;
      } else {
         this.color = config.color;
         this.field_78807_k = false;
         if (!config.playerTexture) {
            this.location = (ResourceLocation)config.getResource();
         } else {
            this.location = null;
         }

      }
   }
}

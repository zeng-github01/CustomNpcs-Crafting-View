package noppes.npcs.client.model.part;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.EntityCustomNpc;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.model.ModelMPM;
import noppes.npcs.client.model.util.Model2DRenderer;
import noppes.npcs.client.model.util.ModelPartInterface;

public class ModelHair extends ModelPartInterface {
   private Model2DRenderer model;

   public ModelHair(ModelMPM base) {
      super(base);
      this.model = new Model2DRenderer(base, 56.0F, 20.0F, 8, 12, 64.0F, 32.0F);
      this.model.func_78793_a(-4.0F, 12.0F, 3.0F);
      this.model.setScale(0.75F);
      this.func_78792_a(this.model);
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
      ModelRenderer parent = this.base.bipedHead;
      if (parent.field_78795_f < 0.0F) {
         this.field_78795_f = -parent.field_78795_f * 1.2F;
         if (parent.field_78795_f > -1.0F) {
            this.field_78797_d = -parent.field_78795_f * 1.5F;
            this.field_78798_e = -parent.field_78795_f * 1.5F;
         }
      } else {
         this.field_78795_f = 0.0F;
         this.field_78797_d = 0.0F;
         this.field_78798_e = 0.0F;
      }

   }

   public void initData(EntityCustomNpc data) {
      ModelPartData config = data.getPartData("hair");
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

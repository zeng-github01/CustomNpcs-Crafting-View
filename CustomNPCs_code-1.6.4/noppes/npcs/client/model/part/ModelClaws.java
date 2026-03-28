package noppes.npcs.client.model.part;

import net.minecraft.util.ResourceLocation;
import noppes.npcs.EntityCustomNpc;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.model.ModelMPM;
import noppes.npcs.client.model.util.Model2DRenderer;
import noppes.npcs.client.model.util.ModelPartInterface;

public class ModelClaws extends ModelPartInterface {
   private Model2DRenderer model;
   private boolean isRight = false;

   public ModelClaws(ModelMPM base, boolean isRight) {
      super(base);
      this.isRight = isRight;
      this.model = new Model2DRenderer(base, 0.0F, 16.0F, 4, 4, 64.0F, 32.0F);
      if (isRight) {
         this.model.func_78793_a(-2.0F, 14.0F, -2.0F);
      } else {
         this.model.func_78793_a(3.0F, 14.0F, -2.0F);
      }

      this.model.field_78796_g = (-(float)Math.PI / 2F);
      this.model.setScale(0.25F);
      this.func_78792_a(this.model);
   }

   public void initData(EntityCustomNpc data) {
      ModelPartData config = data.getPartData("claws");
      if (config != null && (!this.isRight || config.type != 1) && (this.isRight || config.type != 2)) {
         this.color = config.color;
         this.field_78807_k = false;
         if (!config.playerTexture) {
            this.location = (ResourceLocation)config.getResource();
         } else {
            this.location = null;
         }

      } else {
         this.field_78807_k = true;
      }
   }
}

package noppes.npcs.client.model.part;

import net.minecraft.util.ResourceLocation;
import noppes.npcs.EntityCustomNpc;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.model.ModelMPM;
import noppes.npcs.client.model.util.Model2DRenderer;
import noppes.npcs.client.model.util.ModelPartInterface;

public class ModelFin extends ModelPartInterface {
   private Model2DRenderer model;

   public ModelFin(ModelMPM base) {
      super(base);
      this.model = new Model2DRenderer(base, 56.0F, 20.0F, 8, 12, 64.0F, 32.0F);
      this.model.func_78793_a(-0.5F, 12.0F, 10.0F);
      this.model.setScale(0.74F);
      this.model.field_78796_g = ((float)Math.PI / 2F);
      this.func_78792_a(this.model);
   }

   public void initData(EntityCustomNpc data) {
      ModelPartData config = data.getPartData("fin");
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

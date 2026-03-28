package noppes.npcs.client.model.part;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.EntityCustomNpc;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.model.ModelMPM;
import noppes.npcs.client.model.util.Model2DRenderer;
import noppes.npcs.client.model.util.ModelPartInterface;

public class ModelMohawk extends ModelPartInterface {
   private Model2DRenderer model;

   public ModelMohawk(ModelMPM base) {
      super(base);
      this.model = new Model2DRenderer(base, 0, 0, 13, 13);
      this.model.func_78793_a(-0.5F, 0.0F, 9.0F);
      this.setRotation(this.model, 0.0F, ((float)Math.PI / 2F), 0.0F);
      this.model.setScale(0.825F);
      this.func_78792_a(this.model);
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
   }

   public void initData(EntityCustomNpc data) {
      ModelPartData config = data.getPartData("mohawk");
      if (config == null) {
         this.field_78807_k = true;
      } else {
         this.color = config.color;
         this.field_78807_k = false;
         this.location = (ResourceLocation)config.getResource();
      }
   }
}

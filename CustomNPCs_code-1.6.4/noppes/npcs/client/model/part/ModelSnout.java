package noppes.npcs.client.model.part;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.EntityCustomNpc;
import noppes.npcs.ModelPartData;
import noppes.npcs.client.model.ModelMPM;
import noppes.npcs.client.model.util.ModelPartInterface;

public class ModelSnout extends ModelPartInterface {
   private ModelRenderer small;
   private ModelRenderer medium;
   private ModelRenderer large;
   private ModelRenderer bunny;

   public ModelSnout(ModelMPM base) {
      super(base);
      this.small = new ModelRenderer(base, 24, 0);
      this.small.func_78789_a(0.0F, 0.0F, 0.0F, 4, 3, 1);
      this.small.func_78793_a(-2.0F, -3.0F, -5.0F);
      this.func_78792_a(this.small);
      this.medium = new ModelRenderer(base, 24, 0);
      this.medium.func_78789_a(0.0F, 0.0F, 0.0F, 4, 3, 2);
      this.medium.func_78793_a(-2.0F, -3.0F, -6.0F);
      this.func_78792_a(this.medium);
      this.large = new ModelRenderer(base, 24, 0);
      this.large.func_78789_a(0.0F, 0.0F, 0.0F, 4, 3, 3);
      this.large.func_78793_a(-2.0F, -3.0F, -7.0F);
      this.func_78792_a(this.large);
      this.bunny = new ModelRenderer(base, 24, 0);
      this.bunny.func_78789_a(1.0F, 1.0F, 0.0F, 4, 2, 1);
      this.bunny.func_78793_a(-3.0F, -4.0F, -5.0F);
      this.func_78792_a(this.bunny);
      ModelRenderer tooth = new ModelRenderer(base, 24, 3);
      tooth.func_78789_a(2.0F, 3.0F, 0.0F, 2, 1, 1);
      tooth.func_78793_a(0.0F, 0.0F, 0.0F);
      this.bunny.func_78792_a(tooth);
   }

   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity) {
   }

   public void initData(EntityCustomNpc data) {
      ModelPartData config = data.getPartData("snout");
      if (config == null) {
         this.field_78807_k = true;
      } else {
         this.color = config.color;
         this.field_78807_k = false;
         this.small.field_78807_k = config.type != 0;
         this.medium.field_78807_k = config.type != 1;
         this.large.field_78807_k = config.type != 2;
         this.bunny.field_78807_k = config.type != 3;
         if (!config.playerTexture) {
            this.location = (ResourceLocation)config.getResource();
         } else {
            this.location = null;
         }

      }
   }
}

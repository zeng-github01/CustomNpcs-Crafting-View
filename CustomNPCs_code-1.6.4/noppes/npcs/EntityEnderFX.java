package noppes.npcs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.client.ClientProxy;
import noppes.npcs.client.renderer.RenderNPCInterface;
import org.lwjgl.opengl.GL11;

public class EntityEnderFX extends EntityPortalFX {
   private float field_70571_a;
   private int particleNumber;
   private RenderNPCInterface npcRenderer;
   private EntityNPCInterface npc;
   private static final ResourceLocation field_110737_b = new ResourceLocation("textures/particle/particles.png");

   public EntityEnderFX(EntityNPCInterface npc, double par2, double par4, double par6, double par8, double par10, double par12) {
      super(npc.field_70170_p, par2, par4, par6, par8, par10, par12);
      this.npcRenderer = (RenderNPCInterface)RenderManager.field_78727_a.func_78713_a(npc);
      this.npc = npc;
      this.particleNumber = npc.field_70170_p.field_73012_v.nextInt(2);
      this.field_70571_a = this.field_70544_f = this.field_70146_Z.nextFloat() * 0.2F + 0.5F;
      this.field_70552_h = this.field_70553_i = this.field_70551_j = 1.0F;
   }

   public void func_70539_a(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
      Tessellator tessellator = Tessellator.field_78398_a;
      tessellator.func_78381_a();
      float scale = ((float)this.field_70546_d + par2) / (float)this.field_70547_e;
      scale = 1.0F - scale;
      scale *= scale;
      scale = 1.0F - scale;
      this.field_70544_f = this.field_70571_a * scale;
      Minecraft mc = Minecraft.func_71410_x();
      ClientProxy.bindTexture(this.npcRenderer.func_110775_a(this.npc));
      float f = 0.875F;
      float f1 = f + 0.125F;
      float f2 = 0.75F - (float)this.particleNumber * 0.25F;
      float f3 = f2 + 0.25F;
      float f4 = 0.1F * this.field_70544_f;
      float f5 = (float)(this.field_70169_q + (this.field_70165_t - this.field_70169_q) * (double)par2 - field_70556_an);
      float f6 = (float)(this.field_70167_r + (this.field_70163_u - this.field_70167_r) * (double)par2 - field_70554_ao);
      float f7 = (float)(this.field_70166_s + (this.field_70161_v - this.field_70166_s) * (double)par2 - field_70555_ap);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      tessellator.func_78382_b();
      tessellator.func_78380_c(this.func_70070_b(par2));
      par1Tessellator.func_78386_a(1.0F, 1.0F, 1.0F);
      par1Tessellator.func_78374_a((double)(f5 - par3 * f4 - par6 * f4), (double)(f6 - par4 * f4), (double)(f7 - par5 * f4 - par7 * f4), (double)f1, (double)f3);
      par1Tessellator.func_78374_a((double)(f5 - par3 * f4 + par6 * f4), (double)(f6 + par4 * f4), (double)(f7 - par5 * f4 + par7 * f4), (double)f1, (double)f2);
      par1Tessellator.func_78374_a((double)(f5 + par3 * f4 + par6 * f4), (double)(f6 + par4 * f4), (double)(f7 + par5 * f4 + par7 * f4), (double)f, (double)f2);
      par1Tessellator.func_78374_a((double)(f5 + par3 * f4 - par6 * f4), (double)(f6 - par4 * f4), (double)(f7 + par5 * f4 - par7 * f4), (double)f, (double)f3);
      tessellator.func_78381_a();
      ClientProxy.bindTexture(field_110737_b);
      tessellator.func_78382_b();
   }

   public int func_70537_b() {
      return 0;
   }
}

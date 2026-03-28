package noppes.npcs.client;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.TickType;
import java.util.EnumSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import noppes.npcs.client.gui.player.GuiQuestLog;

public class ClientKeyHandler extends KeyBindingRegistry.KeyHandler {
   public ClientKeyHandler(KeyBinding[] keyBindings, boolean[] repeatings) {
      super(keyBindings, repeatings);
   }

   public String getLabel() {
      return null;
   }

   public void keyDown(EnumSet types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
   }

   public void keyUp(EnumSet types, KeyBinding kb, boolean tickEnd) {
      if (kb.field_74515_c.equals("Quest Log") && tickEnd) {
         Minecraft mc = Minecraft.func_71410_x();
         if (mc.field_71462_r == null) {
            NoppesUtil.openGUI(mc.field_71439_g, new GuiQuestLog(mc.field_71439_g));
         } else if (mc.field_71462_r instanceof GuiQuestLog) {
            mc.func_71381_h();
         }
      }

   }

   public EnumSet ticks() {
      return EnumSet.of(TickType.CLIENT);
   }
}

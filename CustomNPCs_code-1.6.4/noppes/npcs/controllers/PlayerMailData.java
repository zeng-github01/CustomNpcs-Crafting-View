package noppes.npcs.controllers;

import java.util.ArrayList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class PlayerMailData implements IPlayerData {
   public ArrayList playermail = new ArrayList();

   public void readNBT(NBTTagCompound compound) {
      ArrayList<PlayerMail> newmail = new ArrayList();
      NBTTagList list = compound.func_74761_m("MailData");

      for(int i = 0; i < list.func_74745_c(); ++i) {
         PlayerMail mail = new PlayerMail();
         mail.readNBT((NBTTagCompound)list.func_74743_b(i));
         newmail.add(mail);
      }

      this.playermail = newmail;
   }

   public NBTTagCompound writeNBT(NBTTagCompound compound) {
      NBTTagList list = new NBTTagList();

      for(PlayerMail mail : this.playermail) {
         list.func_74742_a(mail.writeNBT());
      }

      compound.func_74782_a("MailData", list);
      return compound;
   }

   public boolean hasMail() {
      for(PlayerMail mail : this.playermail) {
         if (!mail.beenRead) {
            return true;
         }
      }

      return false;
   }
}

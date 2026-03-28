package noppes.npcs;

public class NoppesStringUtils {
   public static String formatText(String text, String username) {
      text = text.replaceAll("\\{player\\}", username);
      text = text.replaceAll("@p", username);
      text = text.replaceAll("&", Character.toChars(167)[0] + "");
      return text;
   }
}

package noppes.npcs.permissions;

public class CustomNpcsPermissions implements PermissionsInterface {
   public static PermissionsInterface Instance = new CustomNpcsPermissions();
   private static final String[] permissions = new String[]{"-customnpcs.*"};

   public boolean hasPermission(String username, String permission) {
      return true;
   }
}

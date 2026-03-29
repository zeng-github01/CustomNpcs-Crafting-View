package noppes.npcs;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import noppes.npcs.client.PacketHandlerClient;
import noppes.npcs.commands.CommandSlay;
import noppes.npcs.config.ConfigLoader;
import noppes.npcs.config.ConfigProp;
import noppes.npcs.constants.EnumModelType;
import noppes.npcs.entity.EntityNPCDwarfFemale;
import noppes.npcs.entity.EntityNPCDwarfMale;
import noppes.npcs.entity.EntityNPCElfFemale;
import noppes.npcs.entity.EntityNPCElfMale;
import noppes.npcs.entity.EntityNPCEnderman;
import noppes.npcs.entity.EntityNPCFurryFemale;
import noppes.npcs.entity.EntityNPCFurryMale;
import noppes.npcs.entity.EntityNPCGolem;
import noppes.npcs.entity.EntityNPCHumanFemale;
import noppes.npcs.entity.EntityNPCHumanMale;
import noppes.npcs.entity.EntityNPCOrcFemale;
import noppes.npcs.entity.EntityNPCOrcMale;
import noppes.npcs.entity.EntityNPCPony;
import noppes.npcs.entity.EntityNPCVillager;
import noppes.npcs.entity.EntityNpcCrystal;
import noppes.npcs.entity.EntityNpcDragon;
import noppes.npcs.entity.EntityNpcEnderchibi;
import noppes.npcs.entity.EntityNpcMonsterFemale;
import noppes.npcs.entity.EntityNpcMonsterMale;
import noppes.npcs.entity.EntityNpcNagaFemale;
import noppes.npcs.entity.EntityNpcNagaMale;
import noppes.npcs.entity.EntityNpcSkeleton;
import noppes.npcs.entity.EntityNpcSlime;
import noppes.npcs.entity.EntityProjectile;
import noppes.npcs.events.CraftbenchEvent;
import noppes.npcs.events.EntityKilledEvent;
import noppes.npcs.events.ItemInteractEvent;
import noppes.npcs.events.PlayerGetHurtEvent;
import noppes.npcs.events.WorldLoadEvent;

@NetworkMod(
   clientSideRequired = true,
   serverSideRequired = true,
   channels = {"CNPCs Player"},
   packetHandler = PacketHandlerPlayer.class,
   clientPacketHandlerSpec = @SidedPacketHandler(
   channels = {"CNPCs Client", "CNPCs Player"},
   packetHandler = PacketHandlerClient.class
),
   serverPacketHandlerSpec = @SidedPacketHandler(
   channels = {"CNPCs Server", "CNPCs Player"},
   packetHandler = PacketHandlerServer.class
),
   versionBounds = "[1.6.4]"
)
@Mod(
   modid = "customnpcs",
   name = "CustomNpcs",
   version = "1.6.4"
)
public class CustomNpcs {
   public static boolean TextureSelection = false;
   @ConfigProp
   public static boolean DisableExtraNpcItems = false;
   @ConfigProp(
      info = "Default Item ID range is from 26700"
   )
   public static int ItemStartId = 26700;
   @ConfigProp(
      info = "Default Block ID range is from 1525"
   )
   public static int BlockStartId = 1525;
   @ConfigProp(
      info = "Uses unique entities ids"
   )
   public static boolean UseUniqueEntities = true;
   @ConfigProp(
      info = "To use this UseUniqueEntities has to be false"
   )
   public static int EntityStartId = 120;
   @ConfigProp(
      info = "Navigation search range for NPCs. Not recommended to increase if you have a slow pc or on a server"
   )
   public static int NpcNavRange = 32;
   @ConfigProp(
      info = "Set to true if you want the dialog command option to be able to use op commands like tp etc"
   )
   public static boolean NpcUseOpCommands = false;
   @ConfigProp
   public static boolean InventoryGuiEnabled = true;
   public static long ticks;
   @SidedProxy(
      clientSide = "noppes.npcs.client.ClientProxy",
      serverSide = "noppes.npcs.CommonProxy"
   )
   public static CommonProxy proxy;
   @ConfigProp(
      info = "Enables CustomNpcs startup update message"
   )
   public static boolean EnableUpdateChecker = true;
   public static CustomNpcs instance;
   public static boolean FreezeNPCs = false;
   @ConfigProp(
      info = "Only ops can create and edit npcs"
   )
   public static boolean OpsOnly = false;
   public static File Dir;

   public CustomNpcs() {
      instance = this;
   }

   @EventHandler
   public void load(FMLInitializationEvent ev) {
      MinecraftServer server = MinecraftServer.func_71276_C();
      String dir = "";
      if (server != null) {
         dir = (new File(".")).getAbsolutePath();
      } else {
         dir = Minecraft.func_71410_x().field_71412_D.getAbsolutePath();
      }

      Dir = new File(dir, "customnpcs");
      Dir.mkdir();
      ConfigLoader configLoader = new ConfigLoader(this.getClass(), new File(dir, "config"), "CustomNpcs");
      configLoader.loadConfig();
      if (NpcNavRange < 16) {
         NpcNavRange = 16;
      }

      CustomItems.load();
      proxy.load();
      NetworkRegistry.instance().registerGuiHandler(this, proxy);
      MinecraftForge.EVENT_BUS.register(new PlayerGetHurtEvent());
      MinecraftForge.EVENT_BUS.register(new EntityKilledEvent());
      MinecraftForge.EVENT_BUS.register(new CraftbenchEvent());
      MinecraftForge.EVENT_BUS.register(new ItemInteractEvent());
      MinecraftForge.EVENT_BUS.register(new WorldLoadEvent());
      this.registerNpc(EntityNPCHumanMale.class, EnumModelType.HumanMale.entityName, EnumModelType.HumanMale.id);
      this.registerNpc(EntityNPCVillager.class, "npcvillager", getEntityId());
      this.registerNpc(EntityNPCPony.class, "npcpony", getEntityId());
      this.registerNpc(EntityNPCHumanFemale.class, "npchumanfemale", getEntityId());
      this.registerNpc(EntityNPCDwarfMale.class, "npcdwarfmale", getEntityId());
      this.registerNpc(EntityNPCFurryMale.class, "npcfurrymale", getEntityId());
      this.registerNpc(EntityNpcMonsterMale.class, "npczombiemale", getEntityId());
      this.registerNpc(EntityNpcMonsterFemale.class, "npczombiefemale", getEntityId());
      this.registerNpc(EntityNpcSkeleton.class, "npcskeleton", getEntityId());
      this.registerNpc(EntityNPCDwarfFemale.class, "npcdwarffemale", getEntityId());
      this.registerNpc(EntityNPCFurryFemale.class, "npcfurryfemale", getEntityId());
      this.registerNpc(EntityNPCOrcMale.class, "npcorcfmale", getEntityId());
      this.registerNpc(EntityNPCOrcFemale.class, "npcorcfemale", getEntityId());
      this.registerNpc(EntityNPCElfMale.class, "npcelfmale", getEntityId());
      this.registerNpc(EntityNPCElfFemale.class, "npcelffemale", getEntityId());
      this.registerNpc(EntityNpcCrystal.class, "npccrystal", getEntityId());
      this.registerNpc(EntityNpcEnderchibi.class, "npcenderchibi", getEntityId());
      this.registerNpc(EntityNpcNagaMale.class, "npcnagamale", getEntityId());
      this.registerNpc(EntityNpcNagaFemale.class, "npcnagafemale", getEntityId());
      this.registerNpc(EntityNpcSlime.class, "NpcSlime", getEntityId());
      this.registerNpc(EntityNpcDragon.class, "NpcDragon", getEntityId());
      this.registerNpc(EntityNPCEnderman.class, "npcEnderman", getEntityId());
      this.registerNpc(EntityNPCGolem.class, "npcGolem", getEntityId());
      int thowid = getEntityId();
      EntityRegistry.registerGlobalEntityID(EntityProjectile.class, "throwableitem", thowid);
      EntityRegistry.registerModEntity(EntityProjectile.class, "throwableitem", thowid, this, 64, 3, true);
   }

   @EventHandler
   public void serverstart(FMLServerStartingEvent event) {
      event.registerServerCommand(new CommandSlay());
   }

   public static int getEntityId() {
      return UseUniqueEntities ? EntityRegistry.findGlobalUniqueEntityId() : EntityStartId++;
   }

   private void registerNpc(Class cl, String name, int id) {
      EntityRegistry.registerGlobalEntityID(cl, name, id);
      EntityRegistry.registerModEntity(cl, name, id, this, 80, 3, true);
   }

   public static void GivePlayerItem(Entity entity, EntityPlayer player, ItemStack item) {
      if (!entity.field_70170_p.field_72995_K) {
         item = item.func_77946_l();
         float f = 0.7F;
         double d = (double)(entity.field_70170_p.field_73012_v.nextFloat() * f) + (double)(1.0F - f);
         double d1 = (double)(entity.field_70170_p.field_73012_v.nextFloat() * f) + (double)(1.0F - f);
         double d2 = (double)(entity.field_70170_p.field_73012_v.nextFloat() * f) + (double)(1.0F - f);
         EntityItem entityitem = new EntityItem(entity.field_70170_p, entity.field_70165_t + d, entity.field_70163_u + d1, entity.field_70161_v + d2, item);
         entityitem.field_70293_c = 2;
         entity.field_70170_p.func_72838_d(entityitem);
         int i = item.field_77994_a;
         if (player.field_71071_by.func_70441_a(item)) {
            GameRegistry.onPickupNotification(player, entityitem);
            entity.field_70170_p.func_72956_a(entityitem, "random.pop", 0.2F, ((entity.field_70170_p.field_73012_v.nextFloat() - entity.field_70170_p.field_73012_v.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            player.func_71001_a(entityitem, i);
            if (item.field_77994_a <= 0) {
               entityitem.func_70106_y();
            }
         }

      }
   }

   public static File getWorldSaveDirectory() {
      MinecraftServer server = MinecraftServer.func_71276_C();
      File saves = new File(".");
      if (server != null && !server.func_71262_S()) {
         saves = new File(Minecraft.func_71410_x().field_71412_D, "saves");
      }

      if (server != null) {
         File savedir = new File(new File(saves, server.func_71270_I()), "customnpcs");
         if (!savedir.exists()) {
            savedir.mkdir();
         }

         return savedir;
      } else {
         return null;
      }
   }
}

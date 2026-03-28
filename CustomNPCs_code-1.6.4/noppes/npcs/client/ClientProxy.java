package noppes.npcs.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import noppes.npcs.CommonProxy;
import noppes.npcs.CustomItems;
import noppes.npcs.CustomNpcs;
import noppes.npcs.EntityCustomNpc;
import noppes.npcs.EntityNPCInterface;
import noppes.npcs.blocks.BlockMailbox;
import noppes.npcs.blocks.TileBlockAnvil;
import noppes.npcs.blocks.TileMailbox;
import noppes.npcs.client.controllers.MusicController;
import noppes.npcs.client.events.TextureLoadEvent;
import noppes.npcs.client.gui.GuiMerchantAdd;
import noppes.npcs.client.gui.GuiNpcMobSpawner;
import noppes.npcs.client.gui.GuiNpcMobSpawnerAdd;
import noppes.npcs.client.gui.GuiNpcPather;
import noppes.npcs.client.gui.GuiNpcRedstoneBlock;
import noppes.npcs.client.gui.GuiNpcRemoteEditor;
import noppes.npcs.client.gui.GuiNpcWaypoint;
import noppes.npcs.client.gui.global.GuiNPCManageBanks;
import noppes.npcs.client.gui.global.GuiNPCManageDialogs;
import noppes.npcs.client.gui.global.GuiNPCManageFactions;
import noppes.npcs.client.gui.global.GuiNPCManageQuest;
import noppes.npcs.client.gui.global.GuiNPCManageTransporters;
import noppes.npcs.client.gui.global.GuiNpcManageRecipes;
import noppes.npcs.client.gui.global.GuiNpcQuestReward;
import noppes.npcs.client.gui.mainmenu.GuiNPCGlobalMainMenu;
import noppes.npcs.client.gui.mainmenu.GuiNPCInv;
import noppes.npcs.client.gui.mainmenu.GuiNpcAI;
import noppes.npcs.client.gui.mainmenu.GuiNpcAdvanced;
import noppes.npcs.client.gui.mainmenu.GuiNpcDisplay;
import noppes.npcs.client.gui.mainmenu.GuiNpcStats;
import noppes.npcs.client.gui.player.GuiMailbox;
import noppes.npcs.client.gui.player.GuiMailmanSend;
import noppes.npcs.client.gui.player.GuiNPCBankChest;
import noppes.npcs.client.gui.player.GuiNPCTrader;
import noppes.npcs.client.gui.player.GuiNpcCarpentryBench;
import noppes.npcs.client.gui.player.GuiNpcFollower;
import noppes.npcs.client.gui.player.GuiNpcFollowerHire;
import noppes.npcs.client.gui.player.GuiTransportSelection;
import noppes.npcs.client.gui.questtypes.GuiNpcQuestTypeItem;
import noppes.npcs.client.gui.roles.GuiNpcBankSetup;
import noppes.npcs.client.gui.roles.GuiNpcFollowerSetup;
import noppes.npcs.client.gui.roles.GuiNpcItemGiver;
import noppes.npcs.client.gui.roles.GuiNpcTraderSetup;
import noppes.npcs.client.gui.roles.GuiNpcTransporter;
import noppes.npcs.client.model.ModelDwarfFemale;
import noppes.npcs.client.model.ModelDwarfMale;
import noppes.npcs.client.model.ModelElfFemale;
import noppes.npcs.client.model.ModelElfMale;
import noppes.npcs.client.model.ModelEnderChibi;
import noppes.npcs.client.model.ModelFurryFemale;
import noppes.npcs.client.model.ModelFurryMale;
import noppes.npcs.client.model.ModelNPCEnderman;
import noppes.npcs.client.model.ModelNPCFemale;
import noppes.npcs.client.model.ModelNPCGolem;
import noppes.npcs.client.model.ModelNPCMale;
import noppes.npcs.client.model.ModelNagaFemale;
import noppes.npcs.client.model.ModelNagaMale;
import noppes.npcs.client.model.ModelNpcCrystal;
import noppes.npcs.client.model.ModelNpcDragon;
import noppes.npcs.client.model.ModelNpcSkeleton;
import noppes.npcs.client.model.ModelNpcSlime;
import noppes.npcs.client.model.ModelOrcFemale;
import noppes.npcs.client.model.ModelOrcMale;
import noppes.npcs.client.model.ModelZombieFemale;
import noppes.npcs.client.model.ModelZombieMale;
import noppes.npcs.client.renderer.BlockCarpentryBenchRenderer;
import noppes.npcs.client.renderer.BlockMailboxRenderer;
import noppes.npcs.client.renderer.NpcItemRenderer;
import noppes.npcs.client.renderer.RenderCustomNpc;
import noppes.npcs.client.renderer.RenderNPCHumanFemale;
import noppes.npcs.client.renderer.RenderNPCHumanMale;
import noppes.npcs.client.renderer.RenderNPCPony;
import noppes.npcs.client.renderer.RenderNpcCrystal;
import noppes.npcs.client.renderer.RenderNpcDragon;
import noppes.npcs.client.renderer.RenderNpcSlime;
import noppes.npcs.client.renderer.RenderNpcVillager;
import noppes.npcs.client.renderer.RenderProjectile;
import noppes.npcs.constants.EnumGuiType;
import noppes.npcs.containers.ContainerCarpentryBench;
import noppes.npcs.containers.ContainerManageBanks;
import noppes.npcs.containers.ContainerManageRecipes;
import noppes.npcs.containers.ContainerNPCBankInterface;
import noppes.npcs.containers.ContainerNPCFollower;
import noppes.npcs.containers.ContainerNPCFollowerHire;
import noppes.npcs.containers.ContainerNPCFollowerSetup;
import noppes.npcs.containers.ContainerNPCInv;
import noppes.npcs.containers.ContainerNPCTrader;
import noppes.npcs.containers.ContainerNPCTraderSetup;
import noppes.npcs.containers.ContainerNpcItemGiver;
import noppes.npcs.containers.ContainerNpcQuestReward;
import noppes.npcs.containers.ContainerNpcQuestTypeItem;
import noppes.npcs.entity.EntityElementalStaffFX;
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

public class ClientProxy extends CommonProxy {
   public void load() {
      super.load();
      new MusicController();
      RenderingRegistry.registerEntityRenderingHandler(EntityNPCHumanMale.class, new RenderNPCHumanMale(new ModelNPCMale(0.0F), new ModelNPCMale(1.0F), new ModelNPCMale(0.5F)));
      RenderingRegistry.registerEntityRenderingHandler(EntityNPCElfMale.class, new RenderNPCHumanMale(new ModelElfMale(0.0F), new ModelElfMale(1.0F), new ModelElfMale(0.5F)));
      RenderingRegistry.registerEntityRenderingHandler(EntityNPCOrcMale.class, new RenderNPCHumanMale(new ModelOrcMale(0.0F), new ModelOrcMale(1.0F), new ModelOrcMale(0.5F)));
      RenderingRegistry.registerEntityRenderingHandler(EntityNpcMonsterMale.class, new RenderNPCHumanMale(new ModelZombieMale(0.0F), new ModelZombieMale(1.0F), new ModelZombieMale(0.5F)));
      RenderingRegistry.registerEntityRenderingHandler(EntityNpcSkeleton.class, new RenderNPCHumanMale(new ModelNpcSkeleton(0.0F), new ModelNpcSkeleton(1.0F), new ModelNpcSkeleton(0.5F)));
      RenderingRegistry.registerEntityRenderingHandler(EntityNPCDwarfMale.class, new RenderNPCHumanMale(new ModelDwarfMale(0.0F), new ModelDwarfMale(0.6F), new ModelDwarfMale(0.3F)));
      RenderingRegistry.registerEntityRenderingHandler(EntityNpcNagaMale.class, new RenderNPCHumanMale(new ModelNagaMale(64, 64, 0.0F), new ModelNagaMale(64, 32, 1.0F), new ModelNagaMale(64, 32, 0.5F)));
      RenderingRegistry.registerEntityRenderingHandler(EntityNpcEnderchibi.class, new RenderNPCHumanMale(new ModelEnderChibi(0.0F), new ModelEnderChibi(0.6F), new ModelEnderChibi(0.3F)));
      RenderingRegistry.registerEntityRenderingHandler(EntityNPCEnderman.class, new RenderNPCHumanMale(new ModelNPCEnderman(0.0F), new ModelNPCEnderman(0.6F), new ModelNPCEnderman(0.3F)));
      RenderingRegistry.registerEntityRenderingHandler(EntityNPCGolem.class, new RenderNPCHumanMale(new ModelNPCGolem(0.0F), new ModelNPCGolem(1.0F), new ModelNPCGolem(0.5F)));
      RenderingRegistry.registerEntityRenderingHandler(EntityNPCHumanFemale.class, new RenderNPCHumanFemale(new ModelNPCFemale(0.0F), new ModelNPCFemale(0.6F), new ModelNPCFemale(0.3F)));
      RenderingRegistry.registerEntityRenderingHandler(EntityNPCElfFemale.class, new RenderNPCHumanFemale(new ModelElfFemale(0.0F), new ModelElfFemale(0.6F), new ModelElfFemale(0.3F)));
      RenderingRegistry.registerEntityRenderingHandler(EntityNPCOrcFemale.class, new RenderNPCHumanFemale(new ModelOrcFemale(0.0F), new ModelOrcFemale(0.6F), new ModelOrcFemale(0.3F)));
      RenderingRegistry.registerEntityRenderingHandler(EntityNPCDwarfFemale.class, new RenderNPCHumanFemale(new ModelDwarfFemale(0.0F), new ModelDwarfFemale(0.6F), new ModelDwarfFemale(0.3F)));
      RenderingRegistry.registerEntityRenderingHandler(EntityNpcMonsterFemale.class, new RenderNPCHumanFemale(new ModelZombieFemale(0.0F), new ModelZombieFemale(0.6F), new ModelZombieFemale(0.3F)));
      RenderingRegistry.registerEntityRenderingHandler(EntityNpcNagaFemale.class, new RenderNPCHumanFemale(new ModelNagaFemale(64, 64, 0.0F), new ModelNagaFemale(64, 32, 0.6F), new ModelNagaFemale(64, 32, 0.3F)));
      RenderingRegistry.registerEntityRenderingHandler(EntityNPCFurryMale.class, new RenderNPCHumanMale(new ModelFurryMale(64, 64, 0.0F), new ModelNPCMale(64, 32, 1.0F), new ModelNPCMale(64, 32, 0.5F)));
      RenderingRegistry.registerEntityRenderingHandler(EntityNPCFurryFemale.class, new RenderNPCHumanFemale(new ModelFurryFemale(64, 64, 0.0F), new ModelNPCFemale(64, 32, 1.0F), new ModelNPCFemale(64, 32, 0.5F)));
      RenderingRegistry.registerEntityRenderingHandler(EntityNPCVillager.class, new RenderNpcVillager());
      RenderingRegistry.registerEntityRenderingHandler(EntityNPCPony.class, new RenderNPCPony());
      RenderingRegistry.registerEntityRenderingHandler(EntityNpcCrystal.class, new RenderNpcCrystal(new ModelNpcCrystal(0.5F)));
      RenderingRegistry.registerEntityRenderingHandler(EntityNpcDragon.class, new RenderNpcDragon(new ModelNpcDragon(0.0F), 0.5F));
      RenderingRegistry.registerEntityRenderingHandler(EntityNpcSlime.class, new RenderNpcSlime(new ModelNpcSlime(16), new ModelNpcSlime(0), 0.25F));
      RenderingRegistry.registerEntityRenderingHandler(EntityProjectile.class, new RenderProjectile());
      RenderingRegistry.registerEntityRenderingHandler(EntityCustomNpc.class, new RenderCustomNpc());
      TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
      ClientRegistry.bindTileEntitySpecialRenderer(TileBlockAnvil.class, new BlockCarpentryBenchRenderer());
      BlockMailboxRenderer mailbox = new BlockMailboxRenderer();
      ClientRegistry.bindTileEntitySpecialRenderer(TileMailbox.class, mailbox);
      ((BlockMailbox)CustomItems.mailbox).renderId = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(mailbox);
      Minecraft mc = Minecraft.func_71410_x();
      ArrayList<KeyBinding> keybindings = new ArrayList();

      for(KeyBinding key : mc.field_71474_y.field_74324_K) {
         keybindings.add(key);
      }

      KeyBinding questBinding = new KeyBinding("Quest Log", 38);
      keybindings.add(questBinding);
      mc.field_71474_y.field_74324_K = (KeyBinding[])keybindings.toArray(new KeyBinding[keybindings.size()]);
      KeyBinding[] keys = new KeyBinding[]{questBinding};
      TickRegistry.registerTickHandler(new ClientKeyHandler(keys, new boolean[]{false}), Side.CLIENT);
      MinecraftForge.EVENT_BUS.register(new TextureLoadEvent());
      if (CustomNpcs.EnableUpdateChecker) {
         VersionChecker checker = new VersionChecker();
         checker.start();
      }

      AnalyticsTracking tracker = new AnalyticsTracking();
      tracker.start();
   }

   public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
      if (ID > EnumGuiType.values().length) {
         return null;
      } else {
         EnumGuiType gui = EnumGuiType.values()[ID];
         EntityNPCInterface npc = NoppesUtil.getLastNpc();
         Container container = this.getContainer(gui, player, x, y, z, npc);
         return this.getGui(npc, gui, container);
      }
   }

   private GuiScreen getGui(EntityNPCInterface npc, EnumGuiType gui, Container container) {
      if (gui == EnumGuiType.MainMenuDisplay) {
         if (npc != null) {
            return new GuiNpcDisplay(npc);
         }

         System.out.println("Unable to find spawned npc");
      } else {
         if (gui == EnumGuiType.MainMenuStats) {
            return new GuiNpcStats(npc);
         }

         if (gui == EnumGuiType.MainMenuInv) {
            return new GuiNPCInv(npc, (ContainerNPCInv)container);
         }

         if (gui == EnumGuiType.MainMenuAdvanced) {
            return new GuiNpcAdvanced(npc);
         }

         if (gui == EnumGuiType.QuestReward) {
            return new GuiNpcQuestReward(npc, (ContainerNpcQuestReward)container);
         }

         if (gui == EnumGuiType.QuestItem) {
            return new GuiNpcQuestTypeItem(npc, (ContainerNpcQuestTypeItem)container);
         }

         if (gui == EnumGuiType.MovingPath) {
            return new GuiNpcPather(npc);
         }

         if (gui == EnumGuiType.ManageFactions) {
            return new GuiNPCManageFactions(npc);
         }

         if (gui == EnumGuiType.ManageTransport) {
            return new GuiNPCManageTransporters(npc);
         }

         if (gui == EnumGuiType.ManageRecipes) {
            return new GuiNpcManageRecipes(npc, (ContainerManageRecipes)container);
         }

         if (gui == EnumGuiType.ManageDialogs) {
            return new GuiNPCManageDialogs(npc);
         }

         if (gui == EnumGuiType.ManageQuests) {
            return new GuiNPCManageQuest(npc);
         }

         if (gui == EnumGuiType.ManageBanks) {
            return new GuiNPCManageBanks(npc, (ContainerManageBanks)container);
         }

         if (gui == EnumGuiType.MainMenuGlobal) {
            return new GuiNPCGlobalMainMenu(npc);
         }

         if (gui == EnumGuiType.MainMenuAI) {
            return new GuiNpcAI(npc);
         }

         if (gui == EnumGuiType.PlayerFollowerHire) {
            return new GuiNpcFollowerHire(npc, (ContainerNPCFollowerHire)container);
         }

         if (gui == EnumGuiType.PlayerFollower) {
            return new GuiNpcFollower(npc, (ContainerNPCFollower)container);
         }

         if (gui == EnumGuiType.PlayerTrader) {
            return new GuiNPCTrader(npc, (ContainerNPCTrader)container);
         }

         if (gui == EnumGuiType.PlayerBankSmall || gui == EnumGuiType.PlayerBankUnlock || gui == EnumGuiType.PlayerBankUprade || gui == EnumGuiType.PlayerBankLarge) {
            return new GuiNPCBankChest(npc, (ContainerNPCBankInterface)container);
         }

         if (gui == EnumGuiType.PlayerTransporter) {
            return new GuiTransportSelection(npc);
         }

         if (gui == EnumGuiType.PlayerAnvil) {
            return new GuiNpcCarpentryBench((ContainerCarpentryBench)container);
         }

         if (gui == EnumGuiType.SetupFollower) {
            return new GuiNpcFollowerSetup(npc, (ContainerNPCFollowerSetup)container);
         }

         if (gui == EnumGuiType.SetupItemGiver) {
            return new GuiNpcItemGiver(npc, (ContainerNpcItemGiver)container);
         }

         if (gui == EnumGuiType.SetupTrader) {
            return new GuiNpcTraderSetup(npc, (ContainerNPCTraderSetup)container);
         }

         if (gui == EnumGuiType.SetupTransporter) {
            return new GuiNpcTransporter(npc);
         }

         if (gui == EnumGuiType.SetupBank) {
            return new GuiNpcBankSetup(npc);
         }

         if (gui == EnumGuiType.NpcRemote && Minecraft.func_71410_x().field_71462_r == null) {
            return new GuiNpcRemoteEditor();
         }

         if (gui == EnumGuiType.PlayerMailman) {
            return new GuiMailmanSend();
         }

         if (gui == EnumGuiType.PlayerMailbox) {
            return new GuiMailbox();
         }

         if (gui == EnumGuiType.MerchantAdd) {
            return new GuiMerchantAdd();
         }
      }

      return null;
   }

   public void openGui(int i, int j, int k, EnumGuiType gui, EntityPlayer player) {
      Minecraft minecraft = Minecraft.func_71410_x();
      if (minecraft.field_71439_g == player) {
         GuiScreen guiscreen = null;
         if (gui == EnumGuiType.RedstoneBlock) {
            guiscreen = new GuiNpcRedstoneBlock(i, j, k);
         }

         if (gui == EnumGuiType.MobSpawner) {
            guiscreen = new GuiNpcMobSpawner(i, j, k);
         }

         if (gui == EnumGuiType.MobSpawnerAdd) {
            guiscreen = new GuiNpcMobSpawnerAdd();
         }

         if (gui == EnumGuiType.Waypoint) {
            guiscreen = new GuiNpcWaypoint(i, j, k);
         }

         if (guiscreen != null) {
            minecraft.func_71373_a(guiscreen);
         }

      }
   }

   public void openGui(EntityNPCInterface npc, EnumGuiType gui) {
      Minecraft minecraft = Minecraft.func_71410_x();
      Container container = this.getContainer(gui, minecraft.field_71439_g, 0, 0, 0, npc);
      GuiScreen guiscreen = this.getGui(npc, gui, container);
      if (guiscreen != null) {
         minecraft.func_71373_a(guiscreen);
      }

   }

   public void openGui(EntityPlayer player, Object guiscreen) {
      Minecraft minecraft = Minecraft.func_71410_x();
      if (player.field_70170_p.field_72995_K && guiscreen instanceof GuiScreen) {
         if (guiscreen != null) {
            minecraft.func_71373_a((GuiScreen)guiscreen);
         }

      }
   }

   public void spawnParticle(EntityLivingBase player, String string, Object... ob) {
      if (string.equals("Spell")) {
         int color = (Integer)ob[0];
         int number = (Integer)ob[1];

         for(int i = 0; i < number; ++i) {
            Random rand = player.field_70170_p.field_73012_v;
            double x = (rand.nextDouble() - (double)0.5F) * (double)player.field_70130_N;
            double y = (double)player.func_70047_e();
            double z = (rand.nextDouble() - (double)0.5F) * (double)player.field_70130_N;
            double f = (rand.nextDouble() - (double)0.5F) * (double)2.0F;
            double f1 = -rand.nextDouble();
            double f2 = (rand.nextDouble() - (double)0.5F) * (double)2.0F;
            Minecraft.func_71410_x().field_71452_i.func_78873_a(new EntityElementalStaffFX(player, x, y, z, f, f1, f2, color));
         }
      }

   }

   public boolean hasClient() {
      return true;
   }

   public EntityPlayer getPlayer() {
      return Minecraft.func_71410_x().field_71439_g;
   }

   public void registerItem(int itemID) {
      MinecraftForgeClient.registerItemRenderer(itemID, new NpcItemRenderer());
   }

   public Object loadResource(String texture) {
      return new ResourceLocation(texture);
   }

   public static void bindTexture(ResourceLocation location) {
      try {
         if (location == null) {
            return;
         }

         TextureManager texturemanager = Minecraft.func_71410_x().func_110434_K();
         texturemanager.func_110577_a(location);
      } catch (NullPointerException var2) {
      }

   }
}

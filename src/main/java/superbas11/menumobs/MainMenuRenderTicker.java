package superbas11.menumobs;

import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;
import com.setycz.chickens.ChickensRegistry;
import com.setycz.chickens.ChickensRegistryItem;
import com.setycz.chickens.chicken.EntityChickensChicken;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.util.EnumHand;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import superbas11.menumobs.client.util.EntityUtils;
import superbas11.menumobs.client.util.UUIDFetcher;
import superbas11.menumobs.util.FakeNetworkManager;
import superbas11.menumobs.util.FakeWorld;
import superbas11.menumobs.util.LogHelper;

import javax.annotation.Nullable;
import java.util.AbstractMap.SimpleEntry;
import java.util.*;

@SuppressWarnings({"rawtypes", "unchecked"})
@SideOnly(Side.CLIENT)
public class MainMenuRenderTicker {
    private static Minecraft mcClient;
    private static boolean isRegistered = false;
    private static List entityBlacklist;
    private static List<SimpleEntry<UUID, String>> fallbackPlayerNames;
    private static ItemStack[] playerItems;
    private static ItemStack[] zombieItems;
    private static ItemStack[] horseArmors;
    private static ItemStack[] skelItems;
    private static Random random = new Random();
    private static String[] entStrings;
    private static int id;
    private static boolean erroredOut = false;

    static {
        entityBlacklist = new ArrayList();
        entityBlacklist.add("Mob");
        entityBlacklist.add("Monster");
        entityBlacklist.add("EnderDragon");
        entityBlacklist.add("Squid");
        entityBlacklist.add("Ghast");
        entityBlacklist.add("Bat");
        entityBlacklist.add("CaveSpider");
        entityBlacklist.add("Giant");
        // Millenaire entities
        entityBlacklist.add("MillBlaze");
        entityBlacklist.add("MillGhast");
        entityBlacklist.add("MillWitherSkeleton");
        entityBlacklist.add("ml_GenericAsimmFemale");
        entityBlacklist.add("ml_GenericSimmFemale");
        entityBlacklist.add("ml_GenericVillager");

        entityBlacklist.add("BiomesOPlenty.Phantom");
        entityBlacklist.add("forestry.butterflyGE");
        entityBlacklist.add("TConstruct.Crystal");
        entityBlacklist.add("Thaumcraft.Firebat");
        entityBlacklist.add("Thaumcraft.TaintSpore");
        entityBlacklist.add("Thaumcraft.TaintSwarm");
        entityBlacklist.add("Thaumcraft.Taintacle");
        entityBlacklist.add("Thaumcraft.TaintacleTiny");
        entityBlacklist.add("Thaumcraft.Wisp");
        entityBlacklist.add("TwilightForest.Boggard");
        entityBlacklist.add("TwilightForest.Firefly");
        entityBlacklist.add("TwilightForest.Helmet Crab");
        entityBlacklist.add("TwilightForest.Hydra");
        entityBlacklist.add("TwilightForest.HydraHead");
        entityBlacklist.add("TwilightForest.Lower Goblin Knight");
        entityBlacklist.add("TwilightForest.Mist Wolf");
        entityBlacklist.add("TwilightForest.Mosquito Swarm");
        entityBlacklist.add("TwilightForest.Upper Goblin Knight");
        entityBlacklist.add("graves.playerzombie");
        entityBlacklist.add("headcrumbs.Human");
        entityBlacklist.add("thuttech.thuttechlift");

        fallbackPlayerNames = new ArrayList<SimpleEntry<UUID, String>>();
        // UUIDs gotten using mctools.connorlinfoot.com
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("41834a728902449586b8731b1c253afe"), "superbas11"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("92d459067a50474285b6b079db9dc189"), "bspkrs"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("2efa46fa29484d98b822fa182d254870"), "lorddusk"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("b9a89002b3924545ab4d5b1ff60c88a6"), "Arkember"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("48a16fc8bc1f4e7284e97ec73b7d8ea1"), "TTFTCUTS"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("95fe0728e1bd4989a9803d8976aedda9"), "WayofFlowingTime"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("e6b5c088068044df9e1b9bf11792291b"), "Grumm"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("af1d579e8787433099b2e7b3777c3e7a"), "Sacheverell"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("4c13e7d20e854bdebb121a43be506302"), "Quetzz"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("0192723fb3dc495a959f52c53fa63bff"), "Pahimar"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("f46302d20b7c4cc6aee9cd714ae6b9d1"), "ZeldoKavira"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("0eff7eb1d1b74612a9c9791b7ad6277a"), "sfPlayer1"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("c7d5d58a51a84d2698f55550a37bd8d1"), "jadedcat"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("72ddaa057bbe4ae298922c8d90ea0ad8"), "RWTema"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("754e416456cc4139bb7911cfaafdebcc"), "Scottwears"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("7ef08b4e5f3d40a793d426a19fe0efe2"), "neptunepink"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("90201c8957124f99a1c95d751565560a"), "Aureylian"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("bbb87dbe690f4205bdc572ffb8ebc29d"), "direwolf20"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("d0719201af1d4aab93d0f514ab59ed8e"), "Krystal_Raven"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("069a79f444e94726a5befca90e38aaf5"), "Notch"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("61699b2ed3274a019f1e0ea8c3f06bc6"), "Dinnerbone"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("e358f2774a4c42f69ad83addf4869ea6"), "Adubbz"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("892017ede9a04f259a37b8811614707d"), "AlgorithmX2"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("6d074736b1e94378a99bbd8777821c9c"), "Cloudhunter"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("3af4f9617eb64cb0b26443fd593cf42a"), "Lunatrius"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("75831c039a0a496ba7776a78ef8833a6"), "_Sunstrike"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("b72d87cefa984a5ab5a05db51a018d09"), "sdkillen"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("af1483804ba54a3da47d710f710f9265"), "Minalien"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("6bdd4acd5637448898583de07cc820d5"), "futureamnet"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("8e32d7a9c8124fa78daf465ff9ffa262"), "AbrarSyed"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("6ac7c57d8c154ffeae5d5e04bd606786"), "TDWP_FTW"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("d3cf097a438f4523b770ec11e13ecc32"), "LexManos"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("98beecaf555e40649401b531fb927641"), "Vaht"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("e3ade06278f344ca90fe4fc5781ffc80"), "EddieRuckus"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("83898b2861184900913741ffc46b6e10"), "progwml6"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("0b7509f0245841609ce12772b9a45ac2"), "ohaiiChun"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("aa29ede708174c65b19277a32e772b9c"), "fuj1n"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("d4d119aad410488a87340053577d4a1a"), "Mikeemoo"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("898ed21379b249f98602d1bb03d19ba2"), "boq42"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("9671e3e159184ad0bd473f7f27f57074"), "Toby"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("f3c8d69b077645128434d1b2165909eb"), "dan200"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("cf3e2c7ed70348e0808ef139bf26ff9d"), "ecutruin"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("b1890bac9f4044fa870ba71ce4df05c2"), "nikstick22"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("a9b6837ea916496790c191aef968c96b"), "Mr_okushama"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("0ea8eca3dbf647cc9d1ac64551ca975c"), "sk89q"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("957397819f754c6c9a93621c72f5bf9c"), "ShadwDrgn"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("c501d5507e3c463e8a95256f86d9a47d"), "chicken_bones"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("0f95811ab3b64dbaba034adfec7cf5ab"), "azanor"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("4f3a8d1e33c144e7bce8e683027c7dac"), "Soaryn"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("03020c4cf2f640b1a37c4e2b159b15b7"), "pillbox"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("b476c98e175048bfad5ea21becd0aaeb"), "mDiyo"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("839a930d5b874583b8da0760f9fa0254"), "IceBladeRage"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("4b9a5c51e9324e1ead30637101fb6fae"), "Thunderdark"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("7e6d65ed6fd840a786f8df09791572fc"), "Myrathi"));
        fallbackPlayerNames.add(new SimpleEntry<UUID, String>(UUIDTypeAdapter.fromString("b97e12cedbb14c0cafc8132b708a9b88"), "XCompWiz"));

        playerItems = new ItemStack[]{
                new ItemStack(Items.IRON_SWORD), new ItemStack(Items.DIAMOND_SWORD), new ItemStack(Items.GOLDEN_SWORD),
                new ItemStack(Items.DIAMOND_PICKAXE), new ItemStack(Items.IRON_PICKAXE), new ItemStack(Items.IRON_AXE)
        };

        zombieItems = new ItemStack[]{
                new ItemStack(Items.IRON_SWORD), new ItemStack(Items.DIAMOND_SWORD), new ItemStack(Items.GOLDEN_SWORD), new ItemStack(Items.IRON_AXE)
        };

        skelItems = new ItemStack[]{
                new ItemStack(Items.BOW), new ItemStack(Items.GOLDEN_SWORD), new ItemStack(Items.BOW),
                new ItemStack(Items.BOW), new ItemStack(Items.BOW), new ItemStack(Items.BOW)
        };

        horseArmors = new ItemStack[]{
                new ItemStack(Items.IRON_HORSE_ARMOR), new ItemStack(Items.GOLDEN_HORSE_ARMOR), new ItemStack(Items.DIAMOND_HORSE_ARMOR)
        };

        // Get a COPY dumbass!
        Set entities = new TreeSet(EntityList.NAME_TO_CLASS.keySet());
        entities.removeAll(entityBlacklist);
        entStrings = (String[]) entities.toArray(new String[]{});
        id = -1;
    }

    private World world;
    private EntityLivingBase randMob;
    private EntityPlayerSP player;

    public MainMenuRenderTicker() {
        mcClient = FMLClientHandler.instance().getClient();
    }

    private static EntityLivingBase getNextEntity(World world) {
        Class clazz;
        int tries = 0;
        do {
            if (++id >= entStrings.length)
                id = 0;
            clazz = (Class) EntityList.NAME_TO_CLASS.get((String) entStrings[id]);
        }
        while (!EntityLivingBase.class.isAssignableFrom(clazz) && (++tries <= 5));

        //Using a player as fallback.
        if (!EntityLivingBase.class.isAssignableFrom(clazz))
            return getRandomPlayer(world);

        if (ConfigElements.ALLOW_DEBUG_OUTPUT.getSetting().getBoolean())
            LogHelper.info(entStrings[id]);

        return (EntityLivingBase) EntityList.createEntityByName(entStrings[id], world);
    }

    private static EntityLivingBase getFixedEntity(World world) {
        String[] entityList = ConfigElements.FIXED_MOB.getSetting().getStringList();
        EntityLivingBase entity = null;
        Random random = new Random();
        Class clazz;
        int tries = 0;
        int id;

        do {
            id = random.nextInt(entityList.length);
            clazz = (Class) EntityList.NAME_TO_CLASS.get(entityList[id]);
            if (clazz == null) {
                try {
                    UUID UUID = UUIDFetcher.getUUIDOf(entityList[id]);

                    if (UUID == null) {
                        LogHelper.warning("Entity " + entityList[id] + " is unknown!");
                        continue;
                    }

                    GameProfile gameProfile = mcClient.getSessionService().fillProfileProperties(new GameProfile(UUID, entityList[id]), true);
                    final NetworkPlayerInfo networkPlayerInfo = new NetworkPlayerInfo(gameProfile);
                    entity = new EntityOtherPlayerMP(world, gameProfile) {
                        @Nullable
                        @Override
                        protected NetworkPlayerInfo getPlayerInfo() {
                            return networkPlayerInfo;
                        }
                    };
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                entity = (EntityLivingBase) EntityList.createEntityByName(entityList[id], world);
            }
        } while (entity == null && ++tries < entityList.length);

        //fallback
        if (entity == null) {
            LogHelper.warning("Using fallback!");
            return EntityUtils.getRandomLivingEntity(world, entityBlacklist, 5, fallbackPlayerNames);
        }

        if (ConfigElements.ALLOW_DEBUG_OUTPUT.getSetting().getBoolean())
            LogHelper.info(entityList[id]);

        return entity;
    }

    private static void setRandomMobProperties(EntityLivingBase ent) {

//        if (ent instanceof EntityAgeable && random.nextBoolean())
//            ((EntityAgeable) ent).setGrowingAge(-1);

        if (ent instanceof EntitySheep)
            ((EntitySheep) ent).setFleeceColor(EnumDyeColor.values()[random.nextInt(EnumDyeColor.values().length)]);

        else if (ent instanceof EntityVillager)
            ((EntityVillager) ent).setProfession(random.nextInt(5));

        else if (ent instanceof EntityRabbit) {
            int i = random.nextInt(13);
            ((EntityRabbit) ent).setRabbitType(i < 12 ? (i % 2 == 0 ? i / 2 : (i - 1) / 2) : 99);
        }

        //Chicken mod integration.
        else if (Loader.isModLoaded("chickens") && ent instanceof EntityChickensChicken) {
            List<Integer> chickens = getChickenTypes(ChickensRegistry.getItems());
            ((EntityChickensChicken) ent).setChickenType(chickens.get(random.nextInt(chickens.size())));
        }
    }

    private static void setRandomMobItem(EntityLivingBase ent) {
        try {

            if (ent instanceof AbstractClientPlayer)
                ent.setHeldItem(EnumHand.MAIN_HAND, playerItems[random.nextInt(playerItems.length)]);

            else if (ent instanceof EntityPigZombie)
                ent.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.GOLDEN_SWORD));

            else if (ent instanceof EntityZombie)
                ent.setHeldItem(EnumHand.MAIN_HAND, zombieItems[random.nextInt(zombieItems.length)]);

            else if (ent instanceof EntityEnderman) {
                Object[] blocks = EntityEnderman.CARRIABLE_BLOCKS.toArray();
                IBlockState block = ((Block) blocks[random.nextInt(blocks.length)]).getDefaultState();
                ((EntityEnderman) ent).setHeldBlockState(block);
            } else if (ent instanceof EntityHorse) {
                ((EntityHorse) ent).setType(HorseType.getArmorType(random.nextInt(5)));

                switch (((EntityHorse) ent).getType()) {
                    case HORSE:
                        int baseColor = random.nextInt(7);
                        int markings = random.nextInt(5);
                        ((EntityHorse) ent).setHorseVariant(baseColor | markings << 8);

                        if (random.nextBoolean()) {
                            ((EntityHorse) ent).setHorseSaddled(true);
                            ((EntityHorse) ent).setHorseArmorStack(horseArmors[random.nextInt(horseArmors.length)]);
                        }
                        break;
                    case DONKEY:
                    case MULE:
                        ((EntityHorse) ent).setChested(random.nextBoolean());
                }
            } else if (ent instanceof EntitySkeleton) {
                switch (random.nextInt(3)) {
                    case 0:
                        ((EntitySkeleton) ent).func_189768_a(SkeletonType.WITHER);
                        ent.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.GOLDEN_SWORD));
                        break;
                    case 1:
                        ((EntitySkeleton) ent).func_189768_a(SkeletonType.STRAY);
                        ent.setHeldItem(EnumHand.MAIN_HAND, skelItems[random.nextInt(skelItems.length)]);
                        break;
                    case 2:
                        ((EntitySkeleton) ent).func_189768_a(SkeletonType.NORMAL);
                        ent.setHeldItem(EnumHand.MAIN_HAND, skelItems[random.nextInt(skelItems.length)]);
                        break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static EntityOtherPlayerMP getRandomPlayer(World world) {
        final NetworkPlayerInfo networkPlayerInfo;
        GameProfile gameProfile;
        SimpleEntry<UUID, String> entry = fallbackPlayerNames.get(random.nextInt(fallbackPlayerNames.size()));
        gameProfile = mcClient.getSessionService().fillProfileProperties(new GameProfile(entry.getKey(), entry.getValue()), true);
        networkPlayerInfo = new NetworkPlayerInfo(gameProfile);
        return new EntityOtherPlayerMP(world, gameProfile) {
            @Nullable
            @Override
            protected NetworkPlayerInfo getPlayerInfo() {
                return networkPlayerInfo;
            }
        };
    }

    public static String[] getEntStrings() {
        return entStrings;
    }

    @Optional.Method(modid = "chickens")
    private static List<Integer> getChickenTypes(Collection<ChickensRegistryItem> chickens) {
        List<Integer> result = new ArrayList<Integer>();
        for (ChickensRegistryItem chicken : chickens) {
            result.add(chicken.getId());
        }
        return result;
    }

    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent event) {
        if (Loader.isModLoaded("WorldStateCheckpoints")) {
            MenuMobs.instance.showMainMenuMobs = false;
            LogHelper.severe("Main menu mob rendering is known to cause crashes with WorldStateCheckpoints and has been disabled for the remainder of this session.");
            this.unRegister();
        }

        if (MenuMobs.instance.showMainMenuMobs && !erroredOut && isMainMenu(mcClient.currentScreen)) {
            try {
                if ((player == null) || (player.worldObj == null) || (randMob == null))
                    init();

                //In cause of someone resetting the renderer. cough cough... schematica...
                if (mcClient.getRenderManager().worldObj == null || mcClient.getRenderManager().renderViewEntity == null)
                    mcClient.getRenderManager().cacheActiveRenderInfo(world, mcClient.fontRendererObj, player, player, mcClient.gameSettings, 0.0F);

                if ((world != null) && (player != null) && (randMob != null)) {
                    mcClient.thePlayer = player;
                    ScaledResolution sr = new ScaledResolution(mcClient);
                    final int mouseX = (Mouse.getX() * sr.getScaledWidth()) / mcClient.displayWidth;
                    final int mouseY = sr.getScaledHeight() - ((Mouse.getY() * sr.getScaledHeight()) / mcClient.displayHeight) - 1;
                    int distanceToSide = ((mcClient.currentScreen.width / 2) - 98) / 2;
                    float targetHeight = (float) (sr.getScaledHeight_double() / 5.0F) / 1.8F;
                    float scale = EntityUtils.getEntityScale(randMob, targetHeight, 1.8F);
                    EntityUtils.drawEntityOnScreen(
                            distanceToSide,
                            (int) ((sr.getScaledHeight() / 2) + (randMob.height * scale)),
                            scale,
                            distanceToSide - mouseX,
                            ((sr.getScaledHeight() / 2) + (randMob.height * scale)) - (randMob.height * scale * (randMob.getEyeHeight() / randMob.height)) - mouseY,
                            randMob);
                    EntityUtils.drawEntityOnScreen(
                            sr.getScaledWidth() - distanceToSide,
                            (int) ((sr.getScaledHeight() / 2) + (player.height * targetHeight)),
                            targetHeight,
                            sr.getScaledWidth() - distanceToSide - mouseX,
                            ((sr.getScaledHeight() / 2) + (player.height * targetHeight)) - (player.height * targetHeight * (player.getEyeHeight() / player.height)) - mouseY,
                            player);
                }
            } catch (Throwable e) {
                LogHelper.severe("Main menu mob rendering encountered a serious error and has been disabled for the remainder of this session.");
                e.printStackTrace();
                erroredOut = true;
                player = null;
                randMob = null;
                world = null;
            }
            mcClient.thePlayer = null;
        }
    }

    @SubscribeEvent
    public void onGameTick(TickEvent.ClientTickEvent event) {
        if (world != null && randMob != null && event.phase == TickEvent.Phase.START) {

            world.updateEntity(randMob);
            world.updateEntity(player);

            if (randMob instanceof EntityLiving && ConfigElements.MOB_SOUNDS_VOLUME.getSetting().getDouble() > 0.0F) {
                if (randMob.isEntityAlive() && this.random.nextInt(1000) < ((EntityLiving) randMob).livingSoundTime++) {
                    ((EntityLiving) randMob).livingSoundTime = -((EntityLiving) randMob).getTalkInterval();
                    ((EntityLiving) randMob).playLivingSound();
                }
            }
        }
    }

    private void init() {
        try {
            boolean createNewWorld = world == null;
            WorldInfo worldInfo = new WorldInfo(new NBTTagCompound());

            if (createNewWorld)
                world = new FakeWorld(worldInfo);

            if (createNewWorld || (player == null)) {
                player = new EntityPlayerSP(mcClient, world, new NetHandlerPlayClient(mcClient, mcClient.currentScreen, new FakeNetworkManager(EnumPacketDirection.CLIENTBOUND), mcClient.getSession().getProfile()) {
                    @Override
                    public NetworkPlayerInfo getPlayerInfo(UUID uniqueId) {
                        return new NetworkPlayerInfo(mcClient.getSession().getProfile());
                    }

                    @Override
                    public NetworkPlayerInfo getPlayerInfo(String name) {
                        return new NetworkPlayerInfo(mcClient.getSession().getProfile());
                    }
                }, null);
                int ModelParts = 0;
                for (EnumPlayerModelParts enumplayermodelparts : mcClient.gameSettings.getModelParts()) {
                    ModelParts |= enumplayermodelparts.getPartMask();
                }
                player.getDataManager().set(EntityPlayer.PLAYER_MODEL_FLAG, Byte.valueOf((byte) ModelParts));
                player.setPrimaryHand(mcClient.gameSettings.mainHand);
                player.dimension = 0;
                player.movementInput = new MovementInputFromOptions(mcClient.gameSettings);
                player.eyeHeight = 1.82F;
                setRandomMobItem(player);
            }

            if (createNewWorld || (randMob == null)) {
                if (ConfigElements.FIXED_MOB.getSetting().getStringList().length > 0)
                    randMob = getFixedEntity(world);
                else if (ConfigElements.SHOW_ONLY_PLAYER_MODELS.getSetting().getBoolean()) {
                    randMob = getRandomPlayer(world);
                } else if (ConfigElements.ALLOW_DEBUG_OUTPUT.getSetting().getBoolean()) {
                    randMob = getNextEntity(world);
                } else {
                    randMob = EntityUtils.getRandomLivingEntity(world, entityBlacklist, 4, fallbackPlayerNames);
                }
                if (randMob instanceof EntityPlayer)
                    randMob.getDataManager().set(EntityPlayer.PLAYER_MODEL_FLAG, Byte.valueOf((byte) 127));

                setRandomMobProperties(randMob);
                setRandomMobItem(randMob);
            }

            mcClient.getRenderManager().cacheActiveRenderInfo(world, mcClient.fontRendererObj, player, player, mcClient.gameSettings, 0.0F);
        } catch (Throwable e) {
            LogHelper.severe("Main menu mob rendering encountered a serious error and has been disabled for the remainder of this session.");
            e.printStackTrace();
            erroredOut = true;
            player = null;
            randMob = null;
            world = null;
        }
    }

    public void register() {
        if (!isRegistered) {
            LogHelper.info("Enabling Main Menu Mob render ticker");
            MinecraftForge.EVENT_BUS.register(this);
            isRegistered = true;
        }
    }

    public void unRegister() {
        if (isRegistered) {
            LogHelper.info("Disabling Main Menu Mob render ticker");
            MinecraftForge.EVENT_BUS.unregister(this);
            isRegistered = false;
            randMob = null;
            world = null;
            player = null;
        }
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public boolean isMainMenu(GuiScreen gui) {
        boolean flag;

        if (gui == null)
            return false;
        else {
            try {
                flag = gui.getClass().getCanonicalName().equalsIgnoreCase("lumien.custommainmenu.gui.GuiCustom");
            } catch (Exception e) {
                flag = false;
            }
            return gui instanceof GuiMainMenu || flag;
        }
    }
}
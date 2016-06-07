package superbas11.util;

import com.google.common.collect.ImmutableSetMultimap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenPlains;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Provides a fake world that can be used to render entities in client-side GUIs without a world actually running.
 *
 * @author superbas11
 *
 */
public class FakeWorld extends World
{
    public FakeWorld(WorldInfo worldInfo)
    {
//        super(new FakeSaveHandler(), "", new FakeWorldProvider(), new WorldSettings(new WorldInfo(new NBTTagCompound())), null);
//        this.difficultySetting = EnumDifficulty.HARD;
        super(new FakeSaveHandler(),worldInfo, new FakeWorldProvider(),new Profiler(), true);
    }

    @Override
    public BiomeGenBase getBiomeGenForCoords(BlockPos pos) {
        return new BiomeGenPlains(false, (new BiomeGenBase.BiomeProperties("Plains")).setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(0.8F).setRainfall(0.4F));
    }

    @Override
    public BiomeGenBase getBiomeGenForCoordsBody(BlockPos pos) {
        return new BiomeGenPlains(false, (new BiomeGenBase.BiomeProperties("Plains")).setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(0.8F).setRainfall(0.4F));
    }

    @Override
    protected boolean isChunkLoaded(int i, int i1, boolean b) {
        return false;
    }


    @Override
    public BlockPos getTopSolidOrLiquidBlock(BlockPos pos) {
        return new BlockPos(pos.getX(),63,pos.getZ());
    }

    @Override
    public boolean isAirBlock(BlockPos pos) {
        return pos.getY() > 63;
    }

//    @Override
//    public boolean doChunksNearChunkExist(int par1, int par2, int par3, int par4)
//    {
//        return false;
//    }

//    @Override
//    public boolean checkChunksExist(int par1, int par2, int par3, int par4, int par5, int par6)
//    {
//        return false;
//    }


    @Override
    public boolean setBlockState(BlockPos pos, IBlockState newState, int flags) {
        return super.setBlockState(pos, newState, flags);
    }

//    @Override
//    public int getBlockMetadata(int x, int y, int z)
//    {
//        return 0;
//    }

    @Override
    public IBlockState getBlockState(BlockPos pos) {
       return pos.getY() > 63 ? Blocks.air.getDefaultState() : Blocks.grass.getDefaultState();
    }

    @Override
    public boolean setBlockState(BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public boolean setBlockToAir(BlockPos pos) {
        return true;
    }

//    @Override
//    public boolean breakBlock(int x, int y, int z, boolean p_147480_4_)
//    {
//        return this.isAirBlock(x, y, z);
//    }

//    @Override
//    public boolean setBlock(int x, int y, int z, Block p_147449_4_)
//    {
//        return true;
//    }

//    @Override
//    public void markBlockForUpdate(int p_147471_1_, int p_147471_2_, int p_147471_3_)
//    {}

    @Override
    public void markChunkDirty(BlockPos pos, TileEntity unusedTileEntity) {
    }

//    @Override
//    public void notifyBlockChange(int p_147444_1_, int p_147444_2_, int p_147444_3_, Block p_147444_4_)
//    {}

    @Override
    public void notifyBlockUpdate(BlockPos pos, IBlockState oldState, IBlockState newState, int flags) {
    }


    @Override
    public boolean destroyBlock(BlockPos pos, boolean dropBlock) {
        return this.isAirBlock(pos);
    }

    @Override
    public void notifyNeighborsOfStateChange(BlockPos pos, Block blockType) {
    }

    @Override
    public void notifyNeighborsOfStateExcept(BlockPos pos, Block blockType, EnumFacing skipSide) {
    }

    @Override
    public void notifyBlockOfStateChange(BlockPos pos, Block blockIn) {
    }

    @Override
    public void markAndNotifyBlock(BlockPos pos, Chunk chunk, IBlockState iblockstate, IBlockState newState, int flags) {
    }

    @Override
    public void markBlocksDirtyVertical(int par1, int par2, int par3, int par4)
    {}

    @Override
    public void markBlockRangeForRenderUpdate(int p_147458_1_, int p_147458_2_, int p_147458_3_, int p_147458_4_, int p_147458_5_, int p_147458_6_)
    {}

    @Override
    public boolean isBlockTickPending(BlockPos pos, Block blockType) {
        return false;
    }

    @Override
    public int getLightFromNeighbors(BlockPos pos) {
        return 14;
    }

    @Override
    public int getLight(BlockPos pos, boolean checkNeighbors) {
        return 14;
    }

    @Override
    public int getLight(BlockPos pos) {
        return 14;
    }

    @Override
    public int getLightFor(EnumSkyBlock type, BlockPos pos) {
        return 14;
    }

    @Override
    public int getLightFromNeighborsFor(EnumSkyBlock type, BlockPos pos) {
        return 14;
    }

    @Override
    public boolean canBlockSeeSky(BlockPos pos) {
        return pos.getY() > 62;
    }

    @Override
    public BlockPos getHeight(BlockPos pos) {
        return new BlockPos(pos.getX(),63,pos.getZ());
    }


    @Override
    public int getChunksLowestHorizon(int x, int z) {
        return 63;
    }

    @Override
    protected void updateBlocks() {
    }

    @Override
    public void markBlockRangeForRenderUpdate(BlockPos rangeMin, BlockPos rangeMax) {
    }

    @Override
    public void setLightFor(EnumSkyBlock type, BlockPos pos, int lightValue) {
    }


    @Override
    public boolean isDaytime()
    {
        return true;
    }

    @Override
    public void playSound(EntityPlayer player, BlockPos pos, SoundEvent soundIn, SoundCategory category, float volume, float pitch) {
    }

    @Override
    public void playSound(EntityPlayer player, double x, double y, double z, SoundEvent soundIn, SoundCategory category, float volume, float pitch) {
    }

    @Override
    public void playSound(double x, double y, double z, SoundEvent soundIn, SoundCategory category, float volume, float pitch, boolean distanceDelay) {
    }

    @Override
    public void spawnParticle(EnumParticleTypes particleType, boolean p_175682_2_, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int... p_175682_15_) {
    }

    @Override
    public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int... p_175688_14_) {
    }

    @Override
    public void playRecord(BlockPos blockPositionIn, SoundEvent soundEventIn) {
    }

    @Override
    public RayTraceResult rayTraceBlocks(Vec3d start, Vec3d end, boolean stopOnLiquid) {
        return null;
    }

    @Override
    public RayTraceResult rayTraceBlocks(Vec3d start, Vec3d end) {
        return null;
    }

    @Override
    public RayTraceResult rayTraceBlocks(Vec3d vec31, Vec3d vec32, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
        return null;
    }


    @Override
    public boolean addWeatherEffect(Entity par1Entity)
    {
        return false;
    }

    @Override
    public boolean spawnEntityInWorld(Entity par1Entity)
    {
        return false;
    }

    @Override
    public void onEntityAdded(Entity par1Entity)
    {}

    @Override
    public void onEntityRemoved(Entity par1Entity)
    {}

    @Override
    public void removeEntity(Entity par1Entity)
    {}

    @Override
    public void removePlayerEntityDangerously(Entity par1Entity)
    {}

    @Override
    public int calculateSkylightSubtracted(float par1)
    {
        return 6;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public int getMoonPhase()
    {
        return super.getMoonPhase();
    }

    @Override
    public float getCurrentMoonPhaseFactor()
    {
        return super.getCurrentMoonPhaseFactor();
    }

    @Override
    public float getCelestialAngleRadians(float par1)
    {
        return super.getCelestialAngleRadians(par1);
    }

    @Override
    public void scheduleBlockUpdate(BlockPos pos, Block blockIn, int delay, int priority) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1)
    {
        return super.getStarBrightness(par1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getStarBrightnessBody(float par1)
    {
        return super.getStarBrightnessBody(par1);
    }

//    @Override
//    public void func_147446_b(int p_147446_1_, int p_147446_2_, int p_147446_3_, Block p_147446_4_, int p_147446_5_, int p_147446_6_)
//    {}

    @Override
    public void updateEntities()
    {}

//    @SuppressWarnings("rawtypes")
//    @Override
//    public void func_147448_a(Collection p_147448_1_)
//    {}

    @Override
    public void updateEntity(Entity par1Entity)
    {}

    @Override
    public void updateEntityWithOptionalForce(Entity par1Entity, boolean par2)
    {}

    @Override
    public boolean checkNoEntityCollision(AxisAlignedBB bb) {
        return true;
    }

    @Override
    public boolean checkNoEntityCollision(AxisAlignedBB bb, Entity entityIn) {
        return true;
    }

    @Override
    public boolean checkBlockCollision(AxisAlignedBB bb) {
        return false;
    }

    @Override
    public boolean isAnyLiquid(AxisAlignedBB par1AxisAlignedBB)
    {
        return false;
    }

//    @Override
//    public boolean func_147470_e(AxisAlignedBB p_147470_1_)
//    {
//        return false;
//    }

    @Override
    public boolean handleMaterialAcceleration(AxisAlignedBB par1AxisAlignedBB, Material par2Material, Entity par3Entity)
    {
        return false;
    }

    @Override
    public boolean isMaterialInBB(AxisAlignedBB par1AxisAlignedBB, Material par2Material)
    {
        return false;
    }

    @Override
    public boolean isAABBInMaterial(AxisAlignedBB par1AxisAlignedBB, Material par2Material)
    {
        return false;
    }

    @Override
    public Explosion createExplosion(Entity par1Entity, double par2, double par4, double par6, float par8, boolean par9)
    {
        return super.createExplosion(par1Entity, par2, par4, par6, par8, par9);
    }

    @Override
    public Explosion newExplosion(Entity par1Entity, double par2, double par4, double par6, float par8, boolean par9, boolean par10)
    {
        return super.newExplosion(par1Entity, par2, par4, par6, par8, par9, par10);
    }


    @Override
    public float getBlockDensity(Vec3d vec, AxisAlignedBB bb) {
        return super.getBlockDensity(vec, bb);
    }

    @Override
    public TileEntity getTileEntity(BlockPos pos) {
        return null;
    }

    @Override
    public boolean extinguishFire(EntityPlayer player, BlockPos pos, EnumFacing side) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getDebugLoadedEntities()
    {
        return "";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getProviderName()
    {
        return "";
    }

    @Override
    public void setTileEntity(BlockPos pos, TileEntity tileEntityIn) {
    }

    @Override
    public void removeTileEntity(BlockPos pos) {
    }

    @Override
    public void markTileEntityForRemoval(TileEntity p_147457_1_)
    {}

    @Override
    public boolean isBlockFullCube(BlockPos pos) {
        return super.isBlockFullCube(pos);
    }

    @Override
    public boolean isBlockNormalCube(BlockPos pos, boolean _default) {
        return true;
    }

    @Override
    public void calculateInitialSkylight()
    {
        super.calculateInitialSkylight();
    }

    @Override
    public void setAllowedSpawnTypes(boolean par1, boolean par2)
    {
        super.setAllowedSpawnTypes(par1, par2);
    }

    @Override
    public void tick()
    {}

    @Override
    public void calculateInitialWeatherBody()
    {
        super.calculateInitialWeatherBody();
    }

    @Override
    protected void updateWeather()
    {}

    @Override
    public void updateWeatherBody()
    {}

//    @Override
//    protected void func_147467_a(int p_147467_1_, int p_147467_2_, Chunk p_147467_3_)
//    {}
//
//    @Override
//    protected void func_147456_g()
//    {}


    @Override
    public boolean canBlockFreezeWater(BlockPos pos) {
        return false;
    }

    @Override
    public boolean canBlockFreezeNoWater(BlockPos pos) {
        return false;
    }

    @Override
    public boolean canBlockFreeze(BlockPos pos, boolean noWaterAdj) {
        return false;
    }

    @Override
    public boolean canBlockFreezeBody(BlockPos pos, boolean noWaterAdj) {
        return false;
    }

    @Override
    public boolean canSnowAt(BlockPos pos, boolean checkLight) {
        return false;
    }

    @Override
    public boolean canSnowAtBody(BlockPos pos, boolean checkLight) {
        return false;
    }

//    @Override
//    public boolean updateAllLightTypes(int p_147451_1_, int p_147451_2_, int p_147451_3_)
//    {
//        return false;
//    }
//
//    @Override
//    public boolean updateLightByType(EnumSkyBlock p_147463_1_, int p_147463_2_, int p_147463_3_, int p_147463_4_)
//    {
//        return false;
//    }

    @Override
    public boolean tickUpdates(boolean par1)
    {
        return false;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List getPendingBlockUpdates(Chunk par1Chunk, boolean par2)
    {
        return null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List getEntitiesWithinAABBExcludingEntity(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB)
    {
        return super.getEntitiesWithinAABBExcludingEntity(par1Entity, par2AxisAlignedBB);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List getEntitiesWithinAABB(Class par1Class, AxisAlignedBB par2AxisAlignedBB)
    {
        return super.getEntitiesWithinAABB(par1Class, par2AxisAlignedBB);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Entity findNearestEntityWithinAABB(Class par1Class, AxisAlignedBB par2AxisAlignedBB, Entity par3Entity)
    {
        return null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    @SideOnly(Side.CLIENT)
    public List getLoadedEntityList()
    {
        return super.getLoadedEntityList();
    }

    @Override
    public void loadEntities(Collection<Entity> entityCollection) {
    }

    @Override
    public void unloadEntities(Collection<Entity> entityCollection) {
    }

    @SuppressWarnings("rawtypes")
    @Override
    public int countEntities(Class par1Class)
    {
        return 0;
    }

    @Override
    public int getStrongPower(BlockPos pos) {
        return 0;
    }

    @Override
    public int getStrongPower(BlockPos pos, EnumFacing direction) {
        return 0;
    }

    @Override
    public boolean isSidePowered(BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public int getRedstonePower(BlockPos pos, EnumFacing facing) {
        return 0;
    }

    @Override
    public boolean isBlockPowered(BlockPos pos) {
        return false;
    }

    @Override
    public int isBlockIndirectlyGettingPowered(BlockPos pos) {
        return 0;
    }

    @Override
    public EntityPlayer getClosestPlayerToEntity(Entity par1Entity, double par2)
    {
        return super.getClosestPlayerToEntity(par1Entity, par2);
    }

    @Override
    public EntityPlayer getPlayerEntityByName(String par1Str)
    {
        return super.getPlayerEntityByName(par1Str);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void sendQuittingDisconnectingPacket()
    {
        super.sendQuittingDisconnectingPacket();
    }

    @Override
    public void checkSessionLock() throws MinecraftException
    {}

    @Override
    public long getSeed()
    {
        return 1;
    }

    @Override
    public long getTotalWorldTime()
    {
        return 1;
    }

    @Override
    public long getWorldTime()
    {
        return 1;
    }

    @Override
    public void setWorldTime(long par1)
    {}

    @Override
    public BlockPos getSpawnPoint()
    {
        return new BlockPos(0, 64, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void joinEntityInSurroundings(Entity par1Entity)
    {}

    @Override
    public boolean canSeeSky(BlockPos pos) {
        return pos.getY() > 63;
    }

    @Override
    public boolean canMineBlockBody(EntityPlayer player, BlockPos pos) {
        return false;
    }

    @Override
    public void setEntityState(Entity par1Entity, byte par2)
    {}

    @Override
    public IChunkProvider getChunkProvider()
    {
        return super.getChunkProvider();
    }

    @Override
    public float getThunderStrength(float delta) {
        return 0.0F;
    }

    @Override
    public void addBlockEvent(BlockPos pos, Block blockIn, int eventID, int eventParam) {
    }

    @Override
    public ISaveHandler getSaveHandler()
    {
        return super.getSaveHandler();
    }

    @Override
    public WorldInfo getWorldInfo()
    {
        return super.getWorldInfo();
    }

    @Override
    public GameRules getGameRules()
    {
        return super.getGameRules();
    }

    @Override
    public void updateAllPlayersSleepingFlag()
    {}

    @Override
    public boolean isRainingAt(BlockPos strikePosition) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setThunderStrength(float p_147442_1_)
    {}

    @Override
    public float getRainStrength(float par1)
    {
        return 0.0F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setRainStrength(float par1)
    {}

    @Override
    public boolean isThundering()
    {
        return false;
    }

    @Override
    public boolean isRaining()
    {
        return false;
    }

    @Override
    public boolean isBlockinHighHumidity(BlockPos pos) {
        return false;
    }

    @Override
    public void setItemData(String par1Str, WorldSavedData par2WorldSavedData)
    {}

    @SuppressWarnings("rawtypes")
    @Override
    public WorldSavedData loadItemData(Class par1Class, String par2Str)
    {
        return super.loadItemData(par1Class, par2Str);
    }

    @Override
    public int getUniqueDataId(String par1Str)
    {
        return super.getUniqueDataId(par1Str);
    }

    @Override
    public void playBroadcastSound(int p_175669_1_, BlockPos pos, int p_175669_3_) {
    }

    @Override
    public void playAuxSFX(int p_175718_1_, BlockPos pos, int p_175718_3_) {
    }

    @Override
    public void playAuxSFXAtEntity(EntityPlayer player, int sfxType, BlockPos pos, int p_180498_4_) {
    }

    @Override
    public int getHeight()
    {
        return 256;
    }

    @Override
    public int getActualHeight()
    {
        return 256;
    }

    @Override
    public Random setRandomSeed(int par1, int par2, int par3)
    {
        return super.setRandomSeed(par1, par2, par3);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean extendedLevelsInChunkCache()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getHorizon()
    {
        return super.getHorizon();
    }

    @Override
    public CrashReportCategory addWorldInfoToCrashReport(CrashReport par1CrashReport)
    {
        return super.addWorldInfoToCrashReport(par1CrashReport);
    }

    @Override
    public Calendar getCurrentDate()
    {
        return super.getCurrentDate();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void makeFireworks(double par1, double par3, double par5, double par7, double par9, double par11, NBTTagCompound par13nbtTagCompound)
    {}

    @Override
    public Scoreboard getScoreboard()
    {
        return super.getScoreboard();
    }

    @Override
    public boolean addTileEntity(TileEntity tile) {
        return true;
    }

    @Override
    public void addTileEntities(Collection<TileEntity> tileEntityCollection) {
    }

    @Override
    public boolean isSideSolid(BlockPos pos, EnumFacing side) {
        return pos.getY() <= 63;
    }

    @Override
    public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default) {
        return pos.getY() <= 63;
    }

    @Override
    public ImmutableSetMultimap<ChunkCoordIntPair, Ticket> getPersistentChunks()
    {
        return super.getPersistentChunks();
    }

    @Override
    public int countEntities(EnumCreatureType type, boolean forSpawnCount)
    {
        return 0;
    }

    @Override
    protected IChunkProvider createChunkProvider()
    {
        return new FakeChunkProvider();
    }

    @Override
    public Entity getEntityByID(int i)
    {
        return EntityList.createEntityByID(i, this);
    }

    @Override
    public Chunk getChunkFromChunkCoords(int par1, int par2)
    {
        return null;
    }

    protected static class FakeWorldProvider extends WorldProvider
    {
        @Override
        public DimensionType getDimensionType() {
            return DimensionType.OVERWORLD;
        }

        @Override
        protected void generateLightBrightnessTable()
        {}

        @Override
        protected void registerWorldChunkManager()
        {
            super.registerWorldChunkManager();
        }

        @Override
        public boolean isSurfaceWorld()
        {
            return true;
        }

        @Override
        public boolean canRespawnHere()
        {
            return true;
        }

        @Override
        public int getAverageGroundLevel()
        {
            return 63;
        }


        @Override
        @SideOnly(Side.CLIENT)
        public double getVoidFogYFactor()
        {
            return super.getVoidFogYFactor();
        }

        @Override
        @SideOnly(Side.CLIENT)
        public boolean doesXZShowFog(int par1, int par2)
        {
            return false;
        }

        @Override
        public void setDimension(int dim)
        {}

        @Override
        public String getSaveFolder()
        {
            return null;
        }

        @Override
        public String getWelcomeMessage()
        {
            return "";
        }

        @Override
        public String getDepartMessage()
        {
            return "";
        }

        @Override
        public double getMovementFactor()
        {
            return super.getMovementFactor();
        }

        @Override
        @SideOnly(Side.CLIENT)
        public IRenderHandler getSkyRenderer()
        {
            return super.getSkyRenderer();
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void setSkyRenderer(IRenderHandler skyRenderer)
        {
            super.setSkyRenderer(skyRenderer);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public IRenderHandler getCloudRenderer()
        {
            return super.getCloudRenderer();
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void setCloudRenderer(IRenderHandler renderer)
        {
            super.setCloudRenderer(renderer);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public IRenderHandler getWeatherRenderer()
        {
            return super.getWeatherRenderer();
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void setWeatherRenderer(IRenderHandler renderer)
        {
            super.setWeatherRenderer(renderer);
        }

        @Override
        public BlockPos getRandomizedSpawnPoint() {
            return new BlockPos(0,64,0);
        }

        @Override
        public boolean shouldMapSpin(String entity, double x, double y, double z)
        {
            return false;
        }

        @Override
        public int getRespawnDimension(EntityPlayerMP player)
        {
            return 0;
        }

        @Override
        public BiomeGenBase getBiomeGenForCoords(BlockPos pos)
        {
            return new BiomeGenPlains(false, (new BiomeGenBase.BiomeProperties("Plains")).setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(0.8F).setRainfall(0.4F));
        }

        @Override
        public boolean isDaytime()
        {
            return true;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getStarBrightness(float par1)
        {
            return super.getStarBrightness(par1);
        }

        @Override
        public void setAllowedSpawnTypes(boolean allowHostile, boolean allowPeaceful)
        {}

        @Override
        public void calculateInitialWeather()
        {}

        @Override
        public void updateWeather()
        {}

        @Override
        public boolean canBlockFreeze(BlockPos pos, boolean byWater) {
            return false;
        }

        @Override
        public boolean canSnowAt(BlockPos pos, boolean checkLight) {
            return false;
        }

        @Override
        public void setWorldTime(long time)
        {}

        @Override
        public long getSeed()
        {
            return 1;
        }

        @Override
        public long getWorldTime()
        {
            return 1;
        }

        @Override
        public boolean canMineBlock(EntityPlayer player, BlockPos pos)
        {
            return false;
        }

        @Override
        public boolean isBlockHighHumidity(BlockPos pos)
        {
            return false;
        }

        @Override
        public int getHeight()
        {
            return 256;
        }

        @Override
        public int getActualHeight()
        {
            return 256;
        }

        @Override
        public double getHorizon()
        {
            return super.getHorizon();
        }

        @Override
        public void resetRainAndThunder()
        {}

        @Override
        public boolean canDoLightning(Chunk chunk)
        {
            return false;
        }

        @Override
        public boolean canDoRainSnowIce(Chunk chunk)
        {
            return false;
        }

        @Override
        public BlockPos getSpawnPoint()
        {
            return new BlockPos(0, 64, 0);
        }

        @Override
        public boolean canCoordinateBeSpawn(int par1, int par2)
        {
            return true;
        }
    }

    protected static class FakeSaveHandler implements ISaveHandler
    {
        @Override
        public TemplateManager getStructureTemplateManager() {
            return new TemplateManager();
        }

        @Override
        public WorldInfo loadWorldInfo()
        {
            return null;
        }

        @Override
        public void checkSessionLock()
        {}

        @Override
        public IChunkLoader getChunkLoader(WorldProvider var1)
        {
            return null;
        }

        @Override
        public void saveWorldInfoWithPlayer(WorldInfo var1, NBTTagCompound var2)
        {}

        @Override
        public void saveWorldInfo(WorldInfo var1)
        {}

        @Override
        public IPlayerFileData getPlayerNBTManager()
        {
            return null;
        }

        @Override
        public void flush()
        {}

        @Override
        public File getWorldDirectory()
        {
            return null;
        }

        @Override
        public File getMapFileFromName(String var1)
        {
            return null;
        }
    }

    public static class FakeChunkProvider implements IChunkProvider
    {
        @Override
        public Chunk getLoadedChunk(int x, int z) {
            return null;
        }

        @Override
        public Chunk provideChunk(int var1, int var2)
        {
            return null;
        }

        @Override
        public boolean unloadQueuedChunks()
        {
            return false;
        }

        @Override
        public String makeString()
        {
            return null;
        }
    }
}
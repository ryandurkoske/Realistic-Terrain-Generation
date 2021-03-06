package enhancedbiomes.world.gen.layer;

import enhancedbiomes.world.biome.EnhancedBiomesArchipelago;
import enhancedbiomes.world.biome.EnhancedBiomesBiome;
import enhancedbiomes.world.biome.EnhancedBiomesRock;
import enhancedbiomes.world.biome.EnhancedBiomesSandstone;
import enhancedbiomes.world.biome.EnhancedBiomesSnow;
import enhancedbiomes.world.biome.EnhancedBiomesWetland;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerEBRiverMix extends GenLayer
{
    private GenLayer biomePatternGeneratorChain;
    private GenLayer riverPatternGeneratorChain;

    public GenLayerEBRiverMix(long par1, GenLayer par3GenLayer, GenLayer par4GenLayer)
    {
        super(par1);
        this.biomePatternGeneratorChain = par3GenLayer;
        this.riverPatternGeneratorChain = par4GenLayer;
    }

    /**
     * Initialize layer's local worldGenSeed based on its own baseSeed and the world's global seed (passed in as an
     * argument).
     */
    public void initWorldGenSeed(long par1)
    {
        this.biomePatternGeneratorChain.initWorldGenSeed(par1);
        this.riverPatternGeneratorChain.initWorldGenSeed(par1);
        super.initWorldGenSeed(par1);
    }

    /**
     * Returns a list of integer values generated by this layer. These may be interpreted as temperatures, rainfall
     * amounts, or biomeList[] indices based on the particular GenLayerEB subclass.
     */
    public int[] getInts(int par1, int par2, int par3, int par4)
    {
        int[] aint = this.biomePatternGeneratorChain.getInts(par1, par2, par3, par4);
        int[] aint1 = this.riverPatternGeneratorChain.getInts(par1, par2, par3, par4);
        int[] aint2 = IntCache.getIntCache(par3 * par4);

        for (int i1 = 0; i1 < par3 * par4; ++i1)
        {
            if (aint[i1] != BiomeGenBase.ocean.biomeID && aint[i1] != BiomeGenBase.deepOcean.biomeID && aint[i1] != EnhancedBiomesWetland.biomeLake.biomeID)
            {
                if (aint1[i1] == BiomeGenBase.river.biomeID || aint1[i1] == EnhancedBiomesBiome.biomeRiparian.biomeID)
                {
                	boolean isBiomeChanged = false;
                	if (aint[i1] == BiomeGenBase.frozenRiver.biomeID)
                    {
                        aint2[i1] = BiomeGenBase.frozenRiver.biomeID;
                    }
                	else if (aint[i1] == BiomeGenBase.icePlains.biomeID)
                    {
                        aint2[i1] = BiomeGenBase.frozenRiver.biomeID;
                    }
                	else if (aint[i1] == EnhancedBiomesSnow.biomeTundra.biomeID || 
                		aint[i1] == EnhancedBiomesSnow.biomeIceSheet.biomeID || 
                		aint[i1] == EnhancedBiomesSnow.biomeSnowDesert.biomeID || 
                		aint[i1] == EnhancedBiomesArchipelago.biomeSnowArchipelago.biomeID || 
                		aint[i1] == EnhancedBiomesSnow.biomePolarDesert.biomeID)
                    {
                        aint2[i1] = BiomeGenBase.frozenRiver.biomeID;
                    }
                	else if (aint[i1] == EnhancedBiomesSnow.biomeAlpine.biomeID)
                    {
                        aint2[i1] = EnhancedBiomesSnow.biomeGlacier.biomeID;
                    }
                	else if (aint[i1] == EnhancedBiomesSandstone.biomeSandStoneCanyon.biomeID)
                    {
                        aint2[i1] = EnhancedBiomesSandstone.biomeSandStoneGorge.biomeID;
                    }
                	else if (aint[i1] == EnhancedBiomesRock.biomeStoneCanyon.biomeID)
                    {
                        aint2[i1] = EnhancedBiomesRock.biomeStoneGorge.biomeID;
                    }
                	else if (aint[i1] == EnhancedBiomesWetland.biomeEphemeralLake.biomeID)
                    {
                        aint2[i1] = EnhancedBiomesWetland.biomeEphemeralLake.biomeID;
                    }
                	else if (aint[i1] == EnhancedBiomesSandstone.biomeSandStoneRanges.biomeID)
                    {
                        aint2[i1] = EnhancedBiomesSandstone.biomeCreekBed.biomeID;
                    }
                	else if (BiomeGenBase.getBiomeGenArray()[aint[i1] % 256].temperature <= 0)
                    {
                        aint2[i1] = BiomeGenBase.frozenRiver.biomeID;                        
                    }
                	else if (aint[i1] == BiomeGenBase.mushroomIsland.biomeID && aint[i1] == BiomeGenBase.mushroomIslandShore.biomeID)
                    {
                    	aint2[i1] = BiomeGenBase.mushroomIslandShore.biomeID;
                    }
                	else
                    {
                    	aint2[i1] = aint1[i1] & 255;
                    }
                }
                else
                {
                    aint2[i1] = aint[i1];
                }
            }
            else
            {
                aint2[i1] = aint[i1];
            }
        }

        return aint2;
    }
}
package rtg.world.gen.surface.vanilla;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.ChunkPrimer;
import rtg.api.biome.BiomeConfig;
import rtg.util.CanyonColour;
import rtg.util.CellNoise;
import rtg.util.CliffCalculator;
import rtg.util.OpenSimplexNoise;
import rtg.world.gen.surface.SurfaceBase;

import java.util.Random;

public class SurfaceVanillaMesa extends SurfaceBase {
    public SurfaceVanillaMesa(BiomeConfig config, IBlockState top, IBlockState fill) {
        super(config, top, fill);
    }

    @Override
    public void paintTerrain(ChunkPrimer primer, int i, int j, int x, int y, int depth, World world, Random rand,
                             OpenSimplexNoise simplex, CellNoise cell, float[] noise, float river, BiomeGenBase[] base) {

        float c = CliffCalculator.calc(x, y, noise);
        boolean cliff = c > 1.3f;

        for (int k = 255; k > -1; k--) {
            Block b = primer.getBlockState(x, k, y).getBlock();
            if (b == Blocks.air) {
                depth = -1;
            } else if (b == Blocks.stone) {
                depth++;

                if (depth > -1 && depth < 12) {
                    if (cliff) {
                        primer.setBlockState(x, k, y, CanyonColour.MESA.getForHeight(i, k, j));
                    } else {
                        if (depth > 4) {
                            primer.setBlockState(x, k, y, CanyonColour.MESA.getForHeight(i, k, j));
                        } else if (k > 77) {
                            if (rand.nextInt(5) == 0) {
                                primer.setBlockState(x, k, y, Blocks.dirt.getDefaultState());
                            } else {
                                if (depth == 0) {
                                    primer.setBlockState(x, k, y, topBlock);
                                } else {
                                    primer.setBlockState(x, k, y, fillerBlock);
                                }
                            }
                        } else if (k < 69) {
                            primer.setBlockState(x, k, y, Blocks.dirt.getDefaultState());
                        } else if (k < 78) {
                            if (depth == 0) {
                                if (k < 72 && rand.nextInt(k - 69 + 1) == 0) {
                                    primer.setBlockState(x, k, y, Blocks.dirt.getDefaultState());
                                } else if (rand.nextInt(5) == 0) {
                                    primer.setBlockState(x, k, y, Blocks.dirt.getDefaultState());
                                } else {
                                    primer.setBlockState(x, k, y, topBlock);
                                }
                            } else {
                                primer.setBlockState(x, k, y, fillerBlock);
                            }
                        } else {
                            if (depth == 0) {
                                primer.setBlockState(x, k, y, topBlock);
                            } else {
                                primer.setBlockState(x, k, y, fillerBlock);
                            }
                        }
                    }
                } else if (k > 63) {
                    primer.setBlockState(x, k, y, CanyonColour.MESA.getForHeight(i, k, j));
                }
            }
        }
    }
}

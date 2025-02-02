package dev.soulfur.skyblock.islands;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.World;
import org.bukkit.util.noise.SimplexOctaveGenerator;
import org.bukkit.generator.BlockPopulator;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SkyblockGenerator extends ChunkGenerator {
    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        ChunkData chunkData = createChunkData(world);

        // Generate a flat world with a single layer of bedrock at the bottom
        if (chunkX == 0 && chunkZ == 0) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    chunkData.setBlock(x, 0, z, Material.BEDROCK);
                }
            }
        }

        return chunkData;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Collections.emptyList();
    }
}
package com.flowpowered.engine.geo.world;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.flowpowered.api.generator.WorldGenerator;
import com.flowpowered.api.geo.ServerWorld;
import com.flowpowered.api.geo.discrete.Point;
import com.flowpowered.api.geo.discrete.Transform;
import com.flowpowered.api.io.bytearrayarray.BAAWrapper;
import com.flowpowered.engine.SpoutEngine;
import com.flowpowered.engine.filesystem.WorldFiles;
import com.flowpowered.engine.geo.region.RegionFileManager;
import com.flowpowered.math.imaginary.Quaternionf;
import com.flowpowered.math.vector.Vector3f;
import com.flowpowered.engine.filesystem.SpoutFileSystem;

public class SpoutServerWorld extends SpoutWorld implements ServerWorld {
    private final WorldGenerator generator;
    private final long seed;
	/**
	 * The spawn position.
	 */
	private final Transform spawnLocation;
	/**
	 * RegionFile manager for the world
	 */
	private final RegionFileManager regionFileManager;

    public SpoutServerWorld(SpoutEngine engine, String name, UUID uid, long age, WorldGenerator generator, long seed) {
        super(engine, name, uid, age);
        this.spawnLocation = new Transform(new Point(this, 0, 0, 0), Quaternionf.IDENTITY, Vector3f.ONE);
        this.generator = generator;
        this.seed = seed;
        this.regionFileManager = new RegionFileManager(new File(SpoutFileSystem.WORLDS_DIRECTORY, name));
    }

    public SpoutServerWorld(SpoutEngine engine, String name, WorldGenerator generator) {
        super(engine, name);
        this.spawnLocation = new Transform(new Point(this, 0, 0, 0), Quaternionf.IDENTITY, Vector3f.ONE);
        this.generator = generator;
        this.seed = new Random().nextLong();
        this.regionFileManager = new RegionFileManager(new File(SpoutFileSystem.WORLDS_DIRECTORY, name));
    }

    @Override
    public WorldGenerator getGenerator() {
        return generator;
    }

    @Override
    public long getSeed() {
        return seed;
    }

    @Override
    public Transform getSpawnPoint() {
        return spawnLocation;
    }

    @Override
    public void setSpawnPoint(Transform transform) {
        spawnLocation.set(transform);
    }

    @Override
    public void unload(boolean save) {
    }

    @Override
    public void save() {
        WorldFiles.saveWorld(this);
    }

    @Override
    public File getDirectory() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void queueChunksForGeneration(List<Vector3f> chunks) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void queueChunkForGeneration(Vector3f chunk) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

	public BAAWrapper getRegionFile(int rx, int ry, int rz) {
		if (regionFileManager == null) {
			throw new IllegalStateException("Client does not have file manager");
		}
		return regionFileManager.getBAAWrapper(rx, ry, rz);
	}
}
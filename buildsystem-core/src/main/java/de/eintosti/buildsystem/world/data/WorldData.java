/*
 * Copyright (c) 2023, Thomas Meaney
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */
package de.eintosti.buildsystem.world.data;

import com.cryptomorin.xseries.XMaterial;
import de.eintosti.buildsystem.config.ConfigValues;
import org.bukkit.Difficulty;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WorldData implements ConfigurationSerializable {

    private final Map<String, Type<?>> data = new HashMap<>();

    private final Type<String> customSpawn = register("spawn");
    private final Type<String> permission = register("permission");
    private final Type<String> project = register("project");

    private final Type<Difficulty> difficulty = register("difficulty", new DifficultyType());
    private final Type<XMaterial> material = register("material", new MaterialType());
    private final Type<WorldStatus> status = register("status", new StatusType());

    private final Type<Boolean> blockBreaking = register("block-breaking");
    private final Type<Boolean> blockInteractions = register("block-interactions");
    private final Type<Boolean> blockPlacement = register("block-placement");
    private final Type<Boolean> buildersEnabled = register("builders-enabled");
    private final Type<Boolean> explosions = register("explosions");
    private final Type<Boolean> mobAi = register("mob-ai");
    private final Type<Boolean> physics = register("physics");
    private final Type<Boolean> privateWorld = register("private");

    private final Type<Long> lastEdited = register("last-edited");
    private final Type<Long> lastLoaded = register("last-loaded");
    private final Type<Long> lastUnloaded = register("last-unloaded");

    public <T> Type<T> register(@NotNull String key) {
        return register(key, new Type<>());
    }

    public <T> Type<T> register(@NotNull String key, Type<T> type) {
        this.data.put(key, type);
        return type;
    }

    public WorldData(String name, ConfigValues configValues, boolean privateWorld) {
        this.customSpawn.set(null);
        this.permission.set(configValues.getDefaultPermission(privateWorld).replace("%world%", name));
        this.project.set("-");

        this.difficulty.set(configValues.getWorldDifficulty());
        this.status.set(WorldStatus.NOT_STARTED);

        this.blockBreaking.set(configValues.isWorldBlockBreaking());
        this.blockInteractions.set(configValues.isWorldBlockInteractions());
        this.blockPlacement.set(configValues.isWorldBlockPlacement());
        this.buildersEnabled.set(configValues.isWorldBuildersEnabled(privateWorld));
        this.explosions.set(configValues.isWorldExplosions());
        this.mobAi.set(configValues.isWorldMobAi());
        this.physics.set(configValues.isWorldPhysics());
        this.privateWorld.set(privateWorld);

        this.lastEdited.set((long) -1);
        this.lastLoaded.set((long) -1);
        this.lastUnloaded.set((long) -1);
    }

    public WorldData(String customSpawn, String permission, String project, Difficulty difficulty, XMaterial material, WorldStatus worldStatus, boolean blockBreaking, boolean blockInteractions, boolean blockPlacement, boolean buildersEnabled, boolean explosions, boolean mobAi, boolean physics, boolean privateWorld, long lastLoaded, long lastUnloaded, long lastEdited) {
        this.customSpawn.set(customSpawn);
        this.permission.set(permission);
        this.project.set(project);

        this.difficulty.set(difficulty);
        this.material.set(material);
        this.status.set(worldStatus);

        this.blockBreaking.set(blockBreaking);
        this.blockInteractions.set(blockInteractions);
        this.blockPlacement.set(blockPlacement);
        this.buildersEnabled.set(buildersEnabled);
        this.explosions.set(explosions);
        this.mobAi.set(mobAi);
        this.physics.set(physics);
        this.privateWorld.set(privateWorld);

        this.lastEdited.set(lastEdited);
        this.lastLoaded.set(lastLoaded);
        this.lastUnloaded.set(lastUnloaded);
    }

    public Type<String> customSpawn() {
        return customSpawn;
    }

    public Type<String> permission() {
        return permission;
    }

    public Type<String> project() {
        return project;
    }

    public Type<Difficulty> difficulty() {
        return difficulty;
    }

    public Type<XMaterial> material() {
        return material;
    }

    public Type<WorldStatus> status() {
        return status;
    }

    public Type<Boolean> blockBreaking() {
        return blockBreaking;
    }

    public Type<Boolean> blockInteractions() {
        return blockInteractions;
    }

    public Type<Boolean> blockPlacement() {
        return blockPlacement;
    }

    public Type<Boolean> buildersEnabled() {
        return buildersEnabled;
    }

    public Type<Boolean> explosions() {
        return explosions;
    }

    public Type<Boolean> mobAi() {
        return mobAi;
    }

    public Type<Boolean> physics() {
        return physics;
    }

    public Type<Boolean> privateWorld() {
        return privateWorld;
    }

    public Type<Long> lastEdited() {
        return lastEdited;
    }

    public Type<Long> lastLoaded() {
        return lastLoaded;
    }

    public Type<Long> lastUnloaded() {
        return lastUnloaded;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return data.entrySet().stream()
                .filter(entry -> entry.getValue().get() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getConfigFormat()));
    }

    public static class Type<T> {
        private T value;

        public T get() {
            return value;
        }

        public void set(T value) {
            this.value = value;
        }

        protected Object getConfigFormat() {
            return value;
        }
    }

    public static class DifficultyType extends Type<Difficulty> {
        @Override
        protected Object getConfigFormat() {
            return super.get().name();
        }
    }

    public static class MaterialType extends Type<XMaterial> {
        @Override
        protected Object getConfigFormat() {
            return super.get().name();
        }
    }

    public static class StatusType extends Type<WorldStatus> {
        @Override
        protected Object getConfigFormat() {
            return super.get().name();
        }
    }
}
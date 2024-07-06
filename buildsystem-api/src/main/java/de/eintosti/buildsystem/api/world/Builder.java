/*
 * Copyright (c) 2018-2024, Thomas Meaney
 * Copyright (c) contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package de.eintosti.buildsystem.api.world;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface Builder {

    /**
     * Creates a new {@link Builder} instance with the given uuid and name.
     *
     * @param uuid The uuid
     * @param name The name
     * @return The builder
     */
    static Builder of(UUID uuid, String name) {
        return new BuilderImpl(uuid, name);
    }

    /**
     * Creates a new {@link Builder} instance using the given player.
     *
     * @param player The player
     * @return The builder
     */
    static Builder of(Player player) {
        return of(player.getUniqueId(), player.getName());
    }

    /**
     * Creates a new {@link Builder} instance using a serialized string.
     * <p>
     * The format of the string must be {@code <uuid>,<name>}.
     *
     * @param serialized The serialized builder
     * @return The builder if all the input is valid, otherwise {@code null}
     */
    @Nullable
    static Builder deserialize(@Nullable String serialized) {
        if (serialized == null) {
            return null;
        }

        String[] parts = serialized.split(BuilderImpl.SEPARATOR);
        if (parts.length != 2) {
            return null;
        }

        return of(UUID.fromString(parts[0]), parts[1]);
    }


    /**
     * Returns a unique and persistent id for the builder.
     * <p>
     * Should be equal to the corresponding {@link Player}'s unique id.
     *
     * @return The uuid
     * @see Player#getUniqueId()
     */
    UUID getUniqueId();

    /**
     * Gets the name of the builder.
     *
     * @return The builder name
     */
    String getName();

    /**
     * Sets the name of the builder.
     *
     * @param name The name to change to
     */
    void setName(String name);
}
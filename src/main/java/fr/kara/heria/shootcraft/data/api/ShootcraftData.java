package fr.kara.heria.shootcraft.data.api;

import fr.heriamc.api.data.SerializableData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.server.v1_8_R3.EnumParticle;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ShootcraftData implements SerializableData<UUID> {

    private UUID id;
    private String actualSong;
    private EnumParticle actualParticle;
    private int kills, deaths;

    @Override
    public UUID getIdentifier() {
        return this.id;
    }

    @Override
    public void setIdentifier(UUID uuid) {
        this.id = uuid;
    }
}

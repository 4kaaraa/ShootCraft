package fr.kara.heria.shootcraft.data.api;

import fr.heriamc.api.data.PersistentDataManager;
import fr.heriamc.api.data.mongo.MongoConnection;
import fr.heriamc.api.data.redis.RedisConnection;
import fr.kara.heria.shootcraft.enums.ParticlesEnums;
import fr.kara.heria.shootcraft.enums.SongEnum;

import java.util.UUID;

public class ShootcraftDataManager extends PersistentDataManager<UUID, ShootcraftData> {

    public ShootcraftDataManager(RedisConnection redisConnection, MongoConnection mongoConnection) {
        super(redisConnection, mongoConnection, "shootcraft", "shootcraft");
    }

    @Override
    public ShootcraftData getDefault() {
        return new ShootcraftData(null, SongEnum.PLING.getSong(), ParticlesEnums.DEFAULT.getParticle(), 0,0);
    }

}

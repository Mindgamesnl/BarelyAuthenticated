package net.md_5.bungee.redis;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.redis.service.RedisService;

public class RedisConnector {

    private RedisService redisService;

    public RedisConnector(BungeeCord bungeeCord)
    {
        this.redisService = new RedisService(
                bungeeCord.getConfig().getRedisHost(),
                bungeeCord.getConfig().getRedisPassword(),
                bungeeCord.getConfig().getRedisPort(),
                bungeeCord.getConfig().getRedisDatabase(),
                bungeeCord.getConfig().isRedisUsesSSL()
        );
    }

}

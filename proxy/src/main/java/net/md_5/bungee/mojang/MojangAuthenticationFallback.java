package net.md_5.bungee.mojang;

import lombok.Getter;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.redis.service.RedisService;

import java.util.UUID;

public class MojangAuthenticationFallback {

    @Getter private static MojangAuthenticationFallback instance;
    private boolean mojangReliable = true;
    private boolean hadSuccess = true;
    private int failuresTrigger = 0;
    private int successesTrigger = 0;

    private int failures = 0;
    private int successes = 0;

    public MojangAuthenticationFallback(BungeeCord bungeeCord) {
        instance = this;
        failuresTrigger = bungeeCord.getConfig().getMojangFailsBeforeFallback();
        successesTrigger = bungeeCord.getConfig().getMojangSuccessesBeforeOnline();
    }

    public void pushIpAddress(UUID playerUUID, String ip) {
        if (!mojangReliable) return;
        BungeeCord.getInstance().getRedisConnector().getRedisService().setTimedValue(
                "playerip." + playerUUID.toString(),
                ip,
                BungeeCord.getInstance().getConfig().getRedisIpStorageExpiery()
        );
    }

    public boolean validate(UUID uuid, String ip) {
        String stored = BungeeCord.getInstance().getRedisConnector().getRedisService().getValue("playerip." + uuid.toString());
        if (stored == null) return false;
        return stored.equals(ip);
    }

    private void onUpdate(boolean isReliable) {

    }

    public void registerFailure() {
        failures++;
        hadSuccess = false;
        successes = 0;

        if (failures > failuresTrigger) {
            mojangReliable = false;
            onUpdate(mojangReliable);
        }
    }

    public void registerSuccess() {
        successes++;
        hadSuccess = true;
        failures = 0;

        if (successes > successesTrigger) {
            mojangReliable = true;
            onUpdate(mojangReliable);
        }
    }

}

package net.md_5.bungee.mojang;

import com.google.gson.Gson;
import lombok.Getter;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.mojang.models.CachedPair;
import net.md_5.bungee.mojang.models.StoredProfile;

public class MojangAuthenticationFallback {

    @Getter private static MojangAuthenticationFallback instance;
    @Getter private boolean mojangReliable = true;
    @Getter private boolean redisPreferred = false;
    private Gson gson = new Gson();
    private boolean hadSuccess = true;
    private int failuresTrigger = 0;
    private int successesTrigger = 0;
    private int reAttemptTrigger = 0;

    private int failures = 0;
    private int successes = 0;

    private int reAttemptTimer = 0;

    public MojangAuthenticationFallback(BungeeCord bungeeCord) {
        instance = this;
        failuresTrigger = bungeeCord.getConfig().getMojangFailsBeforeFallback();
        successesTrigger = bungeeCord.getConfig().getMojangSuccessesBeforeOnline();
        reAttemptTrigger = bungeeCord.getConfig().getMojangAttemptInterval();
        redisPreferred = bungeeCord.getConfig().isPreferRedisAuthentication();
    }

    public boolean shouldAttemptMojang() {
        if (isMojangReliable()) return true;
        reAttemptTimer++;
        if (reAttemptTimer > reAttemptTrigger) {
            reAttemptTimer = 0;
            return true;
        }
        return false;
    }

    public void pushPlayer(String playerName, StoredProfile profile) {
        if (!mojangReliable) return;
        BungeeCord.getInstance().getRedisConnector().getRedisService().setTimedValue(
                "profile." + playerName,
                gson.toJson(profile),
                BungeeCord.getInstance().getConfig().getRedisIpStorageExpiery()
        );
    }

    public CachedPair validate(String playerName, String ip) {
        String stored = BungeeCord.getInstance().getRedisConnector().getRedisService().getValue("profile." + playerName);
        if (stored == null) return new CachedPair(null, false);
        StoredProfile storedProfile = gson.fromJson(stored, StoredProfile.class);
        if (storedProfile.getIpAddress().equals(ip)) {
            return new CachedPair(storedProfile, true);
        }
        return new CachedPair(null, false);
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

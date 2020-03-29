package net.md_5.bungee.mojang;

import lombok.Getter;
import net.md_5.bungee.BungeeCord;

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

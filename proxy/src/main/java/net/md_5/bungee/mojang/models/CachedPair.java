package net.md_5.bungee.mojang.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CachedPair {

    private StoredProfile storedProfile;
    private boolean valid;

}

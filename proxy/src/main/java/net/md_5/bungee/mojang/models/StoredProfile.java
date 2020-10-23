package net.md_5.bungee.mojang.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.md_5.bungee.connection.LoginResult;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoredProfile {

    private String name;
    private UUID uuid;
    private String ipAddress;
    private LoginResult profile;

}

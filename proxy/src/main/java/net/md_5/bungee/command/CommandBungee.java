package net.md_5.bungee.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;

public class CommandBungee extends Command
{

    public CommandBungee()
    {
        super( "bungee" );
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        sender.sendMessage( ChatColor.BLUE + "This server is running the BarelyAuthenticated proxy (https://github.com/Mindgamesnl/BarelyAuthenticated) by Mindgamesnl, a redis authentication fork of BungeeCord by md_5" );
    }
}

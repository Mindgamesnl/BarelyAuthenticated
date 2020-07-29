package net.md_5.bungee.protocol;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import java.lang.reflect.Constructor;
import lombok.Data;
import lombok.Getter;
import net.md_5.bungee.protocol.packet.BossBar;
import net.md_5.bungee.protocol.packet.Chat;
import net.md_5.bungee.protocol.packet.ClientSettings;
import net.md_5.bungee.protocol.packet.Commands;
import net.md_5.bungee.protocol.packet.EncryptionRequest;
import net.md_5.bungee.protocol.packet.EncryptionResponse;
import net.md_5.bungee.protocol.packet.EntityStatus;
import net.md_5.bungee.protocol.packet.GameState;
import net.md_5.bungee.protocol.packet.EntityEffect;
import net.md_5.bungee.protocol.packet.EntityRemoveEffect;
import net.md_5.bungee.protocol.packet.Handshake;
import net.md_5.bungee.protocol.packet.KeepAlive;
import net.md_5.bungee.protocol.packet.Kick;
import net.md_5.bungee.protocol.packet.Login;
import net.md_5.bungee.protocol.packet.LoginPayloadRequest;
import net.md_5.bungee.protocol.packet.LoginPayloadResponse;
import net.md_5.bungee.protocol.packet.LoginRequest;
import net.md_5.bungee.protocol.packet.LoginSuccess;
import net.md_5.bungee.protocol.packet.PingPacket;
import net.md_5.bungee.protocol.packet.PlayerListHeaderFooter;
import net.md_5.bungee.protocol.packet.PlayerListItem;
import net.md_5.bungee.protocol.packet.PluginMessage;
import net.md_5.bungee.protocol.packet.Respawn;
import net.md_5.bungee.protocol.packet.ScoreboardDisplay;
import net.md_5.bungee.protocol.packet.ScoreboardObjective;
import net.md_5.bungee.protocol.packet.ScoreboardScore;
import net.md_5.bungee.protocol.packet.SetCompression;
import net.md_5.bungee.protocol.packet.StatusRequest;
import net.md_5.bungee.protocol.packet.StatusResponse;
import net.md_5.bungee.protocol.packet.TabCompleteRequest;
import net.md_5.bungee.protocol.packet.TabCompleteResponse;
import net.md_5.bungee.protocol.packet.Team;
import net.md_5.bungee.protocol.packet.Title;
import net.md_5.bungee.protocol.packet.ViewDistance;

public enum Protocol
{

    // Undef
    HANDSHAKE
    {

        {
            TO_SERVER.registerPacket(
                    Handshake.class,
                    Handshake::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x00 )
            );
        }
    },
    // 0
    GAME
    {

        {
            TO_CLIENT.registerPacket(
                    KeepAlive.class,
                    KeepAlive::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x00 ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x1F ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x21 ),
                    map( ProtocolConstants.MINECRAFT_1_14, 0x20 ),
                    map( ProtocolConstants.MINECRAFT_1_15, 0x21 ),
                    map( ProtocolConstants.MINECRAFT_1_16, 0x20 )
            );
            TO_CLIENT.registerPacket(
                    Login.class,
                    Login::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x01 ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x23 ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x25 ),
                    map( ProtocolConstants.MINECRAFT_1_15, 0x26 ),
                    map( ProtocolConstants.MINECRAFT_1_16, 0x25 )
            );
            TO_CLIENT.registerPacket(
                    Chat.class,
                    Chat::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x02 ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x0F ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x0E ),
                    map( ProtocolConstants.MINECRAFT_1_15, 0x0F ),
                    map( ProtocolConstants.MINECRAFT_1_16, 0x0E )
            );
            TO_CLIENT.registerPacket(
                    Respawn.class,
                    Respawn::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x07 ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x33 ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x34 ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x35 ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x38 ),
                    map( ProtocolConstants.MINECRAFT_1_14, 0x3A ),
                    map( ProtocolConstants.MINECRAFT_1_15, 0x3B ),
                    map( ProtocolConstants.MINECRAFT_1_16, 0x3A )
            );
            TO_CLIENT.registerPacket(
                    BossBar.class,
                    BossBar::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_9, 0x0C ),
                    map( ProtocolConstants.MINECRAFT_1_15, 0x0D ),
                    map( ProtocolConstants.MINECRAFT_1_16, 0x0C )
            );
            // Waterfall start
            TO_CLIENT.registerPacket(
                    EntityEffect.class,
                    EntityEffect::new, // Waterfall - speed up packet construction
                    map(ProtocolConstants.MINECRAFT_1_8, 0x1D),
                    map(ProtocolConstants.MINECRAFT_1_9, 0x4C),
                    map(ProtocolConstants.MINECRAFT_1_9_4, 0x4B),
                    map(ProtocolConstants.MINECRAFT_1_12, 0x4E),
                    map(ProtocolConstants.MINECRAFT_1_12_1, 0x4F),
                    map(ProtocolConstants.MINECRAFT_1_13, 0x53),
                    map(ProtocolConstants.MINECRAFT_1_14, 0x59),
                    map(ProtocolConstants.MINECRAFT_1_15, 0x5A)
            );
            TO_CLIENT.registerPacket(
                    EntityRemoveEffect.class,
                    EntityRemoveEffect::new, // Waterfall - speed up packet construction
                    map(ProtocolConstants.MINECRAFT_1_8, 0x1E),
                    map(ProtocolConstants.MINECRAFT_1_9, 0x31),
                    map(ProtocolConstants.MINECRAFT_1_12, 0x32),
                    map(ProtocolConstants.MINECRAFT_1_12_1, 0x33),
                    map(ProtocolConstants.MINECRAFT_1_13, 0x36),
                    map(ProtocolConstants.MINECRAFT_1_14, 0x38),
                    map(ProtocolConstants.MINECRAFT_1_15, 0x39)
            );
            // Waterfall end
            TO_CLIENT.registerPacket(
                    PlayerListItem.class, // PlayerInfo
                    PlayerListItem::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x38 ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x2D ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x2E ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x30 ),
                    map( ProtocolConstants.MINECRAFT_1_14, 0x33 ),
                    map( ProtocolConstants.MINECRAFT_1_15, 0x34 ),
                    map( ProtocolConstants.MINECRAFT_1_16, 0x33 )
            );
            TO_CLIENT.registerPacket(
                    TabCompleteResponse.class,
                    TabCompleteResponse::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x3A ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x0E ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x10 ),
                    map( ProtocolConstants.MINECRAFT_1_15, 0x11 ),
                    map( ProtocolConstants.MINECRAFT_1_16, 0x10 )
            );
            TO_CLIENT.registerPacket(
                    ScoreboardObjective.class,
                    ScoreboardObjective::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x3B ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x3F ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x41 ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x42 ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x45 ),
                    map( ProtocolConstants.MINECRAFT_1_14, 0x49 ),
                    map( ProtocolConstants.MINECRAFT_1_15, 0x4A )
            );
            TO_CLIENT.registerPacket(
                    ScoreboardScore.class,
                    ScoreboardScore::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x3C ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x42 ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x44 ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x45 ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x48 ),
                    map( ProtocolConstants.MINECRAFT_1_14, 0x4C ),
                    map( ProtocolConstants.MINECRAFT_1_15, 0x4D )
            );
            TO_CLIENT.registerPacket(
                    ScoreboardDisplay.class,
                    ScoreboardDisplay::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x3D ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x38 ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x3A ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x3B ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x3E ),
                    map( ProtocolConstants.MINECRAFT_1_14, 0x42 ),
                    map( ProtocolConstants.MINECRAFT_1_15, 0x43 )
            );
            TO_CLIENT.registerPacket(
                    Team.class,
                    Team::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x3E ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x41 ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x43 ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x44 ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x47 ),
                    map( ProtocolConstants.MINECRAFT_1_14, 0x4B ),
                    map( ProtocolConstants.MINECRAFT_1_15, 0x4C )
            );
            TO_CLIENT.registerPacket(
                    PluginMessage.class,
                    PluginMessage::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x3F ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x18 ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x19 ),
                    map( ProtocolConstants.MINECRAFT_1_14, 0x18 ),
                    map( ProtocolConstants.MINECRAFT_1_15, 0x19 ),
                    map( ProtocolConstants.MINECRAFT_1_16, 0x18 )
            );
            TO_CLIENT.registerPacket(
                    Kick.class,
                    Kick::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x40 ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x1A ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x1B ),
                    map( ProtocolConstants.MINECRAFT_1_14, 0x1A ),
                    map( ProtocolConstants.MINECRAFT_1_15, 0x1B ),
                    map( ProtocolConstants.MINECRAFT_1_16, 0x1A )
            );
            TO_CLIENT.registerPacket(
                    Title.class,
                    Title::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x45 ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x47 ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x48 ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x4B ),
                    map( ProtocolConstants.MINECRAFT_1_14, 0x4F ),
                    map( ProtocolConstants.MINECRAFT_1_15, 0x50 ),
                    map( ProtocolConstants.MINECRAFT_1_16, 0x4F )
            );
            TO_CLIENT.registerPacket(
                    PlayerListHeaderFooter.class,
                    PlayerListHeaderFooter::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x47 ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x48 ),
                    map( ProtocolConstants.MINECRAFT_1_9_4, 0x47 ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x49 ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x4A ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x4E ),
                    map( ProtocolConstants.MINECRAFT_1_14, 0x53 ),
                    map( ProtocolConstants.MINECRAFT_1_15, 0x54 ),
                    map( ProtocolConstants.MINECRAFT_1_16, 0x53 )
            );
            TO_CLIENT.registerPacket(
                    EntityStatus.class,
                    EntityStatus::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x1A ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x1B ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x1C ),
                    map( ProtocolConstants.MINECRAFT_1_14, 0x1B ),
                    map( ProtocolConstants.MINECRAFT_1_15, 0x1C ),
                    map( ProtocolConstants.MINECRAFT_1_16, 0x1B )
            );
            TO_CLIENT.registerPacket(
                    Commands.class,
                    Commands::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_13, 0x11 ),
                    map( ProtocolConstants.MINECRAFT_1_15, 0x12 ),
                    map( ProtocolConstants.MINECRAFT_1_16, 0x11 )
            );
            TO_CLIENT.registerPacket(
                    GameState.class,
                    map( ProtocolConstants.MINECRAFT_1_15, 0x1F ),
                    map( ProtocolConstants.MINECRAFT_1_16, 0x1E )
            );
            TO_CLIENT.registerPacket(
                    ViewDistance.class,
                    ViewDistance::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_14, 0x41 ),
                    map( ProtocolConstants.MINECRAFT_1_15, 0x42 ),
                    map( ProtocolConstants.MINECRAFT_1_16, 0x41 )
            );

            TO_SERVER.registerPacket(
                    KeepAlive.class,
                    KeepAlive::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x00 ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x0B ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x0C ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x0B ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x0E ),
                    map( ProtocolConstants.MINECRAFT_1_14, 0x0F ),
                    map( ProtocolConstants.MINECRAFT_1_16, 0x10 )
            );
            TO_SERVER.registerPacket(
                    Chat.class,
                    Chat::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x01 ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x02 ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x03 ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x02 ),
                    map( ProtocolConstants.MINECRAFT_1_14, 0x03 )
            );
            TO_SERVER.registerPacket(
                    TabCompleteRequest.class,
                    TabCompleteRequest::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x14 ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x01 ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x02 ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x01 ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x05 ),
                    map( ProtocolConstants.MINECRAFT_1_14, 0x06 )
            );
            TO_SERVER.registerPacket(
                    ClientSettings.class,
                    ClientSettings::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x15 ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x04 ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x05 ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x04 ),
                    map( ProtocolConstants.MINECRAFT_1_14, 0x05 )
            );
            TO_SERVER.registerPacket(
                    PluginMessage.class,
                    PluginMessage::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x17 ),
                    map( ProtocolConstants.MINECRAFT_1_9, 0x09 ),
                    map( ProtocolConstants.MINECRAFT_1_12, 0x0A ),
                    map( ProtocolConstants.MINECRAFT_1_12_1, 0x09 ),
                    map( ProtocolConstants.MINECRAFT_1_13, 0x0A ),
                    map( ProtocolConstants.MINECRAFT_1_14, 0x0B )
            );
        }
    },
    // 1
    STATUS
    {

        {
            TO_CLIENT.registerPacket(
                    StatusResponse.class,
                    StatusResponse::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x00 )
            );
            TO_CLIENT.registerPacket(
                    PingPacket.class,
                    PingPacket::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x01 )
            );

            TO_SERVER.registerPacket(
                    StatusRequest.class,
                    StatusRequest::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x00 )
            );
            TO_SERVER.registerPacket(
                    PingPacket.class,
                    PingPacket::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x01 )
            );
        }
    },
    //2
    LOGIN
    {

        {
            TO_CLIENT.registerPacket(
                    Kick.class,
                    Kick::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x00 )
            );
            TO_CLIENT.registerPacket(
                    EncryptionRequest.class,
                    EncryptionRequest::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x01 )
            );
            TO_CLIENT.registerPacket(
                    LoginSuccess.class,
                    LoginSuccess::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x02 )
            );
            TO_CLIENT.registerPacket(
                    SetCompression.class,
                    SetCompression::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x03 )
            );
            TO_CLIENT.registerPacket(
                    LoginPayloadRequest.class,
                    LoginPayloadRequest::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_13, 0x04 )
            );

            TO_SERVER.registerPacket(
                    LoginRequest.class,
                    LoginRequest::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x00 )
            );
            TO_SERVER.registerPacket(
                    EncryptionResponse.class,
                    EncryptionResponse::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_8, 0x01 )
            );
            TO_SERVER.registerPacket(
                    LoginPayloadResponse.class,
                    LoginPayloadResponse::new, // Waterfall - speed up packet construction
                    map( ProtocolConstants.MINECRAFT_1_13, 0x02 )
            );
        }
    };
    /*========================================================================*/
    public static final int MAX_PACKET_ID = 0xFF;
    /*========================================================================*/
    final DirectionData TO_SERVER = new DirectionData( this, ProtocolConstants.Direction.TO_SERVER );
    final DirectionData TO_CLIENT = new DirectionData( this, ProtocolConstants.Direction.TO_CLIENT );

    public static void main(String[] args)
    {
        for ( int version : ProtocolConstants.SUPPORTED_VERSION_IDS )
        {
            dump( version );
        }
    }

    private static void dump(int version)
    {
        for ( Protocol protocol : Protocol.values() )
        {
            dump( version, protocol );
        }
    }

    private static void dump(int version, Protocol protocol)
    {
        dump( version, protocol.TO_CLIENT );
        dump( version, protocol.TO_SERVER );
    }

    private static void dump(int version, DirectionData data)
    {
        for ( int id = 0; id < MAX_PACKET_ID; id++ )
        {
            DefinedPacket packet = data.createPacket( id, version );
            if ( packet != null )
            {
                System.out.println( version + " " + data.protocolPhase + " " + data.direction + " " + id + " " + packet.getClass().getSimpleName() );
            }
        }
    }

    @Data
    private static class ProtocolData
    {

        private final int protocolVersion;
        private final TObjectIntMap<Class<? extends DefinedPacket>> packetMap = new TObjectIntHashMap<>( MAX_PACKET_ID );
        private final java.util.function.Supplier<? extends DefinedPacket>[] packetConstructors = new java.util.function.Supplier[ MAX_PACKET_ID ]; // Waterfall - speed up packet construction
    }

    @Data
    private static class ProtocolMapping
    {

        private final int protocolVersion;
        private final int packetID;
    }

    // Helper method
    private static ProtocolMapping map(int protocol, int id)
    {
        return new ProtocolMapping( protocol, id );
    }

    static final class DirectionData
    {

        private final TIntObjectMap<ProtocolData> protocols = new TIntObjectHashMap<>();
        //
        private final Protocol protocolPhase;
        @Getter
        private final ProtocolConstants.Direction direction;

        public DirectionData(Protocol protocolPhase, ProtocolConstants.Direction direction)
        {
            this.protocolPhase = protocolPhase;
            this.direction = direction;

            for ( int protocol : ProtocolConstants.SUPPORTED_VERSION_IDS )
            {
                protocols.put( protocol, new ProtocolData( protocol ) );
            }
        }

        private ProtocolData getProtocolData(int version)
        {
            ProtocolData protocol = protocols.get( version );
            if ( protocol == null && ( protocolPhase != Protocol.GAME ) )
            {
                protocol = Iterables.getFirst( protocols.valueCollection(), null );
            }
            return protocol;
        }

        public boolean hasPacket(int i, boolean supportsForge) {
            return supportsForge || i >= 0 && i <= MAX_PACKET_ID;
        }

        public final DefinedPacket createPacket(int id, int version)
        {
            return createPacket(id, version, true);
        }

        public final DefinedPacket createPacket(int id, int version, boolean supportsForge)
        {
            ProtocolData protocolData = getProtocolData( version );
            if ( protocolData == null )
            {
                throw new BadPacketException( "Unsupported protocol version " + version );
            }
            if ( !hasPacket(id, supportsForge) )
            {
                throw new BadPacketException( "Packet with id " + id + " outside of range " );
            }

            java.util.function.Supplier<? extends DefinedPacket> constructor = protocolData.packetConstructors[id]; // Waterfall - speed up packet construction
            try
            {
                return ( constructor == null ) ? null : constructor.get(); // Waterfall - speed up packet construction
            } catch ( Exception ex ) // Waterfall - speed up packet construction
            {
                throw new BadPacketException( "Could not construct packet with id " + id, ex );
            }
        }

        private <P extends DefinedPacket> void registerPacket(Class<P> packetClass, java.util.function.Supplier<P> constructor, ProtocolMapping... mappings) // Waterfall - speed up packet construction
        {
            // Waterfall start - speed up packet construction
            /*
            try
            {
                Constructor<? extends DefinedPacket> constructor = packetClass.getDeclaredConstructor();
                */ // Waterfall end

                int mappingIndex = 0;
                ProtocolMapping mapping = mappings[mappingIndex];
                for ( int protocol : ProtocolConstants.SUPPORTED_VERSION_IDS )
                {
                    if ( protocol < mapping.protocolVersion )
                    {
                        // This is a new packet, skip it till we reach the next protocol
                        continue;
                    }

                    if ( mapping.protocolVersion < protocol && mappingIndex + 1 < mappings.length )
                    {
                        // Mapping is non current, but the next one may be ok
                        ProtocolMapping nextMapping = mappings[mappingIndex + 1];
                        if ( nextMapping.protocolVersion == protocol )
                        {
                            Preconditions.checkState( nextMapping.packetID != mapping.packetID, "Duplicate packet mapping (%s, %s)", mapping.protocolVersion, nextMapping.protocolVersion );

                            mapping = nextMapping;
                            mappingIndex++;
                        }
                    }

                    ProtocolData data = protocols.get( protocol );
                    data.packetMap.put( packetClass, mapping.packetID );
                    data.packetConstructors[mapping.packetID] = constructor;
                }
            // Waterfall start - speed up packet construction
            /*
            } catch ( NoSuchMethodException ex )
            {
                throw new BadPacketException( "No NoArgsConstructor for packet class " + packetClass );
            }
             */ // Waterfall end
        }
        // Waterfall start - speed up packet construction (backwards compat)
        private <P extends DefinedPacket> void registerPacket(Class<P> packetClass, ProtocolMapping... mappings) {
            java.util.function.Supplier<P> packetSupplier;
            try {
                Constructor<? extends DefinedPacket> constructor = packetClass.getDeclaredConstructor();
                packetSupplier = () -> {
                    try {
                        return (P) constructor.newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                };
            } catch (ReflectiveOperationException e) {
                throw new BadPacketException( "No NoArgsConstructor for packet class " + packetClass );
            }
            registerPacket(packetClass, packetSupplier, mappings);
        }
        // Waterfall end

        final int getId(Class<? extends DefinedPacket> packet, int version)
        {

            ProtocolData protocolData = getProtocolData( version );
            if ( protocolData == null )
            {
                throw new BadPacketException( "Unsupported protocol version" );
            }
            Preconditions.checkArgument( protocolData.packetMap.containsKey( packet ), "Cannot get ID for packet %s in phase %s with direction %s", packet, protocolPhase, direction );

            return protocolData.packetMap.get( packet );
        }
    }
}

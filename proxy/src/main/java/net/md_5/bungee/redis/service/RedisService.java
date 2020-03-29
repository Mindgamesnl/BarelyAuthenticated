package net.md_5.bungee.redis.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import lombok.Getter;

import java.util.UUID;

public class RedisService {

    private RedisClient redisPub;
    private RedisClient redisSub;
    private RedisURI uri;
    private StatefulRedisPubSubConnection<String, String> redisSubConnection;
    private StatefulRedisPubSubConnection<String, String> redisPubConnection;
    @Getter
    private RedisPubSubAsyncCommands<String, String> asyncSub;
    private RedisPubSubAsyncCommands<String, String> asyncPub;
    @Getter
    private static UUID serviceId = UUID.randomUUID();
    private int database = 0;

    public RedisService(String host, String password, int port, int database, boolean useSSL) {
        if (password.equals("none")) {
            uri = RedisURI.builder()
                    .withHost(host)
                    .withPort(port)
                    .withSsl(useSSL)
                    .build();
        } else {
            uri = RedisURI.builder()
                    .withPassword(password)
                    .withHost(host)
                    .withPort(port)
                    .withSsl(useSSL)
                    .build();
        }

        this.database = database;

        // set up listener
        redisSub = RedisClient.create(uri);
        redisSub.setOptions(ClientOptions.builder().autoReconnect(true).build());
        redisSubConnection = redisSub.connectPubSub();
        redisSubConnection.addListener(new RedisChannelListener());
        asyncSub = redisSubConnection.async();


        // set up publisher
        redisPub = RedisClient.create(uri);
        redisPub.setOptions(ClientOptions.builder().autoReconnect(true).build());
        redisPubConnection = redisPub.connectPubSub();
        asyncPub = redisPubConnection.async();
    }

    public void sendMessage(String channel, String message) {
        asyncPub.publish(channel, message);
    }

    public void shutdown() {
        redisSubConnection.close();
        redisSub.shutdown();
        redisPubConnection.close();
        redisPub.shutdown();
    }

}

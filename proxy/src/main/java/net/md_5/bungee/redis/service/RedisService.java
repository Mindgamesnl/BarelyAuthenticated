package net.md_5.bungee.redis.service;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class RedisService {

    private RedisClient redisData;
    private RedisURI uri;
    private StatefulRedisConnection<String, String> databaseConnection;
    private RedisCommands<String, String> dataHandler;
    private int database = 0;

    public RedisService(String host, String password, int port, int database, boolean useSSL) {
        if (password.equals("none")) {
            uri = RedisURI.builder()
                    .withHost(host)
                    .withPort(port)
                    .withSsl(useSSL)
                    .withDatabase(database)
                    .build();
        } else {
            uri = RedisURI.builder()
                    .withPassword(password)
                    .withHost(host)
                    .withPort(port)
                    .withSsl(useSSL)
                    .withDatabase(database)
                    .build();
        }

        this.database = database;
        // set up db
        redisData = RedisClient.create(uri);
        redisData.setOptions(ClientOptions.builder().autoReconnect(true).build());
        databaseConnection = redisData.connect();
        dataHandler = databaseConnection.sync();
    }

    public void setValue(String key, String value) {
        dataHandler.set(key, value);
    }

    public void setTimedValue(String key, String value, long seconds) {
        dataHandler.setex(key, seconds, value);
    }

    public String getValue(String key) {
        return dataHandler.get(key);
    }

    public void shutdown() {
        redisData.shutdown();
        databaseConnection.close();
    }

}

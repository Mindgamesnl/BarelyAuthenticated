package net.md_5.bungee.redis.service;

import io.lettuce.core.pubsub.RedisPubSubAdapter;

public class RedisChannelListener extends RedisPubSubAdapter<String, String> {

    public RedisChannelListener() {
        // TODO: subscriber
    }

    @Override
    public void message(String channel, String message) {
        // TODO: something else
    }
}

package com.company.notifications.resolver;

import com.company.notifications.domain.model.ChannelType;
import com.company.notifications.ports.out.Channel;

import java.util.HashMap;
import java.util.Map;

public class ChannelRegistry {

    private final Map<ChannelType, Channel> channels = new HashMap<>();

    public void register(ChannelType type, Channel channel) {
        channels.put(type, channel);
    }

    public Channel resolve(ChannelType type) {
        return channels.get(type);
    }
}

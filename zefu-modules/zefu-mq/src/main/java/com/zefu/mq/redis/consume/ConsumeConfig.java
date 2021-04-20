package com.zefu.mq.redis.consume;

import com.zefu.common.base.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;


@Configuration
@EnableCaching
@Slf4j
public class ConsumeConfig {
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // 可以添加多个 messageListener，配置不同的交换机
        container.addMessageListener(listenerAdapter, new PatternTopic(Constants.REDIS_CHANNEL.PROP_READER));
        container.addMessageListener(listenerAdapter, new PatternTopic(Constants.REDIS_CHANNEL.PROP_SET));
        container.addMessageListener(listenerAdapter, new PatternTopic(Constants.REDIS_CHANNEL.SERVICE_INVOKE));
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(ChannelConsume receiver) {
        log.info("redis消息适配器启动");
        return new MessageListenerAdapter(receiver, "onMessage");
    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }
}

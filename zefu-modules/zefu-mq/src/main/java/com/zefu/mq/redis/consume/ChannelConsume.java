package com.zefu.mq.redis.consume;


import com.zefu.common.base.constants.Constants;
import com.zefu.common.base.domain.gateway.mq.PropertyReaderMessageBo;
import com.zefu.common.base.domain.gateway.mq.PropertySetMessageBo;
import com.zefu.common.base.domain.gateway.mq.ServiceInvokeMessageBo;
import com.zefu.common.core.utils.bus.JSONProvider;
import com.zefu.mq.producer.PropertyReaderMessageProducer;
import com.zefu.mq.producer.PropertySetMessageProducer;
import com.zefu.mq.producer.ServiceInvokeMessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class ChannelConsume implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] bytes) {
       try{
           this.process(message, bytes);
       }catch (Exception e){
           log.warn("redis订阅者处理消息异常", e);
       }
    }

    private void process(Message message, byte[] bytes) throws Exception{
        String channel = new String(message.getChannel());
        String body = new String(message.getBody());
        String byteBody = new String(bytes, Constants.GLOBAL.CHARSET_UFT8);
        log.info("channel:{} 收到消息:{}  bytes:{}", channel, body, byteBody);
        switch (channel){
            case Constants.REDIS_CHANNEL.PROP_SET:
                PropertySetMessageBo propertySetMessageBo = JSONProvider.parseObject(body, PropertySetMessageBo.class);
                PropertySetMessageProducer.send(propertySetMessageBo);
                break;
            case Constants.REDIS_CHANNEL.SERVICE_INVOKE:
                ServiceInvokeMessageBo invokeMessageBo = JSONProvider.parseObject(body, ServiceInvokeMessageBo.class);
                ServiceInvokeMessageProducer.send(invokeMessageBo);
                break;
            case Constants.REDIS_CHANNEL.PROP_READER:
                PropertyReaderMessageBo readerMessageBo = JSONProvider.parseObject(body, PropertyReaderMessageBo.class);
                PropertyReaderMessageProducer.send(readerMessageBo);
                break;
            default:
                log.warn("redis收到非法的消息,channel:{} body:{}", channel, body);
                return;
        }
    }
}

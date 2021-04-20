package com.zefu.common.base.domain.bo;

import lombok.Data;


@Data
public class TopicDescBo {
    String name;
    String topic;
    String desc;

    public static TopicDescBo build(String name, String topic, String desc){
        TopicDescBo item = new TopicDescBo();
        item.setDesc(desc);
        item.setName(name);
        item.setTopic(topic);
        return item;
    }
}

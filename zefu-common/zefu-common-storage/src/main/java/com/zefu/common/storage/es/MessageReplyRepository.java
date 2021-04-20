package com.zefu.common.storage.es;

import com.zefu.common.storage.entity.MessageReplyEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

public interface MessageReplyRepository extends ElasticsearchRepository<MessageReplyEntity, String> {

}

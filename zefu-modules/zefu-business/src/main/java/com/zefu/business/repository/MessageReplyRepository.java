package com.zefu.business.repository;

import com.zefu.business.entity.MessageReplyEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface MessageReplyRepository extends ElasticsearchRepository<MessageReplyEntity, String> {

}

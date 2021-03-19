package com.zefu.business.metadata;

import com.zefu.business.storage.EsMessage;
import lombok.Data;


@Data
public class EsInsertDataBo {
    String indexName;
    String identifier;
    /**协议es的数据*/
    EsMessage esMessage;
}

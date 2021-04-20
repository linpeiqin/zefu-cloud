package com.zefu.common.base.metadata;

import com.zefu.common.base.domain.storage.EsMessage;
import lombok.Data;


@Data
public class EsInsertDataBo {
    String          indexName;
    String identifier;
    /**协议es的数据*/
    EsMessage esMessage;
}

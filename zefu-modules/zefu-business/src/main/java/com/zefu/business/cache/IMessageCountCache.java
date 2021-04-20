package com.zefu.business.cache;


import com.zefu.common.base.domain.dto.response.dashboard.DashLineResDto;

public interface IMessageCountCache {
    /**
     * 当日实时消息总数
     * @return
     * */
    Integer todayTotal();
    /**
     * 当日实时消息总数自增
     * @return
     * */
    void todayTotalIncr();
    /**
     * 读取首页面板近七日数据线
     * @return
     * */
    DashLineResDto dashLineRead();
    /**
     * 写入首页面板近七日数据线
     * @param resDto
     * @return
     * */
    void dashLineWrite(DashLineResDto resDto);
}

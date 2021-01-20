package com.itheima.dbsharding.shardingjdbcdemo.service;

import com.itheima.dbsharding.shardingjdbcdemo.entity.StoreInfo;

import java.util.List;
import java.util.Map;

/**
 * 卖家服务
 */
public interface StoreInfoService {
    /**
     * 根据id列表获取所对应的店铺信息
     * @param ids id列表
     * @return 店铺信息集合
     */
    Map<Integer, StoreInfo> queryStoreInfoByIds(List<Long> ids);
}

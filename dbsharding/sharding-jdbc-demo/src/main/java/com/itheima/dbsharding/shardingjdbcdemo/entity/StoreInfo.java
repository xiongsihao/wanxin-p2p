package com.itheima.dbsharding.shardingjdbcdemo.entity;

import lombok.Data;

/**
 * 店铺信息
 */
@Data
public class StoreInfo {

    private Long id;

    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 信誉等级
     */
    private int reputation;

    /**
     * 店铺所在地
     */
    private String regionCode;

    /**
     * 店铺所在地名称
     */
    private String regionName;

}

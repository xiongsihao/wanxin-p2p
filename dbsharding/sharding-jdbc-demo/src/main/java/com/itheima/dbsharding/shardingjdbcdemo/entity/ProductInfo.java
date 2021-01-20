package com.itheima.dbsharding.shardingjdbcdemo.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品基本信息
 */
@Data
public class ProductInfo {


    private Long id;

    /**
     * 所属店铺id
     */
    private Long storeInfoId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 规格
     */
    private String spec;

    /**
     * 产地
     */
    private String regionCode;


    /**
     * 商品价格
     */
    private BigDecimal price;


    /**
     * 商品图片
     */
    private String imageUrl;


    ////////////////////////////////关联信息/////////////////////////////////////////

    /**
     * 商品描述
     */
    private String descript;


    /**
     * 产地名称
     */
    private String placeOfOrigin;


    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 店铺信誉等级
     */
    private int reputation;

    /**
     * 店铺所在地名称
     */
    private String storeRegionName;



}

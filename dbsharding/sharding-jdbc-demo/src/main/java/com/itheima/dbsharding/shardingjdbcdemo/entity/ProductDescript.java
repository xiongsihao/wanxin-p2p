package com.itheima.dbsharding.shardingjdbcdemo.entity;

import lombok.Data;

/**
 * 商品描述
 */
@Data
public class ProductDescript {

    private Long id;

    /**
     * 所属商品id
     */
    private Long productInfoId;

    /**
     * 商品描述
     */
    private String descript;

    /**
     * 所属店铺id
     */
    private Long storeInfoId;

}

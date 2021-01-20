package com.itheima.dbsharding.shardingjdbcdemo.dao;

import com.itheima.dbsharding.shardingjdbcdemo.entity.ProductDescript;
import com.itheima.dbsharding.shardingjdbcdemo.entity.ProductInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ProductDao {

    @Insert("insert into product_info(store_info_id,product_name,spec,region_code,price,image_url) value(#{storeInfoId},#{productName},#{spec},#{regionCode},#{price},#{imageUrl})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "product_info_id")
    int insertProductInfo(ProductInfo productInfo);

    @Insert("insert into product_descript(product_info_id,descript,store_info_id) value(#{productInfoId},#{descript},#{storeInfoId})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int insertProductDescript(ProductDescript productDescript);

    @Select("select i.*, d.descript from product_info i inner join product_descript d on i.product_info_id = d.product_info_id ")
    List<ProductInfo> selectProductList();

}

package com.itheima.dbsharding.shardingjdbcdemo.service;

import com.itheima.dbsharding.shardingjdbcdemo.dao.ProductDao;
import com.itheima.dbsharding.shardingjdbcdemo.entity.ProductDescript;
import com.itheima.dbsharding.shardingjdbcdemo.entity.ProductInfo;
import com.itheima.dbsharding.shardingjdbcdemo.entity.StoreInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private StoreInfoService storeInfoService;

    @Override
    @Transactional
    public void createProduct(ProductInfo product) {
        ProductDescript productDescript = new ProductDescript();
        productDescript.setDescript(product.getDescript());
        productDao.insertProductInfo(product);//新增商品基本信息
        productDescript.setProductInfoId(product.getId());
        productDescript.setStoreInfoId(product.getStoreInfoId()); //冗余店铺信息
        productDao.insertProductDescript(productDescript);//新增商品描述信息
    }

    @Override
    public List<ProductInfo> queryProduct() {
        //1.查询本地商品信息
        List<ProductInfo> productInfos = productDao.selectProductList();
        Set<Long> storeIdSet = new HashSet<>();
        for(ProductInfo p : productInfos){
            storeIdSet.add(p.getStoreInfoId());
        }
        //2.查询并组装店铺信息
        if(storeIdSet.size()!=0){
            Map<Integer, StoreInfo> storeMap = storeInfoService.queryStoreInfoByIds(new ArrayList<>(storeIdSet));
            for(ProductInfo p : productInfos){
                StoreInfo storeInfo = storeMap.get(p.getStoreInfoId());
                p.setStoreName(storeInfo.getStoreName());
                p.setReputation(storeInfo.getReputation());
                p.setStoreRegionName(storeInfo.getRegionName());
            }
        }

        return productInfos;
    }


}

package com.itheima.dbsharding.shardingjdbcdemo.service;

import com.itheima.dbsharding.shardingjdbcdemo.dao.StoreInfoDao;
import com.itheima.dbsharding.shardingjdbcdemo.entity.StoreInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StoreInfoServiceImpl implements StoreInfoService {

    @Autowired
    private StoreInfoDao storeInfoDao;

    @Override
    public Map<Integer, StoreInfo> queryStoreInfoByIds(List<Long> ids) {
        return storeInfoDao.selectStoreInfoByIds(ids);
    }
}

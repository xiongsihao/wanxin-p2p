package cn.xsh.sailing.store;

import cn.xsh.sailing.common.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisVerificationStore implements VerificationStore {

    @Autowired
    private Cache cache;

    @Override
    public void set(String key, String value, Integer expire) {
        cache.set(key,value,expire);
    }

    @Override
    public String get(String key) {
        return cache.get(key);
    }
}

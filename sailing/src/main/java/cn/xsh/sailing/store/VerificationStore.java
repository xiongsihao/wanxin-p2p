package cn.xsh.sailing.store;

/**
 * 验证信息存储 kv
 */
public interface VerificationStore{


    void set(String key, String value, Integer expire);


    String get(String key);


}

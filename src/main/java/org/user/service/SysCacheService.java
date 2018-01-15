package org.user.service;

import com.github.pagehelper.StringUtil;
import com.google.common.base.Joiner;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.user.model.CacheKeyConstants;
import org.user.model.SysUser;
import redis.clients.jedis.ShardedJedis;
import util.JsonMapper;

import javax.annotation.Resource;

@Service
@Slf4j
public class SysCacheService {

    @Resource(name = "redisPool")
    private RedisPool redisPool;

    public void saveCache(String toSavedValue, int timeoutSeconds, CacheKeyConstants prefix) {
        saveCache(toSavedValue, timeoutSeconds, prefix, null);
    }

    public void saveCache(String toSavedValue, int timeoutSeconds, CacheKeyConstants prefix, String... keys) {
        if (toSavedValue == null) {
            return;
        }
        ShardedJedis shardedJedis = null;
        try {
            String cacheKey = generateCacheKey(prefix, keys);
            shardedJedis = redisPool.instance();
            shardedJedis.setex(cacheKey, timeoutSeconds, toSavedValue);
        } catch (Exception e) {
            log.error("save cache exception, prefix:{}, keys:{}", prefix.name(), JsonMapper.obj2String(keys), e);
        } finally {
            redisPool.safeClose(shardedJedis);
        }
    }

    public String getFromCache(CacheKeyConstants prefix, String... keys) {
        ShardedJedis shardedJedis = null;
        String cacheKey = generateCacheKey(prefix, keys);
        try {
            shardedJedis = redisPool.instance();
            String value = shardedJedis.get(cacheKey);
            return value;
        } catch (Exception e) {
            log.error("get from cache exception, prefix:{}, keys:{}", prefix.name(), JsonMapper.obj2String(keys), e);
            return null;
        } finally {
            redisPool.safeClose(shardedJedis);
        }
    }

    private String generateCacheKey(CacheKeyConstants prefix, String... keys) {
        String key = prefix.name();
        if (keys != null && keys.length > 0) {
            key += "_" + Joiner.on("_").join(keys);
        }
        return key;
    }

    public void incre(CacheKeyConstants prefix, String... keys){
        ShardedJedis shardedJedis=null;
        try{
            String cacheKey = generateCacheKey(prefix, keys);
            shardedJedis = redisPool.instance();
            String value = shardedJedis.get(cacheKey);
            if(StringUtils.isNotBlank(value)){
                SysUser sysUser = JsonMapper.string2Obj(value, new TypeReference<SysUser>() {});
                sysUser.setErroCount(sysUser.getErroCount()+1);
            }

        }catch (Exception e){
            log.error("get from cache exception, prefix:{}, keys:{}", prefix.name(), JsonMapper.obj2String(keys), e);
        }finally {
            redisPool.safeClose(shardedJedis);
        }
    }



    public void delKey(CacheKeyConstants prefix, String... keys){
        ShardedJedis shardedJedis=null;
        try{
            String cacheKey = generateCacheKey(prefix, keys);
            shardedJedis = redisPool.instance();
            shardedJedis.del(cacheKey);
        }catch (Exception e){
            log.error("get from cache exception, prefix:{}, keys:{}", prefix.name(), JsonMapper.obj2String(keys), e);
        }finally {
            redisPool.safeClose(shardedJedis);
        }


    }
}

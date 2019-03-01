package scm.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import scm.datastore.DataStore;
import scm.model.Data;

import java.util.List;

@Component
public class APIProcessor {
   private static final Logger LOG = LoggerFactory.getLogger(APIProcessor.class);
   private static final String ALL_DATA_CACHE_NAME = "cache0";
   private static final String DATA_CACHE_NAME = "cache1";

   @Autowired
   private DataStore dataStore;

   @CacheEvict(value = ALL_DATA_CACHE_NAME, cacheManager = "cacheManager", key="#root.target.allDataCacheKey()")
   public Data create() {
      return dataStore.create();
   }

   // in SSMCache implementation, `allEntries` in `CacheEvict` will clear all cache on the physical (instead of logical) cache.
   // e.g. multiple caches (logical) are hosted on the same memcached cluster (physical cache)
   @Caching(evict = {
           @CacheEvict(value = ALL_DATA_CACHE_NAME, cacheManager = "cacheManager", key="#root.target.allDataCacheKey()"),
           @CacheEvict(value = DATA_CACHE_NAME, cacheManager = "cacheManager", key="#root.target.dataCacheKey(#dataId)")
   })
   public Data update(String dataId, Data data) {
      return dataStore.update(dataId, data);
   }

   @Caching(evict = {
           @CacheEvict(value = ALL_DATA_CACHE_NAME, cacheManager = "cacheManager", key="#root.target.allDataCacheKey()"),
           @CacheEvict(value = DATA_CACHE_NAME, cacheManager = "cacheManager", key="#root.target.dataCacheKey(#dataId)")
   })
   public void delete(String dataId) {
      dataStore.delete(dataId);
   }

   // https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#cache-spel-context
   @Cacheable(value = DATA_CACHE_NAME, cacheManager = "cacheManager", key = "#root.target.dataCacheKey(#dataId)", unless = "#result == null")
   public Data get(String dataId) {
      return dataStore.get(dataId);
   }

   @Cacheable(value = ALL_DATA_CACHE_NAME, cacheManager = "cacheManager", key = "#root.target.allDataCacheKey()")
   public List<Data> getAll() {
      LOG.info("msg=\"cache miss\"");
      return dataStore.getAll();
   }

   public static String allDataCacheKey() {
      return String.join(":", "allData");
   }

   public static String dataCacheKey(String id) {
      return String.join(":", "data", id);
   }
}

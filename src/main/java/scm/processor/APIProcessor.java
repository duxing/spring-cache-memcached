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
   private static final String CACHE_NAME = "cache0";
   public static final String ALL_DATA_CACHE_KEY = "all-data";

   @Autowired
   private DataStore dataStore;

   @CacheEvict(value = CACHE_NAME, cacheManager = "cacheManager", key="#root.target.ALL_DATA_CACHE_KEY")
   public Data create() {
      return dataStore.create();
   }

   // Using one `CacheEvict` alone is fine.
   // @CacheEvict(value = CACHE_NAME, cacheManager = "cacheManager", key="#root.target.dataCacheKey(#dataId)")
   @Caching(evict = {
           @CacheEvict(value = CACHE_NAME, cacheManager = "cacheManager", key="#root.target.ALL_DATA_CACHE_KEY"),
           @CacheEvict(value = CACHE_NAME, cacheManager = "cacheManager", key="#root.target.dataCacheKey(#dataId)")
   })
   public Data update(String dataId, Data data) {
      return dataStore.update(dataId, data);
   }

   @Caching(evict = {
           @CacheEvict(value = CACHE_NAME, cacheManager = "cacheManager", key="#root.target.ALL_DATA_CACHE_KEY"),
           @CacheEvict(value = CACHE_NAME, cacheManager = "cacheManager", key="#root.target.dataCacheKey(#dataId)")
   })
   public void delete(String dataId) {
      dataStore.delete(dataId);
   }

   // https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#cache-spel-context
   @Cacheable(value = CACHE_NAME, cacheManager = "cacheManager", key = "#root.target.dataCacheKey(#dataId)")
   public Data get(String dataId) {
      return dataStore.get(dataId);
   }

   @Cacheable(value = CACHE_NAME, cacheManager = "cacheManager", key = "#root.target.ALL_DATA_CACHE_KEY")
   public List<Data> getAll() {
      LOG.info("msg=\"cache miss\"");
      return dataStore.getAll();
   }

   public static String dataCacheKey(String id) {
      return "data:" + id;
   }
}

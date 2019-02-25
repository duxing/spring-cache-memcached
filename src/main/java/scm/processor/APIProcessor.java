package scm.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import scm.datastore.DataStore;
import scm.model.Data;

import java.util.Collection;

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

   @CacheEvict(value = CACHE_NAME, cacheManager = "cacheManager", key="#root.target.ALL_DATA_CACHE_KEY")
   public Data update(String id, Data data) {
      return dataStore.update(id, data);
   }

   @CacheEvict(value = CACHE_NAME, cacheManager = "cacheManager", key="#root.target.ALL_DATA_CACHE_KEY")
   public void delete(String dataId) {
      dataStore.delete(dataId);
   }

   public Data get(String dataId) {
      return dataStore.get(dataId);
   }

   @Cacheable(value = CACHE_NAME, cacheManager = "cacheManager", key = "#root.target.ALL_DATA_CACHE_KEY")
   public Collection<Data> getAll() {
      LOG.info("msg=\"cache miss\"");
      return dataStore.getAll();
   }
}

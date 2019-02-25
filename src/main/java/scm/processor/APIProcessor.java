package scm.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import scm.model.Data;

@Component
public class APIProcessor {
   private final Logger LOG = LoggerFactory.getLogger(APIProcessor.class);

   @Cacheable(value="cache0")
   public Data getData(String dataId) {
      LOG.info("msg=\"cache miss\" dataId=\"{}\"", dataId);
       // assume the body of this method has some io-intense / compute-intense operations.
      return new Data(dataId, "hello world!");
   }
}

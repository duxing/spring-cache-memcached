package scm.datastore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import scm.model.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class DataStore {
    private static final Logger LOG = LoggerFactory.getLogger(DataStore.class);
    private static final String DEFAULT_PAYLOAD = "";

    private final AtomicInteger counter = new AtomicInteger(0);
    private final Map<String, Data> index = new HashMap<>();

    public Data create() {
        String id = String.valueOf(counter.getAndIncrement());
        index.put(id, new Data(id, DEFAULT_PAYLOAD));
        return get(id);
    }

    public Data get(String id) {
        return index.get(id);
    }

    public Data update(String id, Data data) {
        if (!index.containsKey(id)) {
            return null;
        }
        Data i = index.get(id);
        i.setPayload(data.getPayload());

        return get(id);
    }

    public boolean delete(String id) {
        return index.remove(id) != null;
    }

    public List<Data> getAll() {
        return new ArrayList<>(index.values());
    }
}

package scm.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {
    private String id;
    private String payload;

    @JsonCreator
    public Data(
            @JsonProperty("id") String id,
            @JsonProperty("payload") String payload) {
        this.id = id;
        this.payload = payload;
    }

    public String getId() {
        return id;
    }

    public String getPayload() {
        return payload;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}

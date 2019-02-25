package scm.model;


public class Ping {
    private static final String status = "OK";
    private static final Ping IDENTITY = new Ping();

    public String getStatus() { return status; }

    public static Ping get() {
        return IDENTITY;
    }
}

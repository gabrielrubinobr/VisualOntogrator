package visual.jdbc.graph;

import visual.jdbc.util.Util;

import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.setPort;
import static spark.Spark.setIpAddress;

/**
 * @author Michael Hunger @since 22.10.13
 */
public class Server {

    public static void main(String[] args) {
        setPort(Util.getWebPort());
        setIpAddress("127.0.0.1");
        externalStaticFileLocation("src/main/webapp");
        final Service service = new Service(Util.getNeo4jUrl());
        new Routes(service).init();
    }
}

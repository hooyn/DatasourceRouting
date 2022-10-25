package hooyn.routing_datasource.routingconfig;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DatabaseSourceRouting extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DatabaseContextHolder.getDatabaseContext();
    }
}

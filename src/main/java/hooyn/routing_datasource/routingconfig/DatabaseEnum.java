package hooyn.routing_datasource.routingconfig;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DatabaseEnum {
    COMPANY01("company01"),
    COMPANY02("company02");

    private final String databaseSource;
}

package hooyn.routing_datasource.routingconfig;

import org.springframework.util.Assert;

public class DatabaseContextHolder {

    private static ThreadLocal<DatabaseEnum> CONTEXT = new ThreadLocal<>();

    public static void setDatabaseContext(DatabaseEnum databaseSource){
        Assert.notNull(databaseSource, "Routing database cannot be null");
        CONTEXT.set(databaseSource);
    }

    public static DatabaseEnum getDatabaseContext(){
        return CONTEXT.get();
    }

    public static void clearDatabaseContext(){
        CONTEXT.remove();
    }
}

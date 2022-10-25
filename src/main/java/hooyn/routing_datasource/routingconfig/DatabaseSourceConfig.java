package hooyn.routing_datasource.routingconfig;

import com.zaxxer.hikari.HikariDataSource;
import hooyn.routing_datasource.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "hooyn.routing_datasource.repository",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager"
)
public class DatabaseSourceConfig {

    @Bean
    @Primary
    public DataSource dataSource(){
        DatabaseSourceRouting databaseSourceRouting = new DatabaseSourceRouting();
        databaseSourceRouting.setTargetDataSources(targetDataSources());
        databaseSourceRouting.setDefaultTargetDataSource(company01DatabaseSource());
        return databaseSourceRouting;
    }

    private Map<Object, Object> targetDataSources(){
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatabaseEnum.COMPANY01, company01DatabaseSource());
        targetDataSources.put(DatabaseEnum.COMPANY02, company02DatabaseSource());
        return targetDataSources;
    }

    @Bean
    @ConfigurationProperties("spring.datasource.company01")
    public DataSourceProperties company01DatabaseSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean
    public DataSource company01DatabaseSource(){
        return company01DatabaseSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.company02")
    public DataSourceProperties company02DatabaseSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean
    public DataSource company02DatabaseSource(){
        return company02DatabaseSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource()).packages(User.class)
                .build();
    }

    @Bean(name = "transactionManager")
    public JpaTransactionManager transactionManager(
            @Autowired @Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return new JpaTransactionManager(entityManagerFactoryBean.getObject());
    }
}

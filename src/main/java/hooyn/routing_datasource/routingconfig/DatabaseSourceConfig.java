package hooyn.routing_datasource.routingconfig;


import com.zaxxer.hikari.HikariDataSource;
import hooyn.routing_datasource.domain.Memo;
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
        basePackages = "hooyn.routing_datasource",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager"
)
public class DatabaseSourceConfig {

    @Bean
    @Primary
    public DataSource dataSource(){
        DatabaseSourceRouting databaseSourceRouting = new DatabaseSourceRouting();
        databaseSourceRouting.setTargetDataSources(targetDataSources());
        databaseSourceRouting.setDefaultTargetDataSource(company02DataSource());
        return databaseSourceRouting;
    }

    private Map<Object, Object> targetDataSources(){
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatabaseEnum.COMPANY01, company01DataSource());
        targetDataSources.put(DatabaseEnum.COMPANY02, company02DataSource());
        return targetDataSources;
    }

    @Bean
    @ConfigurationProperties("spring.datasource.company01")
    public DataSourceProperties company01DatabaseSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean
    public DataSource company01DataSource(){
        return company01DatabaseSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.company02")
    public DataSourceProperties company02DataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean
    public DataSource company02DataSource(){
        return company02DataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
            EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource())
                .packages(User.class)
                .packages(Memo.class)
                .build();
    }

    @Bean(name = "transactionManager")
    public JpaTransactionManager transactionManager(
            @Autowired @Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return new JpaTransactionManager(entityManagerFactoryBean.getObject());
    }
}

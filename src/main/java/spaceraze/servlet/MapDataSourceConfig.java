package spaceraze.servlet;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "spaceraze.servlet.map.repository",
        entityManagerFactoryRef = "mapEntityManagerFactory",
        transactionManagerRef = "mapTransactionManager"
)
public class MapDataSourceConfig {

    @Bean(name = "mapDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.map")
    public DataSource mapDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mapEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mapEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("mapDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("spaceraze.map")
                .persistenceUnit("map")
                .build();
    }

    @Bean(name = "mapTransactionManager")
    public PlatformTransactionManager mapTransactionManager(
            @Qualifier("mapEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
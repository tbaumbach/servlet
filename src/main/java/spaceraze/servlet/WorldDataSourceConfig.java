package spaceraze.servlet;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "spaceraze.servlet.world.repository",
        entityManagerFactoryRef = "worldEntityManagerFactory",
        transactionManagerRef = "worldTransactionManager"
)
public class WorldDataSourceConfig {

    @Bean(name = "worldDataSource")
    @Primary // world som default-källa om något inte kvalificeras
    @ConfigurationProperties(prefix = "spring.datasource.world")
    public DataSource worldDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "worldEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean worldEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("worldDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("spaceraze.world")
                .persistenceUnit("world")
                .build();
    }

    @Bean(name = "worldTransactionManager")
    @Primary
    public PlatformTransactionManager worldTransactionManager(
            @Qualifier("worldEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
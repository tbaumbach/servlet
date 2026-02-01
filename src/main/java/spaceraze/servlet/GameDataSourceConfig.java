package spaceraze.servlet;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "spaceraze.servlet.game.repository",
        entityManagerFactoryRef = "gameEntityManagerFactory",
        transactionManagerRef = "gameTransactionManager"
)
public class GameDataSourceConfig {

    @Bean(name = "gameDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.game")
    public DataSource gameDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "gameEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean gameEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("gameDataSource") DataSource dataSource) {

        Map<String, Object> properties = new HashMap<>();
        // Sätt dialekt för SQL Server
        properties.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
        // Valfritt: låt Hibernate uppdatera schemat automatiskt
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");

        return builder
                .dataSource(dataSource)
                .packages("spaceraze.game")
                .persistenceUnit("game")
                .properties(properties)
                .build();
    }

    @Bean(name = "gameTransactionManager")
    public PlatformTransactionManager gameTransactionManager(
            @Qualifier("gameEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

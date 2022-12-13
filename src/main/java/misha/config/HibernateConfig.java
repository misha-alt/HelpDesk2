package misha.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "misha")
@EnableTransactionManagement
public class HibernateConfig {

    @Autowired
    private ApplicationContext context;

    @Bean
    public DataSource dataSource (){
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setType(EmbeddedDatabaseType.H2)
                .addScript("scripts/createTable.sql")
                .addScript("scripts/fillInTheTables.sql")
                .setScriptEncoding("UTF-8")
                .continueOnError(true)
                .ignoreFailedDrops(true)
                .build();
    }
    @Bean
    public LocalSessionFactoryBean sessionFactory(){
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("misha.domain");

        return sessionFactory;
    }
    @Bean
    public HibernateTransactionManager transactionManager (){
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
}


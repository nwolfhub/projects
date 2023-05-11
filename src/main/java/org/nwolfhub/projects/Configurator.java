package org.nwolfhub.projects;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.File;
import java.util.Properties;

@Configuration
public class Configurator {
    private static final org.nwolfhub.utils.Configurator configurator = new org.nwolfhub.utils.Configurator(false, new File("projects.cfg"));
    @Bean
    @Primary
    public static Properties getHibernateProps() {
        Properties prop = new Properties();
        prop.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        prop.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        prop.put("hibernate.connection.url", configurator.getValue("db_url"));
        prop.put("hibernate.connection.username", configurator.getValue("db_username"));
        prop.put("hibernate.connection.password", configurator.getValue("db_password"));
        prop.put("hibernate.current_session_context_class", "thread");
        prop.put("hibernate.connection.CharSet", "utf8");
        prop.put("hibernate.hbm2ddl.auto", "create-only");
        prop.put("hibernate.connection.characterEncoding", "utf8");
        prop.put("hibernate.connection.useUnicode", true);
        prop.put("hibernate.connection.pool_size", 100);
        return prop;
    }
}

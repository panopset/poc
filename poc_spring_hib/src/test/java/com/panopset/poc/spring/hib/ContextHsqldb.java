package com.panopset.poc.spring.hib;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springbyexample.orm.hibernate3.annotation.dao.PersonDao;
import org.springbyexample.orm.hibernate3.annotation.dao.PersonDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

/**
 * Attempt to extract XML H2 configuration, using this <a href=
 * "http://www.zabada.com/tutorials/hibernate-and-jpa-with-spring-example"
 * >tutorial</a>.
 * 
 * @author Karl Dinwiddie
 *
 */

@Configuration
public class ContextHsqldb {

    @Bean
    public PersonDao personDAO() {
        PersonDaoImpl pdi = new PersonDaoImpl();
        pdi.setSessionFactory(sessionFactory().getObject());
        return pdi;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriver(new org.hsqldb.jdbcDriver());
        ds.setUrl("jdbc:hsqldb:mem:contact");
        ds.setUsername("foo"); // I guess it doesn't matter in hsqldb.
        ds.setPassword("");
        ds.setValidationQuery("select 1 from INFORMATION_SCHEMA.SYSTEM_USERS");
        ds.setTestOnBorrow(true);
        return ds;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
        lsfb.setDataSource(dataSource());
        lsfb.getHibernateProperties().setProperty("hibernate.dialect",
                "org.hibernate.dialect.HSQLDialect");
        lsfb.getHibernateProperties().setProperty("hibernate.format_sql",
                "true");
        lsfb.setPackagesToScan(
                "org.springbyexample.orm.hibernate3.annotation.bean",
                "org.springbyexample.orm.hibernate3.annotation.dao");
        return lsfb;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager htm = new HibernateTransactionManager();
        htm.setSessionFactory(sessionFactory().getObject());
        return htm;
    }
}

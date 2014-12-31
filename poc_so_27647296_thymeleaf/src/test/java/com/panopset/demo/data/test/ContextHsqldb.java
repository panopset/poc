package com.panopset.demo.data.test;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import com.panopset.demo.dao.StoreDAO;
import com.panopset.demo.dao.StoreDAOImpl;

/**
 * For testing, we'll use hsqldb.
 *
 */
@Configuration
public class ContextHsqldb {

	@Bean
	public StoreDAO storeDAO() {
		StoreDAOImpl sdi = new StoreDAOImpl();
		sdi.setSessionFactory(sessionFactory().getObject());
		return sdi;
	}

	@Bean
	public DataSource dataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriver(new org.hsqldb.jdbcDriver());
		ds.setUrl("jdbc:hsqldb:mem:contact");
		ds.setUsername("foo");
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
		lsfb.setPackagesToScan("com.panopset.demo.bean","com.panopset.demo.dao");
		return lsfb;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager htm = new HibernateTransactionManager();
		htm.setSessionFactory(sessionFactory().getObject());
		return htm;
	}
}

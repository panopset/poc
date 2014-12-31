package com.panopset.demo.framing;

import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.mysql.jdbc.Driver;
import com.panopset.demo.dao.StoreDAO;
import com.panopset.demo.dao.StoreDAOImpl;

/**
 * Thymeleaf and language resource bundle configuration.
 * 
 * @author Karl Dinwiddie
 *
 */
@Configuration
public class DispatcherConfig extends WebMvcConfigurerAdapter {

	@Bean
	public TemplateResolver templateResolver() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/views/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		return templateEngine;
	}

	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setOrder(1);
		return viewResolver;
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("WEB-INF/nls/messages");
		messageSource.setUseCodeAsDefaultMessage(false);
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public StoreDAO storeDAO() {
		StoreDAOImpl sdi = new StoreDAOImpl();
		sdi.setSessionFactory(sessionFactory().getObject());
		return sdi;
	}

	/**
	 * Get the data source.
	 * 
	 * Here is how you create the table in mysql:
	 *
	 *
	 * <pre>
	 * 	 mysql -u root -p&lt;mysql_pwd&gt;
	 * 	 create database practick;
	 * 	  	 
	 * 	 CREATE TABLE practick.STORE (
	 * 	  ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	 * 	  NAME VARCHAR (64) NOT NULL,
	 * 	  TYPE VARCHAR (64) NOT NULL,
	 * 	  ADDRESS VARCHAR (256) NOT NULL
	 * 	 );
	 * 	 
	 * 	 INSERT INTO practick.STORE (NAME, TYPE, ADDRESS) 
	 * 	 values('StoreA','Retail','100 Northpoint');
	 * 	 
	 * 	 INSERT INTO practick.STORE (NAME, TYPE, ADDRESS)
	 * 	 values('StoreB','Warehouse','90 Blue Ravine');
	 * 
	 * </pre>
	 * 
	 * @return Data source for practick table.
	 */
	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource bds = new BasicDataSource();
		try {
			Driver driver = new Driver();
			bds.setDriver(driver);
			bds.setUrl("jdbc:mysql://localhost:3306/practick");
			bds.setUsername("root");
			// TODO: Get this from an encrypted store.
			bds.setPassword(System.getProperty("mysql_pwd"));
		} catch (SQLException e) {
			LoggerFactory.getLogger(this.getClass()).error(DRIVER_CLASS, e);
		}
		return bds;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
		lsfb.setDataSource(dataSource());
		lsfb.getHibernateProperties().setProperty("hibernate.dialect",
				"org.hibernate.dialect.MySQL5Dialect");
		lsfb.getHibernateProperties().setProperty("hibernate.show_sql", "true");
		lsfb.setPackagesToScan("com.panopset.demo.bean",
				"com.panopset.demo.dao");
		return lsfb;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager htm = new HibernateTransactionManager();
		htm.setSessionFactory(sessionFactory().getObject());
		return htm;
	}

	private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
}

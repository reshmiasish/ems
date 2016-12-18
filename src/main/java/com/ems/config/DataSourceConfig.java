package com.ems.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.ems.database.InMemoryMySQLDBBuilder;

/**
 * Class to handle DataSouce configuration and JPA transaction. 
 * @author reshmivn
 * @since 0.0.1
 */

@Configuration
@PropertySource(value={"classpath:hibernate.properties"})
public class DataSourceConfig {

	@Bean
	public DataSource dataSource() {
		// Creating MYSQL Inmemory database with the scripts present in the code. 
		return new InMemoryMySQLDBBuilder().addSqlScript("db/create-db.sql").addSqlScript("db/init.sql").build();
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setDataSource(dataSource());
		emfb.setJpaVendorAdapter(jpaVendorAdapter());
		emfb.setPackagesToScan("com.ems.model");
		emfb.setPersistenceUnitName("ems");
		return emfb;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setGenerateDdl(false);
		return adapter;
	}

}

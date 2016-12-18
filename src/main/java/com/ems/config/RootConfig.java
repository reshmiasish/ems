package com.ems.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Root Configuration. 
 * @author reshmivn
 * @since 0.0.1
 */

@Configuration
@ComponentScan("com.ems*")
@Import(DataSourceConfig.class)
@EnableTransactionManagement
public class RootConfig {

}

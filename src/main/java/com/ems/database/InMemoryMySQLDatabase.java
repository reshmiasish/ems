package com.ems.database;

import com.mysql.management.MysqldResource;
import org.apache.commons.io.FileUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * This is the custom in-memory database for MySQL
 * @author reshmivn
 * @since 0.0.1
 */
public class InMemoryMySQLDatabase extends DriverManagerDataSource {
    private final Logger logger = LoggerFactory.getLogger(InMemoryMySQLDatabase.class);
    private final MysqldResource mysqldResource;

    public InMemoryMySQLDatabase(MysqldResource mysqldResource) {
        this.mysqldResource = mysqldResource;
    }

    /**
     * Specifies the actions to be performed at the shutdown.
     */
    public void shutdown() {
        if (mysqldResource != null) {
            mysqldResource.shutdown();
            if (!mysqldResource.isRunning()) {
                logger.info("Purging MYSQL base directory [{}]", mysqldResource.getBaseDir());
                try {
                    FileUtils.forceDelete(mysqldResource.getBaseDir());
                } catch (IOException e) {
                	logger.error("Purging MYSQL base directory [{}] FAILED", mysqldResource.getBaseDir(), e);
                }
            }
        }
    }
}

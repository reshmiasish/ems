package com.ems.database;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.mysql.management.MysqldResource;
import com.mysql.management.MysqldResourceI;

public class InMemoryMySQLDBBuilder {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryMySQLDBBuilder.class);

    private final String baseDatabaseDir = System.getProperty("java.io.tmpdir");
    private String databaseName = "test_db_" + System.nanoTime();
    private final int port = new Random().nextInt(10000) + 3306;
    private final String username = "root";
    private final String password = "";
    private boolean foreignKeyCheck;

    private final ResourceLoader resourceLoader;
    private final ResourceDatabasePopulator databasePopulator;

    public InMemoryMySQLDBBuilder() {
        resourceLoader = new DefaultResourceLoader();
        databasePopulator = new ResourceDatabasePopulator();
        foreignKeyCheck = true;
    }

    private InMemoryMySQLDatabase createDatabase(MysqldResource mysqldResource) {
        if (!mysqldResource.isRunning()) {
            logger.error("MySQL instance not found... Can't porcced further, stopping the initialization.");
            throw new RuntimeException("Cannot get Datasource, MySQL instance not started.");
        }
        
        InMemoryMySQLDatabase database = new InMemoryMySQLDatabase(mysqldResource);
        database.setDriverClassName("com.mysql.jdbc.Driver");
        database.setUsername(username);
        database.setPassword(password);
        String url = "jdbc:mysql://localhost:" + port + "/" + databaseName + "?" + "createDatabaseIfNotExist=true";

        if (!foreignKeyCheck) {
            url += "&sessionVariables=FOREIGN_KEY_CHECKS=0";
        }
        
        logger.debug("Database url: {}", url);
        database.setUrl(url);
        return database;
    }

    private MysqldResource createMysqldResource() {
        if (logger.isDebugEnabled()) {
            logger.debug("=============== Starting InMemoey MySQL using these parameters ===============");
            logger.debug("baseDatabaseDir : " + baseDatabaseDir);
            logger.debug("databaseName : " + databaseName);
            logger.debug("host : localhost (hardcoded)");
            logger.debug("port : " + port);
            logger.debug("username : root (hardcode)");
            logger.debug("password : (no password)");
            logger.debug("=============================================================================");
        }

        Map<String, String> databaseOptions = new HashMap<String, String>();
        databaseOptions.put(MysqldResourceI.PORT, Integer.toString(port));

        MysqldResource mysqldResource = new MysqldResource(new File(baseDatabaseDir, databaseName));
        mysqldResource.start("embedded-mysqld-thread-" + System.currentTimeMillis(), databaseOptions);

        if (!mysqldResource.isRunning()) {
        	logger.error("Can't fins MySQL instance... Can't porcced further, stopping the initialization.");
            throw new RuntimeException("MySQL did not start.");
        }

        logger.info("MySQL started successfully @ {}", System.currentTimeMillis());
        return mysqldResource;
    }

    private void populateScripts(InMemoryMySQLDatabase database) {
        try {
            DatabasePopulatorUtils.execute(databasePopulator, database);
        } catch (Exception e) {
            logger.error("Error while loadng the DB scripts - {}", e.getMessage(), e);
            database.shutdown();
        }
    }

    public InMemoryMySQLDBBuilder addSqlScript(String script) {
        databasePopulator.addScript(resourceLoader.getResource(script));
        return this;
    }

    /**
     * whether to enable mysql foreign key check
     *
     * @param foreignKeyCheck
     */
    public InMemoryMySQLDBBuilder setForeignKeyCheck(boolean foreignKeyCheck) {
        this.foreignKeyCheck = foreignKeyCheck;
        return this;
    }

    /**
     * @param databaseName
     *            the databaseName to set
     */
    public final void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public InMemoryMySQLDatabase build() {
        MysqldResource mysqldResource = createMysqldResource();
        InMemoryMySQLDatabase database = createDatabase(mysqldResource);
        populateScripts(database);
        return database;
    }
}
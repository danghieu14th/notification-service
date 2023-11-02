package com.example.migration;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;

@SpringBootApplication
public class MigrationApplication implements CommandLineRunner {
    private final ResourceLoader resourceLoader;

    private final ApplicationContext applicationContext;
    public MigrationApplication(ResourceLoader resourceLoader, ApplicationContext applicationContext) {
        this.resourceLoader = resourceLoader;
        this.applicationContext = applicationContext;
    }

    public static void main(String[] args) {
        SpringApplication.run(MigrationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(Arrays.toString(args));
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        Connection connection = dataSource.getConnection();
        Database db = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(
                new JdbcConnection(
                        connection
                        ));
        Liquibase liquibase = new Liquibase(resourceLoader.getResource("resources/changelog-master.xml").getFilename(), new ClassLoaderResourceAccessor(), db);
        Contexts contexts = new Contexts();
        if(args.length !=0 && args[0] != null &&  args[0].split("=")[0].equals("rollback")) {
            String tag = args[0].split("=")[1];
            liquibase.rollback(tag, contexts);
        }
        System.exit(0);
    }
}

package org.example.config;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class Liquibase {


    public static void init() throws SQLException, DatabaseException {
        Properties properties = new Properties();

        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(DataSource.getInstance().getConnection()));
        try (liquibase.Liquibase liquibase = new liquibase.Liquibase("changeLog/db.changelog.yml", new ClassLoaderResourceAccessor(), database)) {
            properties.forEach((key, value) -> liquibase.setChangeLogParameter(Objects.toString(key), value));
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }
}

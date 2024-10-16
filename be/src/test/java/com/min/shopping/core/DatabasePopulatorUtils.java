package com.min.shopping.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabasePopulatorUtils {

    private static final Logger log = LoggerFactory.getLogger(DatabasePopulatorUtils.class);
    private static final String DATA_SQL = "data.sql";

    public static void initData(final DataSource dataSource) {
        executeBySQLFilePath(dataSource, DATA_SQL);
    }

    private static void executeBySQLFilePath(final DataSource dataSource, final String schemaSql) {
        try {
            final var url = DatabasePopulatorUtils.class.getClassLoader().getResource(schemaSql);
            final var file = new File(url.getFile());
            final var sql = Files.readString(file.toPath());
            final Connection connection = dataSource.getConnection();
            final Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (final NullPointerException | IOException | SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    private DatabasePopulatorUtils() {
    }
}

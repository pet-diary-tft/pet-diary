package com.petdiary.domain.rdscore;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

@Slf4j
public class NoOpDataSource implements DataSource {
    private static final String WARN_MESSAGE = "Please check the data source settings!!";
    private static final String NO_OP_MESSAGE = "No operation data source.";
    
    @Override
    public Connection getConnection() throws SQLException {
        log.error(String.format("%s %s", NO_OP_MESSAGE, WARN_MESSAGE));
        throw new UnsupportedOperationException(NO_OP_MESSAGE);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        log.error(String.format("%s %s", NO_OP_MESSAGE, WARN_MESSAGE));
        throw new UnsupportedOperationException(NO_OP_MESSAGE);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        log.error(String.format("%s %s", NO_OP_MESSAGE, WARN_MESSAGE));
        throw new UnsupportedOperationException(NO_OP_MESSAGE);
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        log.error(String.format("%s %s", NO_OP_MESSAGE, WARN_MESSAGE));
        throw new UnsupportedOperationException(NO_OP_MESSAGE);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        log.error(String.format("%s %s", NO_OP_MESSAGE, WARN_MESSAGE));
        throw new UnsupportedOperationException(NO_OP_MESSAGE);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        log.error(String.format("%s %s", NO_OP_MESSAGE, WARN_MESSAGE));
        throw new UnsupportedOperationException(NO_OP_MESSAGE);
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        log.error(String.format("%s %s", NO_OP_MESSAGE, WARN_MESSAGE));
        throw new UnsupportedOperationException(NO_OP_MESSAGE);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        log.error(String.format("%s %s", NO_OP_MESSAGE, WARN_MESSAGE));
        throw new UnsupportedOperationException(NO_OP_MESSAGE);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        log.error(String.format("%s %s", NO_OP_MESSAGE, WARN_MESSAGE));
        throw new UnsupportedOperationException(NO_OP_MESSAGE);
    }
}

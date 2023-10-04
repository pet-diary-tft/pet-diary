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
    private static final String NO_OP_MESSAGE = "[NoOpDataSource] No operation data source.";
    private static final String WARN_MESSAGE = "Please check the data source settings!!";
    
    @Override
    public Connection getConnection() throws SQLException {
        log.error(String.format("%s %s - getConnection", NO_OP_MESSAGE, WARN_MESSAGE));
        throw new UnsupportedOperationException(NO_OP_MESSAGE);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        log.error(String.format("%s %s - getConnection with username, password", NO_OP_MESSAGE, WARN_MESSAGE));
        throw new UnsupportedOperationException(NO_OP_MESSAGE);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        log.error(String.format("%s %s - getLogWriter", NO_OP_MESSAGE, WARN_MESSAGE));
        throw new UnsupportedOperationException(NO_OP_MESSAGE);
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        log.error(String.format("%s %s - setLogWriter", NO_OP_MESSAGE, WARN_MESSAGE));
        throw new UnsupportedOperationException(NO_OP_MESSAGE);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        log.error(String.format("%s %s - setLoginTimeout", NO_OP_MESSAGE, WARN_MESSAGE));
        throw new UnsupportedOperationException(NO_OP_MESSAGE);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        log.error(String.format("%s %s - getLoginTimeout", NO_OP_MESSAGE, WARN_MESSAGE));
        throw new UnsupportedOperationException(NO_OP_MESSAGE);
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        log.error(String.format("%s %s - getParentLogger", NO_OP_MESSAGE, WARN_MESSAGE));
        throw new UnsupportedOperationException(NO_OP_MESSAGE);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        log.error(String.format("%s %s - get unwrap with class<T>", NO_OP_MESSAGE, WARN_MESSAGE));
        throw new UnsupportedOperationException(NO_OP_MESSAGE);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        log.error(String.format("%s %s - get isWrapperFor with class<T>", NO_OP_MESSAGE, WARN_MESSAGE));
        throw new UnsupportedOperationException(NO_OP_MESSAGE);
    }
}

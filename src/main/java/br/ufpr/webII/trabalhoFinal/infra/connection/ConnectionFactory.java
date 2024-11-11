package br.ufpr.webII.trabalhoFinal.infra.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionFactory {

    @Autowired
    DataSource dataSource;

    @Bean
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}

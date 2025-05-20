package com.java.reto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnectionTester implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseConnectionTester(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            String result = jdbcTemplate.queryForObject("SELECT 'Connection successful!' as result", String.class);
            System.out.println("*************************************");
            System.out.println(result);
            System.out.println("*************************************");
        } catch (Exception e) {
            System.out.println("*************************************");
            System.out.println("Database Connection Error: " + e.getMessage());
            System.out.println("*************************************");
            e.printStackTrace();
        }
    }
}
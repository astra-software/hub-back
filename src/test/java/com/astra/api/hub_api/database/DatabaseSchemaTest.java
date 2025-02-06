package com.astra.api.hub_api.database;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
public class DatabaseSchemaTest {

    @SuppressWarnings("resource")
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
        .withDatabaseName("hub_db")
        .withUsername("test")
        .withPassword("test");


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
    }

    @Autowired
    private DataSource dataSource;

    // [F]: Métodos que avaliam se as tabelas estão sendo criadas corretamente
    @Test
    void testFeedbackTableExists() throws Exception{
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT table_name FROM information_schema.tables WHERE table_name = 'feedback'")) {
            assertTrue(rs.next(), "Tabela 'feedback' deveria existir.");
        }
    }

    @Test
    void testFeedbackLabelTableExists() throws Exception{
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT table_name FROM information_schema.tables WHERE table_name = 'feedback_label'")) 
                     {
            assertTrue(rs.next(), "Tabela 'feedback_label' deveria existir.");
        }
    }

    @Test
    void testFeedbackLabelLinkTableExists() throws Exception{
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT table_name FROM information_schema.tables WHERE table_name = 'feedback_label_link'")) {
            assertTrue(rs.next(), "Tabela 'feedback_label_link' deveria existir.");
        }
    }

    @Test
    void testUserFavoriteProjectsTableExists() throws Exception{
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT table_name FROM information_schema.tables WHERE table_name = 'user_favorite_projects'")) {
            assertTrue(rs.next(), "Tabela 'user_favorite_projects' deveria existir.");
        }
    }
    @Test
    void testUserFavoriteArticlesTableExists() throws Exception{
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT table_name FROM information_schema.tables WHERE table_name = 'user_favorite_articles'")) {
            assertTrue(rs.next(), "Tabela 'user_favorite_articles' deveria existir.");
        }
    }

    

}

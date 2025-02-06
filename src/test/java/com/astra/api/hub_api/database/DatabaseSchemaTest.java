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
    @Test
    void testArticlesTableExists() throws Exception{
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT table_name FROM information_schema.tables WHERE table_name = 'article'")) {
            assertTrue(rs.next(), "Tabela 'article' deveria existir.");
        }
    }
    @Test
    void testProjectsTableExists() throws Exception{
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT table_name FROM information_schema.tables WHERE table_name = 'project'")) {
            assertTrue(rs.next(), "Tabela 'project' deveria existir.");
        }
    }
    @Test
    void testImagesTableExists() throws Exception{
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT table_name FROM information_schema.tables WHERE table_name = 'image'")) {
            assertTrue(rs.next(), "Tabela 'image' deveria existir.");
        }
    }
    @Test
    void testUsersTableExists() throws Exception{
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT table_name FROM information_schema.tables WHERE table_name = 'users'")) {
            assertTrue(rs.next(), "Tabela 'users' deveria existir.");
        }
    }
    @Test
    void testPermissionTableExists() throws Exception{
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT table_name FROM information_schema.tables WHERE table_name = 'permission'")) {
            assertTrue(rs.next(), "Tabela 'permission' deveria existir.");
        }
    }
    @Test
    void testUserPermissionsTableExists() throws Exception{
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT table_name FROM information_schema.tables WHERE table_name = 'user_permission_link'")) {
            assertTrue(rs.next(), "Tabela 'user_permission_link' deveria existir.");
        }
    }
    @Test
    void testUtilsTableExists() throws Exception{
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT table_name FROM information_schema.tables WHERE table_name = 'util'")) {
            assertTrue(rs.next(), "Tabela 'util' deveria existir.");
        }
    }
    @Test
    void testDocumentationPageTableExists() throws Exception{
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT table_name FROM information_schema.tables WHERE table_name = 'documentation_page'")) {
            assertTrue(rs.next(), "Tabela 'documentation_page' deveria existir.");
        }
    }
    @Test
    void testDocumentationTopicTableExists() throws Exception{
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT table_name FROM information_schema.tables WHERE table_name = 'documentation_topic'")) {
            assertTrue(rs.next(), "Tabela 'documentation_topic' deveria existir.");
        }
    }
    @Test
    void testCategoriesTableExists() throws Exception{
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT table_name FROM information_schema.tables WHERE table_name = 'category'")) {
            assertTrue(rs.next(), "Tabela 'category' deveria existir.");
        }
    }
    @Test
    void testArticleCategoriesLinkTableExists() throws Exception{
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT table_name FROM information_schema.tables WHERE table_name = 'article_category_link'")) {
            assertTrue(rs.next(), "Tabela 'article_category_link' deveria existir.");
        }
    }
    @Test
    void testProjectCategoriesLinkTableExists() throws Exception{
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT table_name FROM information_schema.tables WHERE table_name = 'project_category_link'")) {
            assertTrue(rs.next(), "Tabela 'project_category_link' deveria existir.");
        }
    }

    

}

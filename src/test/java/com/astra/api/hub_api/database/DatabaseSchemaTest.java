package com.astra.api.hub_api.database;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
// import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.astra.api.hub_api.emodel.FeedbackType;
import com.astra.api.hub_api.emodel.StructureDenominator;
import com.astra.api.hub_api.model.Feedback;
// import com.astra.api.hub_api.model.FeedbackLabel;
// import com.astra.api.hub_api.model.Permission;
import com.astra.api.hub_api.model.User;
import com.astra.api.hub_api.repository.FeedbackRepository;
// import com.astra.api.hub_api.repository.PermissionRepository;
import com.astra.api.hub_api.repository.UserRepository;


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

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;
    
    // @Autowired
    // private PermissionRepository permissionRepository;

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
    void testUserFeedbackRelationship(){
        User user = new User();
        user.setUserName("test");
        User savedUser = userRepository.save(user);

        Feedback feedback = new Feedback();
        feedback.setUser(savedUser);
        feedback.setStructureDenominator(StructureDenominator.ARTICLE);
        feedback.setType(FeedbackType.POSITIVE);
        feedback.setContent("test");
        feedback.setTimestamp(LocalDateTime.now());

        Feedback savedFeedback = feedbackRepository.save(feedback);
        User searchUserRepo = userRepository.findById(savedUser.getId()).orElseThrow();
        
        assertThat(feedbackRepository.findAll()).hasSize(1);
        assertThat(searchUserRepo.getFeedbacks()).hasSize(1);
        assertTrue(searchUserRepo.getFeedbacks().getFirst().getContent().equals(savedFeedback.getContent()));
    }
}

package com.example.backend.config;
import org.bson.Document;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MongoHealthIndicator implements HealthIndicator {

    private final MongoTemplate mongoTemplate;

    public MongoHealthIndicator(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Health health() {
        try {
            mongoTemplate.getDb().runCommand(new Document("ping", 1));
            System.out.println("DB connected.");
            return Health.up().build();
        } catch (Exception e) {
            System.out.println("DB Error: " + e.getMessage());
            return Health.down().withDetail("Error", e.getMessage()).build();
        }
    }
}

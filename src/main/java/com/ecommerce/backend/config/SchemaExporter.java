package com.ecommerce.backend.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.CommandLineRunner;


@Configuration
public class SchemaExporter {

    private final EntityManagerFactory entityManagerFactory;

    public SchemaExporter(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public CommandLineRunner generateSchema() {
        return args -> {
            Map<String, Object> properties = new HashMap<>();
            properties.put("javax.persistence.schema-generation.scripts.action", "create");
            properties.put("javax.persistence.schema-generation.scripts.create-target", "init.sql");

            entityManagerFactory.getProperties().putAll(properties);
            System.out.println("✅ init.sql file should be generated.");
        };
    }
}


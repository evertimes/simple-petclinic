package ru.simple.petclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestPetclinicApplication {

    @Bean
    @ServiceConnection
    //Можно заюзать тестконтейнеры, но в них неудобно залезать через dbeaver.
    PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
    }

    public static void main(String[] args) {
        SpringApplication.from(PetclinicApplication::main).with(TestPetclinicApplication.class).run(args);
    }

}
